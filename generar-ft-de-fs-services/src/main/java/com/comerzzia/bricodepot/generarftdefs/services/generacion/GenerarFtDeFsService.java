package com.comerzzia.bricodepot.generarftdefs.services.generacion;

import com.comerzzia.bricodepot.generarftdefs.persistence.ProveedorConexion;
import com.comerzzia.bricodepot.generarftdefs.persistence.TicketsDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
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

    @Value("${comerzzia.xml.backup-path:../xml_antiguos}")
    private String backupPath;

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
                    Document ft = obtenerTicket(conexion, uidFt);
                    log.debug("procesarCsv() - Tickets obtenidos");
                    if (fs != null && ft != null) {
                        backupXml(uidFt, ft);
                        Document combinado = combinarTickets(fs, ft);
                        log.debug("procesarCsv() - Tickets combinados");
                        String xmlCorregido = marshalTicket(combinado);
                        TicketsDao.eliminarAlbaran(conexion, uidActividad, uidFt);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
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
        ResultSet rs = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);
        Document doc = null;
        if (rs.next()) {
            String xml = rs.getString("ticket");
            xml = sanitizeXml(xml);
            XMLDocument xmlDocument = new XMLDocument(xml.getBytes(StandardCharsets.UTF_8));
            doc = xmlDocument.getDocument();
        }
        rs.close();
        return doc;
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

    private void backupXml(String uid, Document ft) {
        try {
            String xml = marshalTicket(ft);
            File dir = new File(backupPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File fichero = new File(dir, uid + ".xml");
            try (FileWriter writer = new FileWriter(fichero)) {
                writer.write(xml);
            }
            log.debug("backupXml() - Ticket " + uid + " guardado en " + fichero.getAbsolutePath());
        } catch (IOException | TransformerException e) {
            log.error("backupXml() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
        }
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
}
