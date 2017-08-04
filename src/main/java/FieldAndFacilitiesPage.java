import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by ashendri on 01.08.2017.
 */
public class FieldAndFacilitiesPage extends BasePage {

    private By topBinding = By.xpath("//h1[contains(@class, 'ng-binding')]");
    private String sidePanelCategoryLink = "//ul[contains(@class, 'navbar-nav')][2]//a[text()='%s']";
    private By selectedSidePanelLi = By.xpath("//ul[contains(@class, 'navbar-nav')][2]/li[contains(@class, 'selected')]");
    private By dataRow = By.xpath("//div[@ui-grid-row='row']");

    public static String expectedPageTitle = "WSI° Field & Facilities";
    public static String getExpectedPageTitle() {return expectedPageTitle;}

    public FieldAndFacilitiesPage(WebDriver driver) {super(driver);}


    public void switchTo(String category) {
        driver.findElement(By.xpath(String.format(sidePanelCategoryLink, category))).click();
    }

    public void checkSidePanelSelected(String category) {
        if (category.equals("Archive")) {
            assertThat(driver.findElement(topBinding).getText(), containsString(String.format("%sD COMPANY NOTAMS", category).toUpperCase()));
        } else {
            assertThat(driver.findElement(topBinding).getText(), containsString(String.format("%s NOTAMS", category).toUpperCase()));
        }
        assertThat(driver.findElement(selectedSidePanelLi).getText(), containsString(category.toUpperCase()));
    }

    public void compareNotamsSidePanelAndTable(String category) {
        if (!category.equals("Archive")) {
            waitFor(By.xpath(String.format(sidePanelCategoryLink + "/span", category)));
            waitFor(dataRow);
            assertEquals(Integer.parseInt(driver.findElement(By.xpath(String.format(sidePanelCategoryLink + "/span", category))).getText()), driver.findElements(dataRow).size());
        }
    }
}
