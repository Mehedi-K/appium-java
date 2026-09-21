package com.automationframework.appium.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Accessibility > Custom View: a screen built around a custom View exposing
 * its own accessibility node info via a content description, rather than
 * standard widgets.
 */
public class CustomViewPage extends BasePage {

    private static final By INSTRUCTIONS = By.xpath(
            "//*[contains(@content-desc, 'Enable TalkBack')]");

    public CustomViewPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(INSTRUCTIONS);
    }

    public String getInstructions() {
        return waitVisible(INSTRUCTIONS).getAttribute("content-desc");
    }
}
