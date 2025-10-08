package input_validators;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


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

    public static boolean checkBirthday(String str) {
        try {
            LocalDate.parse(str);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
