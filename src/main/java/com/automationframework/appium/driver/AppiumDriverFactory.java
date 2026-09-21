package com.automationframework.appium.driver;

import com.automationframework.appium.utils.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Creates and tears down {@link AndroidDriver} sessions against a running
 * Appium server + Android emulator. Thread-safe via a ThreadLocal so tests
 * could run in parallel without stepping on each other's driver.
 *
 * The app-under-test (ApiDemos-debug.apk) is not committed to the repo; it
 * is downloaded on first use from its official GitHub release at
 * https://github.com/appium/android-apidemos, and cached under apps/ for
 * subsequent runs.
 */
public final class AppiumDriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AppiumDriverFactory.class);
    private static final ThreadLocal<AndroidDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();
    private static final String APP_DOWNLOAD_URL =
            "https://github.com/appium/android-apidemos/releases/download/v6.0.17/ApiDemos-debug.apk";

    private AppiumDriverFactory() {
    }

    public static AndroidDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            DRIVER_THREAD_LOCAL.set(createDriver());
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    private static AndroidDriver createDriver() {
        Path appPath = resolveAppPath();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.platformName());
        options.setAutomationName(ConfigReader.automationName());
        options.setDeviceName(ConfigReader.deviceName());
        options.setApp(appPath.toAbsolutePath().toString());
        options.setAppPackage(ConfigReader.appPackage());
        options.setAppActivity(ConfigReader.appActivity());
        options.setNewCommandTimeout(Duration.ofSeconds(ConfigReader.newCommandTimeoutSeconds()));
        options.setAutoGrantPermissions(true);
        options.setCapability("disableWindowAnimation", true);
        options.setCapability("adbExecTimeout", 60000);

        String platformVersion = ConfigReader.platformVersion();
        if (platformVersion != null && !platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }

        try {
            URL serverUrl = URI.create(ConfigReader.appiumServerUrl()).toURL();
            LOG.info("Starting Android session on {} (app={})", serverUrl, appPath);
            return new AndroidDriver(serverUrl, options);
        } catch (java.net.MalformedURLException | IllegalArgumentException e) {
            throw new IllegalStateException("Invalid appium.server.url: " + ConfigReader.appiumServerUrl(), e);
        }
    }

    /**
     * Resolves the apk under apps/, downloading it from the official
     * android-apidemos release if it is not already present locally.
     */
    private static Path resolveAppPath() {
        Path configuredPath = Paths.get(ConfigReader.appPath());
        Path appPath = configuredPath.isAbsolute()
                ? configuredPath
                : Paths.get(System.getProperty("user.dir")).resolve(configuredPath);

        if (Files.exists(appPath)) {
            return appPath;
        }

        LOG.info("App not found at {}, downloading from {}", appPath, APP_DOWNLOAD_URL);
        try {
            Files.createDirectories(appPath.getParent());
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
            HttpRequest request = HttpRequest.newBuilder(URI.create(APP_DOWNLOAD_URL))
                    .timeout(Duration.ofMinutes(2))
                    .GET()
                    .build();
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(appPath));
            if (response.statusCode() != 200) {
                Files.deleteIfExists(appPath);
                throw new IllegalStateException("Failed to download app, HTTP " + response.statusCode());
            }
            LOG.info("Downloaded app to {}", appPath);
            return appPath;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new IllegalStateException("Unable to download app from " + APP_DOWNLOAD_URL
                    + ". Run apps/download-app.sh manually or check network access.", e);
        }
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            LOG.info("Quitting Android driver session");
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }
}
