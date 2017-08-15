package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static String expectedPageTitle;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = (WebDriverWait) new WebDriverWait(driver, 10).ignoring(WebDriverException.class);
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    protected void clickWhenReady(By locator){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    protected WebElement getWhenVisible(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitFor(By locator){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForPageToBeReady(){
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").
                        equals("complete");
            }
        });
    }

    public void refreshPage(){
        driver.navigate().refresh();
        waitForPageToBeReady();
    }

    public int getRandomNumber(int min, int max){
        return new Random().nextInt((max - min) + 1) + min;
    }
}
