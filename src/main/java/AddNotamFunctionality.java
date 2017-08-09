import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    private By notamsList = By.xpath("(//div[@role='rowgroup'])[2]");
    private String category;
    private By expiresInButton = By.xpath("//button[@class='btn btn-default dropdown-toggle ng-binding ng-scope']");
    private String expiresInDropDown = "//ul[@aria-labelledby='dropdown-expireIn']//li";
    private By startDateField = By.name("startDate");
    private By startDateCalendarButton = By.xpath("//div[@class='row form-fields ng-scope']/div[@class='form-field date-time col-xs-3'][1]//button[@class='btn btn-default']");
    private String startDateCalendarDay = "//table[@role='grid']/tbody/tr[%d]/td[%d]";
    private By idColumnHeader = By.xpath("//div[contains(@role, 'columnheader')]//span[text()='ID']");
    private String lastDataRow = "//div[@class='ui-grid-canvas']/div[last()]/div/div";
    private By cancelNotamButton = By.xpath("//button[text()='CANCEL NOTAM']");
    private By cancelYesButton = By.xpath("//button[text()=' YES ']");
    private By notamCanceledAlert = By.xpath("//div[text()='Notam cancelled successfully!']");
    private By cancelButton = By.xpath("//button[contains(@class, 'cancel')]");
    private By cancelDialog = By.xpath("//div[contains(@class, 'modal-dialog')]");
    private By discardButton = By.xpath("//button[text()=' DISCARD ']");

    private By notamGridRow = By.xpath("//div[contains(@class, 'ui-grid-row')]");
    private By yesButton = By.xpath("//button[text()=' YES ']");
    private By closeNotamCanceledAlertButton = By.xpath("//button[text()='×']");

    private By endDateField = By.xpath("//input[@name='endDate']");



    public AddNotamFunctionality(WebDriver driver) {super(driver);}

    protected String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    protected String getRandomDate() {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, getRandomNumber(0, 5));
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

    public String specifyNotamText() {
        String notamText = getCurrentTime();
        driver.findElement(notamTextInput).sendKeys(notamText);
        return notamText;
    }

    public void selectDataRow() {
        int number;
        if (this.category.equals("AIRPORT PAIR")) { number = 2; } else { number = 1; }
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
        if (this.category.equals("AIRPORT PAIR")) { number = 2; } else { number = 1; }
        waitFor(By.xpath(String.format(addEquipmentButton, number)));
        clickOn(By.xpath(String.format(addEquipmentButton, number)));
        waitFor(By.xpath(equipmentsTable));
    }


    public void selectStartDate() {
        waitFor(startDateField);
        WebElement startDate = driver.findElement(startDateField);
        startDate.clear();
        startDate.sendKeys(getRandomDate());
    }

    public String selectExpiresIn() throws InterruptedException {
        waitFor(expiresInButton);
        clickOn(expiresInButton);
        int rnd = getRandomNumber(1, driver.findElements(By.xpath(expiresInDropDown)).size());
        clickOn(By.xpath(expiresInDropDown + "["+rnd+"]"));
        String expiresIn = wait.until(ExpectedConditions.presenceOfElementLocated(endDateField)).getAttribute("value");
        if (expiresIn.equals("[]") || expiresIn.equals("")){expiresIn = "NEVER";}
        return expiresIn;
    }

    public void cancelNotamCreation() {
        waitFor(cancelButton);
        driver.findElement(cancelButton).click();
        waitFor(cancelDialog);
        driver.findElement(discardButton).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(cancelDialog)));
        assertFalse(driver.findElement(cancelDialog).isDisplayed());
    }
}
