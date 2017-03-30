
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
 *  This class will be used to ensure the genre management pages are working 
 *  properly. 
 * 
 * @author Renuchan
 */
public class GenreManageTestSelenium {
    
    private WebDriver driver; 
    
    @Before
    public void login()
    {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        
        new SeleniumAjaxHelper(driver).login();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        wait = new WebDriverWait(driver, 2000);
    }
    @Test
    public void testCreate()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/genre/genresList.xhtml");
        
        int numRows = countRows();
        
        //click create
        clickMainButton(By.id("form:tbl:j_idt57"));
        
        //now grab the text input 
        WebElement inputElement = driver.findElement(By.id("dialogForm2:name"));
        inputElement.clear();
        inputElement.sendKeys("Popular");
        
        //click the save button 
        clickMainButton(By.id("dialogForm2:j_idt62")); 
        
        
        int numRowsAfter = countRows();
        
        //possible artist got created on a new paginated page, which would make count less
        //so check its not the same number
        
        assertThat((numRows + 1) == numRowsAfter); 
    }
    
    @Test
    public void testEdit()
    {
        
        driver.get("http://localhost:8080/CSDMusicStore/management/genre/genresList.xhtml");
        wait(2000);
        
        String beforeChange = driver.findElement(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]/td[2]")).getText();
        
        selectFirstEntry();        
        clickPreview();
        wait(2000);
        //click edit
        clickMainButton(By.id("dialogForm:j_idt73")); 
        wait(2000);
        
        //add an s to the genre name 
        WebElement inputElement = driver.findElement(By.id("dialogForm:name"));
        inputElement.clear();
        inputElement.sendKeys(beforeChange + "s");
        
        //save 
        clickMainButton(By.id("dialogForm:j_idt77"));      
        wait(2000);
            
        String afterChange = driver.findElement(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]/td[2]")).getText();
        
        assertThat(!afterChange.equals(beforeChange)); 
        
    }
    
    @Test
    public void testPreview()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/genre/genresList.xhtml");
        
        selectFirstEntry();        
        clickPreview();
        wait(2000); 
        
        
        String header = driver.findElement(By.id("j_idt66_title")).getText();
        
        assertThat(header.equals("View Genre"));
        
    }
    @Test
    public void testCreateInvalid()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/genre/genresList.xhtml");
               
        //click create
        clickMainButton(By.id("form:tbl:j_idt57"));
        
        //now grab the text input 
        WebElement inputElement = driver.findElement(By.id("dialogForm2:name"));
        inputElement.clear();
        
        //click the save button 
        clickMainButton(By.id("dialogForm2:j_idt62")); 
        wait(2000);
        
        String expected = "The Name field is required.";
        
        assertThat(expected.equals(getTextFromGrowl()));
    }
    
    @Test
    public void testEditInvalid()
    {
        
        driver.get("http://localhost:8080/CSDMusicStore/management/genre/genresList.xhtml");
        wait(2000);
        
        selectFirstEntry();        
        clickPreview();
        wait(2000);
        //click edit
        clickMainButton(By.id("dialogForm:j_idt73")); 
        wait(2000);
        
        //add an s to the genre name 
        WebElement inputElement = driver.findElement(By.id("dialogForm:name"));
        inputElement.clear();
        
        //save 
        clickMainButton(By.id("dialogForm:j_idt77"));      
            
        String expected = "The Name field is required.";     
        assertThat(expected.equals(getTextFromGrowl()));
        
    }
    
    private String getTextFromGrowl()
    {
        return driver.findElement(By.xpath
        ("//*[@id=\"form:growl_container\"]/div/div/div[2]/span")).getText();
    }
    
    private void clickPreview()
    {
         driver.findElement(By.id("form:tbl:j_idt58")).click();
    }
    
    private void selectFirstEntry()
    {
        List<WebElement> items = driver.findElements(
                By.xpath("//*[@id=\"form:tbl_data\"]/tr")); 
        
        items.get(0).click();
    }
    
    private void clickMainButton(By by)
    {
        driver.findElement(by).click(); 
    }
    
    private int countRows()
    {
        return driver.findElements(
                By.xpath("//*[@id=\"form:tbl_data\"]/tr")).size(); 
    }
    
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
