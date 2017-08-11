package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by ashendri on 01.08.2017.
 */
public class LoginPageTest extends BaseTest {

    private By logInDiv = By.id("ff-login");
    private By failedLoginError = By.xpath("//div[contains(text(), 'Invalid username/password/customerId combination.')]");

    @DataProvider(name = "config.properties")
    public Object[][] credentials(){
        return new Object[][]{
                {randomString(8), randomString(8), randomString(8)},
                {"", "", ""},
                {"  ", "  ", "  "},
                {"~!@#$%^&*()-_=+[]\\{}|;':\",./<>?", "~!@#$%^&*()-_=+[]\\{}|;':\",./<>?", "~!@#$%^&*()-_=+[]\\{}|;':\",./<>?"},
                {validUsername.toUpperCase(), validPassword.toUpperCase(), validCustomerId.toUpperCase()}
        };
    }

    @Test
    public void validLogInTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        wait.until(ExpectedConditions.titleContains("Field & Facilities"));
        String title = driver.getTitle();
        assertTrue(title.contains("WSI") && title.contains("Field & Facilities"));
    }

    @Test(dataProvider = "config.properties")
    public void invalidLogInTest(String username, String password, String customerId) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(username, password, customerId);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(failedLoginError));
        String title = driver.getTitle();
        assertTrue(title.contains("WSI") && title.contains("Field & Facilities Login"));
    }
}