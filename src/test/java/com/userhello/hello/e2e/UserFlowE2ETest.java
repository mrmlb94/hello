package com.userhello.hello.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserFlowE2ETest {

    @Test
    public void testSignupAndLogin() {
        System.setProperty("webdriver.chrome.driver", "/Users/reza/chromedriver-mac-x64/chromedriver");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("http://localhost:8080/signup");

            System.out.println("Opened signup page");

            WebElement unameField = wait.until(ExpectedConditions.elementToBeClickable(By.name("uname")));
            unameField.sendKeys("testuser");
            System.out.println("Entered username");

            WebElement nameField = driver.findElement(By.id("name"));
            nameField.sendKeys("Test");
            System.out.println("Entered name");

            WebElement familyNameField = driver.findElement(By.id("familyName"));
            familyNameField.sendKeys("User");
            System.out.println("Entered family name");

            WebElement birthdateField = driver.findElement(By.id("birthdate"));
            birthdateField.sendKeys("2000-01-01");
            System.out.println("Entered birthdate");

            WebElement birthPlaceField = driver.findElement(By.id("birthPlace"));
            birthPlaceField.sendKeys("City");
            System.out.println("Entered birthplace");

            WebElement currentCountryField = driver.findElement(By.id("currentCountry"));
            currentCountryField.sendKeys("Country");
            System.out.println("Entered current country");

            WebElement currentCityField = driver.findElement(By.id("currentCity"));
            currentCityField.sendKeys("City");
            System.out.println("Entered current city");

            WebElement schoolNameField = driver.findElement(By.id("schoolName"));
            schoolNameField.sendKeys("School");
            System.out.println("Entered school name");

            WebElement gpaField = driver.findElement(By.id("gpa"));
            gpaField.sendKeys("4.0");
            System.out.println("Entered GPA");

            WebElement phoneField = driver.findElement(By.id("phone"));
            phoneField.sendKeys("1234567890");
            System.out.println("Entered phone");

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("test@example.com");
            System.out.println("Entered email");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            submitButton.click();
            System.out.println("Clicked submit button");

            boolean isSignupSuccessful = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Welcome, Test!"));
            assertTrue(isSignupSuccessful, "Signup was not successful");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed due to exception: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}