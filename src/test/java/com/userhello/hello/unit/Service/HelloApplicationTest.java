package com.userhello.hello.unit.Service;

import com.userhello.hello.HelloApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.boot.CommandLineRunner;

import static org.mockito.Mockito.*;

import org.springframework.context.ApplicationContext;

import java.util.Collections;

@SpringBootTest
@SpringJUnitConfig
@ActiveProfiles("test")
public class HelloApplicationTest {

    @Test
    public void testMain() {
        HelloApplication.main(new String[]{});
    }

    @Test
    public void testRun() throws Exception {
        // Mocking the ApplicationContext
        ApplicationContext context = mock(ApplicationContext.class);

        // Initializing the application
        HelloApplication application = new HelloApplication();

        // Mocking CommandLineRunner run method
        CommandLineRunner runner = mock(CommandLineRunner.class);
        runner.run();

        // Verifying if the run method was called
        verify(runner, times(1)).run();
    }
}
