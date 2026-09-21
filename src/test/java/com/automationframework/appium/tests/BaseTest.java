package com.automationframework.appium.tests;

import com.automationframework.appium.driver.AppiumDriverFactory;
import com.automationframework.appium.listeners.ScreenshotListener;
import com.automationframework.appium.pages.ApiDemosHomePage;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Base class for every TestNG test: starts a fresh Appium/Android session
 * before each test method (which re-installs and relaunches the app) and
 * tears it down afterwards.
 */
@Listeners(ScreenshotListener.class)
public abstract class BaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);
    protected AndroidDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = AppiumDriverFactory.getDriver();
        LOG.info("Android session started for test");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess()) {
            LOG.warn("Test {} failed", result.getName());
        }
        AppiumDriverFactory.quitDriver();
        LOG.info("Android session quit after test");
    }

    protected ApiDemosHomePage homePage() {
        return new ApiDemosHomePage(driver);
    }
}
