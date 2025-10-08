package iostream;


import exceptions.network_exception.NetworkException;

import logging.LogUtil;
import network.Transporter;
import printer_options.RainbowPrinter;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            RainbowPrinter.printError("Uncaught exception in thread: " + t.getName());
            RainbowPrinter.printError("Please look at the log file for more details!");
            LogUtil.logFatal(e);
        });
        try {
            String fileName = "/home/cun/IdeaProjects/Lab6/server/src/main/java/database/data.csv";
            Transporter transporter = new Transporter();
            transporter.init(fileName);
            Runtime.getRuntime().addShutdownHook(new Thread(transporter::emergencyShutdown));
            transporter.handleConnection();
        } catch (NetworkException e) {
            RainbowPrinter.printError(e.toString());
        }

    }
}
