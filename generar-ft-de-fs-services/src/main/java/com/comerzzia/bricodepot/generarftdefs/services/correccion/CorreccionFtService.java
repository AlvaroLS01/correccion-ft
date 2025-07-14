package com.comerzzia.bricodepot.generarftdefs.services.correccion;

import com.comerzzia.bricodepot.generarftdefs.persistence.ProveedorConexion;
import com.comerzzia.bricodepot.generarftdefs.persistence.TicketsDao;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CorreccionFtService {

    private static final Logger log = LoggerFactory.getLogger(CorreccionFtService.class);

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
                String[] partes = linea.split(",");
                if (partes.length < 4) {
                    continue;
                }
                String uidFs = partes[0].trim();
                String uidFt = partes[2].trim();

                try {
                    Document fs = obtenerTicket(conexion, uidFs);
                    Document ft = obtenerTicket(conexion, uidFt);
                    if (fs != null && ft != null) {
                        corregirTicket(fs, ft);
                        String xmlCorregido = marshalTicket(ft);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
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
            throws SQLException, ParserConfigurationException, IOException, SAXException {
        ResultSet rs = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);
        Document doc = null;
        if (rs.next()) {
            String xml = rs.getString("ticket");
            xml = sanitizeXml(xml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(xml)));
        }
        rs.close();
        return doc;
    }

    private String marshalTicket(Document ticket) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(ticket), new StreamResult(writer));
        return writer.toString();
    }

    /**
     * Corrige la información del ticket FT copiando los datos de la FS asociada.
     */
    private void corregirTicket(Document fs, Document ft) {
        Element cabeceraFs = (Element) fs.getElementsByTagName("cabecera").item(0);
        Element cabeceraFt = (Element) ft.getElementsByTagName("cabecera").item(0);
        if (cabeceraFs != null && cabeceraFt != null) {
            copiarNodo(cabeceraFs, cabeceraFt, "lineasimpuestos");
            copiarNodo(cabeceraFs, cabeceraFt, "totales");
        }

        Element pagosFs = (Element) fs.getElementsByTagName("pagos").item(0);
        Element pagosFt = (Element) ft.getElementsByTagName("pagos").item(0);
        if (pagosFs != null && pagosFt != null) {
            Node nuevo = ft.importNode(pagosFs, true);
            pagosFt.getParentNode().replaceChild(nuevo, pagosFt);
        }

        NodeList lineasFs = fs.getElementsByTagName("linea");
        NodeList lineasFt = ft.getElementsByTagName("linea");
        int size = Math.min(lineasFs.getLength(), lineasFt.getLength());
        for (int i = 0; i < size; i++) {
            Element lfs = (Element) lineasFs.item(i);
            Element lft = (Element) lineasFt.item(i);
            copiarTexto(lfs, lft, "precio_tarifa_origen");
            copiarTexto(lfs, lft, "precio_total_tarifa_origen");
            copiarTexto(lfs, lft, "precio_sin_dto");
            copiarTexto(lfs, lft, "precio_total_sin_dto");
            copiarTexto(lfs, lft, "precio");
            copiarTexto(lfs, lft, "precio_total");
            copiarTexto(lfs, lft, "importe");
            copiarTexto(lfs, lft, "importe_total");
        }
    }

    private void copiarNodo(Element origen, Element destino, String nombre) {
        NodeList lista = origen.getElementsByTagName(nombre);
        if (lista.getLength() == 0) {
            return;
        }
        Node nodoOrigen = lista.item(0);
        Node nodoImportado = destino.getOwnerDocument().importNode(nodoOrigen, true);
        NodeList existentes = destino.getElementsByTagName(nombre);
        if (existentes.getLength() > 0) {
            destino.replaceChild(nodoImportado, existentes.item(0));
        } else {
            destino.appendChild(nodoImportado);
        }
    }

    private void copiarTexto(Element origen, Element destino, String nombre) {
        NodeList fromList = origen.getElementsByTagName(nombre);
        NodeList toList = destino.getElementsByTagName(nombre);
        if (fromList.getLength() > 0 && toList.getLength() > 0) {
            toList.item(0).setTextContent(fromList.item(0).getTextContent());
        }
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
