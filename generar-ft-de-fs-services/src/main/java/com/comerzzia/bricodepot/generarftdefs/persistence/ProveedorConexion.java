package com.comerzzia.bricodepot.generarftdefs.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProveedorConexion {

    private Connection conexion;

	@Value("${comerzzia.db.url}")
    private String url;

	@Value("${comerzzia.db.user}")
    private String usuario;

	@Value("${comerzzia.db.pass}")
    private String contrasena;

	@Value("${comerzzia.db.class}")
    private String claseDb;

    private static final Logger log = LoggerFactory.getLogger(ProveedorConexion.class);

    public Connection obtenerConexion() {

        if (conexion == null) {
                log.info("Estableciendo conexión con la base de datos...");
                log.info("URL de la base de datos: " + url);
                log.info("Usuario de la base de datos: " + usuario);
                try {
                        Class.forName(claseDb);
                        conexion = DriverManager.getConnection(url, usuario, contrasena);
                        if (conexion != null) {
                                return conexion;
                        }
                } catch (ClassNotFoundException e) {
                        log.error("obtenerConexion() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
                } catch (SQLException e) {
                        log.error("obtenerConexion() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
                }
        } else {
                log.info("Usando la conexión ya abierta.");
                return conexion;
        }

                return conexion;
        }
}
