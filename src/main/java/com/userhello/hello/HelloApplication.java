package com.userhello.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloApplication.class);

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String datasourceUsername;

	@Value("${spring.datasource.password}")
	private String datasourcePassword;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Datasource URL: {}", datasourceUrl);
		LOGGER.info("Datasource Username: {}", datasourceUsername);
		LOGGER.info("Datasource Password: [PROTECTED]");
	}
}
