import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.*;

/**
 * Created by ashendri on 01.08.2017.
 */
public class LoginPageTest extends BaseTest {

    private By logInDiv = By.id("ff-login");
    private By failedLoginError = By.xpath("//div[contains(text(), 'Invalid username/password/customerId combination.')]");


    @Test
    public void validLogInTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        wait.until(ExpectedConditions.titleIs(FieldAndFacilitiesPage.getExpectedPageTitle()));
        assertEquals(FieldAndFacilitiesPage.getExpectedPageTitle(), driver.getTitle());
    }

    @Test
    public void invalidLogInTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(randomString(8), randomString(8), randomString(8));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(failedLoginError));
        assertEquals(LoginPage.getExpectedPageTitle(), driver.getTitle());
    }
}