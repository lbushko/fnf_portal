import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ashendri on 01.08.2017.
 */
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
        driver.get(basePageUrl + "/login.html");
        String title = driver.getTitle();
        assertTrue(title.contains("WSI") && title.contains("Field & Facilities Login"));
        assertTrue(driver.findElement(logInDiv).isDisplayed());
        return new LoginPage(driver);
    }

    public void logIn(String username, String password, String customerId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(logInDiv));
        driver.findElement(nameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(customerIdInput).sendKeys(customerId);
        driver.findElement(logInButton).click();
    }
}
