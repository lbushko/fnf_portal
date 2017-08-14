package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;


/**
 * Created by ashendri on 01.08.2017.
 */
public class BaseTest {

    protected String validUsername;
    protected String validPassword;
    protected String validCustomerId;

    protected String authorizedBy = "FOQA";

    protected WebDriver driver;
    protected WebDriverWait wait;

    Properties prop = new Properties();
    InputStream input = null;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    @Parameters("browser")
    @BeforeClass
    public void startUp(String browser) throws IOException {
        driver = Driver.getDriver(browser);
        wait = (WebDriverWait) new WebDriverWait(driver, 10).ignoring(WebDriverException.class);
        input = new FileInputStream("config.properties");
        prop.load(input);
        validUsername = prop.getProperty("validUsername");
        validPassword = prop.getProperty("validPassword");
        validCustomerId = prop.getProperty("validCustomerId");
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }

    @DataProvider(name = "sidePanelCategory")
    public Object[][] sidePanelCategory() {
        return new Object[][]{
                {"All Active"},
                {"Airport Pair"},
                {"Airframe"},
                {"Equipment"},
                {"Flight"},
                {"Airport"},
                {"General"},
                {"Archive"}
        };
    }

    @DataProvider(name = "Category")
    public Object[][] Category() {
        return new Object[][]{
                {"Airport"},
                {"Airport Pair"},
                {"Flight"},
                {"Airframe"},
                {"Equipment"},
                {"General"}
        };
    }

    protected String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    protected FieldAndFacilitiesPage logIn() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        return new FieldAndFacilitiesPage(driver);
    }
}
