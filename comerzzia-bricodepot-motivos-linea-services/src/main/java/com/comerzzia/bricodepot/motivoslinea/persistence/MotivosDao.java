package com.comerzzia.bricodepot.motivoslinea.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.comerzzia.bricodepot.motivoslinea.model.bbdd.Motivo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MotivosDao {

	private static final Logger log = LoggerFactory.getLogger(MotivosDao.class);

	public static void insertarMotivo(Motivo motivo, Connection conexion) throws SQLException {
		log.debug("insertarMotivo() - Insertamos el motivo");

		PreparedStatement stmt = conexion
				.prepareStatement("insert into x_motivos_datos_tbl values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		stmt.setString(1, motivo.getUidActividad());
		stmt.setString(2, motivo.getUidTicket());
		stmt.setString(3, motivo.getCodAlbaran());
		stmt.setString(4, motivo.getCodAlbaranOrigen());
		stmt.setDate(5, Date.valueOf(motivo.getFecha()));
		stmt.setInt(6, motivo.getLinea());
		stmt.setString(7, motivo.getCodArticulo());
		stmt.setString(8, motivo.getTipoMotivo());
		stmt.setString(9, motivo.getCodigoMotivo());
		stmt.setString(10, motivo.getDescripcionMotivo());
		stmt.setString(11, motivo.getComentarioMotivo());
		stmt.setBigDecimal(12, motivo.getCantidad());
		stmt.setBigDecimal(13, motivo.getPrecioOriginal());
		stmt.setBigDecimal(14, motivo.getPrecioFinal());

		log.info("insertarMotivo() - sql: " + stmt.toString());
		stmt.execute();

	}

}
