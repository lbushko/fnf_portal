import org.testng.annotations.Test;

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
        addNotamFunctionality.selectAirports();
        String equipmentName = addNotamFunctionality.addEquipment();
        addNotamFunctionality.publishNotam();
    }


    @Test
    public void addAirportPairNotamTest() throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate("airport pair");
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        String notamText = addNotamFunctionality.specifyNotamText();
        addNotamFunctionality.selectAirports();
        String equipmentName = addNotamFunctionality.addEquipment();
        addNotamFunctionality.publishNotam();
    }
}
