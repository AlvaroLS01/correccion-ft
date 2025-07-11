package com.comerzzia.bricodepot.correccionft.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionProvider {

	private Connection connection;

	@Value("${comerzzia.db.url}")
	private String url;

	@Value("${comerzzia.db.user}")
	private String user;

	@Value("${comerzzia.db.pass}")
	private String password;

	@Value("${comerzzia.db.class}")
	private String dbClass;

        private static final Logger log = LoggerFactory.getLogger(ConnectionProvider.class);

	public Connection getConnection() {

		if (connection == null) {
			log.info("getConnection() - Inicio de conexion a la BBDD");
			log.info("getConnection() - URL: " + url);
			log.info("getConnection() - USER: " + user);
			try {
				Class.forName(dbClass);
				connection = DriverManager.getConnection(url, user, password);
				if (connection != null) {
					return connection;
				}
			} catch (ClassNotFoundException e) {
				log.error("getConnection() - Ha ocurrido un error: " + e.getMessage(), e);
			} catch (SQLException e) {
				log.error("getConnection() - Ha ocurrido un error: " + e.getMessage(), e);
			}
		} else {
			return connection;
		}

		return connection;
	}
}
