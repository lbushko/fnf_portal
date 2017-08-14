package com.wsi.fnf.ui.automation.test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Driver {

    protected static WebDriver driver;

    public static WebDriver getDriver(String browser){
        if(browser.equalsIgnoreCase("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        else if(browser.equalsIgnoreCase("headless")){
            driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
        }
        return driver;
    }
}