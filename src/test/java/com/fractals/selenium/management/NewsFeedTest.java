//
//package com.fractals.selenium.management;
//
//import io.github.bonigarcia.wdm.ChromeDriverManager;
//import io.github.bonigarcia.wdm.FirefoxDriverManager;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.util.List; 
//import static org.assertj.core.api.Assertions.assertThat;
///**
// * Selenium test for all pages concerning the news feed. Will test if news pages 
// * are capable of performing all CRUD operations successfully. 
// * @author Renuchan
// */
//public class NewsFeedTest {
//    
//    private WebDriver driver;
//  
//    @Before     
//    public void setUp() {
//        FirefoxDriverManager.getInstance().setup();
//        driver = new FirefoxDriver();
//    }
//
//    @Before
//    public void loginAsAdmin()
//    {
//        String user = "ren";
//        String pwd = "123";
//        
//        driver.get("http://localhost:8080/Fractals/client/login.xhtml");
//        WebDriverWait wait = new WebDriverWait(driver, 10);         
//        wait.until(ExpectedConditions.titleIs("Login"));
//        
//        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
//        inputElement.clear();        
//        inputElement.sendKeys(user);
//        
//        inputElement = driver.findElement(By.id("loginForm:password"));         
//        inputElement.clear();         
//        inputElement.sendKeys(pwd);
//        
//        driver.findElement(By.id("loginForm:login")).click();
//        
//        wait.until(ExpectedConditions.titleIs("Fractals"));
//    }
//    
//    @Test     
//    public void testDatatableloaded() throws Exception {
//        driver.get("http://localhost:8080/Fractals/management/newsFeed/List.xhtml");
//        
//        List<WebElement> items = driver.findElements(By.id("data"));
//        boolean isPresent = items.size() > 0? true : false; 
//        assertThat(isPresent);     
//        driver.quit();
//    }
//    
//    @Test
//    public void testCreate()
//    {
//        driver.get("http://localhost:8080/Fractals/management/newsFeed/List.xhtml");
//        //count the number of row in the datatable 
//        int numRows = driver.findElements(By.xpath("//table[@id='data']/tbody/tr/td")).size();
//        
//        //go to the edit page and enter a new news Feed
//        driver.findElement(By.id("create")).click();
//        enterData();
//        
//        //re count the row and see if it increased by 1
//        int numRowAfterCreate = driver.findElements(
//                By.xpath("//table[@id='data']/tbody/tr/td")).size();
//        
//        assertThat(numRowAfterCreate == (numRows + 1));
//        
//        
//    }
//    
//    private void enterData()
//    {
//        
//    }
//    
//    
//}
