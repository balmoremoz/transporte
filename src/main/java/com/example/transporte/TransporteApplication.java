package com.example.transporte;

import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class TransporteApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TransporteApplication.class).sources(TransporteApplication.class)
				.properties(getProperties()).run(args);
	}

	static Properties getProperties() {
		Properties props = new Properties();

		return props;
	}

}
