package iostream;

import exceptions.LogUtil;
import io_utilities.printers.RainbowPrinter;

/**
 * The {@code Main} class serves as the entry point for the application. It initializes and starts the application's core components.
 */
public class Main {
    /**
     * The main method of the application.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // Set a default uncaught exception handler to log any unhandled exceptions in threads.
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            RainbowPrinter.printError("Uncaught exception in thread: " + t.getName());
            RainbowPrinter.printError("Please look at the log file for more details!");
            LogUtil.logStackTrace(e);
        });

        // Create an instance of the Handler class, which is responsible for managing the application's logic.
        Handler handler = new Handler();
        /*String filePath = System.getenv("file_name");
        handler.prepare(filePath);*/
        // Prepare the handler with the path to the data file.
        // This line uses a hardcoded file path. It should be replaced with a more robust method
        // of obtaining the file path in a production environment (e.g., command-line arguments, configuration file, environment variable).
        handler.prepare("/home/cun/IdeaProjects/Lab5/src/main/java/data_files/data.csv");

        // Run the handler, starting the main application loop.
        handler.run();
    }
}




