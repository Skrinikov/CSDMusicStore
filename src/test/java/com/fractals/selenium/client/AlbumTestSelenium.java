package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import com.fractals.utilities.SeleniumAjaxHelper;
import java.util.List;
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
 * Tests the album page of the application.
 * @author Danieil Skrinikov
 */
public class AlbumTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
    }

    /**
     * Checks if the proper page is displayed given the right id. 
     * 
     * @throws Exception 
     */
    @Test     
    public void testAlbumTitle() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
    }
    
    /**
     * Checks if the proper page is displayed given the wrong id. 
     * 
     * @throws Exception 
     */
    @Test     
    public void testAlbumTitle_badId() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=111111");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("404 Error"));
    }

    /**
     * Checks if all the important information about the first track is right. 
     * 
     * @throws Exception 
     */
    @Test     
    public void testTrackText() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        String msg = helper.retryFindGetText(By.cssSelector("#album-title"));
        assertThat(msg).isEqualToIgnoringCase("~Heaven and Hell~");
        
        msg = helper.retryFindGetText(By.cssSelector("#artist-name"));
        assertThat(msg).isEqualToIgnoringCase("Black Sabbath");
        
        msg = helper.retryFindGetText(By.cssSelector("#albumCartForm\\:album-to-cart"));
        assertThat(msg).isEqualToIgnoringCase("$6.99");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(2)"));
        assertThat(msg).isEqualToIgnoringCase("2 - Children of the Sea");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(3)"));
        assertThat(msg).isEqualToIgnoringCase("05:34");
        
        msg = helper.retryFindGetText(By.cssSelector("td.ui-panelgrid-cell:nth-child(4) a"));
        assertThat(msg).isEqualToIgnoringCase("$0.99");
        
        msg = driver.findElement(By.cssSelector("#album-cover")).getAttribute("src");
        assertThat(msg.split(";")[0]).isEqualToIgnoringCase("http://localhost:8080/CSDMusicStore/javax.faces.resource/images/covers/heaven_and_hell.jpg.xhtml");
        
        msg = helper.retryFindGetText(By.cssSelector("#artist-album"));
        assertThat(msg).isEqualToIgnoringCase("Black Sabbath - Heaven and Hell");               
        
        msg = helper.retryFindGetText(By.cssSelector("#album-genre"));
        assertThat(msg).isEqualToIgnoringCase("Rock");
        
        msg = helper.retryFindGetText(By.cssSelector(".track-album-date"));
        assertThat(msg).isEqualToIgnoringCase("1980-04-25");
        
    }

    /**
     * Test if adding the track works.
     * 
     * @throws Exception 
     */
    @Test     
    public void testTrackShoppingCartButton_Big() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        driver.findElement(By.cssSelector("#albumCartForm\\:album-to-cart")).click();
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Shopping Cart (1)"));
    }

    /**
     * Test to see if the small button on the right side works to add the track 
     * to the shopping cart
     * 
     * @throws Exception 
     */
    @Test     
    public void testTrackShoppingCartButton_Small() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(4) a")).click();
        
        wait.until((ExpectedCondition<Boolean>) driver -> 
                helper.retryFindGetText(By.xpath("//*[@id=\"shop-cart-2\"]"))
                      .equalsIgnoreCase("Shopping Cart (1)"));
    }

    /**
     * Checks if the track name (which is a link) redirects to the right page.
     * 
     * @throws Exception 
     */
    @Test     
    public void testTrackNameLink() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(2)")).click();
        
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
    }

    @Test(expected=NoSuchElementException.class) 
    public void testTrackReview_NotLoggedIn() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        helper.retryFindGetText(By.cssSelector(".leave-review > h1:nth-child(1)"));
    }
    
    
    @Test
    public void testTrackReview_LoggedIn() throws Exception {
        helper.login();
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(1)")).click();
        List<WebElement> reviewContainers = driver.findElements(By.className("leave-review-form"));
        assertThat(reviewContainers).hasSize(1);
        
        // Setting up the review.
        WebElement inputElement = reviewContainers.get(0).findElement(By.className("review-text-area"));
        inputElement.clear();        
        inputElement.sendKeys("testing this track with selenium!");
        
        int rand = (int)(Math.random() * 4) + 1;
        reviewContainers.get(0).findElements(By.className("rating-star")).get(rand).click();
        
        // Click the submit btn.
        reviewContainers.get(0).findElement(By.className("review-submit")).click();
        
        wait.withTimeout(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(1)")).click();
        
        String msg = helper.retryFindGetText(By.cssSelector("h4.review-feedback-album"));
        assertThat(msg).isEqualToIgnoringCase("Thank you for your feedback!");       
    }
    
    @Test
    public void testTrackReview_LoggedInInvalid() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Heaven and Hell"));
        
        driver.findElement(By.cssSelector("td.ui-panelgrid-cell:nth-child(1)")).click();
        List<WebElement> reviewContainers = driver.findElements(By.className("leave-review-form")); 
        assertThat(reviewContainers).isEmpty();
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