# appium-java

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-Java_Client-9E1FFF?logo=appium&logoColor=white)
![TestNG](https://img.shields.io/badge/Tests-TestNG-orange)
![CI](https://github.com/Mehedi-K/appium-java/actions/workflows/ci.yml/badge.svg)

An Appium + Java mobile UI test automation framework built with the **Page
Object Model**, using **TestNG** as the runner. It drives
[`android-apidemos`](https://github.com/appium/android-apidemos) — Appium's
own maintained fork of Google's classic ApiDemos sample app — on a local
Android emulator, covering navigation, standard widgets (checkboxes, radio
buttons, spinners, toggle buttons, text fields), scrollable lists, native
alert dialogs, and accessibility screens.

This is a portfolio project meant to demonstrate a clean, maintainable
mobile automation setup — explicit waits everywhere, no `Thread.sleep`, a
proper POM layer, a failure-screenshot listener, and a working GitHub
Actions CI pipeline that boots a real Android emulator and runs the suite
against it.

## Tech stack

- **Java 17**
- **Maven** for build/dependency management
- **Appium Java client 9.x** (`io.appium:java-client`), driving Android via
  the **UiAutomator2** automation engine
- **TestNG** as the test runner (suites, lifecycle listeners)
- **SLF4J + Logback** for lightweight logging
- An **Android emulator** (API 33, `google_apis`, `arm64-v8a`) — no real
  device and no cloud device farm (Sauce Labs/BrowserStack) required

## Target app under test

Tests run against
[**ApiDemos-debug.apk**](https://github.com/appium/android-apidemos), which
`appium/android-apidemos` builds and publishes as a GitHub release. It's a
fork of Google's original Android ApiDemos sample that the Appium project
itself maintains specifically as a stable, offline, no-login, no-network
practice target for Appium tutorials and test suites — the same role
`saucedemo.com` plays for Selenium in this author's `selenium-java` repo.
Using it (rather than a heavier app with a login/network flow) keeps the
suite fast and reliably reproducible in a headless CI emulator.

The apk is **not committed to this repository**. `apps/download-app.sh`
fetches it from its
[official release](https://github.com/appium/android-apidemos/releases)
into `apps/ApiDemos-debug.apk` (gitignored); `AppiumDriverFactory` also
downloads it automatically on first run if it's missing, so `mvn test`
works out of the box both locally and in CI.

## Prerequisites (local runs)

- Java 17 (JDK)
- Maven 3.9+
- Node.js 18+ (the Appium server itself runs on Node)
- Android SDK command-line tools, with:
  - `platform-tools`
  - a platform + system image for an emulator, e.g.
    `platforms;android-33` and `system-images;android-33;google_apis;arm64-v8a`
    (use `arm64-v8a` on Apple Silicon Macs, `x86_64` on Intel Macs/Linux)
  - the `emulator` package

On macOS, the SDK can be installed with Homebrew:

```bash
brew install --cask android-commandlinetools
export ANDROID_HOME=/opt/homebrew/share/android-commandlinetools
export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"

yes | sdkmanager --licenses
sdkmanager --install "platform-tools" "platforms;android-33" \
  "build-tools;33.0.2" "system-images;android-33;google_apis;arm64-v8a" "emulator"
```

Create and boot an AVD:

```bash
avdmanager create avd -n appium_test_avd \
  -k "system-images;android-33;google_apis;arm64-v8a" -d pixel_5 --force

emulator -avd appium_test_avd -no-window -no-audio -no-boot-anim &
adb wait-for-device
```

Install and start the Appium server (in a separate terminal, or backgrounded):

```bash
npm install -g appium
appium driver install uiautomator2
appium
```

## Running the tests

With an emulator booted and the Appium server running on its default port
(`http://127.0.0.1:4723`):

```bash
mvn test
```

The app is downloaded automatically on first run if `apps/ApiDemos-debug.apk`
isn't already present. Test results land in `target/surefire-reports/`, and
any screenshot taken on a test failure lands in `screenshots/` (gitignored).

To point the suite at a different Appium server or device, override the
relevant `config.properties` values with system properties, e.g.:

```bash
mvn test -Dappium.server.url=http://127.0.0.1:4725/ -Ddevice.name=Pixel_6_API_34
```

## Project structure

```
appium-java/
  pom.xml                                       Maven build config (Appium Java client, TestNG, Logback)
  apps/
    download-app.sh                             Fetches ApiDemos-debug.apk from its GitHub release
  src/main/java/com/automationframework/appium/
    driver/
      AppiumDriverFactory.java                  Builds UiAutomator2 capabilities, starts/quits AndroidDriver sessions
    pages/                                      Page Object Model classes
      BasePage.java                             Shared explicit-wait helpers + list-item navigation
      ApiDemosHomePage.java                     Landing screen (top-level API categories)
      ViewsPage.java                             Views category menu
      ControlsThemePage.java / ControlsPage.java Views > Controls (theme picker, then checkboxes/radios/spinner/toggle/edit text)
      TextFieldsPage.java                       Views > TextFields
      ListsPage.java / ArrayListPage.java        Views > Lists > 01. Array
      AppMenuPage.java                          App category menu
      AlertDialogsPage.java, OkCancelDialog.java,
      TextEntryDialog.java                      App > Alert Dialogs and its native dialog variants
      AccessibilityPage.java, CustomViewPage.java Accessibility category + Custom View demo
    utils/
      ConfigReader.java                         Reads config.properties, overridable via -D system properties
  src/test/java/com/automationframework/appium/
    tests/
      BaseTest.java                             TestNG @BeforeMethod/@AfterMethod Appium session lifecycle
      HomeScreenTest.java                       Landing screen + navigation/back behavior
      ControlsTest.java                         Checkboxes, radio buttons, star, toggle button, edit text, Save button
      SpinnerTest.java                          Spinner default value + selection
      TextFieldsTest.java                       Hint text / numeric / password fields
      ListsTest.java                            Scrollable array list
      AlertDialogsTest.java                     Native OK/Cancel + text-entry AlertDialogs
      AccessibilityTest.java                    Accessibility menu + Custom View screen
    listeners/
      ScreenshotListener.java                   TestNG ITestListener: screenshot on failure
  src/test/resources/
    testng.xml                                  Suite definition referencing all test classes
    config.properties                           Appium server URL, capabilities, wait timeouts
  .github/workflows/ci.yml                      GitHub Actions: boots a real emulator and runs the suite on every push/PR to main
```

## Design notes

- **Explicit waits only.** Every page object interaction goes through
  `WebDriverWait` + `ExpectedConditions` in `BasePage`; there is no
  `Thread.sleep` anywhere in the framework. The one deliberate exception is
  the long array-list scroll helper, which issues a single native
  `UiScrollable#scrollIntoView` call rather than wrapping it in a wait loop
  (each retry would otherwise re-trigger a full on-device scroll search).
- **Page Object Model**, mirroring this author's `selenium-java` repo:
  page classes expose behavior (`openViews()`, `toggleCheckbox1()`,
  `selectSpinnerOption(...)`), tests only assert on that behavior.
- **Failure diagnostics.** `ScreenshotListener` (a TestNG `ITestListener`)
  captures a PNG screenshot on any test failure and saves it to
  `screenshots/`, which CI uploads as a build artifact alongside the
  Surefire XML/HTML reports.
- **Self-provisioning app.** Neither the repo nor CI needs to ship the apk;
  it's fetched from its public GitHub release on demand and cached locally.
- **CI.** `.github/workflows/ci.yml` runs on every push/PR to `main`: sets
  up JDK 17 and Node, installs Appium + the UiAutomator2 driver, then uses
  [`reactivecircus/android-emulator-runner`](https://github.com/reactivecircus/android-emulator-runner)
  to boot a real (accelerated, headless) Android emulator on a macOS runner
  and run `mvn -B test` against it. Surefire reports, failure screenshots,
  and the Appium server log are uploaded as build artifacts.

## Why no cloud device farm

Everything here runs against a local/CI-managed Android emulator — no Sauce
Labs, BrowserStack, or other device-farm account is required to clone and
run this suite.
