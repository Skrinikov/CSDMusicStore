package com.fractals.selenium.client;

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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test the search page of the application.
 * @author Aline Shulzhenko
 */
public class SearchTestSelenium {
    private WebDriver driver;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
    }
    
    @Test     
    public void testSearchTitle() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));
        driver.quit();
    }
    
    @Test     
    public void testSearchFormFill() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        Select dropdown = new Select(driver.findElement(By.id("searchForm:choice")));
        dropdown.selectByVisibleText("Search by artist name");
        
        retryFindSendKeys(By.id("searchForm:key"), "blind guardian");
        
        retryFindClick(By.id("searchForm:search"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'A Twist in the Myth')]")).isDisplayed());
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'Turn the Page')]")).isDisplayed());
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_EmptyKey() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));
        
        new Select(driver.findElement(By.id("searchForm:choice"))).selectByVisibleText("Search by artist name");              
        
        retryFindClick(By.id("searchForm:search"));
        
        String msg = retryFindGetText(By.id("searchForm:keyMessage"));
        assertThat(msg).isEqualToIgnoringCase("This value is required.");
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_NoResults() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));
       
        new Select(driver.findElement(By.id("searchForm:choice"))).selectByVisibleText("Search by artist name");
        
        retryFindSendKeys(By.id("searchForm:key"), "qwerty");
        
        retryFindClick(By.id("searchForm:search"));
        
        String msg = retryFindGetText(By.cssSelector("span#results p"));
        assertThat(msg).isEqualToIgnoringCase("There is no data available to display :(");    
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_Date() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        new Select(driver.findElement(By.id("searchForm:choice"))).selectByVisibleText("Search by created date");
        
        retryFindClick(By.id("searchForm:search"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'A Twist in the Myth')]")).isDisplayed());
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'Turn the Page')]")).isDisplayed());
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_OneResultRedirect() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        new Select(driver.findElement(By.id("searchForm:choice"))).selectByVisibleText("Search by track name");
        
        retryFindSendKeys(By.id("searchForm:key"), "a farewell to kings");
        
        retryFindClick(By.id("searchForm:search"));
        
        wait.until(ExpectedConditions.titleIs("A Farewell to Kings"));
        
        driver.quit();
    }
    
    /**
     * Retries to find the element and execute the click on the element.
     * The courtesy of http://darrellgrainger.blogspot.ca/2012/06/staleelementexception.html
     * @param by To find this element.
     */
    private void retryFindClick(By by) {
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
    private void retryFindSendKeys(By by, String key) {
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
    private String retryFindGetText(By by) {
        boolean result = false;
        while(!result) {
            try {
                result = true;
                return driver.findElement(by).getText();
            } 
            catch(org.openqa.selenium.StaleElementReferenceException e) {}
        }
        return null;
    }
}
