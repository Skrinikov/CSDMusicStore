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
    public void getApprovedReviewsPage(){
        String title = bundle.getString("ApprovedReviewsTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/approvedReviewsList.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }
    @Ignore
    @Test   
    public void getDisapprovedReviewsPage(){
        String title = bundle.getString("DisapprovedReviewsTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/disapprovedReviewsList.xhtml");  
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();       
    }
    @Ignore
    @Test   
    public void getPendingReviewsPage(){
        String title = bundle.getString("PendingReviewsTitle");    
        driver.get("http://localhost:8080/CSDMusicStore/management/review/pendingReviewsList.xhtml");  
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
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("form:tbl"))
                )       
        );     
        driver.quit();     
    }
    @Ignore
    @Test
    public void previewEmpty(){
        driver.get("http://localhost:8080/CSDMusicStore/management/review/reviewsPerUserList.xhtml");  
        driver.findElement(By.id("form:tbl_data")).click();
        driver.findElement(By.id("form:tbl2:previewButton")).click();
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.textToBePresentInElementLocated(By.id("dialogForm"), bundle.getString("NothingSelected"))
         )
        );
     
    }
}
