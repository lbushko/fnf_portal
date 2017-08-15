package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.Assert.assertTrue;

public class LoginPage extends BasePage {

    private String basePageUrl = "https://admin-dev.flightfx.com/fnfportal";
    private static String expectedPageTitle = "WSI° Field & Facilities Login";
    private By nameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By customerIdInput = By.name("customerId");
    private By logInButton = By.name("login");
    private By logInDiv = By.id("ff-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static String getExpectedPageTitle() {return expectedPageTitle;}

    public LoginPage getOnPage() {
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                driver.get(basePageUrl + "/login.html");
                return driver.findElement(logInButton).isDisplayed();
            }
        });

        String title = driver.getTitle();
        assertTrue(title.contains("WSI") && title.contains("Field & Facilities Login"));
        assertTrue(driver.findElement(logInDiv).isDisplayed());
        return new LoginPage(driver);
    }

    public void logIn(String username, String password, String customerId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(logInDiv));
        getWhenVisible(nameInput).sendKeys(username);
        getWhenVisible(passwordInput).sendKeys(password);
        getWhenVisible(customerIdInput).sendKeys(customerId);
        clickWhenReady(logInButton);
    }
}
