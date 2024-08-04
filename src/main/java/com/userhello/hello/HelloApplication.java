package com.userhello.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {

	static Logger logger = LoggerFactory.getLogger(HelloApplication.class);

	@Value("${spring.datasource.url}")
	String datasourceUrl;

	@Value("${spring.datasource.username}")
	String datasourceUsername;

	@Value("${spring.datasource.password}")
	String datasourcePassword;

	@Generated // Custom annotation to exclude from JaCoCo coverage
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Datasource URL: {}", datasourceUrl);
		logger.info("Datasource Username: {}", datasourceUsername);
		logger.info("Datasource Password: [PROTECTED]");
	}
}
