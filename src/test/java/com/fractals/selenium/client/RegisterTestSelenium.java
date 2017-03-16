package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test the registration page of the application.
 * @author Aline Shulzhenko
 */
public class RegisterTestSelenium {
    private WebDriver driver;
    private int randomInt;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        Random random = new Random();
        randomInt = random.nextInt();
    }
    
    @Test     
    public void testRegisterFormTitle() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/register.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Registration"));
        driver.quit();
    }

    @Test     
    public void testRegisterFormFill() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/register.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement inputElement = driver.findElement(By.id("registerForm:email"));
        inputElement.clear();        
        inputElement.sendKeys("selenium@selenium.com");
        
        inputElement = driver.findElement(By.id("registerForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("selenium"+randomInt);
        
        inputElement = driver.findElement(By.id("registerForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("abcd");
        
        inputElement = driver.findElement(By.id("registerForm:title"));         
        inputElement.clear();         
        inputElement.sendKeys("mr");
        
        inputElement = driver.findElement(By.id("registerForm:fname"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium");
        
        inputElement = driver.findElement(By.id("registerForm:lname"));         
        inputElement.clear();         
        inputElement.sendKeys("hq");
        
        inputElement = driver.findElement(By.id("registerForm:company"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium sauce");
        
        inputElement = driver.findElement(By.id("registerForm:addr1"));         
        inputElement.clear();         
        inputElement.sendKeys("3040 Sherbrooke St. W");
        
        inputElement = driver.findElement(By.id("registerForm:city"));         
        inputElement.clear();         
        inputElement.sendKeys("Westmount");
        
        Select dropdown = new Select(driver.findElement(By.id("registerForm:province")));
        dropdown.selectByVisibleText("Ontario");
        
        inputElement = driver.findElement(By.id("registerForm:country"));         
        inputElement.clear();         
        inputElement.sendKeys("Canada");
        
        inputElement = driver.findElement(By.id("registerForm:postal"));         
        inputElement.clear();         
        inputElement.sendKeys("h3z1a4");
        
        inputElement = driver.findElement(By.id("registerForm:hometel"));         
        inputElement.clear();         
        inputElement.sendKeys("514-931-8731");
        
        driver.findElement(By.id("registerForm:register")).click();
        
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        driver.quit();
    }
    
    @Test     
    public void testRegisterForm_EmptyFields() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/register.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement inputElement = driver.findElement(By.id("registerForm:email"));
        inputElement.clear();        
        
        inputElement = driver.findElement(By.id("registerForm:username"));
        inputElement.clear();        
        
        inputElement = driver.findElement(By.id("registerForm:password"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:title"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:fname"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:lname"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:company"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:addr1"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:city"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:country"));         
        inputElement.clear();         
        
        inputElement = driver.findElement(By.id("registerForm:postal"));         
        inputElement.clear();               
        
        driver.findElement(By.id("registerForm:register")).click();
        
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement msgP = driver.findElement(By.id("registerForm:passwordMessage"));
        assertThat(msgP.getText()).isEqualToIgnoringCase("This value is required.");
        WebElement msgU = driver.findElement(By.id("registerForm:usernameMessage"));
        assertThat(msgU.getText()).isEqualToIgnoringCase("This value is required.");
        
        driver.quit();
    }
    
    @Test     
    public void testRegisterForm_InvalidValues() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/register.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement inputElement = driver.findElement(By.id("registerForm:email"));
        inputElement.clear();        
        inputElement.sendKeys("selenium@selenium");
        
        inputElement = driver.findElement(By.id("registerForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("selenium"+randomInt);
        
        inputElement = driver.findElement(By.id("registerForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("abcd");
        
        inputElement = driver.findElement(By.id("registerForm:title"));         
        inputElement.clear();         
        inputElement.sendKeys("mr");
        
        inputElement = driver.findElement(By.id("registerForm:fname"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium");
        
        inputElement = driver.findElement(By.id("registerForm:lname"));         
        inputElement.clear();         
        inputElement.sendKeys("hq");
        
        inputElement = driver.findElement(By.id("registerForm:company"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium sauce");
        
        inputElement = driver.findElement(By.id("registerForm:addr1"));         
        inputElement.clear();         
        inputElement.sendKeys("3040 Sherbrooke St. W");
        
        inputElement = driver.findElement(By.id("registerForm:city"));         
        inputElement.clear();         
        inputElement.sendKeys("Westmount");
        
        /*Select dropdown = new Select(driver.findElement(By.id("registerForm:province")));
        dropdown.selectByVisibleText("New York");*/
        
        inputElement = driver.findElement(By.id("registerForm:country"));         
        inputElement.clear();         
        inputElement.sendKeys("Canada");
        
        inputElement = driver.findElement(By.id("registerForm:postal"));         
        inputElement.clear();         
        inputElement.sendKeys("h331a4");
        
        inputElement = driver.findElement(By.id("registerForm:hometel"));         
        inputElement.clear();         
        inputElement.sendKeys("phone number");
        
        driver.findElement(By.id("registerForm:register")).click();
        
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        
        WebElement msg = driver.findElement(By.id("registerForm:emailMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("Invalid email address.");
        
        msg = driver.findElement(By.id("registerForm:postalMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("This postal code value is invalid.");
        
        msg = driver.findElement(By.id("registerForm:hometelMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("This phone number is invalid.");
        
        driver.quit();
    }
    
    @Test
    public void testRegisterForm_InvalidUsername() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/register.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement inputElement = driver.findElement(By.id("registerForm:email"));
        inputElement.clear();        
        inputElement.sendKeys("selenium@selenium.com");
        
        inputElement = driver.findElement(By.id("registerForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("tmason0");
        
        inputElement = driver.findElement(By.id("registerForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("abcd");
        
        inputElement = driver.findElement(By.id("registerForm:title"));         
        inputElement.clear();         
        inputElement.sendKeys("mr");
        
        inputElement = driver.findElement(By.id("registerForm:fname"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium");
        
        inputElement = driver.findElement(By.id("registerForm:lname"));         
        inputElement.clear();         
        inputElement.sendKeys("hq");
        
        inputElement = driver.findElement(By.id("registerForm:company"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium sauce");
        
        inputElement = driver.findElement(By.id("registerForm:addr1"));         
        inputElement.clear();         
        inputElement.sendKeys("3040 Sherbrooke St. W");
        
        inputElement = driver.findElement(By.id("registerForm:city"));         
        inputElement.clear();         
        inputElement.sendKeys("Westmount");
        
        Select dropdown = new Select(driver.findElement(By.id("registerForm:province")));
        dropdown.selectByVisibleText("Ontario");
        
        inputElement = driver.findElement(By.id("registerForm:country"));         
        inputElement.clear();         
        inputElement.sendKeys("Canada");
        
        inputElement = driver.findElement(By.id("registerForm:postal"));         
        inputElement.clear();         
        inputElement.sendKeys("h3z1a4");
        
        inputElement = driver.findElement(By.id("registerForm:hometel"));         
        inputElement.clear();         
        inputElement.sendKeys("514-931-8731");
        
        driver.findElement(By.id("registerForm:register")).click();
        
        wait.until(ExpectedConditions.titleIs("Registration"));
        
        WebElement msg = driver.findElement(By.id("registerForm:usernameMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("Such user already exists.");
        
        driver.quit();
    }
}
