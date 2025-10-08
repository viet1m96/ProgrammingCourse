package validators;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public interface InputForGroupValidator {
    Predicate<String> isValidSearchKey = s -> {
        if(s == null || s.isEmpty()) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    Predicate<String> isValidName = s -> {
        return s != null && !s.isEmpty();
    };

    Predicate<String> isValidStudCount = s -> {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    Predicate<String> isValidEduForm = s -> {
        if(s == null || s.isEmpty()) return true;
        s = s.toUpperCase();
        try {
            FormOfEducation.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    };

    Predicate<String> isValidSemester = s -> {
        if(s == null || s.isEmpty()) return false;
        s = s.toUpperCase();
        try {
            Semester.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    };

    Predicate<String> isValidGroupX = s -> {
        if(s == null || s.isEmpty()) return false;
        try {
            Integer X = Integer.parseInt(s);
            return X.compareTo(-678) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    Predicate<String> isValidGroupY = s -> {
        if(s == null || s.isEmpty()) return false;
        try {
            Integer Y = Integer.parseInt(s);
            return Y.compareTo(-438) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    };


}
