package com.comerzzia.bricodepot.generarftdefs.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TicketsDao {

	private static final Logger log = LoggerFactory.getLogger(TicketsDao.class);

	public static ResultSet consultarTicketPorUid(Connection conexion,String uidActividad, String uidTicket) throws SQLException{
		log.info("consultarTicketPorUid() - Inicio de consulta de ticket con uid: "+ uidTicket);
		String sql = "select * from d_tickets_tbl where uid_actividad = ? and uid_ticket = ?;";
		
		PreparedStatement stmt = conexion.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, uidActividad);
		stmt.setString(2, uidTicket);
		
		log.info("consultarTicketPorUid() - " + stmt.toString());
		ResultSet resultados = stmt.executeQuery();
		return resultados;
	}
	
	
        public static ResultSet consultarTicketsUid(String fechaMin, String fechaMax, String uidActividad, Connection conexion) throws SQLException {
		log.info("consultarTicketsUid() - Inicio de consulta de tickets");
		log.debug("consultarTicketsUid() - UID_ACTIVIDAD: " + uidActividad);
		log.debug("consultarTicketsUid() - FECHA_INICIO: " + fechaMin);
		log.debug("consultarTicketsUid() - FECHA_FIN: " + fechaMax);
		
//		String sql = "select uid_ticket from d_tickets_tbl where uid_actividad = ? and fecha >= ? and fecha <= ? and id_tipo_documento in ('1','2','3','11','1001','1002','1003') order by fecha asc";

		String sql = "select uid_ticket from d_clie_albaranes_cab_tbl where uid_actividad = ? and fecha >= ? and fecha <= ? order by fecha asc, hora asc";
		PreparedStatement stmt = conexion.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, uidActividad);
		stmt.setString(2, fechaMin);
		stmt.setString(3, fechaMax);
//		stmt.setString(4, fechaMin);
//		stmt.setString(5, fechaMax);
		
		log.info("consultarTicketsUid() - " + stmt.toString());
                ResultSet resultados = stmt.executeQuery();
                return resultados;
        }

        public static void actualizarTicket(Connection conexion, String uidActividad, String uidTicket, String xml)
                        throws SQLException {
                String sql = "update d_tickets_tbl set ticket = ?, procesado = 'E' where uid_actividad = ? and uid_ticket = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, xml);
                stmt.setString(2, uidActividad);
                stmt.setString(3, uidTicket);
                log.info("actualizarTicket() - " + stmt.toString());
                stmt.executeUpdate();
        }
}
