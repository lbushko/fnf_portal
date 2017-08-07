import org.junit.Test;

/**
 * Created by ashendri on 04.08.2017.
 */
public class AddNotamTest extends BaseTest {

    private String authorizedBy = "FOQA";

    @Test
    public void addAirportNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("airport");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        String equipmentName = addNotamFunctionality.selectDataRowFromSecondaryPanel();
        addNotamFunctionality.publishNotam();
    }


    @Test
    public void addAirportPairNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("airport pair");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        String equipmentName = addNotamFunctionality.selectDataRowFromSecondaryPanel();
        addNotamFunctionality.publishNotam();
    }

    @Test
    public void addAirframeNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("airframe");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        addNotamFunctionality.publishNotam();
    }

    @Test
    public void addEquipmentNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("equipment");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        addNotamFunctionality.publishNotam();
    }

    @Test
    public void addFlightNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("flight");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        addNotamFunctionality.selectDataRowFromSecondaryPanel();
        addNotamFunctionality.publishNotam();
    }

    @Test
    public void addGeneralNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("general");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectDataRow();
        addNotamFunctionality.selectDataRowFromSecondaryPanel();
        addNotamFunctionality.publishNotam();
    }
}
