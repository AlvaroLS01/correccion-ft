package com.comerzzia.bricodepot.motivoslinea.application.config;

import com.comerzzia.bricodepot.motivoslinea.services.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	protected ConfigurableApplicationContext applicationContext;

	public void run(String... args) throws Exception {
		App app = applicationContext.getBean(App.class);
		app.metodo();
	}

}
