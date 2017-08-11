import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
    private By updateNotamButton = By.xpath("//button[text()='UPDATE NOTAM']");
    private By notamUpdatedAlert = By.xpath("//div[text()='Notam updated successfully!']");

    private static String expectedPageTitle = "WSI° Field & Facilities";


    public FieldAndFacilitiesPage(WebDriver driver) { super(driver); }

    public static String getExpectedPageTitle() { return expectedPageTitle; }

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

        for (WebElement header : columns) {
            if (!header.getText().equals("")) {
                System.out.println("Clicking header... " + header.getText());
                header.click();
                System.out.println("Waiting for coulumnData..." + String.format(columData, count));
                List<WebElement> columnData = driver.findElements(By.xpath(String.format(columData, count)));
                List<String> stringsASC = new ArrayList<String>();
                for (WebElement e : columnData
                        ) {
                    stringsASC.add(e.getText());
                }
                for (String s : stringsASC
                        ) {
                    System.out.println("Actual: " + s);
                }
                List<String> tmpASC = stringsASC;
                Collections.sort(tmpASC);
                for (String s : tmpASC
                        ) {
                    System.out.println("Expected: " + s);
                }
                Assert.assertEquals(tmpASC, stringsASC);
                System.out.println("------------------------------------");
                System.out.println(count);
                System.out.println("Clicking header... " + header.getText());
                header.click();
                System.out.println("Waiting for coulumnData..." + String.format(columData, count));
                List<WebElement> columnDataDESC = driver.findElements(By.xpath(String.format(columData, count)));
                List<String> stringsDESC = new ArrayList<String>();
                for (WebElement e : columnDataDESC
                        ) {
                    stringsDESC.add(e.getText());
                }
                for (String s : stringsDESC
                        ) {
                    System.out.println("DESC Actual: " + s);
                }
                List<String> tmpDESC = stringsDESC;
                for (String s : tmpDESC
                        ) {
                    System.out.println("DESC Expected: " + s);
                }
                Collections.sort(tmpDESC,Collections.reverseOrder());
                Assert.assertEquals(tmpDESC, stringsDESC);
                System.out.println("------------------------------------");
                System.out.println(count);
            }
            count++;
        }
    }

    public void clickLastDataRow(){
        waitFor(idColumnHeader);
        clickOn(idColumnHeader);
        if (isElementPresent(By.xpath(lastDataRow))) {
            waitForClickability(By.xpath(lastDataRow));
            WebElement element = driver.findElement(By.xpath(lastDataRow));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(lastDataRow)));
            clickOn(By.xpath(lastDataRow));
        } else {
            System.out.print("No NOTAMs available");
        }


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

        clickLastDataRow();

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
        clickLastDataRow();
        waitFor(dublicateNotamButton);
        clickOn(dublicateNotamButton);
        return new AddNotamFunctionality(driver);
    }

    public void clickUpdateNotam(){
        waitFor(updateNotamButton);
        clickOn(updateNotamButton);
        waitFor(notamUpdatedAlert);
        assertTrue(isElementPresent(notamUpdatedAlert));
    }
}
