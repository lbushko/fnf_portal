import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.security.SecureRandom;


/**
 * Created by ashendri on 01.08.2017.
 */
public class BaseTest {

    protected String validUsername = "ffportal";
    protected String validPassword = "fusion";
    protected String validCustomerId = "14301";

    protected WebDriver driver;
    protected WebDriverWait wait;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    @Parameters("browser")
    @BeforeClass
    public void startUp(String browser) {
        driver = Driver.getDriver(browser);
        wait = new WebDriverWait(driver, 10);
    }

//    @AfterClass
//    public void tearDown() throws Exception {
//        driver.quit();
//    }

    protected String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }



}
