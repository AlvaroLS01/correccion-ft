package com.comerzzia.bricodepot.generarftdefs.services.correccion;

import com.comerzzia.bricodepot.generarftdefs.model.xml.LineaTicket;
import com.comerzzia.bricodepot.generarftdefs.model.xml.TicketXml;
import com.comerzzia.bricodepot.generarftdefs.persistence.ProveedorConexion;
import com.comerzzia.bricodepot.generarftdefs.persistence.TicketsDao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            JAXBContext context = JAXBContext.newInstance(TicketXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

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
                    TicketXml fs = obtenerTicket(conexion, unmarshaller, uidFs);
                    TicketXml ft = obtenerTicket(conexion, unmarshaller, uidFt);
                    if (fs != null && ft != null) {
                        corregirTicket(fs, ft);
                        String xmlCorregido = marshalTicket(marshaller, ft);
                        TicketsDao.actualizarTicket(conexion, uidActividad, uidFt, xmlCorregido);
                    }
                } catch (Exception e) {
                    log.error("Error procesando la fila para {}: {}", uidFt, e.getMessage(), e);
                }
            }
        } catch (IOException | JAXBException e) {
            log.error("Error procesando CSV: " + e.getMessage(), e);
        }
    }

    private TicketXml obtenerTicket(Connection conexion, Unmarshaller unmarshaller, String uidTicket)
            throws SQLException, JAXBException {
        ResultSet rs = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);
        TicketXml ticket = null;
        if (rs.next()) {
            String xml = rs.getString("ticket");
            xml = sanitizeXml(xml);
            ticket = (TicketXml) unmarshaller.unmarshal(new StringReader(xml));
        }
        rs.close();
        return ticket;
    }

    private String marshalTicket(Marshaller marshaller, TicketXml ticket) throws JAXBException {
        StringWriter sw = new StringWriter();
        marshaller.marshal(ticket, sw);
        return sw.toString();
    }

    /**
     * Corrige la información del ticket FT copiando los datos de la FS asociada.
     */
    private void corregirTicket(TicketXml fs, TicketXml ft) {
        ft.getCabeceraTicket().setLineasImpuestos(fs.getCabeceraTicket().getLineasImpuestos());
        ft.getCabeceraTicket().setTotales(fs.getCabeceraTicket().getTotales());
        ft.setPagos(fs.getPagos());

        List<LineaTicket> lineasFs = fs.getLineas();
        List<LineaTicket> lineasFt = ft.getLineas();
        int size = Math.min(lineasFs.size(), lineasFt.size());
        for (int i = 0; i < size; i++) {
            LineaTicket lfs = lineasFs.get(i);
            LineaTicket lft = lineasFt.get(i);
            lft.setPrecioTarifaOrigen(lfs.getPrecioTarifaOrigen());
            lft.setPrecioTotalTarifaOrigen(lfs.getPrecioTotalTarifaOrigen());
            lft.setPrecioSinDto(lfs.getPrecioSinDto());
            lft.setPrecioTotalSinDto(lfs.getPrecioTotalSinDto());
            lft.setPrecio(lfs.getPrecio());
            lft.setPrecioTotal(lfs.getPrecioTotal());
            lft.setImporte(lfs.getImporte());
            lft.setImporteTotal(lfs.getImporteTotal());
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
