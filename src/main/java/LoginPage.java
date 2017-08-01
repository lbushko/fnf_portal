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
    private static String pageTitle = "WSI° Field & Facilities Login";

    private By nameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By customerIdInput = By.name("customerId");
    private By logInButton = By.name("login");
    private By logInDiv = By.id("ff-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static String getPageTitle() {return pageTitle;}

    public LoginPage getOnPage() {
        driver.get(basePageUrl + "/login.html");
        assertEquals(LoginPage.getPageTitle(), driver.getTitle());
        assertTrue(driver.findElement(logInDiv).isDisplayed());
        return new LoginPage(driver);
    }


    public FieldAndFacilitiesPage logIn(String username, String password, String customerId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(logInDiv));
        driver.findElement(nameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(customerIdInput).sendKeys(customerId);
        driver.findElement(logInButton).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(logInDiv)));
        wait.until(ExpectedConditions.titleIs(FieldAndFacilitiesPage.getPageTitle()));
        return new FieldAndFacilitiesPage(driver);
    }


}
