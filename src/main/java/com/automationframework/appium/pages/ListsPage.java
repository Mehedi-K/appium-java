package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Views > Lists: a scrollable list of list-widget demos (Array, Cursor,
 * ListAdapter, single/multiple choice, ...).
 */
public class ListsPage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public ListsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public ArrayListPage openArrayList() {
        openListItem("01. Array");
        return new ArrayListPage(driver);
    }
}
