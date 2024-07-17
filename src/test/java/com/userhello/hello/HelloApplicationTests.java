package com.userhello.hello;

import com.userhello.hello.model.User;
import com.userhello.hello.repository.UserRepository;
import com.userhello.hello.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("integrationtest")
public class HelloApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() throws ParseException {
		// Create a new User object
		User user = new User();
		user.setUname("testuser");
		user.setName("Test");
		user.setFamilyName("User");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = sdf.parse("2000-01-01");
		user.setBirthdate(birthdate);

		user.setBirthPlace("City");
		user.setCurrentCountry("Country");
		user.setCurrentCity("City");
		user.setSchoolName("School");
		user.setGpa(4.0f); // Use float instead of double
		user.setPhone("1234567890");
		user.setEmail("test@example.com");

		// Save the user using the service
		User savedUser = userService.createUser(user);

		// Retrieve the user from the repository
		Optional<User> retrievedUserOptional = userRepository.findById(savedUser.getId());
		User retrievedUser = retrievedUserOptional.orElse(null);

		// Perform assertions
		assertNotNull(retrievedUser, "User should not be null");
		assertEquals("testuser", retrievedUser.getUname());
		assertEquals("Test", retrievedUser.getName());
		assertEquals("User", retrievedUser.getFamilyName());
		assertEquals(birthdate, retrievedUser.getBirthdate());
		assertEquals("City", retrievedUser.getBirthPlace());
		assertEquals("Country", retrievedUser.getCurrentCountry());
		assertEquals("City", retrievedUser.getCurrentCity());
		assertEquals("School", retrievedUser.getSchoolName());
		assertEquals(4.0f, retrievedUser.getGpa());
		assertEquals("1234567890", retrievedUser.getPhone());
		assertEquals("test@example.com", retrievedUser.getEmail());
	}
}
