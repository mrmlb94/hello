package com.userhello.hello.integration;

import com.userhello.hello.HelloApplication;
import com.userhello.hello.model.User;
import com.userhello.hello.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HelloApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
@ExtendWith(SpringExtension.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndRetrieveUser() {
        // Create a new user
        User newUser = new User();
        newUser.setName("John");
        newUser.setUname("johndoe");
        newUser.setFamilyName("Doe");
        newUser.setBirthdate(new Date(90, 0, 1)); // Jan 1, 1990
        newUser.setBirthPlace("New York");
        newUser.setCurrentCountry("USA");
        newUser.setCurrentCity("New York");
        newUser.setSchoolName("NYU");
        newUser.setGpa(3.5f);
        newUser.setPhone("1234567890");
        newUser.setEmail("john.doe@example.com");

        // Save the user
        User savedUser = userService.createUser(newUser);

        // Retrieve the user
        User retrievedUser = userService.getUserById(savedUser.getId());

        // Verify the user details
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("John");
        assertThat(retrievedUser.getUname()).isEqualTo("johndoe");
        assertThat(retrievedUser.getFamilyName()).isEqualTo("Doe");
        assertThat(retrievedUser.getBirthdate()).isEqualTo(new Date(90, 0, 1));
        assertThat(retrievedUser.getBirthPlace()).isEqualTo("New York");
        assertThat(retrievedUser.getCurrentCountry()).isEqualTo("USA");
        assertThat(retrievedUser.getCurrentCity()).isEqualTo("New York");
        assertThat(retrievedUser.getSchoolName()).isEqualTo("NYU");
        assertThat(retrievedUser.getGpa()).isEqualTo(3.5f);
        assertThat(retrievedUser.getPhone()).isEqualTo("1234567890");
        assertThat(retrievedUser.getEmail()).isEqualTo("john.doe@example.com");
    }
}
