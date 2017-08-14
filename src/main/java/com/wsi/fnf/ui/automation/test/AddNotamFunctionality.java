package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

public class AddNotamFunctionality extends BasePage {

    private By addNotamButton = By.xpath("//button[text()='ADD NEW']");
    private String notamTypeChooser = "//div[contains(@class, 'notam-type-chooser')]";
    private String notamOptionsToChoose = "//a[text()='%s']";
    private By authorizedByDropdown = By.id("dropdown-authorizers");
    private String authorizersDropdown = "//ul/li/a[contains(text(), ' %s ')]";
    private By notamTextInput = By.xpath("//textarea[@name='text']");
    private String dataRow = "(//div[contains(@class, 'ui-grid-row')])[%d]";
    private String addEquipmentButton = "(//div[contains(@class, 'ui-grid-row')]//button)[%d]";
    private String equipmentsTable = "//tbody[contains(@class, 'mini-grid-scroller')]";
    private By updateEquipmentButton = By.xpath("//button[text()='Update']");
    private By publishButton = By.xpath("//button[contains(@class, 'publish')]");
    private By notamCreatedAlert = By.xpath("//div[text()='NOTAM created successfully!']");
    private String category;
    private By expiresInButton = By.xpath("//button[@class='btn btn-default dropdown-toggle ng-binding ng-scope']");
    private String expiresInDropDown = "//ul[@aria-labelledby='dropdown-expireIn']//li";
    private By startDateField = By.name("startDate");
    private By cancelNotamButton = By.xpath("//button[text()='CANCEL NOTAM']");
    private By cancelYesButton = By.xpath("//button[text()=' YES ']");
    private By cancelButton = By.xpath("//button[contains(@class, 'cancel')]");
    private By cancelDialog = By.xpath("//div[contains(@class, 'modal-dialog')]");
    private By discardButton = By.xpath("//button[text()=' DISCARD ']");
    private String notamGridRow = "//div[contains(@class, 'ui-grid-row')]";
    private By closeNotamCanceledAlertButton = By.xpath("//button[text()='×']");
    private By endDateField = By.xpath("//input[@name='endDate']");
    private By notamChangeReason = By.xpath("//textarea[@name='changeReason']");
    private By checkBox = By.xpath("//span[@notam='create.newNotam']//input");
    private String checkBoxElement = "//span[@notam='create.newNotam']/label[%d]/input";


    public AddNotamFunctionality(WebDriver driver) {super(driver);}

    protected String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    protected String getRandomDate(int min, int max) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, getRandomNumber(min, max));
        return new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
    }

    public void selectNotamCategoryToCreate(String category) {
        waitForPageToBeReady();
        this.category = category.toUpperCase();
        clickWhenReady(addNotamButton);
        clickWhenReady(By.xpath(String.format(notamTypeChooser + notamOptionsToChoose, this.category)));
        waitForPageToBeReady();
    }

    public void selectAuthorizedBy(String authorizedBy) {
        waitForPageToBeReady();
        clickWhenReady(authorizedByDropdown);
        clickWhenReady(By.xpath(String.format(authorizersDropdown, authorizedBy)));
        assertEquals(getWhenVisible(authorizedByDropdown).getText(), authorizedBy);
        waitForPageToBeReady();
    }

    public String specifyNotamText(String status) {
        String notamText = status+" "+getCurrentTime();
        WebElement field = getWhenVisible(notamTextInput);
        field.clear();
        field.sendKeys(notamText);
        return notamText;
    }

    public void selectDataRow() {
        int number;
        if (!category.equals("Airport Pair")) { number = 1; } else { number = 2; }
        dataRow = String.format(dataRow, number);
        clickWhenReady(By.xpath(dataRow));
    }

    public String selectDataRowFromSecondaryPanel() {
        callSecondaryPanel();
        String equipmentName = getWhenVisible(By.xpath(equipmentsTable + "//td")).getText();
        clickWhenReady(By.xpath(equipmentsTable + "//td"));
        clickWhenReady(updateEquipmentButton);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(equipmentsTable))));
        return equipmentName;
    }

    public FieldAndFacilitiesPage publishNotam() {
        clickWhenReady(publishButton);
        waitFor(notamCreatedAlert);
        assertTrue(isElementPresent(notamCreatedAlert));
        waitForPageToBeReady();
        return new FieldAndFacilitiesPage(driver);
    }

    private void callSecondaryPanel() {
        int number;
        if (!category.equals("AIRPORT PAIR")) { number = 1; } else { number = 2; }
        clickWhenReady(By.xpath(String.format(addEquipmentButton, number)));
        waitFor(By.xpath(equipmentsTable));
    }


    public void selectStartDate() {
        waitFor(startDateField);
        WebElement startDate = driver.findElement(startDateField);
        startDate.clear();
        startDate.sendKeys(getRandomDate(0,5));
    }

    public String selectEndDate() throws ParseException {
        WebElement endDate = getWhenVisible(endDateField);
        endDate.clear();
        endDate.sendKeys(getRandomDate(5, 10));

        String expiresIn = wait.until(ExpectedConditions.presenceOfElementLocated(endDateField)).getAttribute("value");

        if (expiresIn.equals("[]") || expiresIn.equals("")){expiresIn = "NEVER";}
        else {
            expiresIn = new SimpleDateFormat("MM/dd/yy").format(new SimpleDateFormat("MM/dd/yyyy").parse(expiresIn));
        }
        return expiresIn;
    }

    public String selectExpiresIn() throws InterruptedException, ParseException {
        clickWhenReady(expiresInButton);
        int rnd = getRandomNumber(1, wait.until((ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(expiresInDropDown)))).size());
        clickWhenReady(By.xpath(expiresInDropDown + "["+rnd+"]"));
        String expiresIn = wait.until(ExpectedConditions.presenceOfElementLocated(endDateField)).getAttribute("value");
        if (expiresIn.equals("[]") || expiresIn.equals("")){expiresIn = "NEVER";}
        else {
            expiresIn = new SimpleDateFormat("MM/dd/yy").format(new SimpleDateFormat("MM/dd/yyyy").parse(expiresIn));
        }
        return expiresIn;
    }

    public void checkNotamNotCreated(String notamText) {
        assertFalse(isElementPresent(By.xpath(String.format(notamGridRow + "//div[text()='%s'", notamText))));
    }

    public void cancelNotamCreation() {
        clickWhenReady(cancelButton);
        getWhenVisible(cancelDialog);
        getWhenVisible(discardButton);
        clickWhenReady(discardButton);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(cancelDialog)));
    }

    public void cancelAllNotams() {
        while (getWhenVisible(By.xpath("notamGridRow")).isDisplayed()) {
            cancelNotam();
        }
    }

    public void cancelNotam() {
        clickWhenReady(By.xpath("notamGridRow"));
        clickWhenReady(cancelNotamButton);
        getWhenVisible(cancelDialog);
        clickWhenReady(cancelYesButton);
        clickWhenReady(closeNotamCanceledAlertButton);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(closeNotamCanceledAlertButton));
    }

    public String specifyChangeReason(){
        String notamText = "SOME REASON";
        WebElement field = getWhenVisible(notamChangeReason);
        field.clear();
        field.sendKeys(notamText);
        return notamText;
    }

    public void selectCheckBox(){
        int checkBoxCount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkBox)).size();
        for (int i=1; i <= getRandomNumber(1, checkBoxCount); i++){
            WebElement element = getWhenVisible(By.xpath(String.format(checkBoxElement, getRandomNumber(1, checkBoxCount))));
            if (!element.isSelected()){element.click();}
        }
    }
}
