import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    private By dataRow = By.xpath("//div[@ui-grid-row='row']");
    private By addEquipmentButton = By.xpath("//button[text()='add equipment...']");
    private String equipmentsTable = "//tbody[contains(@class, 'mini-grid-scroller')]";

    public AddNotamFunctionality(WebDriver driver) {super(driver);}

    protected String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(Calendar.getInstance().getTime());
    }

    public void addNotam(String category, String authorizedBy) {
        clickOn(addNotamButton);
        waitFor(By.xpath(notamTypeChooser));
        clickOn(By.xpath(String.format(notamTypeChooser + notamOptionsToChoose, category.toUpperCase())));
        selectAuthorizedBy(authorizedBy.toUpperCase());
        driver.findElement(notamTextInput).sendKeys(getCurrentTime());
        selectAirportPair();
        addEquipment();
    }

    private void selectAuthorizedBy(String authorizedBy) {
        waitFor(authorizedByDropdown);
        clickOn(authorizedByDropdown);
        waitFor(By.xpath(String.format(authorizersDropdown, authorizedBy)));
        driver.findElement(By.xpath(String.format(authorizersDropdown, authorizedBy))).click();
        assertEquals(driver.findElement(authorizedByDropdown).getText(), authorizedBy);
    }

    private void selectAirportPair() {
        waitFor(dataRow);
        driver.findElement(dataRow).click();
    }

    private void addEquipment() {
        waitFor(addEquipmentButton);
        clickOn(addEquipmentButton);
        waitFor(By.xpath(equipmentsTable));
        clickOn(By.xpath(equipmentsTable + "/tr"));
    }

}
