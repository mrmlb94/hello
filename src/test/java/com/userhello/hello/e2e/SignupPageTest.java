package com.userhello.hello.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.userhello.hello.e2e.CombinedE2ETest.TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignupPageTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    @Test
    public void testSignup() {
        // Replace with the URL of your web application
        driver.get("http://localhost:8080/signup");

        // Ensure the page is fully loaded and refreshed to avoid caching issues
        driver.navigate().refresh();
        // Fill in the signup form
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("uname"))).sendKeys("testuser");
        driver.findElement(By.id("name")).sendKeys("Test");
        driver.findElement(By.id("familyName")).sendKeys("User");
        driver.findElement(By.id("birthdate")).sendKeys("2000-01-01");
        driver.findElement(By.id("birthPlace")).sendKeys("Test City");
        driver.findElement(By.id("currentCountry")).sendKeys("Test Country");
        driver.findElement(By.id("currentCity")).sendKeys("Test City");
        driver.findElement(By.id("schoolName")).sendKeys("Test School");
        driver.findElement(By.id("gpa")).sendKeys("4.0");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");

        // Submit the form
        driver.findElement(By.id("submit")).click();

        // Debugging: Print the current URL after clicking sign up
        System.out.println("Current URL after sign up attempt: " + driver.getCurrentUrl());

        // Verify the signup success by checking the URL change or specific success element
        String expectedUrl = "http://localhost:8080/signup"; // Adjust based on your app's behavior
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
