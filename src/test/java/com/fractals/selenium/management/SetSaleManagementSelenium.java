/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ResourceBundle;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author MOUFFOK Sarah
 */

public class SetSaleManagementSelenium {
    
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    private WebDriverWait wait;
    private ResourceBundle bundle;
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SetSaleManagementSelenium.class");
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);  
        helper = new SeleniumAjaxHelper(driver);
        helper.login();
        bundle = ResourceBundle.getBundle("Bundle");
    }
      
    @Test   
    public void getSetSalesPage(){
        String title = bundle.getString("ListSetSalesTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }
    
    @Test
    public void setSaleEmpty(){
        String title = bundle.getString("SpecifySaleValue");   
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        helper.retryFindClick(By.id("form:tbl:0:columnButtonAlbum"));
        helper.retryFindClick(By.id("dialogForm:dialogButtonSetAlbum"));
        wait.until(ExpectedConditions.and(            
            ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),  
            ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
            ExpectedConditions.textToBe(By.className("ui-growl-title"), title))
        );          
        driver.quit();   
    }

    @Test
    public void setSaleSuperior(){
        String title = bundle.getString("SaleValueSuperior"); 
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        helper.retryFindClick(By.id("form:tbl:0:columnButtonAlbum"));
        helper.retryFindSendKeys(By.id("dialogForm:saleAlbumInput"), "0700");
        helper.retryFindClick(By.id("dialogForm:dialogButtonSetAlbum"));
        wait.until(ExpectedConditions.and(            
            ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),  
            ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
            ExpectedConditions.textToBe(By.className("ui-growl-title"), title))
        );   
        driver.quit();   
    }

    @Test
    public void setSale(){
        String title = bundle.getString("SalePriceChanged");
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        helper.retryFindClick(By.id("form:tbl:0:columnButtonAlbum"));
        helper.retryFindSendKeys(By.id("dialogForm:saleAlbumInput"), "0400");
        helper.retryFindClick(By.id("dialogForm:dialogButtonSetAlbum"));
        wait.until(ExpectedConditions.and(            
            ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")),  
            ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
            ExpectedConditions.textToBe(By.className("ui-growl-title"), title))
        ); 
        driver.quit();   
    }
   
    @Test
    public void exitButton(){
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        helper.retryFindClick(By.id("form:tbl:0:columnButtonAlbum"));
        helper.retryFindClick(By.id("dialogForm:exitAlbum"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")));   
        driver.quit();   
    }
    
    
}
