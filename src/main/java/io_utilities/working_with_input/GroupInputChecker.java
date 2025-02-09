package io_utilities.working_with_input;

import enums.FormOfEducation;
import enums.Semester;

/**
 * The {@code GroupInputChecker} class provides static methods for validating input strings related to study group attributes.
 * It extends the {@link InputChecker} class and offers specific validation checks for X and Y coordinates, student count,
 * form of education, and semester.
 */
public class GroupInputChecker extends InputChecker {

    /**
     * Constructs a new {@code GroupInputChecker}.
     */
    public GroupInputChecker() {
    }

    /**
     * Checks if the input string represents a valid X coordinate.
     * The X coordinate must be an integer greater than -678.
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid X coordinate; {@code false} otherwise.
     */
    public static boolean checkX(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -678;
    }

    /**
     * Checks if the input string represents a valid Y coordinate.
     * The Y coordinate must be an integer greater than -438.
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid Y coordinate; {@code false} otherwise.
     */
    public static boolean checkY(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -438;
    }

    /**
     * Checks if the input string represents a valid student count.
     * The student count must be a positive integer.
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid student count; {@code false} otherwise.
     */
    public static boolean checkStdCount(String str) {
        return checkIntegerNumber(str) && Long.parseLong(str) > 0;
    }

    /**
     * Checks if the input string represents a valid form of education.
     * The input string must match one of the values defined in the {@link FormOfEducation} enum (case-insensitive).
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid form of education; {@code false} otherwise.
     */
    public static boolean checkEdu(String str) {
        if (str.isEmpty()) {
            return false;
        }
        str = str.toUpperCase();
        try {
            FormOfEducation.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the input string represents a valid semester.
     * The input string must match one of the values defined in the {@link Semester} enum (case-insensitive).
     *
     * @param str The input string to check.
     * @return {@code true} if the input string is a valid semester; {@code false} otherwise.
     */
    public static boolean checkSemester(String str) {
        if (str.isEmpty()) {
            return false;
        }
        str = str.toUpperCase();
        try {
            Semester.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}