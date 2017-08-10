import org.testng.annotations.Test;

public class UpdateNotamTest extends BaseTest {

    @Test(dataProvider = "Category")
    public void duplicateNotamTest(String Category) throws Exception {
        FieldAndFacilitiesPage fieldAndFacilitiesPage = logIn();
        fieldAndFacilitiesPage.switchTo(Category);
        fieldAndFacilitiesPage.clickLasDataRow();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        String expiresIn = addNotamFunctionality.selectEndDate();
        String notamText = addNotamFunctionality.specifyNotamText("UPDATE");
        String notamChangeReason = addNotamFunctionality.specifyChangeReason();
        fieldAndFacilitiesPage.clickUpdateNotam();
    }
}
