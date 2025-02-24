package io_utilities.working_with_input;

import enums.FormOfEducation;
import enums.Semester;

/**
 * The {@code GroupInputChecker} class provides static methods for validating input strings related to {@link main_objects.StudyGroup} properties.
 * It extends the {@link InputChecker} class and provides specific checks for X and Y coordinates, student count, form of education, and semester.
 */
public class GroupInputChecker extends InputChecker {

    /**
     * Constructs a new {@code GroupInputChecker}.
     */
    public GroupInputChecker() {
    }

    /**
     * Checks if the given string represents a valid X coordinate.
     * The X coordinate must be an integer greater than -678.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid X coordinate, {@code false} otherwise.
     */
    public static boolean checkX(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -678;
    }

    /**
     * Checks if the given string represents a valid Y coordinate.
     * The Y coordinate must be an integer greater than -438.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid Y coordinate, {@code false} otherwise.
     */
    public static boolean checkY(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -438;
    }

    /**
     * Checks if the given string represents a valid student count.
     * The student count must be an integer greater than 0.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid student count, {@code false} otherwise.
     */
    public static boolean checkStdCount(String str) {
        return checkIntegerNumber(str) && Long.parseLong(str) > 0;
    }

    /**
     * Checks if the given string represents a valid {@link FormOfEducation}.
     * The string is converted to uppercase and checked against the enum values of {@link FormOfEducation}.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid {@link FormOfEducation}, {@code false} otherwise.
     */
    public static boolean checkEdu(String str) {
        str = str.toUpperCase();
        try {
            FormOfEducation.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the given string represents a valid {@link Semester}.
     * The string is converted to uppercase and checked against the enum values of {@link Semester}.
     *
     * @param str The string to be checked.
     * @return {@code true} if the string is a valid {@link Semester}, {@code false} otherwise.
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
