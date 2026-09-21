package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Accessibility > ...: a menu of accessibility-focused demos (Accessibility
 * Node Provider, Accessibility Node Querying, Accessibility Service, Custom
 * View).
 */
public class AccessibilityPage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public AccessibilityPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public boolean hasItem(String label) {
        return isVisible(By.xpath("//*[@text='" + label + "']"));
    }

    public CustomViewPage openCustomView() {
        openListItem("Custom View");
        return new CustomViewPage(driver);
    }
}
