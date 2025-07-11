package com.comerzzia.bricodepot.motivoslinea.services;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.comerzzia.bricodepot.motivoslinea.model.bbdd.Albaran;
import com.comerzzia.bricodepot.motivoslinea.model.bbdd.Motivo;
import com.comerzzia.bricodepot.motivoslinea.model.xml.LineaTicket;
import com.comerzzia.bricodepot.motivoslinea.model.xml.MotivoTicket;
import com.comerzzia.bricodepot.motivoslinea.model.xml.TicketXml;
import com.comerzzia.bricodepot.motivoslinea.persistence.AlbaranesDao;
import com.comerzzia.bricodepot.motivoslinea.persistence.ConnectionProvider;
import com.comerzzia.bricodepot.motivoslinea.persistence.MotivosDao;
import com.comerzzia.bricodepot.motivoslinea.persistence.TicketsDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	@Autowired
	public ConnectionProvider provider;

	@Value("${comerzzia.uid_actividad}")
	private String uidActividad;

	@Value("${comerzzia.fecha_inicio}")
	private String fechaInicio;

	@Value("${comerzzia.fecha_fin}")
	private String fechaFin;

	public App() {
	}

	public void metodo() {
		log.info("metodo() - Inicio del programa para insertar los motivos");
		Connection conexion = null;
		int numInserciones = 0;
		List<String> listaUID = new ArrayList<String>();
		List<String> listaUIDErroneos = new ArrayList<String>();
		try {
			conexion = provider.getConnection();

			ResultSet resultadosUid = TicketsDao.consultarTicketsUid(fechaInicio, fechaFin, uidActividad, conexion);

			while (resultadosUid.next()) {
				String uidTicket = resultadosUid.getString("uid_ticket");
				listaUID.add(uidTicket);
			}
			resultadosUid.close();
			
			JAXBContext jaxbContext = JAXBContext.newInstance(TicketXml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			for (String uidTicket : listaUID) {
				ResultSet resultados = TicketsDao.consultarTicketPorUid(conexion, uidActividad, uidTicket);

				if (resultados.next()) {
					String xml = resultados.getString("ticket");
					TicketXml ticketXml = null;

					try {
						ticketXml = (TicketXml) jaxbUnmarshaller.unmarshal(new StringReader(xml));

						Albaran albaran = null;
						for (LineaTicket linea : ticketXml.getLineas()) {
							MotivoTicket motivo = linea.getMotivo();
							if (motivo != null) {

								if (albaran == null) {
									albaran = AlbaranesDao.consultarAlbaran(uidTicket, uidActividad, conexion);
								}
								Motivo motivoBbdd = crearMotivoBbdd(ticketXml, albaran, linea, motivo);
								MotivosDao.insertarMotivo(motivoBbdd, conexion);

								numInserciones++;
							}
						}
					} catch (Exception e) {
						String mensajeError = e.getMessage();
//						log.error("metodo() - Ha ocurrido un error: " + e.getMessage(), e);
						if (ticketXml == null) {
//							log.error("metodo() - Ha ocurrido un error al desparsear el ticket con uid " + uidTicket);
							mensajeError = "Error de parseo";
						}
						
						listaUIDErroneos.add(uidTicket + " - " + mensajeError);
					}

				}

				resultados.close();
			}
			
		} catch (SQLException e) {
			log.error("metodo() - Ha ocurrido un error: " + e.getMessage(), e);

		} catch (JAXBException e) {
			log.error("metodo() - Ha ocurrido un error: " + e.getMessage(), e);
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
				}
			}
		}

		log.info("metodo() - Fin");
		log.info("metodo() - Se han insertado " + numInserciones + " registros en la tabla x_motivos_datos_tbl");
		
		log.info("metodo() - Tickets erróneos: ");
		for (String mensaje : listaUIDErroneos) {
			log.info(mensaje);
		}
	}

	private static Motivo crearMotivoBbdd(TicketXml ticketXml, Albaran albaran, LineaTicket linea,
			MotivoTicket motivo) {
		log.debug("crearMotivoBbdd() - Creacion del objeto motivo para su posterior insercion en bbdd");

		Motivo motivoBbdd = new Motivo();
		motivoBbdd.setUidActividad(motivo.getUidActividad());
		motivoBbdd.setUidTicket(ticketXml.getCabeceraTicket().getUidTicket());
		motivoBbdd.setCodAlbaran(albaran.getCodAlbaran());
		motivoBbdd.setCodAlbaranOrigen(albaran.getCodAlbaranOrigen());
		motivoBbdd.setFecha(formateaFecha(ticketXml.getCabeceraTicket().getFecha()));
		motivoBbdd.setLinea(Integer.parseInt(linea.getIdLinea()));
		motivoBbdd.setCodArticulo(linea.getCodArt());
		motivoBbdd.setTipoMotivo(motivo.getCodigoTipo());
		motivoBbdd.setCodigoMotivo(motivo.getCodigo());
		motivoBbdd.setDescripcionMotivo(motivo.getDescripcion());
		motivoBbdd.setComentarioMotivo(motivo.getComentario());
		motivoBbdd.setCantidad(new BigDecimal(linea.getCantidad()));

		if (motivo.getPrecioArticuloOriginal() != null && !motivo.getPrecioArticuloOriginal().trim().equals("")) {
			motivoBbdd.setPrecioOriginal(new BigDecimal(motivo.getPrecioArticuloOriginal()));
		}
		if (motivo.getPrecioArticuloAplicado() != null && !motivo.getPrecioArticuloAplicado().trim().equals("")) {
			motivoBbdd.setPrecioFinal(new BigDecimal(motivo.getPrecioArticuloAplicado()));
		}

		return motivoBbdd;
	}

	private static LocalDate formateaFecha(String fecha) {
		log.debug("formateaFecha() - Se procede a formatear la fecha " + fecha);
		fecha = fecha.replace("T", " ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDate = LocalDate.parse(fecha, formatter);
		return localDate;
	}

}
