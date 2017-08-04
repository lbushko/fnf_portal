import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ashendri on 02.08.2017.
 */
public class FieldAndFacilitiesPageNavigationTest extends BaseTest {

    @Test
    public void navigateWithinFieldAndFacilitiesPageTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);

        fieldAndFacilitiesPage.checkSidePanelSelected(sidePanelCategories.get(0));
        fieldAndFacilitiesPage.compareNotamsSidePanelAndTable(sidePanelCategories.get(0));

        for (int i = 1; i < sidePanelCategories.size(); i++) {
            fieldAndFacilitiesPage.switchTo(sidePanelCategories.get(i));
            fieldAndFacilitiesPage.checkSidePanelSelected(sidePanelCategories.get(i));
            fieldAndFacilitiesPage.compareNotamsSidePanelAndTable(sidePanelCategories.get(i));
        }
    }
}
