import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ashendri on 01.08.2017.
 */
public class BaseTest {

    static List<String> sidePanelCategories = Arrays.asList("All Active", "Airport Pair", "Airframe", "Equipment", "Flight", "Airport", "General", "Archive");

    protected String validUsername = "ffportal";
    protected String validPassword = "fusion";
    protected String validCustomerId = "14301";

    protected WebDriver driver;
    protected WebDriverWait wait;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    @Before
    public void startUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
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
