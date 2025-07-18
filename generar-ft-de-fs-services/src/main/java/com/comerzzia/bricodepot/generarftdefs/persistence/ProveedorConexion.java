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
                log.debug("obtenerConexion() - Inicio de conexion a la BBDD");
                log.debug("obtenerConexion() - URL: " + url);
                log.debug("obtenerConexion() - USER: " + usuario);
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
                log.debug("obtenerConexion() - Utilizando conexion existente");
                return conexion;
        }

                return conexion;
        }
}
