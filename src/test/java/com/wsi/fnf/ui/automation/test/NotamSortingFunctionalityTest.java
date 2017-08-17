package com.wsi.fnf.ui.automation.test;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class NotamSortingFunctionalityTest extends BaseTest {

    @Description("Testing sorting ASC, DESC functionality")
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