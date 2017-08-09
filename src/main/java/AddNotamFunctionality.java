import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by ashendri on 03.08.2017.
 */
public class AddNotamFunctionality extends BasePage {

    private By addNotamButton = By.xpath("//button[text()='ADD NEW']");
    private String notamTypeChooser = "//div[contains(@class, 'notam-type-chooser')]";
    private String notamOptionsToChoose = "//a[text()='%s']";
    private By authorizedByDropdown = By.id("dropdown-authorizers");
    private String authorizersDropdown = "//ul/li/a[contains(text(), '%s')]";
    private By notamTextInput = By.xpath("//textarea[@name='text']");
    private String dataRow = "(//div[contains(@class, 'ui-grid-row')])[%d]";
    private String addEquipmentButton = "(//div[contains(@class, 'ui-grid-row')]//button)[%d]";
    private String equipmentsTable = "//tbody[contains(@class, 'mini-grid-scroller')]";
    private By updateEquipmentButton = By.xpath("//button[text()='Update']");
    private By publishButton = By.xpath("//button[contains(@class, 'publish')]");
    private By notamCreatedAlert = By.xpath("//div[text()='NOTAM created successfully!']");
//    private By notamsList = By.xpath("(//div[@role='rowgroup'])[2]");
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
        this.category = category.toUpperCase();
        clickOn(addNotamButton);
        waitFor(By.xpath(notamTypeChooser));
        clickOn(By.xpath(String.format(notamTypeChooser + notamOptionsToChoose, this.category)));
    }

    public void selectAuthorizedBy(String authorizedBy) {
        waitFor(authorizedByDropdown);
        clickOn(authorizedByDropdown);
        waitFor(By.xpath(String.format(authorizersDropdown, authorizedBy)));
        driver.findElement(By.xpath(String.format(authorizersDropdown, authorizedBy))).click();
        assertEquals(driver.findElement(authorizedByDropdown).getText(), authorizedBy);
    }

    public String specifyNotamText(String status) {
        String notamText = status+" "+getCurrentTime();
        WebElement field = driver.findElement(notamTextInput);
        field.clear();
        field.sendKeys(notamText);
        return notamText;
    }

    public void selectDataRow() {
        int number;
        if (!category.equals("AIRPORT PAIR")) { number = 1; } else { number = 2; }
        dataRow = String.format(dataRow, number);
        waitFor(By.xpath(dataRow));
        clickOn(By.xpath(dataRow));
    }

    public String selectDataRowFromSecondaryPanel() {
        callSecondaryPanel();
        String equipmentName = driver.findElement(By.xpath(equipmentsTable + "//td")).getText();
        clickOn(By.xpath(equipmentsTable + "//td"));
        clickOn(updateEquipmentButton);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(equipmentsTable))));
        return equipmentName;
    }

    public FieldAndFacilitiesPage publishNotam() {
        waitFor(publishButton);
        assertFalse(isElementPresent(notamCreatedAlert));
        driver.findElement(publishButton).click();
        waitFor(notamCreatedAlert);
        assertTrue(isElementPresent(notamCreatedAlert));
        return new FieldAndFacilitiesPage(driver);
    }

    private void callSecondaryPanel() {
        int number;
        if (!category.equals("AIRPORT PAIR")) { number = 1; } else { number = 2; }
        waitFor(By.xpath(String.format(addEquipmentButton, number)));
        clickOn(By.xpath(String.format(addEquipmentButton, number)));
        waitFor(By.xpath(equipmentsTable));
    }


    public void selectStartDate() {
        waitFor(startDateField);
        WebElement startDate = driver.findElement(startDateField);
        startDate.clear();
        startDate.sendKeys(getRandomDate(0,5));
    }

    public String selectEndDate() throws ParseException {
        waitFor(endDateField);
        WebElement endDate = driver.findElement(endDateField);
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
        waitFor(expiresInButton);
        clickOn(expiresInButton);
        int rnd = getRandomNumber(1, driver.findElements(By.xpath(expiresInDropDown)).size());
        clickOn(By.xpath(expiresInDropDown + "["+rnd+"]"));
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
        waitFor(cancelButton);
        driver.findElement(cancelButton).click();
        waitFor(cancelDialog);
        driver.findElement(discardButton).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(cancelDialog)));
//        assertFalse(driver.findElement(cancelDialog).isDisplayed());
    }

    public void cancelAllNotams() {
        while (isElementPresent(By.xpath("notamGridRow"))) {
            cancelNotam();
        }
    }

    public void cancelNotam() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("notamGridRow")));
        clickOn(By.xpath("notamGridRow"));
        wait.until(ExpectedConditions.elementToBeClickable(cancelNotamButton));
        clickOn(cancelNotamButton);
        waitFor(cancelDialog);
        clickOn(cancelYesButton);
        waitFor(closeNotamCanceledAlertButton);
        clickOn(closeNotamCanceledAlertButton);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(closeNotamCanceledAlertButton));
    }

    public String specifyChangeReason(){
        String notamText = "SOME REASON";
        WebElement field = driver.findElement(notamChangeReason);
        field.clear();
        field.sendKeys(notamText);
        return notamText;
    }
}
