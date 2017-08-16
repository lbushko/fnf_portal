package com.wsi.fnf.ui.automation.test;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

public class DeleteAllNotams extends BaseTest {

    @Test(dataProvider = "Category")
    public void deleteTest(String Category) throws InterruptedException {
            logIn();
            FieldAndFacilitiesPage fieldAndFacilitiesPage = new FieldAndFacilitiesPage(driver);
            fieldAndFacilitiesPage.switchTo(Category);
            boolean flag = true;
            while (flag) {
                try {
                    fieldAndFacilitiesPage.clickLastDataRow();
                    Thread.sleep(200);
                    fieldAndFacilitiesPage.deleteNotam();
                } catch (NoSuchElementException e) {
                    flag = false;
                }
            }
    }
}
