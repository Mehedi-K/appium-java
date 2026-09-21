package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * App > ... category screen: a scrollable list of application-level demos
 * (Alert Dialogs, Activity, Search, ...).
 */
public class AppMenuPage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public AppMenuPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public boolean hasItem(String label) {
        return isVisible(By.xpath("//*[@text='" + label + "']"));
    }

    public AlertDialogsPage openAlertDialogs() {
        openListItem("Alert Dialogs");
        return new AlertDialogsPage(driver);
    }
}
