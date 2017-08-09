import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private By gridHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[1]");
    //TODO переробити цей селект
//    private String columData = "//div[@class='ng-isolate-scope']/div['%s']";
    private String columData = "//div[@class='ui-grid-canvas']/div/div/div[%s]/div";
    private By idColumnHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[text()='ID']");
    private String lastDataRow = "//div[@class='ui-grid-canvas']/div[last()]/div/div";
    private String lastDataRowCell = "//div[@class='ui-grid-canvas']/div[last()]/div/div[%d]";
    private By cancelNotamButton = By.xpath("//button[text()='CANCEL NOTAM']");
    private By cancelYesButton = By.xpath("//button[text()=' YES ']");
    private By notamCanceledAlert = By.xpath("//div[text()='Notam cancelled successfully!']");
    private By dublicateNotamButton = By.xpath("//button[text()='DUPLICATE NOTAM']");


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
        int notamsNumber;
        if (!category.equals("Archive")) {
            waitFor(By.xpath(String.format(sidePanelCategoryLink + "/span", category)));
            if (!isElementPresent(dataRow)) {
                notamsNumber = 0;
            }
            else {
                notamsNumber = driver.findElements(dataRow).size();
            }
            assertEquals(Integer.parseInt(driver.findElement(By.xpath(String.format(sidePanelCategoryLink + "/span", category))).getText()), notamsNumber);
        }
    }

    public void checkSorting() {

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

    public void clickLasDataRow(){
        waitFor(idColumnHeader);
        clickOn(idColumnHeader);
        WebElement element = driver.findElement(By.xpath(lastDataRow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(lastDataRow)));
        clickOn(By.xpath(lastDataRow));
    }

    public void checkNotamCreatedAndCancel(String notamText, String expiresIn) {

        List<WebElement> columnHeaders = driver.findElements(gridHeader);

        List<String> strings = new ArrayList<String>();

        for (WebElement e : columnHeaders
                ) {
            strings.add(e.getText());
            System.out.println(e.getText());
        }

        int columnExpirationIndex = strings.indexOf("EXPIRATION") + 1;
        int columnNotamindex = strings.indexOf("NOTAM") + 1;
        System.out.println(columnExpirationIndex);

        clickLasDataRow();

        System.out.println("expiresIn: " + expiresIn);
        System.out.println("in table: " + driver.findElement(By.xpath(String.format(lastDataRowCell, columnExpirationIndex))).getText());

        Assert.assertEquals(notamText, driver.findElement(By.xpath(String.format(lastDataRowCell, columnNotamindex))).getText());
        Assert.assertEquals(expiresIn, driver.findElement(By.xpath(String.format(lastDataRowCell, columnExpirationIndex))).getText());

        String parentWindow = driver.getWindowHandle();

        waitFor(cancelNotamButton);
        clickOn(cancelNotamButton);

        for (String handler : driver.getWindowHandles()
                ) {
            driver.switchTo().window(handler);
        }
        wait.until(ExpectedConditions.elementToBeClickable(cancelYesButton));
        clickOn(cancelYesButton);
        driver.switchTo().window(parentWindow);

        waitFor(notamCanceledAlert);
        assertTrue(isElementPresent(notamCanceledAlert));
    }

    public AddNotamFunctionality clickDuplicateNotam(){
        clickLasDataRow();
        waitFor(dublicateNotamButton);
        clickOn(dublicateNotamButton);
        return new AddNotamFunctionality(driver);
    }
}
