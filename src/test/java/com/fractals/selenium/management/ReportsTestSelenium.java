package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.List;
import java.util.Random;
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
 *
 * @author Danieil
 */
public class ReportsTestSelenium {
    
    private WebDriver driver;
    
    @Before     
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        
        driver.get("http://localhost:10860/CSDMusicStore/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Login"));
        
        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();        
        inputElement.sendKeys("selenium");
        
        inputElement = driver.findElement(By.id("loginForm:password"));         
        inputElement.clear();         
        inputElement.sendKeys("abcd");
        
        driver.findElement(By.id("loginForm:login")).click();
    }
    
    /**
     * Checks to see if the reports can be accessed.
     * @throws Exception 
     */
    @Test     
    public void testReportsFormTitle() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.quit();
    }      
    
    /**
     * Checks if total sales work
     * @throws Exception 
     */
    @Test     
    public void testReports_totalSales() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        WebElement dateStart = driver.findElement(By.id("totalSales:trackSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalSales:trackSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement form = driver.findElement(By.id("totalSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSalesItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalSalesItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isNotEmpty();
        driver.quit();
    }
    
    /**
     * Check if the page handles empty fields in the total sales inputs
     * @throws Exception 
     */
    @Test     
    public void testIReports_totalSalesEmptyFields() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        WebElement form = driver.findElement(By.id("totalSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSales:totalSalesMsgs")));   
 
        driver.quit();
    }
    
    /**
     * Checks if the ajax in album sales reports works. Working test
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByAlbum() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_4")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalAlbumSales:trackSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalAlbumSales:trackSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalAlbumSales:trackSaleInput"));
        input.clear();        
        input.sendKeys("34");
        WebElement form = driver.findElement(By.id("totalAlbumSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSalesAlbumItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalSalesAlbumItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isNotEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the ajax in album sales reports works. Should be empty list
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByAlbumEmpty() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_4")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalAlbumSales:trackSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalAlbumSales:trackSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalAlbumSales:trackSaleInput"));
        input.clear();        
        input.sendKeys("39");
        WebElement form = driver.findElement(By.id("totalAlbumSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSalesAlbumItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalSalesAlbumItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isEmpty();
        driver.quit();
    }
    
    /**
     * Checks if sales by artist ajax works. Working test
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByArtist() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_5")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalArtistSales:artistSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalArtistSales:artistSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalArtistSales:artistSaleInput"));
        input.clear();        
        input.sendKeys("6");
        WebElement form = driver.findElement(By.id("totalArtistSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSalesArtistItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalSalesArtistItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isNotEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the sales by artist is empty if given an artist who was never
     * bought
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByArtistEmpty() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_5")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalArtistSales:artistSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalArtistSales:artistSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalArtistSales:artistSaleInput"));
        input.clear();        
        input.sendKeys("2");
        WebElement form = driver.findElement(By.id("totalArtistSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalSalesArtistItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalSalesArtistItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isEmpty();
        driver.quit();
    }
    
    /**
     * Checks if sales by track ajax works. Working test
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByTrack() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_3")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalTrackSales:trackSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalTrackSales:trackSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalTrackSales:trackSaleInput"));
        input.clear();        
        input.sendKeys("3");
        WebElement form = driver.findElement(By.id("totalTrackSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalTrackSalesItems")));    
        
        WebElement tbody = driver.findElement(By.id("totalTrackSalesItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isNotEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the sales by track is empty.
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByTrackEmpty() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_3")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalTrackSales:trackSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalTrackSales:trackSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalTrackSales:trackSaleInput"));
        input.clear();        
        input.sendKeys("2");
        WebElement form = driver.findElement(By.id("totalTrackSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalTrackSalesItems")));   
        
        WebElement tbody = driver.findElement(By.id("totalTrackSalesItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the sales by track shows errors when the inputs are missing
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByTrackEmptyInput() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_3")).click();
        
        WebElement form = driver.findElement(By.id("totalTrackSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalTrackSales:totalTrackSalesMsgs")));   
        
        driver.quit();
    }
    
    /**
     * Checks if sales by track ajax works. Working test
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByClient() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_2")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalClientSales:clientSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalClientSales:clientSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalClientSales:clientSaleInput"));
        input.clear();        
        input.sendKeys("thunter2r");
        WebElement form = driver.findElement(By.id("totalClientSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalClientSalesItems")));    
        
        WebElement tbody = driver.findElement(By.id("totalClientSalesItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isNotEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the sales by track is empty.
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByClientEmpty() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_2")).click();
        
        WebElement dateStart = driver.findElement(By.id("totalClientSales:clientSaleDateStart_input"));
        dateStart.clear();        
        dateStart.sendKeys("01/01/2017");
        WebElement dateEnd = driver.findElement(By.id("totalClientSales:clientSaleDateEnd_input"));
        dateEnd.clear();        
        dateEnd.sendKeys("22/03/2017");
        WebElement input = driver.findElement(By.id("totalClientSales:clientSaleInput"));
        input.clear();        
        input.sendKeys("agreen26");
        WebElement form = driver.findElement(By.id("totalClientSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalClientSalesItems")));    
        
        WebElement tbody = driver.findElement(By.id("totalClientSalesItems"));
        List<WebElement> items = tbody.findElements(By.tagName("tr"));
        
        assertThat(items).isEmpty();
        driver.quit();
    }
    
    /**
     * Checks if the sales by track shows errors when the inputs are missing
     * 
     * @throws Exception 
     */
    @Test     
    public void testReports_salesByClientEmptyInput() throws Exception {
        driver.get("http://localhost:10860/CSDMusicStore/management/reports.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);         
        wait.until(ExpectedConditions.titleIs("Reports - Fractals"));
        
        driver.findElement(By.id("tab_link_2")).click();
        
        WebElement form = driver.findElement(By.id("totalClientSales"));
        List<WebElement> btn = form.findElements(By.tagName("button"));
        btn.get(0).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalClientSales:totalClientSalesMsgs")));   
        
        driver.quit();
    }
}
