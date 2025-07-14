package com.comerzzia.bricodepot.generarftdefs.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:comerzzia-*context.xml"})
@SpringBootApplication
public class GenerarFtDeFsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerarFtDeFsApplication.class, args);
    }

}
