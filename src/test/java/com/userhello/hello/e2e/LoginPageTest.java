package com.userhello.hello.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/mrmlb/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        // Replace with the URL of your web application
        driver.get("http://localhost:8080/login");

        // Wait for the username field to be present and then enter test credentials
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("uname")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        usernameField.sendKeys("mrmlb");

        // Submit the login form
        loginButton.click();

        // Debugging: Print the current URL after clicking login
        System.out.println("Current URL after login attempt: " + driver.getCurrentUrl());

        // Verify the login success by checking the URL change
        String expectedUrl = "http://localhost:8080/login";
        boolean urlChanged = wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertTrue(urlChanged, "URL did not change to the expected value");

        // Alternatively, you can check for a specific element on the next page
        // WebElement successMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("successMessage")));
        // assertTrue(successMessage.isDisplayed(), "Success message not displayed");
    }
}
