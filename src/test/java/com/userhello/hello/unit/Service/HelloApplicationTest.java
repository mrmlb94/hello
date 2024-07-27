package com.userhello.hello.unit.Service;

import com.userhello.hello.HelloApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;

@SpringBootTest
@SpringJUnitConfig
@ActiveProfiles("test")
public class HelloApplicationTest {

    @Test
    public void testMain() {
        SpringApplication application = new SpringApplication(HelloApplication.class);
        application.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        application.run();
    }
}
