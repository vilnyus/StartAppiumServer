package com.appium;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AppiumServer1 {

    static void Start() {
        getInstance().start();
    }

    static AppiumDriverLocalService getInstance(){
        DesiredCapabilities dc = new DesiredCapabilities();

        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        dc.setCapability("platformName", "android");
        dc.setCapability("appPackage", "com.google.android.calculator");
        dc.setCapability("appActivity", "com.android.calculator2.Calculator");

        AppiumServiceBuilder service = new AppiumServiceBuilder();
        service
                .withAppiumJS(new File("C:\\Program Files\\Appium Server GUI\\resources\\app\\dist\\main.js"))
                .usingDriverExecutable(new File("C:\\Users\\vagha\\AppData\\Roaming\\npm\\appium"))
                .usingPort(4723)
                .withLogFile(new File("/resources/AppiumLog.txt"));

        return AppiumDriverLocalService.buildService(service);
    }

    public static void main(String[] args){
        AppiumServer1.Start();
    }
}
