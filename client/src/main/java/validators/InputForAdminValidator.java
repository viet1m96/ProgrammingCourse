package validators;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public interface InputForAdminValidator {
    Predicate<String> isValidBirthday = s -> {
        if(s == null || s.isEmpty()) return true;
        try {
            LocalDate.parse(s);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    };

    Predicate<String> isValidWeight = s -> {
        if(s == null || s.isEmpty()) return true;
        try {
            Integer W = Integer.parseInt(s);
            return W > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    Predicate<String> isValidEyeColor = s -> {
        if(s == null || s.isEmpty()) return false;
        s = s.toUpperCase();
        try {
            Color.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    };

    Predicate<String> isValidCoordinate = s -> {
      if(s == null || s.isEmpty()) return true;
      try {
          Integer W = Integer.parseInt(s);
          return true;
      } catch (NumberFormatException e) {
          return false;
      }
    };

    Predicate<String> isValidPlace = s -> {
      return true;
    };
}
