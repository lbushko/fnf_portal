package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;

public class NotamSortingFunctionalityTest extends BaseTest {

    @Test(dataProvider = "sidePanelCategory")
    public void notamSortingFunctionalityTest(String sidePanelCategorie) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getOnPage();
        loginPage.logIn(validUsername, validPassword, validCustomerId);
        FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);
        fieldAndFacilitiesPage.switchTo(sidePanelCategorie);
        fieldAndFacilitiesPage.checkSorting();
    }
}