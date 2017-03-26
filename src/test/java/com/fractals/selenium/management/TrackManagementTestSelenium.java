package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ArrayList;
import java.util.List;
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
public class TrackManagementTestSelenium {
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
    
    @Test
    public void getTrackDetailsPage(){
        
        WebDriverWait wait = new WebDriverWait(driver, 10);  
        
        goToPreview(By.xpath("//*[@id=\"form:tbl_data\"]/tr[4]"));
        
        wait.until(ExpectedConditions.titleIs("Track Details"));
        driver.quit();
        
    }
    
    /**
     * Assume starting from list page
     * @param by By
     */
    private void goToPreview(By by){
        driver.get("http://localhost:8080/CSDMusicStore/management/track/tracksList.xhtml");
        WebElement row = driver.findElement(by);
        row.click();
        WebElement detail = driver.findElement(By.xpath("//*[@id=\"form:tbl:preview\"]"));   
        detail.click();
        
    }
    
    private List<WebElement> buildListOfWebElements (WebElement ... elements){
        List<WebElement> list = new ArrayList<>();
        
        for (WebElement element : elements){
            list.add(element);
        }
        
        return list;
    }
    
}
