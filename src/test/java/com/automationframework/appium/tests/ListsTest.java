package com.automationframework.appium.tests;

import com.automationframework.appium.pages.ArrayListPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Covers Views > Lists > 01. Array: a long, alphabetically sorted
 * ArrayAdapter-backed list, used to verify scrolling behavior.
 */
public class ListsTest extends BaseTest {

    private ArrayListPage arrayList;

    @BeforeMethod(alwaysRun = true)
    public void navigateToArrayList() {
        arrayList = homePage().openViews().openLists().openArrayList();
        assertTrue(arrayList.isDisplayed(), "Array list screen should be displayed before each test");
    }

    @Test(description = "The array list is displayed with its first item visible")
    public void testArrayListIsDisplayedWithFirstItem() {
        String firstItem = arrayList.getFirstVisibleItemText();
        assertFalse(firstItem.isBlank(), "The first visible list item should have text");
    }

    @Test(description = "Scrolling the list reveals an item several screens down")
    public void testScrollingRevealsItemFurtherDown() {
        assertTrue(arrayList.scrollToItem("Edam"), "Scrolling should reveal 'Edam' further down the list");
    }

    @Test(description = "Scrolling the list reveals an item roughly midway through the alphabet")
    public void testScrollingRevealsMidAlphabetItem() {
        assertTrue(arrayList.scrollToItem("Monterey Jack"), "Scrolling should reveal 'Monterey Jack' in the list");
    }
}
