package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import com.fractals.utilities.SeleniumAjaxHelper;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the track page of the application.
 * @author Aline Shulzhenko
 */
public class TrackTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
    }

    @Test     
    public void testTrackTitle() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
    }

    @Test     
    public void testTrackText() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        String msg = helper.retryFindGetText(By.cssSelector("#track-title"));
        assertThat(msg).isEqualToIgnoringCase("Children of the Sea");
        
        msg = helper.retryFindGetText(By.cssSelector("#album-title"));
        assertThat(msg).isEqualToIgnoringCase("Heaven and Hell");
        
        msg = helper.retryFindGetText(By.cssSelector("#trackCartForm\\:track-to-cart"));
        assertThat(msg).isEqualToIgnoringCase("$0.99");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(2)"));
        assertThat(msg).isEqualToIgnoringCase("2 - Children of the Sea");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(3)"));
        assertThat(msg).isEqualToIgnoringCase("05:34");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(4) a"));
        assertThat(msg).isEqualToIgnoringCase("$0.99");
        
        msg = driver.findElement(By.cssSelector("#track-cover")).getAttribute("src");
        assertThat(msg.split(";")[0]).isEqualToIgnoringCase("http://localhost:8080/CSDMusicStore/javax.faces.resource/images/covers/heaven_and_hell.jpg.xhtml");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-artist"));
        assertThat(msg).isEqualToIgnoringCase("Black Sabbath");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-writer"));
        assertThat(msg).isEqualToIgnoringCase("Ronnie James Dio, Tony Iommi, Geezer Butler, Bill Ward");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-album"));
        assertThat(msg).isEqualToIgnoringCase("Heaven and Hell");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-genre"));
        assertThat(msg).isEqualToIgnoringCase("Rock");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-album-date"));
        assertThat(msg).isEqualToIgnoringCase("1980-04-25");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-duration"));
        assertThat(msg).isEqualToIgnoringCase("05:34");
    }

    @Test     
    public void testTrackShoppingCartButton_Big() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        driver.findElement(By.cssSelector("#trackCartForm\\:track-to-cart")).click();
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Cart (1)"));
    }

    @Test     
    public void testTrackShoppingCartButton_Small() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(4) a")).click();
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Cart (1)"));
    }

    @Test     
    public void testTrackNameLink() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(2)")).click();
        
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
    }

    @Test     
    public void testTrackAlbumLink() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        driver.findElement(By.cssSelector(".track-album")).click();
        
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
    }

    @Test(expected=NoSuchElementException.class) 
    public void testTrackReview_NotLoggedIn() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        helper.retryFindGetText(By.cssSelector(".leave-review > h1:nth-child(1)"));
    }
    
    @Test
    public void testTrackReview_LoggedIn() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        WebElement inputElement = driver.findElement(By.id("review-track-form:review-text"));
        inputElement.clear();        
        inputElement.sendKeys("great track!");
        
        helper.retryFindClick(By.xpath("//*[@id=\"review-track-form\" and not(@disabled)]/div/div/div/span[4]"));

        helper.retryFindClick(By.id("review-track-form:review-submit"));
        
        wait.withTimeout(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        
        String msg = helper.retryFindGetText(By.cssSelector("h3.review-feedback"));
        assertThat(msg).isEqualToIgnoringCase("Thank you for your feedback!");
    }
    
    @Test(expected=NoSuchElementException.class) 
    public void testTrackReview_LoggedInInvalid() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        WebElement inputElement = driver.findElement(By.id("review-track-form:review-text"));
        inputElement.clear();        
        
        helper.retryFindClick(By.xpath("//*[@id=\"review-track-form\" and not(@disabled)]/div/div/div/span[4]"));

        helper.retryFindClick(By.id("review-track-form:review-submit"));
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        
        helper.retryFindGetText(By.cssSelector("h3.review-feedback"));
    }
    
    @Test
    public void testTrack_ClickSimilar() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        String title = driver.findElement(By.cssSelector("div.row:nth-child(2) > div:nth-child(1) > a:nth-child(1) > h4:nth-child(2)")).getText(); 
        
        driver.findElement(By.cssSelector("div.row:nth-child(2) > div:nth-child(1) > a:nth-child(1)")).click();
        
        wait.until(ExpectedConditions.titleIs(title));
    }
    
    @After
    public void tearDown()
    {
        driver.quit();
    }
}
