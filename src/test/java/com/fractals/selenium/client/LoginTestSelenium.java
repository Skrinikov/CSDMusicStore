package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test the login page of the application.
 * @author Aline Shulzhenko
 */
public class LoginTestSelenium {
    
    private WebDriver driver;
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("LoginTestSelenium.class");
    
     @Before     
     public void setUp() {
        /*FirefoxDriverManager.getInstance().setup();
        driver = new FirefoxDriver();*/
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
     }

    @Test     
    public void testLoginFormTitle() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        driver.quit();
    }

    @Test     
    public void testLoginFormFill() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("lynn");
        
        inputElement = driver.findElement(By.id("loginForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("abcd");
        
        driver.findElement(By.id("loginForm:login")).click();
        
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        driver.quit();
    }

    @Test     
    public void testLoginForm_InvalidPassword() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("lynn");
        
        inputElement = driver.findElement(By.id("loginForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("qwerty");
        
        driver.findElement(By.id("loginForm:login")).click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement msg = driver.findElement(By.cssSelector("div span#loginFormMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("Invalid username or password!");
        
        driver.quit();
    }

    @Test     
    public void testLoginForm_InvalidUser() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("tmason");
        
        inputElement = driver.findElement(By.id("loginForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("qwerty");
        
        driver.findElement(By.id("loginForm:login")).click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement msg = driver.findElement(By.cssSelector("div span#loginFormMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("Invalid username or password!");
        
        driver.quit();
    }
    
    @Test     
    public void testLoginForm_EmptyFields() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        driver.findElement(By.id("loginForm:login")).click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement msgP = driver.findElement(By.id("loginForm:passwordMessage"));
        assertThat(msgP.getText()).isEqualToIgnoringCase("This value is required.");
        WebElement msgU = driver.findElement(By.id("loginForm:usernameMessage"));
        assertThat(msgU.getText()).isEqualToIgnoringCase("This value is required.");
        
        driver.quit();
    }
    
}
