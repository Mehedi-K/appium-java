package com.automationframework.appium.tests;

import com.automationframework.appium.pages.ControlsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * Covers Views > Controls > 1. Light Theme, the classic ApiDemos screen
 * packed with standard widgets: checkboxes, radio buttons, a star checkbox,
 * toggle buttons, a Save button and an edit text.
 */
public class ControlsTest extends BaseTest {

    private ControlsPage controls;

    @BeforeMethod(alwaysRun = true)
    public void navigateToControls() {
        controls = homePage().openViews().openControls().openLightTheme();
        assertTrue(controls.isDisplayed(), "Controls screen should be displayed before each test");
    }

    @Test(description = "Checkbox 1 and Checkbox 2 toggle independently of each other")
    public void testCheckboxesToggleIndependently() {
        assertFalse(controls.isCheckbox1Checked(), "Checkbox 1 should start unchecked");
        assertFalse(controls.isCheckbox2Checked(), "Checkbox 2 should start unchecked");

        controls.toggleCheckbox1();
        assertTrue(controls.isCheckbox1Checked(), "Checkbox 1 should be checked after tapping it");
        assertFalse(controls.isCheckbox2Checked(), "Checkbox 2 should remain unaffected");

        controls.toggleCheckbox2();
        assertTrue(controls.isCheckbox2Checked(), "Checkbox 2 should be checked after tapping it");
    }

    @Test(description = "RadioButton 1 and RadioButton 2 are mutually exclusive")
    public void testRadioButtonsAreMutuallyExclusive() {
        controls.selectRadio1();
        assertTrue(controls.isRadio1Selected(), "RadioButton 1 should be selected");
        assertFalse(controls.isRadio2Selected(), "RadioButton 2 should not be selected");

        controls.selectRadio2();
        assertTrue(controls.isRadio2Selected(), "RadioButton 2 should be selected");
        assertFalse(controls.isRadio1Selected(), "RadioButton 1 should be deselected");
    }

    @Test(description = "The Star checkbox can be toggled on")
    public void testStarCheckboxToggles() {
        assertFalse(controls.isStarChecked(), "Star checkbox should start unchecked");
        controls.toggleStar();
        assertTrue(controls.isStarChecked(), "Star checkbox should be checked after tapping it");
    }

    @Test(description = "Tapping the first ToggleButton flips its displayed state")
    public void testToggleButtonChangesLabel() {
        String initialText = controls.getFirstToggleButtonText();
        controls.toggleFirstToggleButton();
        String updatedText = controls.getFirstToggleButtonText();

        assertNotEquals(updatedText, initialText, "Toggle button text should change after tapping it");
        assertEquals(updatedText, "ON", "Toggle button should read ON once switched on");
    }

    @Test(description = "The edit text field accepts and reflects typed input")
    public void testEditTextAcceptsInput() {
        controls.typeInEditField("Appium");
        assertEquals(controls.getEditFieldText(), "Appium", "Edit field should reflect the typed text");
    }

    @Test(description = "The Save button is enabled while its disabled sibling is not")
    public void testSaveButtonEnabledAndDisabledButtonDisabled() {
        assertTrue(controls.isSaveButtonEnabled(), "The primary Save button should be enabled");
        assertFalse(controls.isDisabledButtonEnabled(), "The disabled Save button should stay disabled");
        controls.clickSaveButton();
    }
}
