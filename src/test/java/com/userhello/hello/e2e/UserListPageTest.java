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
import java.util.List;
import java.util.UUID;

import static com.userhello.hello.e2e.CombinedE2ETest.TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserListPageTest {
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
    public void testUserFlow() {
        // Replace with the URL of your web application
        driver.get("http://localhost:8080/users");

        // Click "Add New User"
        WebElement addNewUserLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New User")));
        addNewUserLink.click();

        // Fill out the signup form with a unique uname
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

        // Assuming successful signup redirects to a different page, navigate back to the user list page
        driver.get("http://localhost:8080/users");

        // Verify that the table is populated with user data
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tbody tr")));
        assertFalse(rows.isEmpty(), "User list table is empty");

        // Click "Edit" for the first user
        WebElement editLink = rows.get(0).findElement(By.linkText("Edit"));
        editLink.click();

        // Update the user information
        WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
        nameField.clear();
        nameField.sendKeys("Updated Name");
        WebElement familyNameField = driver.findElement(By.name("familyName"));
        familyNameField.clear();
        familyNameField.sendKeys("Updated FamilyName");
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        updateButton.click(); // Assuming there is a "submit" button for updating

        // Verify the user is redirected back to the user list page
        driver.get("http://localhost:8080/users");
        rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("tbody tr")));
        assertFalse(rows.isEmpty(), "User list table is empty after update");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
