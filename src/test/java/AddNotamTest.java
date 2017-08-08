import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Arrays;

/**
 * Created by ashendri on 04.08.2017.
 */
public class AddNotamTest extends BaseTest {

        @Test(dataProvider = "Category")
        public void addNotamTest(String Category) throws Exception {
            logIn();
            AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
            addNotamFunctionality.selectNotamCategoryToCreate(Category);
            addNotamFunctionality.selectAuthorizedBy(authorizedBy);
            addNotamFunctionality.selectStartDate();
            addNotamFunctionality.selectExpiresIn();
            String notamText = addNotamFunctionality.specifyNotamText();
            if (!Category.equals("general")) {
                addNotamFunctionality.selectDataRow();
            }
            if (Arrays.asList("airport", "airport pair", "flight").contains(Category)) {
                addNotamFunctionality.selectDataRowFromSecondaryPanel();
            }
            addNotamFunctionality.publishNotam();
        }



    @Test
    public void cancelNotam() throws Exception {

    }

}


