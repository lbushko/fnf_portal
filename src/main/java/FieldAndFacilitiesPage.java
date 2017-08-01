import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by ashendri on 01.08.2017.
 */
public class FieldAndFacilitiesPage extends BasePage {

    private By topBinding = By.xpath("//h1[contains(@class, 'ng-binding')]");

    private By sidePanelAllActiveLink = By.xpath("//ul[contains(@class, 'navbar-nav')][2]/li[contains(text(), 'ALL ACTIVE')]");
    private By sidePanelAirportPairLink = By.xpath("//ul[contains(@class, 'navbar-nav')][2]/li/a[contains(text(), 'AIRPORT PAIR')]");

    private By sidePanelLi = By.xpath("//ul[contains(@class, 'navbar-nav')][2]/li[contains(@class, 'selected')]");

    public static String pageTitle = "WSI° Field & Facilities";

    public static String getPageTitle() {return pageTitle;}

    public FieldAndFacilitiesPage(WebDriver driver) {super(driver);}

    public void checkAllActiveNotamsSelected() {
        assertThat(driver.findElement(topBinding).getText(), containsString("ALL ACTIVE NOTAMS"));
        assertThat(driver.findElement(sidePanelLi).getText(), containsString("ALL ACTIVE"));
    }

    public void checkAirPortPairSelected() {
        assertThat(driver.findElement(topBinding).getText(), containsString("AIRPORT PAIR NOTAMS"));
        assertThat(driver.findElement(sidePanelLi).getText(), containsString("AIRPORT PAIR"));
    }

    public void switchToAirportPair() {
        driver.findElement(sidePanelAirportPairLink).click();
        checkAirPortPairSelected();
    }
}
