package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Views > Controls: ApiDemos shows a theme picker before the actual controls
 * screen, so every widget demo is reachable under one of these theme
 * entries. Light Theme is used throughout this suite for consistency.
 */
public class ControlsThemePage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public ControlsThemePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public ControlsPage openLightTheme() {
        openListItem("1. Light Theme");
        return new ControlsPage(driver);
    }
}
