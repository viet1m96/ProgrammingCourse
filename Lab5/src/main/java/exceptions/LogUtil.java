package exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code LogUtil} class provides utility methods for logging exceptions and stack traces using Log4j 2.
 * It centralizes logging functionality to ensure consistent logging practices throughout the application.
 */
public class LogUtil {

    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private LogUtil() {
    }

    /**
     * Logs the stack trace of a given {@link Throwable} object at the ERROR level.
     * This method is useful for capturing detailed information about exceptions that occur during program execution.
     *
     * @param t The {@link Throwable} object whose stack trace is to be logged.
     */
    public static void logStackTrace(Throwable t) {
        logger.error("Stack Trace:", t);
    }
}
