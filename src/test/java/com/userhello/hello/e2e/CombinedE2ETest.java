package com.userhello.hello.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CombinedE2ETest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    static final int TIMEOUT = 10;

    @BeforeAll
    void setup() {
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
    @Order(1)
    void testSignup() {
        driver.get("http://localhost:8080/signup");
        driver.navigate().refresh();

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
        driver.findElement(By.id("submit")).click();

        String expectedUrl = "http://localhost:8080/signup";
        boolean urlChanged = wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertTrue(urlChanged, "URL did not change to the expected value");
    }

    @Test
    @Order(2)
    void testLogin() {
        driver.get("http://localhost:8080/login");

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("uname")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        usernameField.sendKeys("testuser");
        loginButton.click();

        String expectedUrl = "http://localhost:8080/login";
        boolean urlChanged = wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertTrue(urlChanged, "URL did not change to the expected value");
    }

    @Test
    @Order(3)
    void testQuizAndLogout() {
        driver.get("http://localhost:8080/quiz");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vivaldi1"))).click();
        driver.findElement(By.id("crescendo2")).click();
        driver.findElement(By.id("12_3")).click();
        driver.findElement(By.id("3_1414")).click();

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.id("logoutButton")).click();

        boolean urlChanged = wait.until(ExpectedConditions.urlToBe("http://localhost:8080/login"));
        assertTrue(urlChanged, "URL did not change to the expected login page");
    }

    @Test
    @Order(4)
    void testUserFlow() {
        driver.get("http://localhost:8080/users");

        WebElement addNewUserLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New User")));
        addNewUserLink.click();

        String uniqueUname = "user_" + UUID.randomUUID().toString().substring(0, 8);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("uname"))).sendKeys(uniqueUname);
        driver.findElement(By.id("name")).sendKeys("New");
        driver.findElement(By.id("familyName")).sendKeys("User");
        driver.findElement(By.id("birthdate")).sendKeys("2000-01-01");
        driver.findElement(By.id("birthPlace")).sendKeys("New City");
        driver.findElement(By.id("currentCountry")).sendKeys("New Country");
        driver.findElement(By.id("currentCity")).sendKeys("New City");
        driver.findElement(By.id("schoolName")).sendKeys("New School");
        driver.findElement(By.id("gpa")).sendKeys("4.0");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        driver.findElement(By.id("email")).sendKeys("newuser@example.com");
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        submitButton.click();

        driver.get("http://localhost:8080/users");
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tbody tr")));
        assertFalse(rows.isEmpty(), "User list table is empty");

        WebElement editLink = rows.get(0).findElement(By.linkText("Edit"));
        editLink.click();

        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        nameField.clear();
        nameField.sendKeys("Updated Name");
        WebElement familyNameField = driver.findElement(By.name("familyName"));
        familyNameField.clear();
        familyNameField.sendKeys("Updated FamilyName");
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        updateButton.click();

        driver.get("http://localhost:8080/users");
        rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tbody tr")));
        assertFalse(rows.isEmpty(), "User list table is empty after update");
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
