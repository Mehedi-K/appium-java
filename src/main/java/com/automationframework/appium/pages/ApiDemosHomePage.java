package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * The app's landing screen: a scrollable list of top-level API categories
 * (Accessibility, App, Content, Graphics, Views, ...).
 */
public class ApiDemosHomePage extends BasePage {

    private static final By LIST = By.id("android:id/list");

    public ApiDemosHomePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public boolean hasCategory(String categoryName) {
        return isVisible(By.xpath("//*[@text='" + categoryName + "']"));
    }

    public ViewsPage openViews() {
        openListItem("Views");
        return new ViewsPage(driver);
    }

    public AccessibilityPage openAccessibility() {
        openListItem("Accessibility");
        return new AccessibilityPage(driver);
    }

    public AppMenuPage openApp() {
        openListItem("App");
        return new AppMenuPage(driver);
    }
}
