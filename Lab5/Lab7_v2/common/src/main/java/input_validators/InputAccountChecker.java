package input_validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputAccountChecker extends InputChecker {

    public static boolean checkUser(String user) {
        return user != null && !user.isEmpty() && user.length() <= 20;
    }

    public static boolean checkPassword(String password) {
        if (password == null) return false;
        String pattern = "^(?=.*\\d)(?=.*[!@#$%^&*()\\-+])(?=.*[A-Z])(?=.*[a-z]).{8,20}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(password);
        return m.matches();
    }

}
