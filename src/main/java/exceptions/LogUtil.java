package exceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    private LogUtil() {
        // Private constructor to prevent instantiation
    }

    public static void logStackTrace(Throwable t) {
        logger.error("Stack Trace:", t);
    }

}