package com.automationframework.appium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads test configuration from src/test/resources/config.properties.
 * Values can be overridden with -D system properties or environment
 * variables, e.g. -Dappium.server.url=... or APPIUM_SERVER_URL=..., which
 * lets CI point the suite at a different Appium server/emulator without
 * touching the properties file.
 */
public final class ConfigReader {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Unable to find " + CONFIG_FILE + " on the classpath");
            }
            PROPERTIES.load(input);
            LOG.info("Loaded configuration from {}", CONFIG_FILE);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + CONFIG_FILE, e);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        // System property takes precedence, then environment variable, then the properties file,
        // so CI can override values without editing checked-in config.
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public static String appiumServerUrl() {
        return get("appium.server.url", "http://127.0.0.1:4723/");
    }

    public static String platformName() {
        return get("platform.name", "Android");
    }

    public static String automationName() {
        return get("automation.name", "UiAutomator2");
    }

    public static String deviceName() {
        return get("device.name", "Android Emulator");
    }

    public static String platformVersion() {
        return get("platform.version", "");
    }

    public static String appPath() {
        return get("app.path", "apps/ApiDemos-debug.apk");
    }

    public static String appPackage() {
        return get("app.package", "io.appium.android.apis");
    }

    public static String appActivity() {
        return get("app.activity", ".ApiDemos");
    }

    public static int explicitWaitSeconds() {
        return Integer.parseInt(get("explicit.wait.seconds", "15"));
    }

    public static int newCommandTimeoutSeconds() {
        return Integer.parseInt(get("new.command.timeout.seconds", "120"));
    }
}
