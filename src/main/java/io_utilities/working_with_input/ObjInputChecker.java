package io_utilities.working_with_input;

import enums.TypeOfGrp;
import enums.TypeOfPer;

/**
 * The {@code ObjInputChecker} class provides static methods for validating input strings based on the expected type
 * for either a StudyGroup or a Person object. It uses enums {@link TypeOfGrp} and {@link TypeOfPer} to determine
 * the validation rules.
 */
public class ObjInputChecker {

    /**
     * Constructs a new {@code ObjInputChecker}.
     */
    public ObjInputChecker() {
    }

    /**
     * Checks if the input string is valid for a StudyGroup attribute, based on the specified type.
     * This method uses a switch statement to determine the appropriate validation logic based on the {@link TypeOfGrp} enum.
     *
     * @param str  The input string to check.
     * @param type The {@link TypeOfGrp} enum representing the type of the StudyGroup attribute.
     * @return {@code true} if the input string is valid for the specified type; {@code false} otherwise.
     */
    public static boolean checkInputForGroup(String str, TypeOfGrp type) {
        return switch (type) {
            case ID -> str.matches("[0-9]+");
            case STRING -> InputChecker.checkString(str);
            case X -> GroupInputChecker.checkX(str);
            case Y -> GroupInputChecker.checkY(str);
            case COUNT -> GroupInputChecker.checkStdCount(str);
            case EDU -> GroupInputChecker.checkEdu(str);
            case SEMESTER -> GroupInputChecker.checkSemester(str);
        };
    }

    /**
     * Checks if the input string is valid for a Person attribute, based on the specified type.
     * This method uses a switch statement to determine the appropriate validation logic based on the {@link TypeOfPer} enum.
     *
     * @param str  The input string to check.
     * @param type The {@link TypeOfPer} enum representing the type of the Person attribute.
     * @return {@code true} if the input string is valid for the specified type; {@code false} otherwise.
     */
    public static boolean checkInputForPerson(String str, TypeOfPer type) {
        return switch (type) {
            case STRING -> InputChecker.checkString(str);
            case X, Y, Z -> InputChecker.checkIntegerNumber(str);
            case BIRTHDAY -> PersonInputChecker.checkBirthday(str);
            case WEIGHT -> PersonInputChecker.checkWeight(str);
            case COLOR -> PersonInputChecker.checkColor(str);
        };
    }
}