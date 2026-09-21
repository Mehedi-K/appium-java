package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * App > Alert Dialogs: a menu of standard AlertDialog variants. Each opens a
 * native Android dialog sharing the platform's android:id/button1 (OK) /
 * android:id/button2 (Cancel) / android:id/alertTitle ids.
 */
public class AlertDialogsPage extends BasePage {

    // Unlike the other ApiDemos category menus, this screen is a plain
    // ScrollView of Buttons (io.appium.android.apis:id/screen), not a
    // standard ListView with an android:id/list id.
    private static final By SCREEN = By.id("io.appium.android.apis:id/screen");

    public AlertDialogsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(SCREEN);
    }

    public OkCancelDialog openOkCancelDialogWithMessage() {
        openListItem("OK Cancel dialog with a message");
        return new OkCancelDialog(driver);
    }

    public TextEntryDialog openTextEntryDialog() {
        openListItem("Text Entry dialog");
        return new TextEntryDialog(driver);
    }
}
