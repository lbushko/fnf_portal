import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ashendri on 09.08.2017.
 */
public class NotamSortingFunctionalityTest extends BaseTest {

    @DataProvider(name = "Column")
    public Object[][] ColumnsToSort() {
        return new Object[][]{
                {"ID"},
                {"NOTAM"},
                {"NOTAM Type"},
                {"Expiration"},
                {"Updated"}
        };
    };

    @Test(dataProvider = "Column")
    public void sortNotamsUpTest(String Column) throws Exception {
        logIn();
        NotamsSortingFunctionality notamsSortingFunctionality = new NotamsSortingFunctionality(driver);
        List<String> columnValuesUnsorted = notamsSortingFunctionality.getColumnContent(Column);
        assertEquals(notamsSortingFunctionality.getNotamsSortedOnUi(Column, "up"), notamsSortingFunctionality.getNotamsSortedProgrammatically(columnValuesUnsorted, "up"));
    }

    @Test(dataProvider = "Column")
    public void sortNotamsDownTest(String Column) throws Exception {
        logIn();
        NotamsSortingFunctionality notamsSortingFunctionality = new NotamsSortingFunctionality(driver);
        List<String> columnValuesUnsorted = notamsSortingFunctionality.getColumnContent(Column);
        assertEquals(notamsSortingFunctionality.getNotamsSortedOnUi(Column, "down"), notamsSortingFunctionality.getNotamsSortedProgrammatically(columnValuesUnsorted, "down"));
    }
}