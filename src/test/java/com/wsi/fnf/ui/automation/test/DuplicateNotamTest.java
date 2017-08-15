package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;

public class DuplicateNotamTest extends BaseTest{

    @Test(dataProvider = "Category")
    public void duplicateNotamTest(String Category) throws Exception {
        FieldAndFacilitiesPage fieldAndFacilitiesPage = logIn();
        fieldAndFacilitiesPage.switchTo(Category);
        AddNotamFunctionality addNotamFunctionality = fieldAndFacilitiesPage.clickDuplicateNotam();
        addNotamFunctionality.selectStartDate();
        String expiresIn = addNotamFunctionality.selectExpiresIn();
        String notamText = addNotamFunctionality.specifyNotamText("DUPLICATE");
        addNotamFunctionality.selectCheckBox();
        addNotamFunctionality.publishNotam();
        fieldAndFacilitiesPage.clickLastDataRow();
        fieldAndFacilitiesPage.checkNotamCreated(notamText, expiresIn);
        fieldAndFacilitiesPage.deleteNotam();
    }
}
