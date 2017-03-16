package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import com.fractals.utilities.SeleniumAjaxHelper;
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
 * Test the search page of the application.
 * @author Aline Shulzhenko
 */
public class SearchTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
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

        helper.retryFindSelect(By.id("searchForm:choice"), "Search by artist name");
        
        helper.retryFindSendKeys(By.id("searchForm:key"), "blind guardian");
        
        helper.retryFindClick(By.id("searchForm:search"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'A Twist in the Myth')]")).isDisplayed());
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'Turn the Page')]")).isDisplayed());
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_EmptyKey() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));
     
        helper.retryFindSelect(By.id("searchForm:choice"), "Search by artist name");              
        
        helper.retryFindClick(By.id("searchForm:search"));
        
        String msg = helper.retryFindGetText(By.id("searchForm:keyMessage"));
        assertThat(msg).isEqualToIgnoringCase("This value is required.");
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_NoResults() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));
       
        helper.retryFindSelect(By.id("searchForm:choice"), "Search by artist name");
        
        helper.retryFindSendKeys(By.id("searchForm:key"), "qwerty");
        
        helper.retryFindClick(By.id("searchForm:search"));
        
        String msg = helper.retryFindGetText(By.cssSelector("span#results p"));
        assertThat(msg).isEqualToIgnoringCase("There is no data available to display :(");    
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_Date() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        helper.retryFindSelect(By.id("searchForm:choice"), "Search by created date");
        
        helper.retryFindClick(By.id("searchForm:search"));
        
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'A Twist in the Myth')]")).isDisplayed());
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//*[contains(text(), 'Turn the Page')]")).isDisplayed());
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_OneResultRedirect() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        helper.retryFindSelect(By.id("searchForm:choice"), "Search by track name");
        
        helper.retryFindSendKeys(By.id("searchForm:key"), "a farewell to kings");
        
        helper.retryFindClick(By.id("searchForm:search"));
        
        wait.until(ExpectedConditions.titleIs("A Farewell to Kings"));
        
        driver.quit();
    }
    
    @Test     
    public void testSearch_LinkToNextPage() throws Exception {
        driver.get("http://localhost:8080/Fractals/client/search.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Search"));

        helper.retryFindSelect(By.id("searchForm:choice"), "Search by artist name");
        
        helper.retryFindSendKeys(By.id("searchForm:key"), "blind guardian");
        
        helper.retryFindClick(By.id("searchForm:search"));
        helper.clickNextLink();
        
        wait.until(ExpectedConditions.titleIs("Turn the Page"));
        
        driver.quit();
    }
    
}
