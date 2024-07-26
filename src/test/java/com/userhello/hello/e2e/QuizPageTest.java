package com.userhello.hello.e2e;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuizPageTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        // Setting system properties for WebDriver, assuming ChromeDriver is available in the path specified in the Dockerfile
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
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

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
