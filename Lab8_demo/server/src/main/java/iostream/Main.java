package iostream;

import authorization_lib.JwtUtil;
import config.AppConfig;
import exceptions.log_exceptions.EnvNotExistsException;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import logging.LogUtil;
import network.Transporter;


public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("Uncaught exception in thread: " + t.getName());
            System.out.println("Please look at the log file for more details!");
            LogUtil.logTrace(e);
        });
        AppConfig.init();
        try {
            JwtUtil.init();
            Transporter transporter = new Transporter();
            transporter.init();
            Runtime.getRuntime().addShutdownHook(new Thread(transporter::emergencyShutdown));
            transporter.handleConnection();
        } catch (NetworkException | LogException | EnvNotExistsException e) {
            LogUtil.logTrace(e);
            System.out.println(e.getMessage());
        }
    }
}
