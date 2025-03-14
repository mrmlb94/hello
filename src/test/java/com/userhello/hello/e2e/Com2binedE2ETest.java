package com.userhello.hello.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Com2binedE2ETest {

    private String token;

    @BeforeAll
    static void waitForBackend() {
        RestAssured.baseURI = "http://localhost:8081";

        Awaitility.await().atMost(60, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS)
            .until(() -> {
                try {
                    return given().when().get("/actuator/health").then().extract().statusCode() == 200;
                } catch (Exception e) {
                    return false;
                }
            });

        System.out.println("✅ Backend is up! Proceeding with tests.");
    }


    @Test
    void testSignup() {
        String uniqueUsername = "testuser" + Instant.now().toEpochMilli(); // unique each run
        
        given()
            .contentType(ContentType.URLENC)
            .formParam("uname", uniqueUsername)
            .formParam("name", "Test")
            .formParam("familyName", "User")
            .formParam("birthdate", "2000-01-01")
            .formParam("birthPlace", "Test City")
            .formParam("currentCountry", "Test Country")
            .formParam("currentCity", "Test City")
            .formParam("schoolName", "Test School")
            .formParam("gpa", "4.0")
            .formParam("phone", "1234567890")
            .formParam("email", uniqueUsername + "@example.com")
        .when()
            .post("/signup")
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .body(containsString("Welcome"));
    }

    @Test
    void testLogin() {
        String response = given()
            .contentType(ContentType.URLENC)
            .formParam("uname", "testuser")
        .when()
            .post("/login")
        .then()
            .statusCode(anyOf(is(200), is(302))) 
            .extract().asString();

        if (response.contains("Welcome")) { 
            token = "dummy-token";
        } else {
            token = null;
            Assertions.fail("❌ Login failed! No valid token.");
        }
    }

    @Test
    void testQuizAndLogout() {
        if (token == null) testLogin(); // Ensure token is available

        // Submit Quiz: Add score parameter
        given()
            .contentType(ContentType.URLENC)
            .header("Authorization", "Bearer " + token)
            .formParam("score", "10")
        .when()
            .post("/submitQuiz")
        .then()
            .statusCode(200)
            .body(containsString("Score submitted successfully"));

        // Logout: Using GET since your controller maps logout as GET
        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/logout")
        .then()
        .statusCode(anyOf(is(200), is(302)));
    }

    @Test
    void testUserFlow() {
        if (token == null) {
            System.out.println("❌ Login failed! Token is null.");
            Assertions.fail("Login test did not return a valid token.");
        }

        // Create new user using signup endpoint
        String uniqueUname = "user_" + java.util.UUID.randomUUID().toString().substring(0, 8);
        given()
            .contentType(ContentType.URLENC)
            .header("Authorization", "Bearer " + token)
            .formParam("uname", uniqueUname)
            .formParam("name", "New")
            .formParam("familyName", "User")
            .formParam("birthdate", "2000-01-01")
            .formParam("birthPlace", "New City")
            .formParam("currentCountry", "New Country")
            .formParam("currentCity", "New City")
            .formParam("schoolName", "New School")
            .formParam("gpa", "4.0")
            .formParam("phone", "1234567890")
            .formParam("email", "newuser@example.com")
        .when()
            .post("/signup")
        .then()
            .statusCode(anyOf(is(200), is(201)))
            .body(containsString("Welcome"));
    }
}
