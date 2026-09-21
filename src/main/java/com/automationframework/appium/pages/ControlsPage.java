package com.automationframework.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Views > Controls > 1. Light Theme: the classic ApiDemos "Controls1"
 * screen. A single screen packed with standard Android widgets (buttons,
 * checkboxes, radio buttons, a star checkbox, toggle buttons, a spinner and
 * an edit text), which makes it ideal for exercising a broad set of
 * interactions with real, stable resource ids.
 */
public class ControlsPage extends BasePage {

    private static final By SAVE_BUTTON = By.id("io.appium.android.apis:id/button");
    private static final By SAVE_BUTTON_DISABLED = By.id("io.appium.android.apis:id/button_disabled");
    private static final By EDIT_TEXT = By.id("io.appium.android.apis:id/edit");
    private static final By CHECKBOX_1 = By.id("io.appium.android.apis:id/check1");
    private static final By CHECKBOX_2 = By.id("io.appium.android.apis:id/check2");
    private static final By RADIO_1 = By.id("io.appium.android.apis:id/radio1");
    private static final By RADIO_2 = By.id("io.appium.android.apis:id/radio2");
    private static final By STAR = By.id("io.appium.android.apis:id/star");
    private static final By TOGGLE_1 = By.id("io.appium.android.apis:id/toggle1");
    private static final By TOGGLE_2 = By.id("io.appium.android.apis:id/toggle2");
    private static final By SPINNER = By.id("io.appium.android.apis:id/spinner1");
    private static final By SPINNER_SELECTED_TEXT = By.id("android:id/text1");

    public ControlsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(CHECKBOX_1);
    }

    // --- Save button ---

    public void clickSaveButton() {
        waitClickable(SAVE_BUTTON).click();
    }

    public boolean isSaveButtonEnabled() {
        return waitVisible(SAVE_BUTTON).isEnabled();
    }

    public boolean isDisabledButtonEnabled() {
        return waitVisible(SAVE_BUTTON_DISABLED).isEnabled();
    }

    // --- Edit text ---

    public void typeInEditField(String text) {
        WebElement edit = waitVisible(EDIT_TEXT);
        edit.clear();
        edit.sendKeys(text);
    }

    public String getEditFieldText() {
        return waitVisible(EDIT_TEXT).getText();
    }

    // --- Checkboxes ---

    public void toggleCheckbox1() {
        waitClickable(CHECKBOX_1).click();
    }

    public void toggleCheckbox2() {
        waitClickable(CHECKBOX_2).click();
    }

    public boolean isCheckbox1Checked() {
        return isChecked(CHECKBOX_1);
    }

    public boolean isCheckbox2Checked() {
        return isChecked(CHECKBOX_2);
    }

    // --- Radio buttons ---

    public void selectRadio1() {
        waitClickable(RADIO_1).click();
    }

    public void selectRadio2() {
        waitClickable(RADIO_2).click();
    }

    public boolean isRadio1Selected() {
        return isChecked(RADIO_1);
    }

    public boolean isRadio2Selected() {
        return isChecked(RADIO_2);
    }

    // --- Star checkbox ---

    public void toggleStar() {
        waitClickable(STAR).click();
    }

    public boolean isStarChecked() {
        return isChecked(STAR);
    }

    // --- Toggle buttons ---

    public void toggleFirstToggleButton() {
        waitClickable(TOGGLE_1).click();
    }

    public String getFirstToggleButtonText() {
        return waitVisible(TOGGLE_1).getText();
    }

    // --- Spinner ---

    public String getSelectedSpinnerValue() {
        return waitVisible(SPINNER).findElement(SPINNER_SELECTED_TEXT).getText();
    }

    public void selectSpinnerOption(String option) {
        waitClickable(SPINNER).click();
        By optionLocator = AppiumBy.androidUIAutomator("new UiSelector().text(\"" + option + "\")");
        waitClickable(optionLocator).click();
    }

    private boolean isChecked(By locator) {
        String checked = waitVisible(locator).getAttribute("checked");
        return Boolean.parseBoolean(checked);
    }
}
