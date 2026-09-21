package com.automationframework.appium.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Views > Lists > 01. Array: a long, alphabetically sorted list of cheeses
 * backed by a plain ArrayAdapter. Good for verifying scrolling and that a
 * specific, far-down item can be located via UiScrollable.
 */
public class ArrayListPage extends BasePage {

    private static final By LIST = By.id("android:id/list");
    private static final By FIRST_ITEM = By.id("android:id/text1");

    public ArrayListPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isVisible(LIST);
    }

    public String getFirstVisibleItemText() {
        return waitVisible(FIRST_ITEM).getText();
    }

    /**
     * Scrolls the list until an item with the given exact text is visible.
     * This issues a single native UiScrollable#scrollIntoView call, which
     * performs its own bounded search/scroll loop on-device; it is
     * deliberately NOT wrapped in a WebDriverWait poll, since each retry
     * would otherwise re-trigger a full scroll search and multiply the
     * already-nontrivial cost of searching a very long list.
     */
    public boolean scrollToItem(String text) {
        By locator = AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().resourceId(\"android:id/list\"))"
                        + ".setMaxSearchSwipes(40)"
                        + ".scrollIntoView(new UiSelector().text(\"" + text + "\"))");
        return !driver.findElements(locator).isEmpty();
    }
}
