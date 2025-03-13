package com.userhello.hello.unit;


import org.junit.jupiter.api.Test;

import com.userhello.hello.model.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserBuilderTest {

    @Test
    void testBuilderSetId() {
        Long id = 1L;
        User user = new User.Builder()
                .setId(id)
                .build();
        assertEquals(id, user.getId());
    }

    @Test
    void testBuilderSetName() {
        String name = "Ali";
        User user = new User.Builder()
                .setName(name)
                .build();
        assertEquals(name, user.getName());
    }

    @Test
    void testBuilderSetUname() {
        String uname = "Ali_Ahmadi";
        User user = new User.Builder()
                .setUname(uname)
                .build();
        assertEquals(uname, user.getUname());
    }

    @Test
    void testBuilderSetFamilyName() {
        String familyName = "Ahmadi";
        User user = new User.Builder()
                .setFamilyName(familyName)
                .build();
        assertEquals(familyName, user.getFamilyName());
    }

    @Test
    void testBuilderSetBirthdate() {
        Date birthdate = new Date();
        User user = new User.Builder()
                .setBirthdate(birthdate)
                .build();
        assertEquals(birthdate, user.getBirthdate());
    }

    @Test
    void testBuilderSetBirthPlace() {
        String birthPlace = "New York";
        User user = new User.Builder()
                .setBirthPlace(birthPlace)
                .build();
        assertEquals(birthPlace, user.getBirthPlace());
    }

    @Test
    void testBuilderSetCurrentCountry() {
        String currentCountry = "USA";
        User user = new User.Builder()
                .setCurrentCountry(currentCountry)
                .build();
        assertEquals(currentCountry, user.getCurrentCountry());
    }

    @Test
    void testBuilderSetCurrentCity() {
        String currentCity = "New York";
        User user = new User.Builder()
                .setCurrentCity(currentCity)
                .build();
        assertEquals(currentCity, user.getCurrentCity());
    }

    @Test
    void testBuilderSetSchoolName() {
        String schoolName = "NYU";
        User user = new User.Builder()
                .setSchoolName(schoolName)
                .build();
        assertEquals(schoolName, user.getSchoolName());
    }

    @Test
    void testBuilderSetGpa() {
        Float gpa = 3.5f;
        User user = new User.Builder()
                .setGpa(gpa)
                .build();
        assertEquals(gpa, user.getGpa());
    }

    @Test
    void testBuilderSetPhone() {
        String phone = "123-456-7890";
        User user = new User.Builder()
                .setPhone(phone)
                .build();
        assertEquals(phone, user.getPhone());
    }

    @Test
    void testBuilderSetEmail() {
        String email = "Ali.Ahmadi@example.com";
        User user = new User.Builder()
                .setEmail(email)
                .build();
        assertEquals(email, user.getEmail());
    }

    @Test
    void testBuilderBuild() {
        User user = new User.Builder()
                .setId(1L)
                .setName("Ali")
                .setUname("Ali_Ahmadi")
                .setFamilyName("Ahmadi")
                .setBirthdate(new Date())
                .setBirthPlace("New York")
                .setCurrentCountry("USA")
                .setCurrentCity("New York")
                .setSchoolName("NYU")
                .setGpa(3.5f)
                .setPhone("123-456-7890")
                .setEmail("Ali.Ahmadi@example.com")
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Ali", user.getName());
        assertEquals("Ali_Ahmadi", user.getUname());
        assertEquals("Ahmadi", user.getFamilyName());
        assertEquals("New York", user.getBirthPlace());
        assertEquals("USA", user.getCurrentCountry());
        assertEquals("New York", user.getCurrentCity());
        assertEquals("NYU", user.getSchoolName());
        assertEquals(3.5f, user.getGpa());
        assertEquals("123-456-7890", user.getPhone());
        assertEquals("Ali.Ahmadi@example.com", user.getEmail());
    }
}
