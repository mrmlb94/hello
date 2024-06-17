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

            // Wait for the username field to be interactable
            WebElement unameField = wait.until(ExpectedConditions.elementToBeClickable(By.name("uname")));
            unameField.sendKeys("testuser");

            WebElement nameField = driver.findElement(By.id("name"));
            nameField.sendKeys("Test");

            WebElement familyNameField = driver.findElement(By.id("familyName"));
            familyNameField.sendKeys("User");

            WebElement birthdateField = driver.findElement(By.id("birthdate"));
            birthdateField.sendKeys("2000-01-01");

            WebElement birthPlaceField = driver.findElement(By.id("birthPlace"));
            birthPlaceField.sendKeys("City");

            WebElement currentCountryField = driver.findElement(By.id("currentCountry"));
            currentCountryField.sendKeys("Country");

            WebElement currentCityField = driver.findElement(By.id("currentCity"));
            currentCityField.sendKeys("City");

            WebElement schoolNameField = driver.findElement(By.id("schoolName"));
            schoolNameField.sendKeys("School");

            WebElement gpaField = driver.findElement(By.id("gpa"));
            gpaField.sendKeys("4.0");

            WebElement phoneField = driver.findElement(By.id("phone"));
            phoneField.sendKeys("1234567890");

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("test@example.com");

            // Wait for the submit button to be interactable
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            submitButton.click();

            // Add assertions or further steps as necessary
            assertTrue(driver.getPageSource().contains("Welcome, testuser"));

        } finally {
            driver.quit();
        }
    }
}
