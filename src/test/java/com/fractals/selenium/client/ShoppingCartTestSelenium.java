package com.fractals.selenium.client;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
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
 * 
 * N.B. The data in orders table for selenium user should be cleaned up each time
 * before this test class executes to ensure that all tracks were never bought before, which
 * can create anomalies in test results.
 * 
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
        
        //add items to the cart
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=2");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=3");
        driver.findElement(By.id("trackCartForm:track-to-cart")).click();
    }

    @Test     
    public void testShoppingCartTitle() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        driver.quit();
    }

    @Test     
    public void testShoppingCart_ContinueShoppingLink() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        driver.get("http://localhost:8080/CSDMusicStore/client/search.xhtml");
        driver.findElement(By.xpath("//*[@id=\"shop-cart-2\"]")).click();
        
        driver.findElement(By.id("continueForm:continue-shop-btn")).click();
               
        wait.until(ExpectedConditions.titleIs("Advanced Search"));
        driver.quit();
    }

    @Test     
    public void testShoppingCart_LoginLink() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("log-link")).click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        driver.quit();
    }

    @Test     
    public void testShoppingCart_DeleteItem() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Cart (1)"));
        
        driver.quit();
    }

    @Test     
    public void testShoppingCart_ItemLink() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.xpath("//*[@id=\"cart-form:cart-table\"]/tbody/tr[1]/td[3]/a")).click();
        
        String title = driver.getTitle();
        assertThat(title).isIn("Turn the Page", "Shots");
        
        driver.quit();
    }
 
    @Test     
    public void testShoppingCart_CheckoutLink() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        driver.findElement(By.id("checkout-link")).click();
        
        wait.until(ExpectedConditions.titleIs("Checkout"));
        
        driver.quit();
    }

    @Test     
    public void testShoppingCart_NoItems() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/shopping_cart.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));
        
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        helper.retryFindClick(By.xpath("//*[@id=\"cart-form:cart-table:0:cart-remove\"]"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Cart (0)"));
        assertThat(driver.findElement(By.id("nodata")).getText()).isEqualToIgnoringCase("There are no items in your shopping cart.");
        
        driver.quit();
    }
    
    
}