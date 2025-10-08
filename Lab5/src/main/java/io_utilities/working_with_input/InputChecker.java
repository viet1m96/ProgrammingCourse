package io_utilities.working_with_input;

import exceptions.LogUtil;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;

import java.io.IOException;

/**
 * The {@code InputChecker} class provides static utility methods for validating user input.
 * It includes methods for checking the general format of input, validating strings, checking if a string represents an integer,
 * determining if a string is empty, and prompting the user for a yes/no confirmation.
 */
public class InputChecker {

    /**
     * Checks if the given input string is valid based on a general format.
     * The input is considered valid if it is not null, not empty, and contains at most two words (separated by spaces).
     *
     * @param input The input string to be checked.
     * @return {@code true} if the input is valid, {@code false} otherwise.
     */
    public static boolean checkInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.split(" ").length <= 2;
    }

    /**
     * Checks if the given string is a valid string.
     * A string is considered valid if it is not null and not empty.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is valid, {@code false} otherwise.
     */
    public static boolean checkString(String str) {
        if (str == null) {
            return false;
        }
        return !str.isEmpty();
    }

    /**
     * Checks if the given string represents a valid integer number.
     * It attempts to parse the string as an integer and returns {@code false} if a {@link NumberFormatException} is caught.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid integer number, {@code false} otherwise.
     */
    public static boolean checkIntegerNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the given string is either null or empty.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is null or empty, {@code false} otherwise.
     */
    public static boolean maybeEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Prompts the user for a yes/no confirmation and returns a boolean value based on their response.
     * It uses {@link RainbowPrinter} to display the prompt and {@link InputReader} to read the user's input.
     * It handles {@link IOException} and logs any stack traces using {@link LogUtil}.
     *
     * @param action The action for which confirmation is being requested.
     * @param notice A notice or message to be displayed to the user.
     * @return {@code true} if the user enters "yes", {@code false} if the user enters "no".
     */
    public static boolean yesOrNo(String action, String notice) {
        try {
            RainbowPrinter.printInfo(notice);
            RainbowPrinter.printInfo("Do you want to " + action + "?" + "(yes/no)");
            InputReader inputReader = new InputReader();
            inputReader.setReader();
            String input = "";
            while (input.isEmpty()) {
                input = inputReader.readLine();
                if (input.equalsIgnoreCase("yes")) {
                    return true;
                } else if (input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    try {
                        throw new WrongInputException();
                    } catch (WrongInputException e) {
                        input = "";
                        RainbowPrinter.printError(e.toString());
                    }
                }
            }
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
        }
        return false;
    }
}
