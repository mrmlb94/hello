package com.userhello.hello;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {

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
		System.out.println("Datasource URL: " + datasourceUrl);
		System.out.println("Datasource Username: " + datasourceUsername);
		System.out.println("Datasource Password: " + datasourcePassword);
	}
}
