package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/** The "Text Entry dialog": a native AlertDialog with a custom name/password form. */
public class TextEntryDialog extends BasePage {

    private static final By TITLE = By.id("android:id/alertTitle");
    private static final By USERNAME_FIELD = By.id("io.appium.android.apis:id/username_edit");
    private static final By PASSWORD_FIELD = By.id("io.appium.android.apis:id/password_edit");
    private static final By OK_BUTTON = By.id("android:id/button1");
    private static final By CANCEL_BUTTON = By.id("android:id/button2");

    public TextEntryDialog(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(USERNAME_FIELD);
    }

    public String getTitle() {
        return waitVisible(TITLE).getText();
    }

    public void typeUsername(String text) {
        WebElement field = waitVisible(USERNAME_FIELD);
        field.clear();
        field.sendKeys(text);
    }

    public String getUsername() {
        return waitVisible(USERNAME_FIELD).getText();
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
