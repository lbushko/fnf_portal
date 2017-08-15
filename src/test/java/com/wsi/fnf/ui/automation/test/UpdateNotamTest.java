package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;

public class UpdateNotamTest extends BaseTest {

    @Test(dataProvider = "Category")
    public void updateNotamTest(String Category) throws Exception {
        FieldAndFacilitiesPage fieldAndFacilitiesPage = logIn();
        fieldAndFacilitiesPage.switchTo(Category);
        fieldAndFacilitiesPage.clickLastDataRow();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        String expiresIn = addNotamFunctionality.selectEndDate();
        String notamText = addNotamFunctionality.specifyNotamText("UPDATE");
        fieldAndFacilitiesPage.selectCheckBox();
        String notamChangeReason = addNotamFunctionality.specifyChangeReason();
        fieldAndFacilitiesPage.clickUpdateNotam();
        fieldAndFacilitiesPage.clickLastDataRow();
        fieldAndFacilitiesPage.checkNotamCreated(notamText, expiresIn);
        fieldAndFacilitiesPage.deleteNotam();
    }
}
