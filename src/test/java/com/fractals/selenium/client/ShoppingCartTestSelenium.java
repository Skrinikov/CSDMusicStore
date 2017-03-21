package com.fractals.selenium.client;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the shopping cart page of the application.
 * @author Aline Shulzhenko
 */
public class ShoppingCartTestSelenium {
    
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
        Random random = new Random();
        
        //add items to the cart
        driver.get("http://localhost:8080/Fractals/client/Track.xhtml?id=2");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
        int val = random.nextInt(132)+4;
        driver.get("http://localhost:8080/Fractals/client/Track.xhtml?id="+val);
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
    }

    @Test     
    public void testShoppingCartTitle() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        driver.quit();
    }

    @Test     
    public void testShoppingCart_ContinueShoppingLink() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        driver.get("http://localhost:8080/Fractals/client/Track.xhtml?id=28");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
        
        driver.findElement(By.id("continueForm:continue-shop-btn")).click();
               
        wait.until(ExpectedConditions.titleIs("Blind Eye"));
        driver.quit();
    }

    @Test     
    public void testShoppingCart_LoginLink() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("log-link")).click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        driver.quit();
    }

    @Test     
    public void testShoppingCart_DeleteItem() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Shopping Cart (1)"));
        
        driver.quit();
    }

    @Test     
    public void testShoppingCart_ItemLink() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.xpath("//*[@id=\"cart-form:cart-table\"]/tbody/tr[1]/td[3]/a")).click();
        
        wait.until(ExpectedConditions.titleIs("Turn the Page"));
        
        driver.quit();
    }
    
    @Test     
    public void testShoppingCart_CheckoutLink() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("checkout-link")).click();
        
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        driver.quit();
    }
    
    @Test     
    public void testShoppingCart_NoItems() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Shopping Cart (0)"));
        assertThat(driver.findElement(By.id("nodata")).getText()).isEqualToIgnoringCase("There are no items in your shopping cart.");
        
        driver.quit();
    }
    
    
}