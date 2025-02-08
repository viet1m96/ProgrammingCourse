package io_utilities.working_with_input;

import enums.FormOfEducation;
import enums.Semester;

public class GroupInputChecker extends InputChecker {
    public GroupInputChecker() {}

    public static boolean checkX(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -678;
    }
    public static boolean checkY(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > -438;
    }
    public static boolean checkStdCount(String str) {
        return checkIntegerNumber(str) && Long.parseLong(str) > 0;
    }

    public static boolean checkEdu(String str) {
        if(str.isEmpty()) return false;
        str = str.toUpperCase();
        try {
            FormOfEducation.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public static boolean checkSemester(String str) {
        if(str.isEmpty()) return false;
        str = str.toUpperCase();
        try {
            Semester.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
