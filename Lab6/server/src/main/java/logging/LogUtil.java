package logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
    private static Logger InfoLogger = LogManager.getLogger("INFO");
    private static Logger TraceLogger = LogManager.getLogger("TraceLogger");
    private static Logger FatalLogger = LogManager.getLogger("FatalLogger");

    public static void logInfo(String message) {
        InfoLogger.info(message);
    }

    public static void logTrace(Throwable throwable) {
        TraceLogger.error("Stack trace", throwable);
    }

    public static void logFatal(Throwable throwable) {
        FatalLogger.error("Stack trace", throwable);
    }
}
