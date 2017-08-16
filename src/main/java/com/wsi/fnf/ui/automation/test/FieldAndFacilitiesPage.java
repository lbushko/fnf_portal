package com.wsi.fnf.ui.automation.test;

import org.junit.Assert;
import org.openqa.selenium.*;
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
    private String row = "//div[@class='ui-grid-canvas']/div[%d]/div/div[1]";
    private By gridHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[1]");
    private String columData = "//div[@class='ui-grid-canvas']/div/div/div[%s]/div";
    private By updatedColumnHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[text()='Updated']");
    private String lastDataRow = "//div[@class='ui-grid-canvas']/div[last()]/div/div";
    private String lastDataRowCell = "//div[@class='ui-grid-canvas']/div[last()]/div/div[%d]";
    private By cancelNotamButton = By.xpath("//button[text()='CANCEL NOTAM']");
    private By cancelYesButton = By.xpath("//button[text()=' YES ']");
    private By cancelYesButton2 = By.xpath("//button[@class='btn btn-action ng-binding']");
    private By notamCanceledAlert = By.xpath("//div[text()='Notam cancelled successfully!']");
    private By dublicateNotamButton = By.xpath("//button[text()='DUPLICATE NOTAM']");
    private By updateNotamButton = By.xpath("//button[text()='UPDATE NOTAM']");
    private By notamUpdatedAlert = By.xpath("//div[text()='Notam updated successfully!']");
    private static String expectedPageTitle = "WSI° Field & Facilities";
    private By checkBox = By.xpath("//span[@notam='notam.selectedNotam']//input");
    private String checkBoxElement = "//span[@notam='notam.selectedNotam']/label[%d]/input";


    public FieldAndFacilitiesPage(WebDriver driver) { super(driver); }

    public static String getExpectedPageTitle() { return expectedPageTitle; }

    public void switchTo(String category) {
        clickWhenReady(By.xpath(String.format(sidePanelCategoryLink, category)));
        waitForPageToBeReady();
    }

    public void checkSidePanelSelected(String category) {
        if (category.equals("Archive")) {
            assertThat(getWhenVisible(topBinding).getText(), containsString(String.format("%sD COMPANY NOTAMS", category).toUpperCase()));
        } else {
            assertThat(getWhenVisible(topBinding).getText(), containsString(String.format("%s NOTAMS", category).toUpperCase()));
        }
        assertThat(getWhenVisible(selectedSidePanelLi).getText(), containsString(category.toUpperCase()));
    }

    public void compareNotamsSidePanelAndTable(String category) {
        int notamsNumber;
        if (!category.equals("Archive")) {
            waitFor(By.xpath(String.format(sidePanelCategoryLink + "/span", category)));
            if (!isElementPresent(dataRow)) {
                notamsNumber = 0;
            }
            else {
                notamsNumber = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dataRow)).size();
            }
            assertEquals(Integer.parseInt(getWhenVisible(By.xpath(String.format(sidePanelCategoryLink + "/span", category))).getText()), notamsNumber);
        }
    }

    public void checkSorting() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(gridHeader));
        List<WebElement> columns = driver.findElements(gridHeader);

        int count = 1;
        for (WebElement header : columns) {
            if (!header.getText().equals("")) {
                header.click();
                List<WebElement> columnData = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(columData, count))));
                List<String> stringsASC = new ArrayList<String>();

                for (WebElement e : columnData
                        ) {
                    stringsASC.add(e.getText());
                }

                List<String> tmpASC = stringsASC;
                Collections.sort(tmpASC);
                Assert.assertEquals(tmpASC, stringsASC);

                header.click();
                List<WebElement> columnDataDESC = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(columData, count))));
                List<String> stringsDESC = new ArrayList<String>();

                for (WebElement e : columnDataDESC
                        ) {
                    stringsDESC.add(e.getText());
                }

                List<String> tmpDESC = stringsDESC;
                Collections.sort(tmpDESC,Collections.reverseOrder());
                Assert.assertEquals(tmpDESC, stringsDESC);
            }
            count++;
        }
    }

    public void clickLastDataRow(){
        waitForPageToBeReady();
        clickWhenReady(updatedColumnHeader);
        if (getWhenVisible(By.xpath(lastDataRow)).isDisplayed()) {
            WebElement element = driver.findElement(By.xpath(lastDataRow));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            clickWhenReady(By.xpath(lastDataRow));
        } else {
            System.out.print("No NOTAMs available");
        }
    }

    public void checkNotamCreated(String notamText, String expiresIn) {
        List<WebElement> columnHeaders = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(gridHeader));
        List<String> strings = new ArrayList<String>();

        for (WebElement e : columnHeaders
                ) {
            strings.add(e.getText());
        }

        int columnExpirationIndex = strings.indexOf("EXPIRATION") + 1;
        int columnNotamindex = strings.indexOf("NOTAM") + 1;

        Assert.assertEquals(notamText, getWhenVisible(By.xpath(String.format(lastDataRowCell, columnNotamindex))).getText());
        Assert.assertEquals(expiresIn, getWhenVisible(By.xpath(String.format(lastDataRowCell, columnExpirationIndex))).getText());
    }

    public void deleteNotam(){
        String parentWindow = driver.getWindowHandle();
        clickWhenReady(cancelNotamButton);

        for (String handler : driver.getWindowHandles()
                ) {
            driver.switchTo().window(handler);
        }

        waitForPageToBeReady();
        try {
            getWhenVisible(cancelYesButton);
            clickWhenReady(cancelYesButton);
        } catch (WebDriverException e){
            getWhenVisible(cancelYesButton2);
            clickWhenReady(cancelYesButton2);
        }
        driver.switchTo().window(parentWindow);
        waitForPageToBeReady();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cancelYesButton));
        assertTrue(getWhenVisible(notamCanceledAlert).isDisplayed());
    }

    public AddNotamFunctionality clickDuplicateNotam(){
        clickLastDataRow();
        clickWhenReady(dublicateNotamButton);
        return new AddNotamFunctionality(driver);
    }

    public void clickUpdateNotam(){
        clickWhenReady(updateNotamButton);
        getWhenVisible(notamUpdatedAlert);
        assertTrue(getWhenVisible(notamUpdatedAlert).isDisplayed());
        refreshPage();
        waitForPageToBeReady();
    }

    public void selectCheckBox(){
        int checkBoxCount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkBox)).size();
        for (int i=1; i <= getRandomNumber(1, checkBoxCount); i++){
            WebElement element = getWhenVisible(By.xpath(String.format(checkBoxElement, getRandomNumber(1, checkBoxCount))));
            if (!element.isSelected()){element.click();}
        }
    }
}
