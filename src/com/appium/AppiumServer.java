package com.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;

//import javax.swing.text.Utilities;
//import agent1997.automation.framework.utilities.Utilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class AppiumServer {
    private final AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
    private AppiumDriverLocalService server;
    private int port;
    private final String appiumLogsLoc = "appium-logs";
    private final String logFileName = "logs";

    public AppiumServer() {
//        this.port = Utilities.getAvailablePort();
        this.port = 4723;
        this.serviceBuilder.usingPort(port);


        this.server = AppiumDriverLocalService.buildService(serviceBuilder);

        this.server.start();
    }

    public void stop() {
        this.server.stop();
    }

    public AppiumDriverLocalService get(){
        return this.server;
    }

    public void redirectLog(){

        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "resources");

        this.server.clearOutPutStreams();

        File directory = new File(filePath + this.appiumLogsLoc);

        if(!directory.exists()) {
            directory.mkdir();
        }


        try {
            this.server.addOutPutStream(new FileOutputStream(filePath + this.appiumLogsLoc
                    + new SimpleDateFormat("File-ddMMyy-hhmmss.SSS.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static int getAvailablePort() {
        int port = 4723;
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }


}

//https://medium.com/geekculture/how-to-start-appium-server-programmatically-in-java-2ae2265cde10
