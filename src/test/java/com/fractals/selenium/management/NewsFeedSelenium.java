package com.fractals.selenium.management;

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

/**
 * Selenium test for all pages concerning the news feed. Will test if news pages
 * are capable of performing all CRUD operations successfully.
 *
 * @author Renuchan
 */
@Ignore
public class NewsFeedSelenium {

    private WebDriver driver;

    @Before
    public void loginAsAdmin() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();

        String user = "ren";
        String pwd = "123";

        driver.get("http://localhost:8080/CSDMusicStore/client/login.xhtml");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs("Login"));

        WebElement inputElement = driver.findElement(By.id("loginForm:username"));
        inputElement.clear();
        inputElement.sendKeys(user);

        inputElement = driver.findElement(By.id("loginForm:password"));
        inputElement.clear();
        inputElement.sendKeys(pwd);

        driver.findElement(By.id("loginForm:login")).click();

        wait.until(ExpectedConditions.titleIs("Fractals"));
    }

    @Test      
    public void testDatatableloaded() throws Exception {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");

        List<WebElement> items = driver.findElements(By.id("nf_form:data"));
        boolean isPresent = items.size() > 0 ? true : false;
        assertThat(isPresent);
        driver.quit();
    }

    @Test  
    public void testCreate() {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");
        //count the number of row in the datatable 
        int numRows = driver.findElements(
                By.xpath("//*[@id=\"nf_form:data_data\"]/tr")).size();

        //go to the edit page and enter a new news Feed
        driver.findElement(By.id("nf_form:create")).click();

        try {
            enterData();
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }

        //re count the row and see if it increased by 1
        int numRowAfterCreate = driver.findElements(
                By.xpath("//*[@id=\"nf_form:data_data\"]/tr")).size();

        assertThat(numRowAfterCreate == (numRows + 1));
        driver.quit();

    }

    private void enterData() throws InterruptedException {
        Thread.sleep(3000);

        String source = "cbc";
        String link = "http://rss.cbc.ca/lineup/business.xml";

        //set the link
        WebElement we = driver.findElement(By.id("nf_editForm:link"));
        we.clear();
        we.sendKeys(link);

        //set source 
        WebElement we2 = driver.findElement(By.id("nf_editForm:source"));
        we2.clear();
        we2.sendKeys(source);

        driver.findElement(By.id("nf_editForm:save")).click();

        Thread.sleep(6000);

    }

    @Test
    public void TestEmptyField() {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");
        driver.findElement(By.id("nf_form:create")).click();

        wait(3000);
        //clear link
        WebElement we = driver.findElement(By.id("nf_editForm:link"));
        we.clear();

        //clear source 
        WebElement we2 = driver.findElement(By.id("nf_editForm:source"));
        we2.clear();

        driver.findElement(By.id("nf_editForm:save")).click();

        wait(6000);

        //the page shouldn't change
        WebElement headerEle = driver.findElement(By.id("nf_editForm:pageHeader"));
        String text = headerEle.getText();

        assertThat(text.equals("Insert Data Below"));
        driver.quit();
    }

    @Test
    public void TestDelete() {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");
        wait(3000);
        //count the number of row in the datatable 
        int numRows = driver.findElements(By.xpath("//*[@id=\"nf_form:data_data\"]/tr")).size();

        //delete the last item on the list;

        String id = "nf_form:data:" + (numRows - 1) + ":delete";
        driver.findElement(By.id(id)).click();
        wait(3000);

        //re count the row and see if it increased by 1
        int numRowAfterCreate = driver.findElements(
                By.xpath("//*[@id=\"nf_form:data_data\"]/tr")).size();

        assertThat(numRowAfterCreate == (numRows - 1));

        driver.quit();
    }

    @Test
    public void testInvalidLink()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");
        driver.findElement(By.id("nf_form:create")).click();

        wait(3000);
        //clear link
        WebElement we = driver.findElement(By.id("nf_editForm:link"));
        we.clear();
        we.sendKeys("newLink");

        //clear source 
        WebElement we2 = driver.findElement(By.id("nf_editForm:source"));
        we2.clear();
        we2.sendKeys("cbc");

        driver.findElement(By.id("nf_editForm:save")).click();

        wait(6000);

        //the page shouldn't change
        WebElement headerEle = driver.findElement(By.id("nf_editForm:pageHeader"));
        String text = headerEle.getText();

        assertThat(text.equals("Insert Data Below"));
        driver.quit();
    }
    
    @Test
    public void testLongString()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/newsFeed/List.xhtml");
        driver.findElement(By.id("nf_form:create")).click();

        wait(3000);
        //clear link
        WebElement we = driver.findElement(By.id("nf_editForm:link"));
        we.clear();
        we.sendKeys("newLink.xml");

        //clear source 
        WebElement we2 = driver.findElement(By.id("nf_editForm:source"));
        we2.clear();
        we2.sendKeys(getLongString());

        driver.findElement(By.id("nf_editForm:save")).click();

        wait(6000);

        //the page shouldn't change
        WebElement headerEle = driver.findElement(By.id("nf_editForm:pageHeader"));
        String text = headerEle.getText();

        assertThat(text.equals("Insert Data Below"));
        driver.quit();
    }
    
    private String getLongString()
    {
        StringBuilder str = new StringBuilder();
        
        for(int i = 0; i < 110; i++)
            str.append("a");
        
        return str.toString();
    }
    
    
    
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
