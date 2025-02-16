package io_utilities.working_with_input;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PersonInputChecker extends InputChecker {

    public PersonInputChecker() {
    }

    public static boolean checkWeight(String str) {
        return checkIntegerNumber(str) && Integer.parseInt(str) > 0;
    }

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(str, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


}