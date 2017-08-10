import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.awt.*;
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

    @DataProvider(name = "SortingOrder")
    public Object[][] SortingOrder() {
        return new Object[][]{
                {"up"},
                {"down"}
        };
    };

    @Test(dataProvider = "Column")
    public void sortByIdTest(String Column) throws Exception {
        logIn();
        NotamsSortingFunctionality notamsSortingFunctionality = new NotamsSortingFunctionality(driver);

        List<String> columnValuesUnsorted = notamsSortingFunctionality.getColumnContent(Column);
//        notamsSortingFunctionality.printList("unsorted", columnValuesUnsorted);

        String sortingOrder = notamsSortingFunctionality.getSortingOrder(Column);

        List<String> columnValuesSortedFromUi = notamsSortingFunctionality.getColumnContent(Column);
//        notamsSortingFunctionality.printList("sorted from ui", columnValuesSortedFromUi);

        List<String> columnValuesSortedProgrammatically = notamsSortingFunctionality.sortNotamsProgrammatically(columnValuesUnsorted, sortingOrder);
//        notamsSortingFunctionality.printList("sorted programmatically", columnValuesSortedProgrammatically);

        assertEquals(columnValuesSortedFromUi, columnValuesSortedProgrammatically);

        sortingOrder = notamsSortingFunctionality.getSortingOrder(Column);

        columnValuesSortedFromUi = notamsSortingFunctionality.getColumnContent(Column);
//        notamsSortingFunctionality.printList("sorted from ui", columnValuesSortedFromUi);

        columnValuesSortedProgrammatically = notamsSortingFunctionality.sortNotamsProgrammatically(columnValuesUnsorted, sortingOrder);
//        notamsSortingFunctionality.printList("sorted programmatically", columnValuesSortedProgrammatically);

        assertEquals(columnValuesSortedFromUi, columnValuesSortedProgrammatically);

    }
}
