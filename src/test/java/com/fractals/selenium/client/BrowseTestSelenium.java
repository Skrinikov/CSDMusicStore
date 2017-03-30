package com.fractals.selenium.client;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests the login page of the application.
 * @author Aline Shulzhenko
 */
public class BrowseTestSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
    }
    
    @Test     
    public void testBrowseTitle() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/browse_genre.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Browse"));
        driver.quit();
    }

    @Test     
    public void testBrowse_CheckAllGenres() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/browse_genre.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Browse"));
        
        List<WebElement> list = driver.findElements(By.className("browse-genre-li"));
        List<String> genres = new ArrayList<>();
        int size = list.size();
        //gets the list of all available genres
        for (int i = 0; i < size; i++) {
            genres.add(list.get(i).getText());
        }
        
        //checks that the elements after the genre button is clicked correspond to this genre
        for (int i = 1; i <= size; i++) {
            driver.get("http://localhost:8080/CSDMusicStore/client/browse_genre.xhtml");
            wait.until(ExpectedConditions.titleIs("Browse"));
            
            helper.retryFindClick(By.cssSelector(".nav-pills > li:nth-child("+i+") > a:nth-child(1)"));
            helper.retryFindClick(By.cssSelector("div.col-xs-12:nth-child(1) > div:nth-child(1) > a:nth-child(1)"));

            String msg = helper.retryFindGetText(By.cssSelector(".track-genre"));//
            assertThat(msg).isEqualToIgnoringCase(genres.get(i-1));
        }
        
        driver.quit();
    }
    
    @Test     
    public void testBrowseGenreTracks() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/client/browse_genre.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Browse"));
        
        List<WebElement> list = driver.findElements(By.className("browse-genre-li"));
        List<String> genres = new ArrayList<>();
        int size = list.size();
        //gets the list of all available genres
        for (int i = 0; i < size; i++) {
            String genre = list.get(i).getText();
            genres.add(genre.charAt(0) + genre.substring(1).toLowerCase());
        }
        
        //checks that the page and items correspond to the specified genre
        for (int i = 1; i <= size; i++) {
            String genre = genres.get(i-1);
            driver.get("http://localhost:8080/CSDMusicStore/client/browse_genreTracks.xhtml?id="+i);
            wait.until(ExpectedConditions.titleIs("Browse " + genre));

            String msg = helper.retryFindGetText(By.cssSelector(".browse-header"));
            assertThat(msg).isEqualToIgnoringCase("Browse " + genre);
            
            helper.retryFindClick(By.cssSelector("div.col-xs-18:nth-child(1) > div:nth-child(1) > a:nth-child(1)"));
            msg = helper.retryFindGetText(By.cssSelector(".track-genre"));//
            assertThat(msg).isEqualToIgnoringCase(genres.get(i-1));
        }
        
        driver.quit();
    }
}
