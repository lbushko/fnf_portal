import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ashendri on 01.08.2017.
 */
public class FieldAndFacilitiesPage extends BasePage {

    private By topBinding = By.xpath("//h1[contains(@class, 'ng-binding')]");
    private String sidePanelCategoryLink = "//ul[contains(@class, 'navbar-nav')][2]//a[text()='%s']";
    private By selectedSidePanelLi = By.xpath("//ul[contains(@class, 'navbar-nav')][2]/li[contains(@class, 'selected')]");
    private By dataRow = By.xpath("//div[@ui-grid-row='row']");
    private By gridHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[1]");
    //TODO переробити цей селект
//    private String columData = "//div[@class='ng-isolate-scope']/div['%s']";
    private String columData = "//div[@class='ui-grid-canvas']/div/div/div[%s]/div";

    public static String expectedPageTitle = "WSI° Field & Facilities";

    public static String getExpectedPageTitle() {
        return expectedPageTitle;
    }

    public FieldAndFacilitiesPage(WebDriver driver) {
        super(driver);
    }


    public void switchTo(String category) {
        waitFor(By.xpath(String.format(sidePanelCategoryLink, category)));
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

    public void ckeckSorting() {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(gridHeader));
        List<WebElement> columns = driver.findElements(gridHeader);

        int count = 1;

        for (WebElement header : columns
                ) {
            if (!header.getText().equals("")) {
                System.out.println("Clicking header... " + header.getText());
                header.click();
                System.out.println("Waiting for coulumnData..." + String.format(columData, count));
                List<WebElement> columnDataZ = driver.findElements(By.xpath(String.format(columData, count)));
                List<String> strings = new ArrayList<String>();

                for (WebElement e : columnDataZ
                        ) {
                    strings.add(e.getText());
                }

                for (String s : strings
                        ) {
                    System.out.println("Actual: " + s);
                }

                List<String> tmp = strings;
                Collections.sort(tmp);

                for (String s : tmp
                        ) {
                    System.out.println("Expected: " + s);
                }

                Assert.assertEquals(tmp, strings);
                System.out.println("------------------------------------");
                System.out.println(count);
            }
            count++;
        }
    }
}
