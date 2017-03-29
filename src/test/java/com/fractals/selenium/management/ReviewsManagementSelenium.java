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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author MOUFFOK Sarah
 */
public class ReviewsManagementSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    private WebDriverWait wait;
    private ResourceBundle bundle;
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("ReviewsManagementSelenium.class");
    
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
    public void getReviewsPerStatusPage(){
        String title = bundle.getString("ReviewsPerStatusTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerStatusList.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }
   
    @Ignore
    @Test   
    public void getReviewsPerTrackPage(){
        String title = bundle.getString("ReviewsPerTrackTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerTrackList.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }
    @Ignore
    @Test   
    public void getReviewsPerUserPage(){
        String title = bundle.getString("ReviewsPerUserTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerUserList.xhtml");  
        wait.until(
                ExpectedConditions.and(
                    ExpectedConditions.titleIs(title),
                    ExpectedConditions.invisibilityOfElementLocated(By.id("form:tbl2")),
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form:tbl"))
                 )       
        );     
        driver.quit();       
    }
    @Ignore
    @Test
    public void selectUser(){
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerUserList.xhtml");  
        driver.findElement(By.id("form:tbl_data")).click();
        wait.until(
                ExpectedConditions.and(               
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:tbl2")),
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form:tbl")),
                    ExpectedConditions.visibilityOfElementLocated(By.id("form:tbl2:previewButton"))
                )       
        );     
        driver.quit();     
    }
    
    @Ignore
    @Test
    public void previewEmpty(){
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerUserList.xhtml");  
        driver.findElement(By.id("form:tbl_data")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form:tbl2:previewButton")));
        driver.findElement(By.id("form:tbl2:previewButton")).click();
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                        ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), bundle.getString("NothingSelected"))
                )
        );
        driver.quit();
    }
    @Ignore
    @Test
    public void previewReview() {
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerUserList.xhtml");
        driver.findElement(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]")).click();
        int i = 2;
        while (helper.toBoolean(ExpectedConditions.textToBePresentInElementLocated(By.id("form:tbl2"), "No records found."), wait)) 
            driver.findElement(By.xpath("//*[@id=\"form:tbl_data\"]/tr[" + i + "]")).click();
        
        driver.findElement(By.xpath("//*[@id=\"form:tbl2_data\"]/tr[1]")).click();
        driver.findElement(By.id("form:tbl2:previewButton")).click();              
        
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                        ExpectedConditions.not(
                                ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), bundle.getString("NothingSelected"))
                                )
                )
        );
        
        driver.findElement(By.id("dialogForm:exitDialogButton")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")));
        driver.quit();
    }
    
    
    @Test
    public void pendingToApprovedToDisapproved(){
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerStatusList.xhtml");
        
        //new Actions(driver).moveToElement(driver.findElement(By.id("form:j_idt50:2"))).click().perform(); //regular click doesnt work   
        
        helper.retryFindClick(By.id("form:j_idt50:2"));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form:tbl")));
        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl_data\"]/tr[1]"));
        helper.retryFindClick(By.id("form:tbl:previewButton"));
        
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:approveButton")),
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:disapproveButton")),
                        ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), "PENDING")  
                )
        );
        
        helper.retryFindClick(By.id("dialogForm:approveButton"));
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:approveButton")),
                        ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:disapproveButton")),
                        ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), "APPROVED")
                )
        );
        helper.retryFindClick(By.id("dialogForm:disapproveButton"));
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:approveButton")),
                        ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:disapproveButton")),
                        ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), "DISAPPROVED")                  
                )
        );
        helper.retryFindClick(By.id("dialogForm:exitDialogButton"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm")));
        driver.quit();
        
    }
    
    
    
    
    
}
