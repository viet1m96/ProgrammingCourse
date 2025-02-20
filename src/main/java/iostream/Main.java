package iostream;

import exceptions.LogUtil;
import io_utilities.printers.RainbowPrinter;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            RainbowPrinter.printError("Uncaught exception in thread: " + t.getName());
            RainbowPrinter.printError("Please look at the log file for more details!");
            LogUtil.logStackTrace(e);
        });
        Handler handler = new Handler();
        /*String filePath = System.getenv("file_name");
        handler.prepare(filePath);*/
        handler.prepare("/home/cun/IdeaProjects/Lab5/src/main/java/data_files/data.csv");
        handler.run();
    }
}