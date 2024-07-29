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
public class QuizPageTest {
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
