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
                .withAppiumJS(new File("C:\\Users\\vagha\\AppData\\Roaming\\npm\\node_modules\\appium"))
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .withCapabilities(dc)
                .usingPort(4723)
                .withLogFile(new File(System.getProperty("user.dir") + "\\resources\\AppiumLog.txt" + Thread.currentThread().getId()));

        return AppiumDriverLocalService.buildService(service);
    }

    public static void main(String[] args){
        AppiumServer1.Start();
    }
}
