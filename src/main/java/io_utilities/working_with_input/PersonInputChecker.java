package io_utilities.working_with_input;

import enums.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonInputChecker extends InputChecker{
    public PersonInputChecker() {}

    public static boolean checkWeight(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > 0;
    }
    public static boolean checkColor(String str) {
        if(str.isEmpty()) return false;
        str = str.toUpperCase();
        try {
            Color.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean checkBirthday(String str) {
        if(str.isEmpty()) return false;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            formatter.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
