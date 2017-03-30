
package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 * This class will check if the artist page for management are functioning properly. 
 * 
 * @author Renuchan
 */
public class ArtistManagementSelenium {
    
    private WebDriver driver;
    
    @Before
    public void setup() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        new SeleniumAjaxHelper(driver).login();
    }

    @Test
    public void createArtist()
    {      
        assertThat(testCreate("Bill Bob"));
        driver.quit(); 
    }
    
    private boolean testCreate(String value)
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/artist/artistsList.xhtml");
        
        goToLastPaginatedPage();
        
        int numRows = driver.findElements(
                By.xpath("//*[@id=\"j_idt50:tbl_data\"]/tr")).size();
        
        //click create
        driver.findElement(By.id("j_idt50:tbl:j_idt58")).click();
        
        //now grab the text input 
        WebElement inputElement = driver.findElement(By.id("j_idt49:name"));
        inputElement.clear();
        inputElement.sendKeys(value);
        
        //click the save button 
        driver.findElement(By.id("j_idt49:j_idt52")).click(); 
        
        //go to list page 
        driver.findElement(By.id("j_idt80")).click();
        
        //recount the list
        goToLastPaginatedPage();
        
        int numRowsAfter = driver.findElements(
                By.xpath("//*[@id=\"j_idt50:tbl_data\"]/tr")).size();
        
        //possible artist got created on a new paginated page, which would make count less
        //so check its not the same number
        return numRows != numRowsAfter;
       
    }
    
    @Test
    public void getDetails()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/artist/artistsList.xhtml");
        
        List<WebElement> items = driver.findElements(
                By.xpath("//*[@id=\"j_idt50:tbl_data\"]/tr")); 
        
        items.get(0).click();
        
        //click details 
        driver.findElement(By.id("j_idt50:tbl:j_idt59")).click();
        
        WebElement header = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/div/h1"));
        
        assertThat(header.getText().equals("Artist Details"));
        driver.quit(); 
        
    }

    @Test
    public void testWithInvalid()
    {
        boolean valid = false; 
        try{
            
            //this test should fail and throw an exception
            testCreate("");
         
        }catch(Exception e)
        {
            valid = true; 
        }
        
        assertThat(valid);
        
        driver.quit(); 
    }
    
    @Test
    public void testColumnChange()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/artist/artistsList.xhtml");
        
        int numRows = countHeaders();
        
        clickIdColumn();
        
        int numRowAfter = countHeaders();
        
        assertThat(numRowAfter == (numRows - 1));
        
        driver.quit(); 
        
        
    }
    
    private void clickIdColumn()
    {  
        SeleniumAjaxHelper sh = new SeleniumAjaxHelper(driver); 
        
        driver.findElement(
                By.id("j_idt50:tbl:toggler")).click();
     
        driver.findElement(
                By.xpath("//*[@id=\"j_idt50:tbl:j_idt53\"]/ul/li[1]")).click();      
               
    }
    
    private int countHeaders()
    {
        return driver.findElements(
                By.xpath("//*[@id=\"j_idt50:tbl_data\"]/th")).size();
    }
    
    private void goToLastPaginatedPage()
    {
        List<WebElement> links = driver.findElements(
                By.xpath("//*[@id=\"j_idt50:tbl_paginator_top\"]/span[2]/a"));
        
        int last = links.size() - 1;
        
        //click on the last
        links.get(last).click();
        
        wait(1000);
    }
    
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
