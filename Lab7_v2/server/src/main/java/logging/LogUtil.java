package logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
    private static final Logger InfoLogger = LogManager.getLogger("INFO");
    private static final Logger TraceLogger = LogManager.getLogger("TraceLogger");

    public static void logInfo(String message) {
        InfoLogger.info(message);
    }

    public static void logTrace(Throwable throwable) {
        TraceLogger.error("Stack trace", throwable);
    }

}
