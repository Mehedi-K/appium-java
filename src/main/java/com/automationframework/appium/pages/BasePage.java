package com.automationframework.appium.pages;

import com.automationframework.appium.utils.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Shared explicit-wait helpers for every page object. All navigation between
 * ApiDemos screens goes through the same standard Android ListView, so the
 * generic {@link #openListItem(String)} lives here rather than being
 * duplicated on each subclass.
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.explicitWaitSeconds()));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitPresentAll(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isVisible(By locator) {
        try {
            return waitVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scrolls a standard Android list into view of an item with the given
     * exact text and taps it. Used to drill down through ApiDemos' nested
     * category screens (Home -> Views -> Controls -> ... etc).
     */
    protected void openListItem(String exactText) {
        By locator = AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                        + ".scrollIntoView(new UiSelector().text(\"" + exactText + "\").instance(0))");
        waitClickable(locator).click();
    }

    /** Navigates back one screen using the platform back action. */
    public void navigateBack() {
        driver.navigate().back();
    }
}
