package iostream;

import logging.LogUtil;
import printer_options.RainbowPrinter;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            RainbowPrinter.printError("Uncaught exception in thread: " + t.getName());
            RainbowPrinter.printError("Please look at the log file for more details!");
            LogUtil.logTrace(e);
        });
        Controller controller = new Controller();
        controller.init();
        controller.run();
    }
}
