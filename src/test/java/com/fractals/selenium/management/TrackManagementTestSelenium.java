package com.fractals.selenium.management;

import com.fractals.utilities.SeleniumAjaxHelper;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium test for 
 * @author Thai-Vu Nguyen
 */
public class TrackManagementTestSelenium {

    private WebDriver driver;
    private SeleniumAjaxHelper helper;

    @Before
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        helper = new SeleniumAjaxHelper(driver);

        //pray the user is admin
        helper.login();
        getToListPage();
    }

    /**
     * Check if we can reach the Album List page of the management
     */
    @Test
    public void getTrackListPage() {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        //Title of Page is stored to bundle
        String title = bundle.getString("ListTrackTitle");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleIs(title));
        driver.quit();
    }

    @Test
    public void getTrackPreview() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Row 2
        goToPreview(2);

        //Check if all the expected elements are there
        waitForPreviewLoad(wait);

        driver.quit();

    }

    @Test
    public void clickNotChosenTrackPreview() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        //0 for no row selected
        goToPreview (0);
        
        //We should only see dialogForm and it's exit button
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.invisibilityOfElementLocated(By.id("dialogForm:edit"))
        ));
        
        driver.quit();

    }

    /**
     * Assume starting from list page
     *
     * @param by By
     */
    private void goToPreview(int row) {

        clickRow(row);

        helper.retryFindClick(By.xpath("//*[@id=\"form:tbl:preview\"]"));

    }

    private List<WebElement> buildListOfWebElements(WebElement... elements) {
        List<WebElement> list = new ArrayList<>();

        for (WebElement element : elements) {
            list.add(element);
        }

        return list;
    }

    private void getToListPage() {
        driver.get("http://localhost:8080/CSDMusicStore/management/track/tracksList.xhtml");
    }

    private void clickRow(int rowNumber) {
        if (rowNumber > 0) {
            WebElement row
                    = driver.findElement(
                            By.xpath(
                                    "//*[@id=\"form:tbl_data\"]/tr[" + rowNumber + "]")
                    );
            row.click();
        }
    }

    private void waitForPreviewLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:delete")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:edit")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
        ));
    }

    private void waitForPreviewEditLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:save")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:cancel")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm:exit"))
        ));
    }

    private void waitForCreateLoad(WebDriverWait wait) {
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:create")),
                ExpectedConditions.visibilityOfElementLocated(By.id("dialogForm2:exit"))
        ));

    }
}
