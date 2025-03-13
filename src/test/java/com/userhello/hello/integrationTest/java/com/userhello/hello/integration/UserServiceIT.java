package com.userhello.hello.integrationTest.java.com.userhello.hello.integration;

import com.userhello.hello.model.User;
import com.userhello.hello.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class UserServiceIT {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("user_db")
            .withUsername("postgres001")
            .withPassword("Am2836");

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUname("testuser");
        user.setName("Ali");
        user.setFamilyName("Ahmadi");

        User savedUser = userService.signUp(user);

        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUname());
    }

    @Test
    void testFindByName() {
        User user = new User();
        user.setUname("uniqueuser");
        user.setName("Alice");
        user.setFamilyName("Wonderland");
        userService.signUp(user);

        List<User> users = userService.findByName("Alice");

        assertFalse(users.isEmpty());
        assertEquals("Alice", users.get(0).getName());
    }
}
