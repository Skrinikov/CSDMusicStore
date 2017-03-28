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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    public void getOrderPage() {
        String title = bundle.getString("ListOrderTitle");
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        wait.until(ExpectedConditions.titleIs(title));
    }

    @Ignore
    @Test
    public void noOrderSelected() {
        String s = bundle.getString("NothingSelected");
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        helper.retryFindClick(By.id("form:tbl:previewButton"));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), s)
        )
        );
    }


    @Test
    public void DeletedOrderSelected() {
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");

        //find deleted order = netCost equals 0
        int i = 1;
        while (!helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]/td[3]")).equals("0.0")) 
            i++;
        

        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]"));
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
        helper.retryFindClick(By.id("dialogForm:orderItemTbl:0:deleteOrderItem"));
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

    @Ignore
    @Test
    public void deleteItemCheckNetCost() {
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");

        int i = 1;
        while (helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]/td[3]")).equals("0.0")) {
            i++;
        }

        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("dialogForm")));
        if (helper.retryFindGetText(By.id("dialogForm:nbOrderItems")).equals("1")) {
            helper.retryFindClick(By.id("dialogForm:orderItemTbl:0:deleteOrderItem"));
        } else {
            i = 1;
            while (helper.retryFindGetText(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[5]")).equals("true")) {
                i++;
            }
        }

        Double deletedOrderItemPrice, beforeOrderPrice, afterOrderPrice;

        beforeOrderPrice = Double.valueOf(helper.retryFindGetText(By.id("dialogForm:netCost")));

        helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + (i - 1) + ":deleteOrderItem"));
        helper.retryFindClick(By.id("dialogForm:yes"));

        deletedOrderItemPrice = Double.valueOf(helper.retryFindGetText(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[4]")));
        afterOrderPrice = Double.valueOf(helper.retryFindGetText(By.id("dialogForm:netCost")));

        Assert.assertEquals(afterOrderPrice, (beforeOrderPrice - deletedOrderItemPrice), 0.01);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[5]"), "true"));
    }
    @Ignore
    @Test
    public void deleteOrderItemTwice() {
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        int i = 1;
        while (helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]/td[3]")).equals("0.0")) 
            i++;
       
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("dialogForm")));
        String s = "0";
        String s2 = "";
        if (!helper.retryFindGetText(By.id("dialogForm:nbOrderItems")).equals("1")) {
            i = 1;
            
            while (helper.retryFindGetText(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[5]")).equals("true")) 
                i++;
            
            s2 = "[" + i + "]";
            s = String.valueOf(i - 1);
        }

        helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + s + ":deleteOrderItem"));
        helper.retryFindClick(By.id("dialogForm:yes"));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr" + s2 + "/td[5]"), "true"),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:confirmDialog"))
        ));
        helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + s + ":deleteOrderItem"));
        helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + s + ":deleteOrderItem")); //weird that click has to be done twice
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:confirmDialog")));        
        new Actions(driver).moveToElement(driver.findElement(By.id("dialogForm:yes"))).click().perform(); //regular click doesnt work   
        
         wait.until(
                ExpectedConditions.and(            
                    ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:confirmDialog")),  
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), bundle.getString("OrderItemAlreadyDeleted"))
                )
        );
    }
    
    
    @Ignore
    @Test
    public void deleteOrderTwice() {
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        int i = 1;
        while (helper.retryFindGetText(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]/td[3]")).equals("0.0")) 
            i++;
       
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("dialogForm")));
        helper.retryFindClick(By.id("dialogForm:deleteOrderButton"));
        helper.retryFindClick(By.id("dialogForm:yes"));
        
        wait.until(ExpectedConditions.and(              
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:confirmDialog")),
                ExpectedConditions.textToBe(By.xpath("//*[@id=\"dialogForm\"]/div[1]/div[6]/span[2]"), "true"),
                ExpectedConditions.textToBe(By.id("dialogForm:netCost"), "0.0")
        ));
       
        helper.retryFindClick(By.id("dialogForm:deleteOrderButton"));
        new Actions(driver).moveToElement(driver.findElement(By.id("dialogForm:yes"))).click().perform(); //regular click doesnt work   
        
        wait.until(
                ExpectedConditions.and(            
                    ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:confirmDialog")),  
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), bundle.getString("OrderAlreadyDeleted"))
                )
        );
    }
}
