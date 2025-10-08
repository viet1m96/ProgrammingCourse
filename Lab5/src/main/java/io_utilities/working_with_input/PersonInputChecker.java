package io_utilities.working_with_input;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The {@code PersonInputChecker} class provides static methods for validating input strings related to {@link main_objects.Person} properties.
 * It extends the {@link InputChecker} class and provides specific checks for weight, color, and birthday.
 */
public class PersonInputChecker extends InputChecker {

    /**
     * Constructs a new {@code PersonInputChecker}.
     */
    public PersonInputChecker() {
    }

    /**
     * Checks if the given string represents a valid weight.
     * The weight must be an integer greater than 0.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid weight, {@code false} otherwise.
     */
    public static boolean checkWeight(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > 0;
    }

    /**
     * Checks if the given string represents a valid {@link Color}.
     * The string is converted to uppercase and checked against the enum values of {@link Color}.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid {@link Color}, {@code false} otherwise.
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
     * Checks if the given string represents a valid birthday in the format "dd-MM-yyyy".
     * It uses {@link DateTimeFormatter} to parse the string and returns {@code false} if a {@link DateTimeParseException} is caught.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid birthday, {@code false} otherwise.
     */
    public static boolean checkBirthday(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(str, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
