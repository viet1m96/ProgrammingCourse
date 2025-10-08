package io_utilities.working_with_input;

import enums.TypeOfGrp;
import enums.TypeOfPer;

/**
 * The {@code ObjInputChecker} class provides static methods for validating input strings for different object properties,
 * specifically for {@link main_objects.StudyGroup} and {@link main_objects.Person} objects.  It differentiates between input
 * from the console and input from a file, allowing for different validation rules (e.g., allowing "null" values from files).
 */
public class ObjInputChecker {

    /**
     * Constructs a new {@code ObjInputChecker}.
     */
    public ObjInputChecker() {
    }

    /**
     * Checks if the given string is a valid input for a {@link main_objects.StudyGroup} property when the input is coming from the console.
     * The validation is based on the specified {@link TypeOfGrp} enum, which indicates the type of the property being checked.
     *
     * @param str  The string to be checked.
     * @param type The {@link TypeOfGrp} enum representing the type of the property.
     * @return {@code true} if the string is a valid input for the property, {@code false} otherwise.
     */
    public static boolean checkInputForGroupConsole(String str, TypeOfGrp type) {
        return switch (type) {
            case ID -> str.matches("[0-9]+");
            case STRING -> InputChecker.checkString(str);
            case X -> GroupInputChecker.checkX(str);
            case Y -> GroupInputChecker.checkY(str);
            case COUNT -> GroupInputChecker.checkStdCount(str);
            case EDU -> (InputChecker.maybeEmpty(str) || GroupInputChecker.checkEdu(str));
            case SEMESTER -> GroupInputChecker.checkSemester(str);
        };
    }

    /**
     * Checks if the given string is a valid input for a {@link main_objects.Person} property when the input is coming from the console.
     * The validation is based on the specified {@link TypeOfPer} enum, which indicates the type of the property being checked.
     *
     * @param str  The string to be checked.
     * @param type The {@link TypeOfPer} enum representing the type of the property.
     * @return {@code true} if the string is a valid input for the property, {@code false} otherwise.
     */
    public static boolean checkInputForPersonConsole(String str, TypeOfPer type) {
        return switch (type) {
            case NAME -> InputChecker.checkString(str);
            case X, Y, Z -> InputChecker.checkIntegerNumber(str);
            case BIRTHDAY -> (InputChecker.maybeEmpty(str) || PersonInputChecker.checkBirthday(str));
            case WEIGHT -> (InputChecker.maybeEmpty(str) || PersonInputChecker.checkWeight(str));
            case COLOR -> PersonInputChecker.checkColor(str);
            case LOCATION -> (InputChecker.maybeEmpty(str) || InputChecker.checkString(str));
        };
    }

    /**
     * Checks if the given string is a valid input for a {@link main_objects.StudyGroup} property when the input is coming from a file.
     * The validation is based on the specified {@link TypeOfGrp} enum, which indicates the type of the property being checked.
     * This method allows the string "null" to represent a null value for certain properties.
     *
     * @param str  The string to be checked.
     * @param type The {@link TypeOfGrp} enum representing the type of the property.
     * @return {@code true} if the string is a valid input for the property, {@code false} otherwise.
     */
    public static boolean checkInputForGroupFile(String str, TypeOfGrp type) {
        return switch (type) {
            case ID -> str.matches("[0-9]+");
            case STRING -> InputChecker.checkString(str);
            case X -> GroupInputChecker.checkX(str);
            case Y -> GroupInputChecker.checkY(str);
            case COUNT -> GroupInputChecker.checkStdCount(str);
            case EDU -> (str.equals("null") || GroupInputChecker.checkEdu(str));
            case SEMESTER -> GroupInputChecker.checkSemester(str);
        };
    }

    /**
     * Checks if the given string is a valid input for a {@link main_objects.Person} property when the input is coming from a file.
     * The validation is based on the specified {@link TypeOfPer} enum, which indicates the type of the property being checked.
     * This method allows the string "null" to represent a null value for certain properties.
     *
     * @param str  The string to be checked.
     * @param type The {@link TypeOfPer} enum representing the type of the property.
     * @return {@code true} if the string is a valid input for the property, {@code false} otherwise.
     */
    public static boolean checkInputForPersonFile(String str, TypeOfPer type) {
        return switch (type) {
            case NAME -> InputChecker.checkString(str);
            case X, Y, Z -> (str.equals("null") || InputChecker.checkIntegerNumber(str));
            case BIRTHDAY -> (str.equals("null") || PersonInputChecker.checkBirthday(str));
            case WEIGHT -> (str.equals("null") || PersonInputChecker.checkWeight(str));
            case COLOR -> PersonInputChecker.checkColor(str);
            case LOCATION -> (str.equals("null") || InputChecker.checkString(str));
        };
    }
}
