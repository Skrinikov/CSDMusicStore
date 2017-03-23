package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Thai-Vu Nguyen
 */
public class AlbumManagementTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
        
        //pray the user is admin
        helper.login();
    }
    
    /**
     * Check if we can reach the Album List page of the management
     */
    @Test
    public void getAlbumListPage(){
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        //Title of Page is stored to bundle
        String title = bundle.getString("ListAlbumTitle");
        
        driver.get("http://localhost:8080/CSDMusicStore/management/album/albumsList.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();
        
    }
    
    @Test
    public void getAlbumDetailsPage(){
        
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        goToDetails(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        
        wait.until(ExpectedConditions.titleIs("Album Details"));
        driver.quit();
        
    }
    
    public void createAlbum(){
        goToDetails(By.xpath("//*[@id=\"form:tbl_data\"]/tr[3]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //TODO
    }
    
    public void editAlbum(){
        goToDetails(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //TODO
        wait.until(ExpectedConditions.attributeContains(By.id(""), "", ""));


    }
    
    /**
     * Assume starting from list page
     * @param by By
     */
    private void goToDetails(By by){
        driver.get("http://localhost:8080/CSDMusicStore/management/album/albumsList.xhtml");
        WebElement row = driver.findElement(by);
        row.click();
        WebElement detail = driver.findElement(By.xpath("//*[@id=\"form:tbl:detail\"]"));   
        detail.click();
        
    }
    
    
    
  
}
