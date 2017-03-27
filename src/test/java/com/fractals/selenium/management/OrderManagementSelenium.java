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
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Sarah
 */
public class OrderManagementSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    private WebDriverWait wait;
    private ResourceBundle bundle;
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("OrderManagementSelenium.class");
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);  
        helper = new SeleniumAjaxHelper(driver);
        helper.login();
        bundle = ResourceBundle.getBundle("Bundle");
    }
    @Ignore
    @Test
    public void getOrderPage(){
        String title = bundle.getString("ListOrderTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));   
    }
    @Ignore
    @Test
    public void noOrderSelected(){
        String s = bundle.getString("NothingSelected");
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");  
        helper.retryFindClick(By.id("form:tbl:previewButton"));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), s)
            )
        );
    }
    @Ignore
    @Test
    public void DeletedOrderSelected(){
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        
        //find deleted order = netCost equals 0
        int i = 1;
        while(!helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+i+"]/td[3]")).equals("0.0"))
            i++;
        
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+i+"]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));   
        
        //check it sees something is selected
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.not(
                       ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), 
                               bundle.getString("NothingSelected"))
                )
            )
        );    
        //check error message if deleting already deleted order
        helper.retryFindClick(By.id("dialogForm:deleteOrderButton"));
        helper.retryFindClick(By.id("dialogForm:yes"));
        wait.until(
                ExpectedConditions.and(
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), 
                            bundle.getString("OrderAlreadyDeleted"))
                )
        );
        //check error message if deleting already deleted order item
        /*helper.retryFindClick(By.id("dialogForm:orderItemTbl:0:deleteOrderItem")); //DOESNT WORK     
        helper.retryFindClick(By.id("dialogForm:yes"));
        wait.until(
                ExpectedConditions.and(
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), 
                            bundle.getString("OrderItemAlreadyDeleted"))
                )
        );*/
        driver.quit();
    }
    
    @Test
    public void deleteItemCheckNetCost(){
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        
      
        int i = 2; //GO BACK TO ONE AFTER
        while(helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+i+"]/td[3]")).equals("0.0"))
            i++;
        
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr["+i+"]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));   
        
        i=1;
        while(helper.retryFindGetText(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr["+i+"]/td[5]")).equals("true"))
            i++;
        
        //helper.retryFindClick(By.id("dialogForm:orderItemTbl:"+(i-1)+":deleteOrderItem"));
        
    }
}
