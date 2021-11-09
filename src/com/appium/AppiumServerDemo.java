package com.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class AppiumServerDemo {


    private AppiumDriver driver;
    private static AppiumDriverLocalService service;


    @BeforeClass
    public static void startAppiumServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();

        builder.usingAnyFreePort();
        // Tell builder where node is installed. Or set this path in an environment variable named NODE_PATH
//        builder.usingDriverExecutable(new File("path_to_node"));
        // Tell builder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH
//        builder.withAppiumJS(new File("path_to_appium"));

        //        HashMap<String, String> environment = new HashMap();
//        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
//        builder.withEnvironment(environment);

        builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
        builder.withAppiumJS(new File("C:\\Users\\vagha\\AppData\\Roaming\\npm\\node_modules\\appium"));

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    @BeforeMethod
    public void startSession() {
        System.out.println("Service URL location is:  " + service.getUrl());

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("appPackage", "com.google.android.calculator");
        caps.setCapability("appActivity", "com.android.calculator2.Calculator");

        driver = new AndroidDriver<MobileElement>(service.getUrl(), caps);

    }

    @AfterMethod
    public void endSession() {
        try {
            driver.quit();
        } catch (Exception ign) {}
    }

    @AfterClass
    public static void stopAppiumServer() {
        service.stop();
    }

//    @Test
//    public void test() {
//        // test code goes here
//        assertTrue(true);
//    }

    @Test
    public void testCalculator() {
        MobileElement el2 = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_1");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("plus");
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_2");
        el4.click();
        MobileElement el5 = (MobileElement) driver.findElementByAccessibilityId("equals");
        el5.click();

        Assert.assertEquals(driver.findElementById("com.google.android.calculator:id/result_final").getText(), "3");
    }

    @Test
    public void testADBDevices() {
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;

            Pattern pattern = Pattern.compile("^([a-zA-Z0-9\\-]+)(\\s+)(device)");
            Matcher matcher;

            while ((line = in.readLine()) != null) {
                if (line.matches(pattern.pattern())) {
                    matcher = pattern.matcher(line);
                    if (matcher.find())
                        System.out.println("Connected device" + matcher.group(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(true);
    }
}