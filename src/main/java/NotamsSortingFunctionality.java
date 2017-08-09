import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.*;

/**
 * Created by ashendri on 09.08.2017.
 */
public class NotamsSortingFunctionality extends BasePage {

    public NotamsSortingFunctionality(WebDriver driver) {super(driver);}

    public Map<String, Integer> notamColumns = ImmutableMap.of("ID", 1, "NOTAM", 2, "NOTAM Type", 3, "Expiration", 4, "Updated", 5);

    private String columnHeader = "//span[contains(@class, 'ui-grid-header-cell-label') and text()='%s']";
    private String tableCell = "//div[contains(@class, 'ui-grid-canvas')]/div[contains(@class, 'ui-grid-row')][%s]//div[@role='gridcell'][%d]";
    private String sortingArrow = "//i[contains(@class, 'ui-grid-icon-%s-dir')]";

    public List getColumnContent(String columnName) {
        List<String> columnValues = new ArrayList<String>();
        int rowNum = 1;
        while (isElementPresent(By.xpath(String.format(tableCell, rowNum, notamColumns.get(columnName))))) {
            columnValues.add(driver.findElement(By.xpath(String.format(tableCell, rowNum, notamColumns.get(columnName)))).getText());
            rowNum++;
        }
        return columnValues;
    }

    public String getSortingOrder(String columnName) {
        String order = "";
        waitFor(By.xpath(String.format(columnHeader, columnName)));
        clickOn(By.xpath(String.format(columnHeader, columnName)));
        if (isElementPresent(By.xpath(String.format(sortingArrow, "up")))) {
            order = "up";
        } else if (isElementPresent(By.xpath(String.format(sortingArrow, "down")))) {
            order = "down";
        }
        return order;
    }

    public List sortNotams(List notams, String order) {
        if (order.equals("up")) {
            Collections.sort(notams);
        } else if (order.equals("down")) {
            Collections.sort(notams,Collections.reverseOrder());
        }
        return notams;
    }

    public void printList(String comment, List list) {
        System.out.println("\n" + comment);
        for (Object value : list) {
            System.out.println(value);
        }
    }



}
