import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ashendri on 01.08.2017.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static String expectedPageTitle;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public static String getExpectedPageTitle() {return expectedPageTitle;}

    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    protected void waitFor(By by) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
    }

    protected void clickOn(By by) {
        driver.findElement(by).click();
    }

    public int getRandomNumber(int min, int max){
        return new Random().nextInt((max - min) + 1) + min;
    }
}
