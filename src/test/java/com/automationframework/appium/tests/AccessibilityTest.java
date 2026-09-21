package com.automationframework.appium.tests;

import com.automationframework.appium.pages.AccessibilityPage;
import com.automationframework.appium.pages.CustomViewPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/** Covers the Accessibility category, including its Custom View demo screen. */
public class AccessibilityTest extends BaseTest {

    private AccessibilityPage accessibility;

    @BeforeMethod(alwaysRun = true)
    public void navigateToAccessibility() {
        accessibility = homePage().openAccessibility();
        assertTrue(accessibility.isDisplayed(), "Accessibility screen should be displayed before each test");
    }

    @Test(description = "The Accessibility screen lists its demo entries")
    public void testAccessibilityScreenListsDemos() {
        assertTrue(accessibility.hasItem("Custom View"), "Accessibility screen should list 'Custom View'");
        assertTrue(accessibility.hasItem("Accessibility Node Provider"),
                "Accessibility screen should list 'Accessibility Node Provider'");
    }

    @Test(description = "The Custom View demo screen displays its accessibility instructions")
    public void testCustomViewScreenDisplaysInstructions() {
        CustomViewPage customView = accessibility.openCustomView();
        assertTrue(customView.isDisplayed(), "Custom View screen should be displayed");
        assertTrue(customView.getInstructions().contains("TalkBack"),
                "Custom View instructions should mention TalkBack");
    }

    @Test(description = "Back navigation returns from Custom View to the Accessibility menu")
    public void testBackNavigationReturnsToAccessibilityMenu() {
        CustomViewPage customView = accessibility.openCustomView();
        assertTrue(customView.isDisplayed(), "Should have navigated into Custom View first");

        customView.navigateBack();

        assertTrue(accessibility.isDisplayed(), "Back navigation should return to the Accessibility menu");
    }
}
