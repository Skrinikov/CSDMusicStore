package com.fractals.selenium.client;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import com.fractals.utilities.SeleniumAjaxHelper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
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
        driver.quit();
    }
    
    @Test     
    public void testTrackTextTitle() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=1");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Children of the Sea"));
        
        String msg = helper.retryFindGetText(By.cssSelector("#track-title"));
        assertThat(msg).isEqualToIgnoringCase("Children of the Sea");
        
        msg = helper.retryFindGetText(By.cssSelector("#album-title"));
        assertThat(msg).isEqualToIgnoringCase("Heaven and Hell");
        
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
        
        driver.quit();
    }
}
