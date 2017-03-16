package com.fractals.selenium.client;

import com.fractals.utilities.SeleniumAjaxHelper;
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
import org.openqa.selenium.support.ui.WebDriverWait;;

/**
 * Tests the checkout page of the application.
 * @author Aline Shulzhenko
 */
public class CheckoutTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
        
        //add items to the cart
        driver.get("http://localhost:8080/Fractals/client/Track.xhtml?id=2");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
        driver.get("http://localhost:8080/Fractals/client/Track.xhtml?id=13");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
    }

    @Test     
    public void testCheckoutTitle() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        driver.quit();
    }

    @Test     
    public void testCheckout_NotLoggedIn() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        driver.quit();
    }

    @Test     
    public void testCheckoutFormFill_MasterCard() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        WebElement inputElement = driver.findElement(By.id("checkoutForm:number"));
        inputElement.clear();        
        inputElement.sendKeys("79927398713");
        
        inputElement = driver.findElement(By.id("checkoutForm:date"));         
        inputElement.clear();         
        inputElement.sendKeys("12/2112");
        
        inputElement = driver.findElement(By.id("checkoutForm:name"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium");
        
        driver.findElement(By.id("checkoutForm:checkout")).click();
        
        wait.until(ExpectedConditions.titleIs("Invoice"));
        
        driver.quit();
    }

    @Test     
    public void testCheckoutFormFill_Visa() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        helper.retryFindClick(By.linkText("Visa"));
        
        helper.retryFindSendKeys(By.id("checkoutForm:number"), "79927398713");
        
        helper.retryFindSendKeys(By.id("checkoutForm:date"), "12/2112");
        
        helper.retryFindSendKeys(By.id("checkoutForm:name"), "selenium");
        
        helper.retryFindSendKeys(By.id("checkoutForm:code"), "123");
        
        helper.retryFindClick(By.id("checkoutForm:checkout"));
        
        wait.until(ExpectedConditions.titleIs("Invoice"));
        
        driver.quit();
    }

    @Test     
    public void testCheckoutFormFill_Clear() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        WebElement inputElement = driver.findElement(By.id("checkoutForm:number"));
        inputElement.clear();        
        inputElement.sendKeys("79927398713");
        
        inputElement = driver.findElement(By.id("checkoutForm:date"));         
        inputElement.clear();         
        inputElement.sendKeys("12/2112");
        
        inputElement = driver.findElement(By.id("checkoutForm:name"));         
        inputElement.clear();         
        inputElement.sendKeys("selenium");
        
        driver.findElement(By.id("checkoutForm:clear")).click();
        
        WebElement msg = driver.findElement(By.id("checkoutForm:number"));
        assertThat(msg.getText()).isEqualToIgnoringCase("");
        
        msg = driver.findElement(By.id("checkoutForm:date"));
        assertThat(msg.getText()).isEqualToIgnoringCase("");
        
        msg = driver.findElement(By.id("checkoutForm:name"));
        assertThat(msg.getText()).isEqualToIgnoringCase("");
        
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        driver.quit();
    }
    
    @Test     
    public void testCheckoutFormFill_EmptyFields() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        WebElement inputElement = driver.findElement(By.id("checkoutForm:number"));
        inputElement.clear();        
        inputElement.sendKeys("");
        
        inputElement = driver.findElement(By.id("checkoutForm:date"));         
        inputElement.clear();         
        inputElement.sendKeys("12/12");
        
        inputElement = driver.findElement(By.id("checkoutForm:name"));         
        inputElement.clear();         
        inputElement.sendKeys("");
        
        driver.findElement(By.id("checkoutForm:checkout")).click();
        
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        WebElement msg = driver.findElement(By.id("checkoutForm:numberMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("This value is required.");
        
        msg = driver.findElement(By.id("checkoutForm:dateMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("Invalid format");
        
        msg = driver.findElement(By.id("checkoutForm:nameMessage"));
        assertThat(msg.getText()).isEqualToIgnoringCase("This value is required.");
        
        driver.quit();
    }
    
    @Test     
    public void testCheckoutFormFill_InvalidData() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/checkout.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        helper.retryFindClick(By.linkText("Visa"));
        
        helper.retryFindSendKeys(By.id("checkoutForm:number"), "21");
        
        helper.retryFindSendKeys(By.id("checkoutForm:date"), "12/2002");
        
        helper.retryFindSendKeys(By.id("checkoutForm:name"), "selenium");
        
        helper.retryFindSendKeys(By.id("checkoutForm:code"), "1");
        
        helper.retryFindClick(By.id("checkoutForm:checkout"));
        
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        
        assertThat(helper.retryFindGetText(By.id("checkoutForm:numberMessage")))
                .isEqualToIgnoringCase("Invalid credit card number");
        
        assertThat(helper.retryFindGetText(By.id("checkoutForm:dateMessage")))
                .isEqualToIgnoringCase("This credit card has expired");
        
        assertThat(helper.retryFindGetText(By.id("checkoutForm:codeMessage")))
                .isEqualToIgnoringCase("Invalid code for Visa card");
        
        driver.quit();
    }
}

//registration clear