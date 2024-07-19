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

public class QuizPageTest {

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
    public void testQuizAndLogout() {
        // Replace with the URL of your web application
        driver.get("http://localhost:8080/quiz");

        // Fill in the quiz form
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vivaldi1"))).click();
        driver.findElement(By.id("crescendo2")).click();
        driver.findElement(By.id("12_3")).click();
        driver.findElement(By.id("3_1414")).click();

        // Submit the quiz form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Click the logout button
        driver.findElement(By.id("logoutButton")).click();

        // Verify the user is redirected to the login page
        boolean urlChanged = wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login"));
        assertTrue(urlChanged, "URL did not change to the expected login page");
    }
}
