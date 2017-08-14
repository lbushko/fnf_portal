package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;

/**
 * Created by ashendri on 02.08.2017.
 */
public class FieldAndFacilitiesPageNavigationTest extends BaseTest {

    @Test(dataProvider = "sidePanelCategory")
    public void navigateWithinFieldAndFacilitiesPageTest(String sidePanelCategory) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);
        fieldAndFacilitiesPage.switchTo(sidePanelCategory);
        fieldAndFacilitiesPage.checkSidePanelSelected(sidePanelCategory);
        fieldAndFacilitiesPage.compareNotamsSidePanelAndTable(sidePanelCategory);
    }
}
