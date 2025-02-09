package io_utilities.printers;

/**
 * The {@code RainbowPrinter} class provides utility methods for printing output to the console with different colors.
 * It uses ANSI escape codes to colorize the text, providing visual cues for different types of information.
 */
public class RainbowPrinter {

    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    /**
     * Prints the given object to the console in green color, indicating informational output.
     * The output is reset to white after the object is printed.
     *
     * @param toOut The object to print to the console.  The object's {@code toString()} method will be used.
     */
    public static void printInfo(Object toOut) {
        System.out.println(GREEN + toOut + WHITE);
    }

    /**
     * Prints the given object to the console in red color, indicating an error message.
     * The output is reset to white after the object is printed.
     *
     * @param toError The object to print to the console as an error message.  The object's {@code toString()} method will be used.
     */
    public static void printError(Object toError) {
        System.out.println(RED + toError + WHITE);
    }

    /**
     * Prints the given object to the console in yellow color, typically used for displaying conditions or warnings.
     * The output is reset to white after the object is printed.
     *
     * @param toOut The object to print to the console as a condition or warning.  The object's {@code toString()} method will be used.
     */
    public static void printCondition(Object toOut) {
        System.out.println(YELLOW + toOut + WHITE);
    }

    /**
     * Prints the given object to the console in cyan color, often used for displaying results.
     * The output is reset to white after the object is printed.
     *
     * @param toOut The object to print to the console as a result. The object's {@code toString()} method will be used.
     */
    public static void printResult(Object toOut) {
        System.out.println(CYAN + toOut + WHITE);
    }
}