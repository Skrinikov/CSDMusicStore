package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.event.Level;

/**
 *
 * @author Thai-Vu Nguyen
 */
public class AlbumManagementTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
     private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("AlbumManagementTestSelenium.class");
    
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
    public void clickNotChosenAlbumDetails(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //Get to the page
        driver.get("http://localhost:8080/CSDMusicStore/management/album/albumsList.xhtml");
        
        //Click Preview without touching any Album
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:preview\"]"));
        
        //We should only see dialogForm and it's exit button
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:edit"))
        ));
    }
    
    /**
     * 
     */
    @Test
    public void getAlbumPreview(){
        
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:edit")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
        ));
       
        
    }
    
    public void createAlbum(){
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[3]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //TODO
    }
    
    public void editAlbum(){
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //TODO
        wait.until(ExpectedConditions.attributeContains(By.id(""), "", ""));


    }
    
    /**
     * Assume starting from list page
     * @param by By
     */
    private void goToPreview(By by){
        driver.get("http://localhost:8080/CSDMusicStore/management/album/albumsList.xhtml");
        WebElement row = driver.findElement(by);
        row.click();
        //WebElement detail = driver.findElement(By.xpath("//*[@id=\"form:tbl:preview\"]"));   
        //detail.click();
        
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:preview\"]"));
        
    }
    
    private List<WebElement> buildListOfWebElements (WebElement ... elements){
        List<WebElement> list = new ArrayList<>();
        
        for (WebElement element : elements){
            list.add(element);
        }
        
        return list;
    }
    
    
    
    
    
  
}
