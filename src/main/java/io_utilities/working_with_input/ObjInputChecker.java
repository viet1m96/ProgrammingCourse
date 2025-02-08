package io_utilities.working_with_input;


import enums.TypeOfGrp;
import enums.TypeOfPer;

public class ObjInputChecker {
    public ObjInputChecker() {}

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
