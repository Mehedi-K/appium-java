package com.automationframework.appium.tests;

import com.automationframework.appium.pages.ControlsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Covers the Spinner (dropdown) widget on Views > Controls > 1. Light
 * Theme, which defaults to "Mercury" and offers the rest of the planets.
 */
public class SpinnerTest extends BaseTest {

    private ControlsPage controls;

    @BeforeMethod(alwaysRun = true)
    public void navigateToControls() {
        controls = homePage().openViews().openControls().openLightTheme();
        assertTrue(controls.isDisplayed(), "Controls screen should be displayed before each test");
    }

    @Test(description = "The spinner defaults to Mercury")
    public void testDefaultSpinnerValueIsMercury() {
        assertEquals(controls.getSelectedSpinnerValue(), "Mercury", "Spinner should default to Mercury");
    }

    @Test(description = "Selecting a spinner option updates the displayed selection")
    public void testSelectingSpinnerOptionUpdatesSelectedValue() {
        controls.selectSpinnerOption("Jupiter");
        assertEquals(controls.getSelectedSpinnerValue(), "Jupiter", "Spinner should now show Jupiter");
    }

    @Test(description = "Selecting a different spinner option again updates the selection")
    public void testSelectingAnotherSpinnerOptionUpdatesSelectedValue() {
        controls.selectSpinnerOption("Saturn");
        assertEquals(controls.getSelectedSpinnerValue(), "Saturn", "Spinner should now show Saturn");
    }
}
