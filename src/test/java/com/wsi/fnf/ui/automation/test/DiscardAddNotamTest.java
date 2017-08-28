package com.wsi.fnf.ui.automation.test;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import java.util.Arrays;

public class DiscardAddNotamTest extends BaseTest {

    @Description("Testing discard Notam functionality")
    @Test(dataProvider = "Category")
    public void discardAddNotamTest(String Category) throws Exception {
        logIn();
        AddNotamFunctionality addNotamFunctionality = new AddNotamFunctionality(driver);
        addNotamFunctionality.selectNotamCategoryToCreate(Category);
        addNotamFunctionality.selectAuthorizedBy(authorizedBy);
        addNotamFunctionality.selectStartDate();
        addNotamFunctionality.selectExpiresIn();
        addNotamFunctionality.selectCheckBox();
        String notamText = addNotamFunctionality.specifyNotamText("NEW");
        if (!Category.equals("General")) {
            addNotamFunctionality.selectDataRow();
        }
        if (Arrays.asList("Airport", "Airport Pair", "Flight").contains(Category)) {
            addNotamFunctionality.callSecondaryPanel();
        }
        addNotamFunctionality.cancelNotamCreation();
        addNotamFunctionality.checkNotamNotCreated(notamText);
    }
}
