
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys; 
/**
 *  This class will test user management pages to ensure it is working 
 *  properly. 
 * 
 * @author Renuchan
 */
public class UserManagementTestSelenium {
    
    private WebDriver driver; 
    private SeleniumAjaxHelper sah; 
    private WebDriverWait wait; 
    
    @Before
    public void login()
    {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        
        sah = new SeleniumAjaxHelper(driver); 
        sah.login();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Fractals"));
        
        wait = new WebDriverWait(driver, 2000);
    }
    
    @Test
    public void testPreview()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/user/usersList.xhtml");
        
        //go to form
        clickFirstElementInTable();
        clickPreviewBtn();
        
        WebElement we = driver.findElement(By.id("popupBox_title"));
        String header = we.getText();
        
        assertThat(header != null);
    }
    
    @Test
    public void testEdit()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/user/usersList.xhtml");
        
        String newTitle = "Mr"; 
        
        String title = 
                driver.findElement(By.xpath
        ("//*[@id=\"form:tbl_data\"]/tr[1]/td[3]/span")).getText();
        
        if(title.equals("Mr"))
            newTitle = "Sir"; 
        
        //go to form
        clickFirstElementInTable();
        clickPreviewBtn();
        clickEdit();
        
        //grab the text field in the form
        WebElement we = driver.findElement(By.id("dialogForm:title"));  
        we.clear();
        we.sendKeys(newTitle);
        
        clickSaveAndConfirm(); 
        
        String titleChange = 
                driver.findElement(By.xpath
        ("//*[@id=\"form:tbl_data\"]/tr[1]/td[3]/span")).getText();
        
        
        assertThat(newTitle.equals(titleChange));

    }
    
    private void clickFirstElementInTable()
    {
        List<WebElement> items = driver.findElements(
                By.xpath("//*[@id=\"form:tbl_data\"]/tr")); 
        
        items.get(0).click();
        wait(2000);
    }
    
    private void clickPreviewBtn()
    {
       driver.findElement(By.id("form:tbl:j_idt91")).click();    
       wait(2000);  
    }
    
    private void clickEdit()
    {
        driver.findElement(By.id("dialogForm:j_idt132")).click();
        wait(2000);   
    }
 
    private void clickSaveAndConfirm()
    {
        //click save
        driver.findElement(By.id("dialogForm:j_idt134")).click();
        //click confirm
        driver.findElement(By.id("dialogForm:j_idt154")).click();
        //sah.retryFindClick(By.id("dialogForm:j_idt154"));
        wait(2000);
    }
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
