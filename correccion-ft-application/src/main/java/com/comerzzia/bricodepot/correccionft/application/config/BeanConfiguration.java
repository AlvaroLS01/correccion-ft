package com.comerzzia.bricodepot.correccionft.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.bricodepot.correccionft.persistence.ProveedorConexion;

@Configuration
public class BeanConfiguration {

    @Bean
    ProveedorConexion proveedorConexion() {
        return new ProveedorConexion();
    }
	
}
