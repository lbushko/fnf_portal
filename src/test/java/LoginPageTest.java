import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ashendri on 01.08.2017.
 */
public class LoginPageTest extends BaseTest {

    private String username = "ffportal";
    private String password = "fusion";
    private String customerId = "14301";

    @Test
    public void logInTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(username, password, customerId);
    }

    @Test
    public void navigateWithinFieldAndFacilitiesPageTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        FieldAndFacilitiesPage fieldAndFacilitiesPage = loginPage.logIn(username, password, customerId);
        fieldAndFacilitiesPage.checkAllActiveNotamsSelected();
        fieldAndFacilitiesPage.switchToAirportPair();
    }

}