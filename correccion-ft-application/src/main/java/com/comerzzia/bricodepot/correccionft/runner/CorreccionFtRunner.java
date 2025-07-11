package com.comerzzia.bricodepot.correccionft.runner;

import com.comerzzia.bricodepot.correccionft.services.correccion.CorreccionFtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class CorreccionFtRunner implements CommandLineRunner {

    @Autowired
    private CorreccionFtService servicio;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            servicio.procesarCsv(reader);
        }
    }
}
