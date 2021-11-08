package com.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.util.HashMap;

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

    @Test
    public void test() {
        // test code goes here
        assertTrue(true);
    }
}