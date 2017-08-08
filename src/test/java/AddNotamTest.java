import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by ashendri on 04.08.2017.
 */
public class AddNotamTest extends BaseTest {

    private String authorizedBy = "FOQA";

    @DataProvider(name = "Category")
    public static Object[][] Category(){
        return new Object[][]{
                {"airframe"},
                {"equipment"}
        };
    }
    
    @DataProvider(name = "CategoryWithSecondPanel")
    public static Object[][] CategoryWithSecondPanel() {
        return new Object[][]{
                {"airport"},
                {"airport pair"},
                {"flight"},
                {"general"}
        };
    }

        @Test(dataProvider = "Category")
        public void addAirframeNotamTest (String Category) throws Exception {
            logIn();
            AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
            addNotamFunctionality.selectNotamCategoryToCreate(Category);
            addNotamFunctionality.selectAuthorizedBy(authorizedBy);
            addNotamFunctionality.selectStartDate();
            addNotamFunctionality.selectExpiresIn();
            String notamText = addNotamFunctionality.specifyNotamText();
            addNotamFunctionality.selectDataRow();
            addNotamFunctionality.publishNotam();
        }

        @Test(dataProvider = "CategoryWithSecondPanel")
        public void addAirportNotamTest (String Category) throws Exception {
            logIn();
            AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
            addNotamFunctionality.selectNotamCategoryToCreate(Category);
            addNotamFunctionality.selectAuthorizedBy(authorizedBy);
            addNotamFunctionality.selectStartDate();
            addNotamFunctionality.selectExpiresIn();
            addNotamFunctionality.specifyNotamText();
            addNotamFunctionality.selectDataRow();
            addNotamFunctionality.selectDataRowFromSecondaryPanel();
            String notamText = addNotamFunctionality.specifyNotamText();
            addNotamFunctionality.publishNotam();
        }

}
