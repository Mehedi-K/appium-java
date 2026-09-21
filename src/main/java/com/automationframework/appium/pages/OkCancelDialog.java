package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/** The plain "OK Cancel dialog with a message" native AlertDialog. */
public class OkCancelDialog extends BasePage {

    // This dialog is built with setMessage() only (no separate setTitle()),
    // so Android renders the message text through the alertTitle TextView;
    // there is no separate android:id/message node in this dialog.
    private static final By MESSAGE = By.id("android:id/alertTitle");
    private static final By OK_BUTTON = By.id("android:id/button1");
    private static final By CANCEL_BUTTON = By.id("android:id/button2");

    public OkCancelDialog(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(MESSAGE);
    }

    public String getMessage() {
        return waitVisible(MESSAGE).getText();
    }

    public AlertDialogsPage clickOk() {
        waitClickable(OK_BUTTON).click();
        return new AlertDialogsPage(driver);
    }

    public AlertDialogsPage clickCancel() {
        waitClickable(CANCEL_BUTTON).click();
        return new AlertDialogsPage(driver);
    }
}
