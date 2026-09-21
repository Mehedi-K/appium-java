package com.automationframework.appium.tests;

import com.automationframework.appium.pages.ApiDemosHomePage;
import com.automationframework.appium.pages.ViewsPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/** Covers the app's landing screen: the top-level list of API categories. */
public class HomeScreenTest extends BaseTest {

    @Test(description = "Home screen loads and lists the core ApiDemos categories")
    public void testHomeScreenDisplaysCoreCategories() {
        ApiDemosHomePage home = homePage();
        assertTrue(home.isDisplayed(), "Home screen list should be displayed on launch");
        assertTrue(home.hasCategory("Views"), "Home screen should list the 'Views' category");
        assertTrue(home.hasCategory("App"), "Home screen should list the 'App' category");
        assertTrue(home.hasCategory("Accessibility"), "Home screen should list the 'Accessibility' category");
    }

    @Test(description = "Tapping the Views category navigates to the Views screen")
    public void testNavigateIntoViewsCategory() {
        ViewsPage views = homePage().openViews();
        assertTrue(views.isDisplayed(), "Views screen should be displayed after navigation");
        assertTrue(views.hasItem("Controls"), "Views screen should list the 'Controls' item");
    }

    @Test(description = "Device back navigation returns from Views to the home screen")
    public void testBackNavigationReturnsToHomeScreen() {
        ApiDemosHomePage home = homePage();
        ViewsPage views = home.openViews();
        assertTrue(views.isDisplayed(), "Should have navigated into Views first");

        views.navigateBack();

        assertTrue(home.isDisplayed(), "Back navigation should return to the home screen");
        assertTrue(home.hasCategory("Views"), "Home screen categories should be visible again");
    }
}
