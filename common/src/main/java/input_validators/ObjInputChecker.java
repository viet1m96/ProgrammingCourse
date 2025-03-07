package input_validators;

import enums.TypeOfGrp;
import enums.TypeOfPer;

public class ObjInputChecker {

    /**
     * Constructs a new {@code ObjInputChecker}.
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
