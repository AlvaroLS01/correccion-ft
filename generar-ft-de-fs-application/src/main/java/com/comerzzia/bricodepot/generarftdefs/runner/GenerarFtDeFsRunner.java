package com.comerzzia.bricodepot.generarftdefs.runner;

import com.comerzzia.bricodepot.generarftdefs.services.generacion.GenerarFtDeFsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class GenerarFtDeFsRunner implements CommandLineRunner {

    @Autowired
    private GenerarFtDeFsService servicio;

    @Value("${comerzzia.csv.path:../correcciones.csv}")
    private String csvPath;

    @Override
    public void run(String... args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            servicio.procesarCsv(reader);
        }
    }
}
