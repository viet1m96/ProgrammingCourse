package io_utilities.working_with_input;

/**
 * The {@code InputChecker} class provides static utility methods for validating input strings.
 * It offers basic checks for null or empty strings, leading spaces, multiple spaces, tabs, and whether
 * a string represents an integer number.
 */
public class InputChecker {

    /**
     * Checks if the input string is valid based on a set of criteria.
     * The input string must not be null or empty, must not start with a space, must not contain more than two words
     * separated by spaces, must not contain double spaces, and must not contain tabs.
     *
     * @param input The input string to check.
     * @return {@code true} if the input string is valid; {@code false} otherwise.
     */
    public static boolean checkInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        if (input.startsWith(" ")) {
            return false;
        }
        if (input.split(" ").length > 2) {
            return false;
        }
        if (input.contains("  ")) {
            return false;
        }
        if (input.contains("\t")) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the input string is a valid string after trimming whitespace.
     * The string must not be null, and after trimming leading and trailing whitespace, it must not be empty.
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid string; {@code false} otherwise.
     */
    public static boolean checkString(String str) {
        if (str == null) {
            return false;
        }
        str = str.trim();
        return !str.isEmpty();
    }

    /**
     * Checks if the input string represents a valid integer number.
     * The string must not be null or empty, must not contain a decimal point, and must contain only digits
     * (optionally preceded by a minus sign).
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid integer number; {@code false} otherwise.
     */
    public static boolean checkIntegerNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        if (str.contains(".")) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if (i == 0 && tmp == '-') {
                continue;
            }
            if ((tmp < '0' || tmp > '9') && tmp != '.') {
                return false;
            }
        }
        return true;
    }
}