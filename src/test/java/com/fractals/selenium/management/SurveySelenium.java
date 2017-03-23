
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
 *  This class will be used to test the integrity of the all survey pages on
 *  the management side. 
 * 
 * @author Renuchan
 */
public class SurveySelenium {
 
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
    public void tableLoad()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/survey/List.xhtml");

        List<WebElement> items = driver.findElements(By.id("surveyInfo:data"));
        boolean isPresent = items.size() > 0 ? true : false;
        assertThat(isPresent);
        driver.quit();
    }
    
    @Test
    public void testCreate()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/survey/List.xhtml");
        //count the number of row in the datatable 
        int numRows = driver.findElements(
                By.xpath("//*[@id=\"surveyInfo:data_data\"]/tr")).size();

        //go to the edit page and enter a new news Feed
        driver.findElement(By.id("create")).click();

        try {
            String question = "HEllo";
            String[] choices = {"a","b", "c", "d"}; 
            
            enterData(question, choices);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }

        //re count the row and see if it increased by 1
        int numRowAfterCreate = driver.findElements(
                By.xpath("//*[@id=\"surveyInfo:data_data\"]/tr")).size();

        assertThat(numRowAfterCreate == (numRows + 1));
        driver.quit();
    }
    
    private void enterData(String question, String[] choices) throws InterruptedException {
        Thread.sleep(3000);
        //set the question
        WebElement we = driver.findElement(By.id("survey_form:question"));
        we.clear();
        we.sendKeys(question);
        
        //Grab the choice text fields 
        
        for(int i = 0; i < 4; i++)
        {
            String xpathStr = "//*[@id=\"survey_form:choicesInput:" + i + ":choice\"]"; 
            WebElement ele = driver.findElement(By.xpath(xpathStr));
            ele.clear();
            ele.sendKeys(choices[i]);          
        }
              
        driver.findElement(By.id("survey_form:save")).click();
        Thread.sleep(6000);

    }
    
    @Test
    public void testDelete()
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/survey/List.xhtml");
        wait(3000);
        //count the number of row in the datatable 
        int numRows = driver.findElements(By.xpath("//*[@id=\"surveyInfo:data_data\"]/tr")).size();

        //delete the last item on the list;

        String id = "surveyInfo:data:" + (numRows - 1) + ":delete";
        driver.findElement(By.id(id)).click();
        wait(3000);

        //re count the row and see if it increased by 1
        int numRowAfterCreate = driver.findElements(
                By.xpath("//*[@id=\"surveyInfo:data_data\"]/tr")).size();

        assertThat(numRowAfterCreate == (numRows - 1));

        driver.quit();
    }
    
    @Test
    public void testEmptyQuestion()
    {
        String question = "";
        String[] choices = {"a","d","a","d"}; 
        
        assertThat(specialCreateCases(question, choices, "Please provide a Question")); 
        driver.quit();
    }
    
    @Test
    public void testEmptyChoice()
    {
        String question = "HEllo There";
        String[] choices = {"","d","a","d"}; 
        
        assertThat(specialCreateCases(question, choices, "Please provide a choice for the question"));  
        driver.quit();
    }
    
    @Test
    public void testLongQuestion()
    {
        String text = "Content is too long"; 
        
        String question = getLongString();
        String[] choices = {"","d","a","d"}; 
        
        assertThat(specialCreateCases(question, choices, text)); 
        driver.quit();
        
    }
    
    @Test
    public void testLongChoice()
    {
        String text = "Content is too long"; 
        
        String question = "Hello";
        String[] choices = {getLongString(),"d","a","d"};  
        
        assertThat(specialCreateCases(question, choices, text)); 
        driver.quit();
        
    }

    private boolean specialCreateCases(String question, String[] choices, 
            String searchText)
    {
        driver.get("http://localhost:8080/CSDMusicStore/management/survey/List.xhtml");
        driver.findElement(By.id("create")).click();
        
        try {
            enterData(question,choices);
        } catch (InterruptedException ex) {
            Logger.getLogger(SurveySelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        wait(1500); 
        return checkErrorText(searchText);
    }
    
    private boolean checkErrorText(String keyText)
    {
        //check growl has the message 
        List<WebElement> error_msgs = driver.findElements(
                By.xpath("//*[@id=\"survey_form:growl_container\"]/div[1]/div/div[2]/span")); 
        
        for(WebElement ele: error_msgs)
            if (ele.getText().equals(keyText))
                return true; 
            
        return false; 
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewsFeedSelenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getLongString()
    {
        StringBuilder str = new StringBuilder();
        
        for(int i = 0; i < 110; i++)
            str.append("a");
        
        return str.toString();
    }
}
