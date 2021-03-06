package com.fractals.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Set of helper methods that are useful when working with the ajax page
 * and selenium tests.
 * The idea is the courtesy of http://darrellgrainger.blogspot.ca/2012/06/staleelementexception.html
 * @author Aline Shulzhenko
 */
public class SeleniumAjaxHelper {
    private WebDriver driver;
    
    /**
     * Initializes the object.
     * @param driver WebDriver.
     */
    public SeleniumAjaxHelper(WebDriver driver) {
        this.driver = driver;
    }
    /**
     * Retries to find the element and execute the click on the element.
     * @param by To find this element.
     */
    public void retryFindClick(By by) {
        boolean result = false;
        while(!result) {
            try {
                driver.findElement(by).click();
                result = true;
            } 
            catch(org.openqa.selenium.StaleElementReferenceException e) {}
        }
    }
    
    /**
     * Retries to find the element and execute the send a key for the element.
     * @param by To find this element.
     * @param key A key to send.
     */
    public void retryFindSendKeys(By by, String key) {
        boolean result = false;
        while(!result) {
            try {
                driver.findElement(by).sendKeys(key);
                result = true;
            } 
            catch(org.openqa.selenium.StaleElementReferenceException e) {}
        }
    }
    
    /**
     * Retries to find the element and get the text of the element.
     * @param by To find this element.
     * @return the text of this element.
     */
    public String retryFindGetText(By by) {
        boolean result = false;
        while(!result) {
            try {
                return driver.findElement(by).getText();
            } 
            catch(org.openqa.selenium.StaleElementReferenceException e) {}
        }
        return null;
    }
    
    /**
     * Retries to find the option menu and select one of the options.
     * @param by To find this element.
     * @param select The option to select.
     */
    public void retryFindSelect(By by, String select) {
        boolean result = false;
        while(!result) {
            try {
                new Select(driver.findElement(by)).selectByVisibleText(select);
                result = true;
            } 
            catch(org.openqa.selenium.StaleElementReferenceException e) {}
        }
    }
    
    /**
     * Retries to find the link a and click on the element.
     */
    public void clickNextLink() {
        boolean result = false;
        while(!result) {
            try {
                driver.findElement(By.cssSelector("div#search-track-list a")).click();
                result = true;
            } 
            catch(NoSuchElementException e) {}
        }
    }
    
    /**
     * Logs in the tester.
     */
    public void login() {
        driver.get("http://localhost:8080/CSDMusicStore/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("a");
        
        inputElement = driver.findElement(By.id("loginForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("a");
        
        driver.findElement(By.id("loginForm:login")).click();
    }
    
    /**
     * Buys tracks that are currently in the shopping cart.
     */
    public void buy() {
        driver.get("http://localhost:8080/CSDMusicStore/client/checkout.xhtml");
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
    }
    
    /**
     * Returns false if TimeoutException happens.
     * @param e ExpectedCondition
     * @param wait WebDriverWait
     * @return false if TimeoutException happens.
     * @author MOUFFOK Sarah
     */
    public boolean toBoolean(ExpectedCondition e, WebDriverWait wait) {
        try {
            wait.until(e);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
}
