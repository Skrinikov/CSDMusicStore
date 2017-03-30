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
 * Selenium Testing class responsible for the management side of Album
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
        String title = bundle.getString("list_album_title");
        
        getToListPage();
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();
    }
    
    /**
     * Test if clicking Preview button without selecting any Album 
     * displays the proper dialog
     */
    @Test
    public void clickNotChosenAlbumDetails(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //Get to the page
        getToListPage();
        
        //Click Preview without touching any Album
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:preview\"]"));
        
        //We should only see dialogForm and it's exit button
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:edit"))
        ));
        
        driver.quit();
    }
    
    /**
     * Test if clicking Preview button with a selecting Album 
     * displays the proper dialog
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
        
        driver.quit();
       
        
    }
    
    @Test
    public void goToCreateAlbum(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //Get to the page
        getToListPage();
        
        //Click Create button
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:create\"]"));
        
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:create")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:exit"))
        ));
        
        driver.quit();
    }
    
    @Test
    public void goToEditAlbum(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:edit"))
                
        ));
        
        WebElement edit = driver.findElement(By.id("dialogForm:edit"));
        edit.click();
        
        //Buttons that needs to be seen: Save, Cancel, Exit
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:save")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:cancel")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
        ));
        
        driver.close();

    }
    
    @Test
    public void clickExitOnPreview(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
                
        ));
        
        WebElement exit = driver.findElement(By.id("dialogForm:exit"));
        exit.click();
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")));
        
        driver.close();
    }
    
    @Test
    public void clickExitOnEditPreview(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        
        WebElement edit = getEditButton(wait, 3);
        
        edit.click();
        
        waitForPreviewEditLoad(wait);
        
        driver.close();
    }
    
    @Test
    public void clickExitOnCreate(){
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //Get to the page
        getToListPage();
        
        //Click Create button
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:create\"]"));
        
        waitForCreateLoad(wait);
        
        WebElement exit = driver.findElement(By.id("dialogForm2:exit"));
        exit.click();
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm2")));
        
        driver.close();
    }
    
    @Test
    public void checkInvalidCreateAttempt(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        getToListPage();
        
        //Click Create button
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:create\"]"));
        
        waitForCreateLoad(wait);
        
        WebElement create = driver.findElement(By.id("dialogForm2:create"));
        create.click();
        
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2")),
                ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container"))
        ));
        
        driver.close();
        
    }
    
    /**
     * Assume starting from list page
     * @param by By
     */
    private void goToPreview(By by){
        getToListPage();
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
    
    private void getToListPage(){
        driver.get("http://localhost:8080/CSDMusicStore/management/album/albumsList.xhtml");
    }
    
    private WebElement getEditButton(WebDriverWait wait, int row){
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+ row + "]"));
        
        waitForPreviewLoad(wait);
        
        return driver.findElement(By.id("dialogForm:edit"));
    }
    
    private void waitForPreviewLoad(WebDriverWait wait){
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:edit")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
                
        ));
    }
    
    private void waitForPreviewEditLoad (WebDriverWait wait){
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:save")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:cancel")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
                
        ));
    }
    
    private void waitForCreateLoad(WebDriverWait wait){
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:create")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:exit"))
        ));
        
    }
    
    
    
  
}
