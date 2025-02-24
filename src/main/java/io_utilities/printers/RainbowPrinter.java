package io_utilities.printers;

/**
 * The {@code RainbowPrinter} class provides utility methods for printing colored output to the console.
 * It uses ANSI escape codes to color the text, providing different colors for different types of information, such as informational messages, errors, conditions, and results.
 * This can improve the readability and clarity of console output.
 */
public class RainbowPrinter {

    private static final String YELLOW = "\u001B[33m"; // ANSI escape code for yellow color
    private static final String RED = "\u001B[31m";    // ANSI escape code for red color
    private static final String GREEN = "\u001B[32m";   // ANSI escape code for green color
    private static final String CYAN = "\u001B[36m";    // ANSI escape code for cyan color
    private static final String WHITE = "\u001B[37m";   // ANSI escape code for white color (reset to default)

    /**
     * Prints the given object to the console in green color, followed by resetting the color to white.
     *
     * @param toOut The object to be printed as an informational message.
     */
    public static void printInfo(Object toOut) {
        System.out.println(GREEN + toOut + WHITE);
    }

    /**
     * Prints the given object to the console in red color, followed by resetting the color to white.
     *
     * @param toError The object to be printed as an error message.
     */
    public static void printError(Object toError) {
        System.out.println(RED + toError + WHITE);
    }

    /**
     * Prints the given object to the console in yellow color, followed by resetting the color to white.
     *
     * @param toOut The object to be printed as a condition or warning message.
     */
    public static void printCondition(Object toOut) {
        System.out.println(YELLOW + toOut + WHITE);
    }

    /**
     * Prints the given object to the console in cyan color, followed by resetting the color to white.
     *
     * @param toOut The object to be printed as a result or output message.
     */
    public static void printResult(Object toOut) {
        System.out.println(CYAN + toOut + WHITE);
    }
}
