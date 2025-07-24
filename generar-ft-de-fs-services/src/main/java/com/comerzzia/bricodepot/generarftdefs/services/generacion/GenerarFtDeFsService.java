package com.comerzzia.bricodepot.generarftdefs.services.generacion;

import com.comerzzia.bricodepot.generarftdefs.persistence.ProveedorConexion;
import com.comerzzia.bricodepot.generarftdefs.persistence.TicketsDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.comerzzia.core.util.xml.XMLDocument;
import com.comerzzia.core.util.xml.XMLDocumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerarFtDeFsService {

    private static final Logger log = LoggerFactory.getLogger(GenerarFtDeFsService.class);

    @Autowired
    private ProveedorConexion proveedorConexion;

    @Value("${comerzzia.uid_actividad}")
    private String uidActividad;

    @Value("${comerzzia.xml.backup.dir:../XMLantiguos}")
    private String xmlBackupDir;

    /**
     * Procesa un fichero CSV con las correcciones necesarias.
     *
     * @param reader CSV con encabezado uid_ticket_original,total_original,uid_ticket_convertido,total_convertido
     */
    public void procesarCsv(BufferedReader reader) {
        Connection conexion = null;
        try {
            log.debug("procesarCsv() - Obteniendo conexión a base de datos");
            conexion = proveedorConexion.obtenerConexion();
            conexion.setAutoCommit(false);
            log.debug("procesarCsv() - Conexión establecida");

            String linea = reader.readLine();
            // Saltamos cabecera
            if (linea == null) {
                return;
            }
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 4) {
                    continue;
                }
                String uidFs = partes[0].trim();
                String uidFt = partes[2].trim();
                log.debug("procesarCsv() - Procesando corrección FS " + uidFs + " FT " + uidFt);

                try {
                    Document fs = obtenerTicket(conexion, uidFs);
                    String xmlFtOriginal = obtenerTicketXml(conexion, uidFt);
                    Document ft = xmlFtOriginal != null ? parseXml(xmlFtOriginal) : null;
                    log.debug("procesarCsv() - Tickets obtenidos");
                    if (fs != null && ft != null) {
                        guardarXmlAntiguo(uidFt, xmlFtOriginal);
                        Document combinado = combinarTickets(fs, ft);
                        log.debug("procesarCsv() - Tickets combinados");
                        String uidDiarioCajaFs = obtenerUidDiarioCaja(fs);
                        String uidDiarioCajaFt = obtenerUidDiarioCaja(ft);
                        String xmlCorregido = marshalTicket(combinado);
                        TicketsDao.eliminarAlbaran(conexion, uidActividad, uidFt);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
                        copiarMovimientos(conexion, uidDiarioCajaFs, uidDiarioCajaFt, uidFs, uidFt);
                        conexion.commit();
                        log.debug("procesarCsv() - Ticket " + uidFt + " actualizado");
                    }
                } catch (Exception e) {
                    log.error("procesarCsv() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
                    log.debug("procesarCsv() - Revirtiendo transacción");
                    try {
                        if (conexion != null) {
                            conexion.rollback();
                        }
                    } catch (SQLException ex) {
                        log.error("procesarCsv() - " + ex.getClass().getName() + " - " + ex.getLocalizedMessage(), ex);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            log.error("procesarCsv() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
        } finally {
            if (conexion != null) {
                try {
                    log.debug("procesarCsv() - Cerrando conexión");
                    conexion.close();
                } catch (SQLException e) {
                    log.error("procesarCsv() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
                }
            }
        }
    }

    private Document obtenerTicket(Connection conexion, String uidTicket)
            throws SQLException, XMLDocumentException {
        String xml = obtenerTicketXml(conexion, uidTicket);
        if (xml == null) {
            return null;
        }
        XMLDocument xmlDocument = new XMLDocument(xml.getBytes(StandardCharsets.UTF_8));
        return xmlDocument.getDocument();
    }

    private String obtenerTicketXml(Connection conexion, String uidTicket) throws SQLException {
        log.debug("obtenerTicketXml() - Consultando ticket " + uidTicket);
        ResultSet rs = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);
        String xml = null;
        if (rs.next()) {
            xml = rs.getString("ticket");
            xml = sanitizeXml(xml);
            log.debug("obtenerTicketXml() - Ticket " + uidTicket + " encontrado");
        } else {
            log.debug("obtenerTicketXml() - Ticket " + uidTicket + " no encontrado");
        }
        rs.close();
        return xml;
    }

    private String marshalTicket(Document ticket) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(ticket), new StreamResult(writer));
        return writer.toString();
    }

    /**
     * Combina el ticket FS con la cabecera y datos de facturación del ticket FT.
     *
     * @return Documento con el contenido fusionado
     */
    private Document combinarTickets(Document fs, Document ft) {
        Element cabeceraFt = (Element) ft.getElementsByTagName("cabecera").item(0);
        Element cabeceraFs = (Element) fs.getElementsByTagName("cabecera").item(0);
        if (cabeceraFt != null && cabeceraFs != null) {
            Node importado = fs.importNode(cabeceraFt, true);
            cabeceraFs.getParentNode().replaceChild(importado, cabeceraFs);
        }
        return fs;
    }

    private String obtenerUidDiarioCaja(Document doc) {
        NodeList nodes = doc.getElementsByTagName("uid_diario_caja");
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private void copiarMovimientos(Connection conexion, String uidDiarioCajaFs, String uidDiarioCajaFt, String uidFs, String uidFt)
            throws SQLException {
        if (uidDiarioCajaFs == null || uidDiarioCajaFt == null) {
            return;
        }

        List<Movimiento> movFs = new ArrayList<>();
        ResultSet rs = TicketsDao.consultarMovimientosCaja(conexion, uidActividad, uidDiarioCajaFs, uidFs);
        while (rs.next()) {
            Movimiento m = new Movimiento();
            m.linea = rs.getInt("linea");
            m.fecha = rs.getTimestamp("fecha");
            m.cargo = rs.getBigDecimal("cargo");
            m.abono = rs.getBigDecimal("abono");
            m.concepto = rs.getString("concepto");
            m.documento = rs.getString("documento");
            m.codmedpag = rs.getString("codmedpag");
            m.codconceptoMov = rs.getString("codconcepto_mov");
            Object o = rs.getObject("id_tipo_documento");
            m.idTipoDocumento = o != null ? rs.getLong("id_tipo_documento") : null;
            m.uidTransaccionDet = rs.getString("uid_transaccion_det");
            m.coddivisa = rs.getString("coddivisa");
            m.tipoDeCambio = rs.getBigDecimal("tipo_de_cambio");
            m.usuario = rs.getString("usuario");
            movFs.add(m);
        }
        rs.close();

        // Obtenemos los movimientos actuales de la factura para conocer el último número de línea
        List<Movimiento> movFt = new ArrayList<>();
        rs = TicketsDao.consultarMovimientosCaja(conexion, uidActividad, uidDiarioCajaFt, uidFt);
        while (rs.next()) {
            Movimiento m = new Movimiento();
            m.linea = rs.getInt("linea");
            movFt.add(m);
        }
        rs.close();

        // Borramos los movimientos existentes de la factura
        TicketsDao.borrarMovimientosCaja(conexion, uidActividad, uidDiarioCajaFt, uidFt);

        // Numero de linea máximo existente tras borrar
        int lineaActual = TicketsDao.obtenerMaxLineaCaja(conexion, uidActividad, uidDiarioCajaFt);

        // Insertamos todos los movimientos del ticket FS al final
        for (Movimiento src : movFs) {
            lineaActual++;
            TicketsDao.insertarMovimientoCaja(conexion, uidActividad, uidDiarioCajaFt, lineaActual, src.fecha,
                    src.cargo, src.abono, src.concepto, src.documento, src.codmedpag, uidFt, src.codconceptoMov,
                    src.idTipoDocumento, src.uidTransaccionDet, src.coddivisa, src.tipoDeCambio, src.usuario);
        }
    }

    private static class Movimiento {
        int linea;
        java.sql.Timestamp fecha;
        java.math.BigDecimal cargo;
        java.math.BigDecimal abono;
        String concepto;
        String documento;
        String codmedpag;
        String codconceptoMov;
        Long idTipoDocumento;
        String uidTransaccionDet;
        String coddivisa;
        java.math.BigDecimal tipoDeCambio;
        String usuario;
    }

    private String sanitizeXml(String xml) {
        if (xml == null) {
            return null;
        }
        xml = xml.replace("<giftcards/>", "");
        byte[] bytes = xml.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private Document parseXml(String xml) throws XMLDocumentException {
        if (xml == null) {
            log.debug("parseXml() - XML nulo");
            return null;
        }
        log.debug("parseXml() - Parseando XML");
        XMLDocument xmlDocument = new XMLDocument(xml.getBytes(StandardCharsets.UTF_8));
        return xmlDocument.getDocument();
    }

    private void guardarXmlAntiguo(String uidFt, String xmlFt) {
        if (xmlFt == null) {
            log.debug("guardarXmlAntiguo() - XML nulo para " + uidFt);
            return;
        }
        try {
            Path dir = Paths.get(xmlBackupDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
                log.debug("guardarXmlAntiguo() - Directorio creado " + dir.toString());
            }
            Path ruta = dir.resolve(uidFt + ".xml");
            Files.write(ruta, xmlFt.getBytes(StandardCharsets.UTF_8));
            log.debug("guardarXmlAntiguo() - XML guardado en " + ruta.toString());
        } catch (IOException e) {
            log.error("guardarXmlAntiguo() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
        }
    }
}