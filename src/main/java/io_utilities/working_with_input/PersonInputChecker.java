package io_utilities.working_with_input;

import enums.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The {@code PersonInputChecker} class provides static methods for validating input strings related to attributes of a Person object.
 * It extends the {@link InputChecker} class and provides specific validation checks for weight, hair color, and birthday.
 */
public class PersonInputChecker extends InputChecker {

    /**
     * Constructs a new {@code PersonInputChecker}.
     */
    public PersonInputChecker() {
    }

    /**
     * Checks if the input string represents a valid weight.
     * The weight must be a positive integer.
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid weight; {@code false} otherwise.
     */
    public static boolean checkWeight(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > 0;
    }

    /**
     * Checks if the input string represents a valid hair color.
     * The input string must match one of the values defined in the {@link Color} enum (case-insensitive).
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid hair color; {@code false} otherwise.
     */
    public static boolean checkColor(String str) {
        if (str.isEmpty()) {
            return false;
        }
        str = str.toUpperCase();
        try {
            Color.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the input string represents a valid birthday in the format "dd-MM-yyyy".
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid birthday; {@code false} otherwise.
     */
    public static boolean checkBirthday(String str) {
        if (str.isEmpty()) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formatter.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}