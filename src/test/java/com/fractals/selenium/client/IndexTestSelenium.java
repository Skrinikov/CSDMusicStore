package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.List;
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
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Danieil
 */
@Ignore
public class IndexTestSelenium {
    
    private WebDriver driver;
    private Random random;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        random = new Random();
    }
    
    /**
     * Checks to see if the index can be accessed.
     * @throws Exception 
     */
    @Test     
    public void testIndexFormTitle() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/index.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        driver.quit();
    }
    
    /**
     * Checks if the index can be accessed via default path.
     * @throws Exception 
     */
    @Test     
    public void testIndexFormNormalUrl() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        driver.quit();
    }
    /**
     * Tests to see if the login button works.
     * 
     * @throws Exception 
     */   
    @Test
    public void testIndexToLoginBtn() throws Exception{
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        WebElement btn = driver.findElement(By.id("to-login-btn"));
        btn.click();
        
        wait.until(ExpectedConditions.titleIs("Login"));
        
        driver.quit();
    }
    
    /**
     * Randomly chooses a surey option and checks that it is updated.
     * @throws Exception 
     */
    @Test
    public void testIndexSurveyOptions() throws Exception{
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        WebElement container = driver.findElement(By.id("survey-options"));
        List<WebElement> options = container.findElements(By.tagName("a"));
        
        int rand = (int)(Math.random() * 4);       
        options.get(rand).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("survey-answers")));        
        driver.quit();
    }
    
    /**
     * Randomly chooses a surey option and checks that it is updated. Then refreshes
     * the page to see if the result persists. 
     * 
     * @throws Exception 
     */
    @Test
    public void testIndexSurveyOptionsWithRefresh() throws Exception{
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        WebElement container = driver.findElement(By.id("survey-options"));
        List<WebElement> options = container.findElements(By.tagName("a"));
        
        int rand = (int)(Math.random() * 4);       
        options.get(rand).click();     
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("survey-answers")));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("survey-answers")));
        
        driver.quit();
    }
    
    /**
     * Checks if there are 3 new release titles. 
     * @throws Exception 
     */
    @Test
    public void testReleaseCount() throws Exception{
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        WebElement container = driver.findElement(By.id("releases"));
        List<WebElement> options = container.findElements(By.tagName("div"));
        assertThat(options).hasSize(3);
        driver.quit();
        
    }
    
    /**
     * Checks if the rss is present on the index page
     * 
     * @throws Exception 
     */
    @Test
    public void testIndexRss() throws Exception{
        driver.get("http://localhost:10860/CSDMusicStore/");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rss")));
        
        driver.quit();
    }
    
}
