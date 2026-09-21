package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Views > ... category screen: a scrollable list of view-related demos
 * (Controls, Lists, Spinner, Text Fields, ...).
 */
public class ViewsPage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public ViewsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public boolean hasItem(String label) {
        return isVisible(By.xpath("//*[@text='" + label + "']"));
    }

    public ControlsThemePage openControls() {
        openListItem("Controls");
        return new ControlsThemePage(driver);
    }

    public TextFieldsPage openTextFields() {
        openListItem("TextFields");
        return new TextFieldsPage(driver);
    }

    public ListsPage openLists() {
        openListItem("Lists");
        return new ListsPage(driver);
    }
}
