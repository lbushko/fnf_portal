import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ashendri on 02.08.2017.
 */
public class FieldAndFacilitiesPageTest extends BaseTest {

    private String allActive = "All Active";
//    private String airportPair = "Airport Pair";
//    private String airframe = "Airframe";
//    private String equipment = "Equipment";
//    private String flight = "Flight";
//    private String airport = "Airport";
//    private String general = "General";
//    private String archive = "Archive";
    static List<String> sidePanelCategories = Arrays.asList("Airport Pair", "Airframe", "Equipment", "Flight", "Airport", "General", "Archive");
    //static HashMap<String, String> =

    @Test
    public void navigateWithinFieldAndFacilitiesPageTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);

        fieldAndFacilitiesPage.checkSidePanelSelected(allActive);

        for (String category : sidePanelCategories) {
            fieldAndFacilitiesPage.switchTo(category);
            fieldAndFacilitiesPage.checkSidePanelSelected(category);
        }
    }
}
