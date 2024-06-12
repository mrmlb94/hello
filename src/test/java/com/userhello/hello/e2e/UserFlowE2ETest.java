package com.userhello.hello.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserFlowE2ETest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/reza/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/signup");
    }

    @Test
    public void testSignupAndLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit")));  // Ensure element is present in the DOM
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));  // Wait for element to be visible and clickable
        WebElement usernameInput = driver.findElement(By.name("uname"));
        WebElement emailInput = driver.findElement(By.name("email"));

        usernameInput.sendKeys("testuser");
        emailInput.sendKeys("testuser@example.com");
        submitButton.click();  // First click to submit the signup form

        // Adding a wait to ensure form is processed and the welcome page is loaded
        wait.until(ExpectedConditions.urlContains("welcome"));
        assertTrue(driver.getCurrentUrl().contains("welcome"));

        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout")));
        logoutLink.click();
        assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
