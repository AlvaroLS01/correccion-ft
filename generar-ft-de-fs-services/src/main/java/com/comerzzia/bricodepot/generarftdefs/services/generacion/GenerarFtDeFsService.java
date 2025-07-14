package com.comerzzia.bricodepot.generarftdefs.services.generacion;

import com.comerzzia.bricodepot.generarftdefs.persistence.ProveedorConexion;
import com.comerzzia.bricodepot.generarftdefs.persistence.TicketsDao;

import java.io.BufferedReader;
import java.io.IOException;
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

    /**
     * Procesa un fichero CSV con las correcciones necesarias.
     *
     * @param reader CSV con encabezado uid_ticket_original,total_original,uid_ticket_convertido,total_convertido
     */
    public void procesarCsv(BufferedReader reader) {
        Connection conexion = null;
        try {
            conexion = proveedorConexion.obtenerConexion();

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

                try {
                    Document fs = obtenerTicket(conexion, uidFs);
                    Document ft = obtenerTicket(conexion, uidFt);
                    if (fs != null && ft != null) {
                        Document combinado = combinarTickets(fs, ft);
                        String xmlCorregido = marshalTicket(combinado);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
                        TicketsDao.eliminarAlbaran(conexion, uidActividad, uidFt);
                    }
                } catch (Exception e) {
                    log.error("Error procesando la fila para {}: {}", uidFt, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error("Error procesando CSV: " + e.getMessage(), e);
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
