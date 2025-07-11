package com.comerzzia.bricodepot.motivoslinea.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.comerzzia.bricodepot.motivoslinea.persistence.ConnectionProvider;

@Configuration
public class BeanConfiguration {

    @Bean
    ConnectionProvider connectionProvider() {
        return new ConnectionProvider();
    }
	
}
