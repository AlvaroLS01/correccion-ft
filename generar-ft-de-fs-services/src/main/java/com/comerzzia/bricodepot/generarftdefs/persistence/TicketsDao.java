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

        /**
         * Elimina el albarán asociado al ticket indicado. Se borran todos los
         * registros dependientes antes de eliminar la cabecera del albarán.
         */
        public static void eliminarAlbaran(Connection conexion, String uidActividad, String uidTicket)
                        throws SQLException {
                String selectSql = "select id_clie_albaran from d_clie_albaranes_cab_tbl where uid_actividad = ? and uid_ticket = ?";
                PreparedStatement selectStmt = conexion.prepareStatement(selectSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                selectStmt.setString(1, uidActividad);
                selectStmt.setString(2, uidTicket);
                log.info("eliminarAlbaran() - " + selectStmt.toString());
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                        long idClieAlbaran = rs.getLong("id_clie_albaran");

                        eliminarAlbaranDependencias(conexion, uidActividad, uidTicket, idClieAlbaran);

                        String deleteCab = "delete from d_clie_albaranes_cab_tbl where uid_actividad = ? and id_clie_albaran = ?";
                        PreparedStatement stmtCab = conexion.prepareStatement(deleteCab);
                        stmtCab.setString(1, uidActividad);
                        stmtCab.setLong(2, idClieAlbaran);
                        log.info("eliminarAlbaran() - " + stmtCab.toString());
                        stmtCab.executeUpdate();
                        stmtCab.close();
                }
                rs.close();
                selectStmt.close();
        }

        private static void eliminarAlbaranDependencias(Connection conexion, String uidActividad, String uidTicket, long idClieAlbaran)
                        throws SQLException {
                String sqlVenta = "delete from d_clie_ventas_det_mod_pre_tbl where uid_documento = ? and uid_actividad = ?";
                PreparedStatement stmtVenta = conexion.prepareStatement(sqlVenta);
                stmtVenta.setString(1, uidTicket);
                stmtVenta.setString(2, uidActividad);
                log.info("eliminarAlbaranDependencias() - " + stmtVenta.toString());
                stmtVenta.executeUpdate();
                stmtVenta.close();

                String sqlImp = "delete from d_clie_albaranes_imp_tbl where id_clie_albaran = ? and uid_actividad = ?";
                PreparedStatement stmtImp = conexion.prepareStatement(sqlImp);
                stmtImp.setLong(1, idClieAlbaran);
                stmtImp.setString(2, uidActividad);
                log.info("eliminarAlbaranDependencias() - " + stmtImp.toString());
                stmtImp.executeUpdate();
                stmtImp.close();

                String sqlPag = "delete from d_clie_albaranes_pag_tbl where id_clie_albaran = ? and uid_actividad = ?";
                PreparedStatement stmtPag = conexion.prepareStatement(sqlPag);
                stmtPag.setLong(1, idClieAlbaran);
                stmtPag.setString(2, uidActividad);
                log.info("eliminarAlbaranDependencias() - " + stmtPag.toString());
                stmtPag.executeUpdate();
                stmtPag.close();

                String sqlDet = "delete from d_clie_albaranes_det_tbl where id_clie_albaran = ? and uid_actividad = ?";
                PreparedStatement stmtDet = conexion.prepareStatement(sqlDet);
                stmtDet.setLong(1, idClieAlbaran);
                stmtDet.setString(2, uidActividad);
                log.info("eliminarAlbaranDependencias() - " + stmtDet.toString());
                stmtDet.executeUpdate();
                stmtDet.close();

                String sqlDocEnl = "delete from D_DOCUMENTOS_ENL_TBL where uid_actividad = ? and uid_documento = ?";
                PreparedStatement stmtEnl = conexion.prepareStatement(sqlDocEnl);
                stmtEnl.setString(1, uidActividad);
                stmtEnl.setString(2, uidTicket);
                log.info("eliminarAlbaranDependencias() - " + stmtEnl.toString());
                stmtEnl.executeUpdate();
                stmtEnl.close();

                String sqlXTickets = "delete from x_tickets_tbl where uid_actividad = ? and (uid_ticket = ? or uid_factura_completa = ?)";
                PreparedStatement stmtXTickets = conexion.prepareStatement(sqlXTickets);
                stmtXTickets.setString(1, uidActividad);
                stmtXTickets.setString(2, uidTicket);
                stmtXTickets.setString(3, uidTicket);
                log.info("eliminarAlbaranDependencias() - " + stmtXTickets.toString());
                stmtXTickets.executeUpdate();
                stmtXTickets.close();
        }
}
