package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;

/**
 * Created by ashendri on 09.08.2017.
 */
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