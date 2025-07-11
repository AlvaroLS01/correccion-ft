package com.comerzzia.bricodepot.correccionft.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:comerzzia-*context.xml"})
@SpringBootApplication
public class CorreccionFtApplication{

        public static void main(String[] args) {
                SpringApplication.run(CorreccionFtApplication.class, args);
        }

}
