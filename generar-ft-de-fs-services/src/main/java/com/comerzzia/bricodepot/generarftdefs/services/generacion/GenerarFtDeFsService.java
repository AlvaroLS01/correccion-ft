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
            log.info("Conectando a la base de datos...");
            conexion = proveedorConexion.obtenerConexion();
            conexion.setAutoCommit(false);
            log.info("Conexión establecida.");

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
                log.info("Aplicando la corrección del ticket FS " + uidFs + " al ticket FT " + uidFt + "...");

                try {
                    Document fs = obtenerTicket(conexion, uidFs);
                    String xmlFtOriginal = obtenerTicketXml(conexion, uidFt);
                    Document ft = xmlFtOriginal != null ? parseXml(xmlFtOriginal) : null;
                    log.info("Tickets cargados correctamente.");
                    if (fs != null && ft != null) {
                        guardarXmlAntiguo(uidFt, xmlFtOriginal);
                        Document combinado = combinarTickets(fs, ft);
                        log.info("Información de los tickets combinada.");
                        String xmlCorregido = marshalTicket(combinado);
                        TicketsDao.eliminarAlbaran(conexion, uidActividad, uidFt);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
                        conexion.commit();
                        log.info("Ticket " + uidFt + " actualizado.");
                    }
                } catch (Exception e) {
                    log.error("procesarCsv() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
                    log.info("Ha ocurrido un problema. Deshaciendo los cambios.");
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
                    log.info("Cerrando la conexión con la base de datos.");
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
        log.info("Buscando el ticket " + uidTicket + " en la base de datos...");
        ResultSet rs = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);
        String xml = null;
        if (rs.next()) {
            xml = rs.getString("ticket");
            xml = sanitizeXml(xml);
            log.info("Ticket " + uidTicket + " encontrado.");
        } else {
            log.info("No se ha encontrado el ticket " + uidTicket + ".");
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
            log.info("No se ha encontrado el XML.");
            return null;
        }
        log.info("Leyendo el XML.");
        XMLDocument xmlDocument = new XMLDocument(xml.getBytes(StandardCharsets.UTF_8));
        return xmlDocument.getDocument();
    }

    private void guardarXmlAntiguo(String uidFt, String xmlFt) {
        if (xmlFt == null) {
            log.info("No hay XML para " + uidFt + ".");
            return;
        }
        try {
            Path dir = Paths.get(xmlBackupDir);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
                log.info("Se ha creado la carpeta " + dir.toString() + " para guardar el XML.");
            }
            Path ruta = dir.resolve(uidFt + ".xml");
            Files.write(ruta, xmlFt.getBytes(StandardCharsets.UTF_8));
            log.info("XML guardado en " + ruta.toString() + ".");
        } catch (IOException e) {
            log.error("guardarXmlAntiguo() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
        }
    }
}
