package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ResourceBundle;
import org.junit.Assert;
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
public class OrderManagementSelenium {

    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    private WebDriverWait wait;
    private ResourceBundle bundle;
    private static final java.util.logging.Logger log = 
            java.util.logging.Logger.getLogger("OrderManagementSelenium.class");
   
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
    public void getOrderPage() {
        String title = bundle.getString("list_order_title");
        driver.get("http://localhost:8080/CSDMusicStore/management/order/ordersList.xhtml");
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();
    }
    
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
        driver.quit();
    }

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

        deletedOrderItemPrice = Double.valueOf(helper.retryFindGetText(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[4]")));
        afterOrderPrice = Double.valueOf(helper.retryFindGetText(By.id("dialogForm:netCost")));

        Assert.assertEquals(afterOrderPrice, (beforeOrderPrice - deletedOrderItemPrice), 0.01);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr[" + i + "]/td[5]"), "true"));
        driver.quit();
    }
  
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
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"dialogForm:orderItemTbl_data\"]/tr" + s2 + "/td[5]"), "true"));
        helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + s + ":deleteOrderItem"));
        //helper.retryFindClick(By.id("dialogForm:orderItemTbl:" + s + ":deleteOrderItem")); //weird that click has to be done twice
        
         wait.until(
                ExpectedConditions.and(            
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), bundle.getString("order_item_already_deleted"))
                )
        );
         driver.quit();
    }
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
        
        wait.until(ExpectedConditions.textToBe(By.id("dialogForm:netCost"), "0.0"));
       
        helper.retryFindClick(By.id("dialogForm:deleteOrderButton"));
        
        wait.until(
                ExpectedConditions.and(            
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:growl_container")), 
                    ExpectedConditions.textToBe(By.className("ui-growl-title"), bundle.getString("order_already_deleted"))
                )
        );
        driver.quit();
    }
    
 
}
