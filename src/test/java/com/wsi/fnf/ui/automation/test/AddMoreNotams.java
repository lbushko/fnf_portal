package com.wsi.fnf.ui.automation.test;

import org.testng.annotations.Test;
import java.util.Arrays;

public class AddMoreNotams extends BaseTest{

    @Test(dataProvider = "Category")
    public void addNotamTest(String Category) throws Exception {
        logIn();

        for (int i = 0; i < 15; i++) {
            FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);
            fieldAndFacilitiesPage.switchTo("All Active");
            AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
            addNotamFunctionality.selectNotamCategoryToCreate(Category);
            addNotamFunctionality.selectAuthorizedBy(authorizedBy);
            addNotamFunctionality.selectStartDate();
            String expiresIn = addNotamFunctionality.selectExpiresIn();
            addNotamFunctionality.selectCheckBox();
            String notamText = addNotamFunctionality.specifyNotamText("NEW");
            if (!Category.equals("General")) {
                addNotamFunctionality.selectDataRow();
            }
            if (Arrays.asList("Airport", "Airport Pair", "Flight").contains(Category)) {
                addNotamFunctionality.selectDataRowFromSecondaryPanel();
            }
            addNotamFunctionality.publishNotam();
            fieldAndFacilitiesPage.clickLastDataRow();
            fieldAndFacilitiesPage.checkNotamCreated(notamText, expiresIn);
        }
    }
}
