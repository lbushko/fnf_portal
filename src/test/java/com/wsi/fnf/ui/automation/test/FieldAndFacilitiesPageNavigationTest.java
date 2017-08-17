package com.wsi.fnf.ui.automation.test;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class FieldAndFacilitiesPageNavigationTest extends BaseTest {

    @Description("Testing navigation functionality")
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
