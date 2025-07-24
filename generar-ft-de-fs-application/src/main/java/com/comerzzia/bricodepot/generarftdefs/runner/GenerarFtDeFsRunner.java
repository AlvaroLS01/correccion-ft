package com.comerzzia.bricodepot.generarftdefs.runner;

import com.comerzzia.bricodepot.generarftdefs.services.generacion.GenerarFtDeFsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class GenerarFtDeFsRunner implements CommandLineRunner {

	@Autowired
	private GenerarFtDeFsService servicio;

	private static final Logger log = LoggerFactory.getLogger(GenerarFtDeFsRunner.class);

	@Value("${comerzzia.csv.path:../correcciones.csv}")
	private String csvPath;

	@Override
	public void run(String... args) throws Exception {
		log.debug("run() - Leyendo fichero CSV de correcciones desde " + csvPath);
		try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
			servicio.procesarCsv(reader);
			log.debug("run() - Procesamiento de correcciones finalizado");
		}
	}
}