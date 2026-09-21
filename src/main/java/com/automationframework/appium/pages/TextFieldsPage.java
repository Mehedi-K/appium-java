package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Views > TextFields: a set of EditText widgets with different input types
 * (plain hint text, password, numeric, etc). Used to exercise text entry
 * and verify what was typed is actually reflected back by the widget.
 */
public class TextFieldsPage extends BasePage {

    private static final By FIELD_HINT_TEXT = By.id("io.appium.android.apis:id/edit");
    private static final By FIELD_PASSWORD = By.id("io.appium.android.apis:id/edit1");
    private static final By FIELD_NUMERIC = By.id("io.appium.android.apis:id/edit2");

    public TextFieldsPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(FIELD_HINT_TEXT);
    }

    public void typeInHintTextField(String text) {
        WebElement field = waitVisible(FIELD_HINT_TEXT);
        field.clear();
        field.sendKeys(text);
    }

    public String getHintTextFieldValue() {
        return waitVisible(FIELD_HINT_TEXT).getText();
    }

    public void typeInNumericField(String text) {
        WebElement field = waitVisible(FIELD_NUMERIC);
        field.clear();
        field.sendKeys(text);
    }

    public String getNumericFieldValue() {
        return waitVisible(FIELD_NUMERIC).getText();
    }

    public boolean isPasswordFieldDisplayed() {
        return isVisible(FIELD_PASSWORD);
    }
}
