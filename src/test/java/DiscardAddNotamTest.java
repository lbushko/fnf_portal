import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by ashendri on 08.08.2017.
 */
public class DiscardAddNotamTest extends BaseTest {

    @Test(dataProvider = "Category")
    public void discardAddNotamTest(String Category) throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate(Category);
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        addNotamFunctionality.selectStartDate();
        addNotamFunctionality.selectExpiresIn();
        String notamText = addNotamFunctionality.specifyNotamText("NEW");
        if (!Category.equals("General")) {
            addNotamFunctionality.selectDataRow();
        }
        if (Arrays.asList("Airport", "Airport Pair", "Flight").contains(Category)) {
            addNotamFunctionality.selectDataRowFromSecondaryPanel();
        }
        addNotamFunctionality.cancelNotamCreation();
        addNotamFunctionality.checkNotamNotCreated(notamText);
    }
}
