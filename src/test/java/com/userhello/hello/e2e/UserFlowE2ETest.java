package com.userhello.hello.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserFlowE2ETest {

    private boolean isServerUp(String url) {
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            return (code == 200);
        } catch (IOException e) {
            return false;
        }
    }

    @Test
    public void testSignupAndLogin() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/reza/chromedriver-mac-x64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");  // Run Chrome in headless mode
        options.addArguments("--disable-gpu"); // applicable to Windows environment
        options.addArguments("--window-size=1920,1200");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-infobars");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Adjust the timeout as needed

        // Wait for the server to start
        while (!isServerUp("http://localhost:8080/signup")) {
            System.out.println("Waiting for server to start...");
            Thread.sleep(1000); // Wait for 1 second
        }

        try {
            driver.get("http://localhost:8080/signup");

            System.out.println("Opened signup page");

            String randomUsername = "testuser" + UUID.randomUUID().toString().substring(0, 8);

            WebElement unameField = wait.until(ExpectedConditions.elementToBeClickable(By.name("uname")));
            unameField.sendKeys(randomUsername);
            System.out.println("Entered username: " + randomUsername);

            WebElement nameField = driver.findElement(By.id("name"));
            nameField.sendKeys("Test");
            System.out.println("Entered name");

            WebElement familyNameField = driver.findElement(By.id("familyName"));
            familyNameField.sendKeys("User");
            System.out.println("Entered family name");

            WebElement birthdateField = driver.findElement(By.id("birthdate"));
            birthdateField.sendKeys("2000-01-01");
            System.out.println("Entered birthdate");

            WebElement birthPlaceField = driver.findElement(By.id("birthPlace"));
            birthPlaceField.sendKeys("City");
            System.out.println("Entered birthplace");

            WebElement currentCountryField = driver.findElement(By.id("currentCountry"));
            currentCountryField.sendKeys("Country");
            System.out.println("Entered current country");

            WebElement currentCityField = driver.findElement(By.id("currentCity"));
            currentCityField.sendKeys("City");
            System.out.println("Entered current city");

            WebElement schoolNameField = driver.findElement(By.id("schoolName"));
            schoolNameField.sendKeys("School");
            System.out.println("Entered school name");

            WebElement gpaField = driver.findElement(By.id("gpa"));
            gpaField.sendKeys("4.0");
            System.out.println("Entered GPA");

            WebElement phoneField = driver.findElement(By.id("phone"));
            phoneField.sendKeys("1234567890");
            System.out.println("Entered phone");

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("test@example.com");
            System.out.println("Entered email");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
            submitButton.click();
            System.out.println("Clicked submit button");

            // Check for the presence of the welcome message
            boolean isSignupSuccessful = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Welcome, Test!"));
            assertTrue(isSignupSuccessful, "Signup was not successful");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed due to exception: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
