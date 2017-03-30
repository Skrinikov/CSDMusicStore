/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.Random;
import java.util.ResourceBundle;
import org.junit.Before;
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
    private SeleniumAjaxHelper helper;
    private WebDriver driver;
    private WebDriverWait wait;
    private ResourceBundle bundle;
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SetSaleManagementSelenium.class");
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10); 
        helper = new SeleniumAjaxHelper(driver);           
        bundle = ResourceBundle.getBundle("Bundle");
        helper.login();   
    }
 
    @Test   
    public void getSetSalesPage(){
        String title = bundle.getString("list_set_sale_title");    
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }

    @Test
    public void setSaleEmpty(){
        String title = bundle.getString("specify_sale_value");   
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
        String title = bundle.getString("sale_value_superior"); 
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        int n = new Random().nextInt(10);
       
        helper.retryFindClick(By.id("form:tbl:"+n+":columnButtonAlbum"));
          
        String s = helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+(n+1)+"]/td[8]"));
        s = String.valueOf((Double.parseDouble(s)) + 1) ;
        for(int i = 0; i<s.length(); i++)
            if(s.charAt(i) == '.')
                 s = new StringBuilder(s).deleteCharAt(i).toString();
        if(s.length()== 3)
        s = "0" + s;
    
        
        helper.retryFindSendKeys(By.id("dialogForm:saleAlbumInput"),s);
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
        String title = bundle.getString("sale_price_changed");
        driver.get("http://localhost:8080/CSDMusicStore/management/setSales.xhtml");    
        
        int n = new Random().nextInt(10);
       
        helper.retryFindClick(By.id("form:tbl:"+n+":columnButtonAlbum"));
            
        String s = driver.findElement(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+(n+1)+"]/td[8]")).getText();
        s = String.valueOf((Double.parseDouble(s)) - 1) ;
        for(int i = 0; i<s.length(); i++)
            if(s.charAt(i) == '.')
                 s = new StringBuilder(s).deleteCharAt(i).toString();
        if(s.length()== 3)
        s = "0" + s;
    
        helper.retryFindSendKeys(By.id("dialogForm:saleAlbumInput"),s);
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")));
        new SeleniumAjaxHelper(driver).retryFindClick(By.id("dialogForm:exitAlbum"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")));   
        driver.quit();   
        
    }
    
    
}
