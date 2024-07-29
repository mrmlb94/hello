package com.userhello.hello.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginPageTest {
    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        // Use WebDriverManager to manage ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Setup Chrome options for running headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Ensure it runs in headless mode
        options.addArguments("--no-sandbox");  // Bypass OS security model, required for Docker
        options.addArguments("--disable-dev-shm-usage");  // Overcome limited resource problems
        options.addArguments("--disable-gpu");  // GPU hardware acceleration isn't useful for tests

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testLogin() {
        driver.get("http://localhost:8080/login");
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("uname")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        usernameField.sendKeys("mrmlb");
        loginButton.click();
        String expectedUrl = "http://localhost:8080/login";
        boolean urlChanged = wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertTrue(urlChanged, "URL did not change to the expected value");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
