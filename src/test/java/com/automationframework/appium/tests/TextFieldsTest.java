package com.automationframework.appium.tests;

import com.automationframework.appium.pages.TextFieldsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/** Covers Views > TextFields: a set of EditText widgets with different input types. */
public class TextFieldsTest extends BaseTest {

    private TextFieldsPage textFields;

    @BeforeMethod(alwaysRun = true)
    public void navigateToTextFields() {
        textFields = homePage().openViews().openTextFields();
        assertTrue(textFields.isDisplayed(), "TextFields screen should be displayed before each test");
    }

    @Test(description = "The hint-text field accepts and reflects typed input")
    public void testHintTextFieldAcceptsInput() {
        textFields.typeInHintTextField("automation framework");
        assertEquals(textFields.getHintTextFieldValue(), "automation framework",
                "Hint text field should reflect the typed text");
    }

    @Test(description = "The numeric field accepts and reflects typed digits")
    public void testNumericFieldAcceptsInput() {
        textFields.typeInNumericField("12345");
        assertEquals(textFields.getNumericFieldValue(), "12345",
                "Numeric field should reflect the typed digits");
    }

    @Test(description = "The password field is present on the screen")
    public void testPasswordFieldIsDisplayed() {
        assertTrue(textFields.isPasswordFieldDisplayed(), "Password field should be visible on the screen");
    }
}
