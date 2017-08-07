import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
//    private String addEquipmentButton = "(//button[text()='add equipment...'])[%d]";
    private String addEquipmentButton = "(//div[contains(@class, 'ui-grid-row')]//button)[%d]";
    private String equipmentsTable = "//tbody[contains(@class, 'mini-grid-scroller')]";
    private By updateEquipmentButton = By.xpath("//button[text()='Update']");
    private By publishButton = By.xpath("//button[text()='Publish']");
    private By notamCreatedAlert = By.xpath("//div[text()='NOTAM created successfully!']");
    private String category;
    private By expiresInButton = By.xpath("//button[@class='btn btn-default dropdown-toggle ng-binding ng-scope']");
    private String expiresInDropDown = "//ul[@aria-labelledby='dropdown-expireIn']//li";
    private By startDateField = By.name("startDate");

    public AddNotamFunctionality(WebDriver driver) {super(driver);}

    protected String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    protected String getStartDate() {
        return new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
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

    public void publishNotam() {
        waitFor(publishButton);
        assertFalse(isElementPresent(notamCreatedAlert));
        driver.findElement(publishButton).click();
        waitFor(notamCreatedAlert);
        assertTrue(isElementPresent(notamCreatedAlert));
    }

    private void callSecondaryPanel() {
        int number;
        if (this.category.equals("AIRPORT PAIR")) { number = 2; } else { number = 1; }
        waitFor(By.xpath(String.format(addEquipmentButton, number)));
        clickOn(By.xpath(String.format(addEquipmentButton, number)));
        waitFor(By.xpath(equipmentsTable));
    }

    public void selectStartDate(){
        waitFor(startDateField);
        WebElement startDate = driver.findElement(startDateField);
        startDate.clear();
        startDate.sendKeys(getStartDate());
    }

    public void selectExpiresIn() {
        waitFor(expiresInButton);
        clickOn(expiresInButton);
        int rnd = getRandomNumber(1, driver.findElements(By.xpath(expiresInDropDown)).size());
        clickOn(By.xpath(expiresInDropDown + "["+rnd+"]"));
    }
}
