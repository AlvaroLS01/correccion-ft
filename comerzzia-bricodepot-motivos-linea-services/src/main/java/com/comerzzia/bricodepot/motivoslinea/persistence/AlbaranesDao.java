package com.comerzzia.bricodepot.motivoslinea.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.comerzzia.bricodepot.motivoslinea.model.bbdd.Albaran;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AlbaranesDao {

	private static final Logger log = LoggerFactory.getLogger(AlbaranesDao.class);

	public static Albaran consultarAlbaran(String uidTicket, String uidActividad, Connection conexion)
			throws SQLException {
		log.debug("consultarAlbaran() - Consultando albaran para el uidTicket " + uidTicket);
		log.debug("consultarAlbaran() - UID_TICKET: " + uidTicket);
		log.debug("consultarAlbaran() - UID_ACTIVIDAD: " + uidActividad);

		String sql = "select * from d_clie_albaranes_cab_tbl where uid_actividad = ? and uid_ticket = ?";
		PreparedStatement stmt = conexion.prepareStatement(sql);
		stmt.setString(1, uidActividad);
		stmt.setString(2, uidTicket);

		log.debug("consultarAlbaran() - sql: " + stmt.toString());
		ResultSet resultados = stmt.executeQuery();

		Albaran albaran = new Albaran();
		while (resultados.next()) {
			albaran.setCodAlbaran(resultados.getString("cod_albaran"));
			albaran.setCodAlbaranOrigen(resultados.getString("cod_albaran_origen"));
			albaran.setUidTicket(resultados.getString("uid_ticket"));
		}

		return albaran;
	}
}
