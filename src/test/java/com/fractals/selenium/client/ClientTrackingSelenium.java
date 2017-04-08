package com.fractals.selenium.client;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Thai-Vu Nguyen
 */
public class ClientTrackingSelenium {
    private WebDriver driver;
    private SeleniumAjaxHelper helper;
    
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);
    }
     
    @Test
    public void checkTrackingInIndexViaTrackLoggedIn (){
        helper.login();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=55");
        
        WebElement genreBlock = driver.findElement(By.xpath("//*[@id=\"j_idt107\"]/div[1]/div/div[1]/div/ul/li[1]/span[2]"));
        String genre = genreBlock.getText();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/index.xhtml");
        
        genreBlock = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[2]/h3/a"));
        String indexGenre = genreBlock.getText();
        
        boolean valid = indexGenre.equalsIgnoreCase(genre);
        
        driver.close();
        
        assertTrue (valid);
        
    }
    
    @Test
    public void checkTrackingInIndexViaAlbumLoggedIn (){
        helper.login();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=20");
        
        WebElement genreBlock = driver.findElement(By.xpath("//*[@id=\"j_idt107\"]/div[1]/div/div[1]/div/ul/li[1]/span[2]"));
        String genre = genreBlock.getText();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/index.xhtml");
        
        genreBlock = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[2]/h3/a"));
        String indexGenre = genreBlock.getText();
        
        boolean valid = indexGenre.equalsIgnoreCase(genre);
        
        driver.close();
        
        assertTrue (valid);
        
    }
    
    @Test
    public void checkTrackingInIndexViaAlbumLoggedOut(){
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id=15");
        
        WebElement genreBlock = driver.findElement(By.xpath("//*[@id=\"j_idt107\"]/div[1]/div/div[1]/div/ul/li[1]/span[2]"));
        String genre = genreBlock.getText();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/index.xhtml");
        
        genreBlock = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[2]/h3/a"));
        String indexGenre = genreBlock.getText();
        
        boolean valid = indexGenre.equalsIgnoreCase(genre);
        
        driver.close();
        
        assertTrue (valid);
    }
    
    @Test
    public void checkTrackingInIndexViaTrackLoggedOut(){
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=11");
        
        WebElement genreBlock = driver.findElement(By.xpath("//*[@id=\"j_idt107\"]/div[1]/div/div[1]/div/ul/li[1]/span[2]"));
        String genre = genreBlock.getText();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/index.xhtml");
        
        genreBlock = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[2]/h3/a"));
        String indexGenre = genreBlock.getText();
        
        boolean valid = indexGenre.equalsIgnoreCase(genre);
        
        driver.close();
        
        assertTrue (valid);
    }
    
    @Test
    public void updateGenreCookieByGoingToAlbum(){
        String genreId = "2";
        
        String albumId="22";
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Album.xhtml?id="+ albumId);
        Cookie cookie = driver.manage().getCookieNamed("genreID");
        
        String storedCookieId = cookie.getValue();
        
        assertEquals(storedCookieId, genreId);
        driver.close();
        
    }
    
    @Test
    public void updateGenreCookieByGoingToTrack(){
        String genreId = "4";
        
        String trackId="72";
        
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id="+ trackId);
        Cookie cookie = driver.manage().getCookieNamed("genreID");
        
        String storedCookieId = cookie.getValue();
        
        assertEquals(storedCookieId, genreId);
        driver.close();
        
    }
    
    @Test
    public void checkRecommendationsNotLoggedIn (){
        driver.get("http://localhost:8080/CSDMusicStore/client/Track.xhtml?id=43");
        
        WebElement genreBlock = driver.findElement(By.xpath("//*[@id=\"j_idt107\"]/div[1]/div/div[1]/div/ul/li[1]/span[2]"));
        String genre = genreBlock.getText();
        
        driver.get("http://localhost:8080/CSDMusicStore/client/index.xhtml");
        
        genreBlock = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div/div[2]/h3/a"));
        String indexGenre = genreBlock.getText();
        
        boolean valid = indexGenre.equalsIgnoreCase(genre);
        
        driver.close();
        
        assertTrue (valid);
        
        
    }
    
    
    
}
