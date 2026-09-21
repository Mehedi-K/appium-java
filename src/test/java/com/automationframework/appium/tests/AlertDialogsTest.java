package com.automationframework.appium.tests;

import com.automationframework.appium.pages.AlertDialogsPage;
import com.automationframework.appium.pages.OkCancelDialog;
import com.automationframework.appium.pages.TextEntryDialog;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Covers App > Alert Dialogs: native AlertDialog variants launched from a
 * menu, each sharing Android's standard button1/button2/alertTitle ids.
 */
public class AlertDialogsTest extends BaseTest {

    private AlertDialogsPage alertDialogs;

    @BeforeMethod(alwaysRun = true)
    public void navigateToAlertDialogs() {
        alertDialogs = homePage().openApp().openAlertDialogs();
        assertTrue(alertDialogs.isDisplayed(), "Alert Dialogs screen should be displayed before each test");
    }

    @Test(description = "The OK Cancel dialog displays its message and can be dismissed with OK")
    public void testOkCancelDialogDismissesWithOk() {
        OkCancelDialog dialog = alertDialogs.openOkCancelDialogWithMessage();
        assertTrue(dialog.isDisplayed(), "OK Cancel dialog should be visible");
        assertFalse(dialog.getMessage().isBlank(), "Dialog message should not be blank");

        AlertDialogsPage backToMenu = dialog.clickOk();
        assertTrue(backToMenu.isDisplayed(), "Should return to the Alert Dialogs menu after tapping OK");
    }

    @Test(description = "The OK Cancel dialog can also be dismissed with Cancel")
    public void testOkCancelDialogDismissesWithCancel() {
        OkCancelDialog dialog = alertDialogs.openOkCancelDialogWithMessage();
        assertTrue(dialog.isDisplayed(), "OK Cancel dialog should be visible");

        AlertDialogsPage backToMenu = dialog.clickCancel();
        assertTrue(backToMenu.isDisplayed(), "Should return to the Alert Dialogs menu after tapping Cancel");
    }

    @Test(description = "The Text Entry dialog accepts typed input and can be confirmed")
    public void testTextEntryDialogAcceptsInputAndConfirms() {
        TextEntryDialog dialog = alertDialogs.openTextEntryDialog();
        assertTrue(dialog.isDisplayed(), "Text Entry dialog should be visible");
        assertEquals(dialog.getTitle(), "Text Entry dialog", "Dialog title should read 'Text Entry dialog'");

        dialog.typeUsername("appium-user");
        assertEquals(dialog.getUsername(), "appium-user", "Username field should reflect typed text");

        AlertDialogsPage backToMenu = dialog.clickOk();
        assertTrue(backToMenu.isDisplayed(), "Should return to the Alert Dialogs menu after confirming");
    }
}
