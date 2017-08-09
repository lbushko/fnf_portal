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
            String expiresIn = addNotamFunctionality.selectExpiresIn();
            System.out.println(expiresIn);
            String notamText = addNotamFunctionality.specifyNotamText("NEW");
            if (!Category.equals("General")) {
                addNotamFunctionality.selectDataRow();
            }
            if (Arrays.asList("Airport", "Airport pair", "Flight").contains(Category)) {
                addNotamFunctionality.selectDataRowFromSecondaryPanel();
            }
            FieldAndFacilitiesPage fieldAndFacilitiesPage = addNotamFunctionality.publishNotam();
            fieldAndFacilitiesPage.checkNotamCreatedAndCancel(notamText, expiresIn);
        }



//    @Test
//    public void cancelNotam() throws Exception {
//        logIn();
//        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
//        addNotamFunctionality.cancelAllNotams();
//    }


}


