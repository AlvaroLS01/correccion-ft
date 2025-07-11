package com.comerzzia.bricodepot.motivoslinea.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:comerzzia-*context.xml"})
@SpringBootApplication
public class MotivosLineaApplication{

	public static void main(String[] args) {
		SpringApplication.run(MotivosLineaApplication.class, args);
	}

}
