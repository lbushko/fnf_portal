import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by ashendri on 01.08.2017.
 */
public class BaseTest {

    protected WebDriver driver;

    @Before
    public void startUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
