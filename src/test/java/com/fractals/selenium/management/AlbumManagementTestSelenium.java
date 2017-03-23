/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.List;
import java.util.ResourceBundle;
import org.junit.Before;
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
    }
    
    public void getAlbumListPage(){
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        String title = bundle.getString("ListAlbumTitle");
        driver.get("http://localhost:8080/Fractals/management/album/albumsList.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();
        
    }
    
    public void getAlbumDetailsPage(){
        driver.get("http://localhost:8080/Fractals/management/album/albumsList.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        //TODO: FIND A WAY TO CLICK ROW OF TABLE
        List<WebElement> items = driver.findElements(By.id("tbl"));
        
        if (items.size() > 0){
            items.get(0).click();
            //TODO
        }
        
        driver.quit();
        
    }
  
}
