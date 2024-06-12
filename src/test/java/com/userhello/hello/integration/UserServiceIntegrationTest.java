package com.userhello.hello.integration;

import com.userhello.hello.Service.UserService;
import com.userhello.hello.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndRetrieveUser() {
        User newUser = new User();
        newUser.setName("Test User");
        newUser.setUname("testuser");
        newUser.setEmail("test@example.com");

        User savedUser = userService.signUp(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Test User");

        User foundUser = userService.findByUname("testuser").orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
