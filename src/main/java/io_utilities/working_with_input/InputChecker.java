package io_utilities.working_with_input;

public class InputChecker {
    public static boolean checkInput(String input) {
        if(input == null || input.isEmpty()) return false;
        if(input.startsWith(" ")) return false;
        if(input.split(" ").length > 2) return false;
        if(input.contains("  ")) return false;
        if(input.contains("\t")) return false;
        return true;
    }
    public static boolean checkString(String str) {
        if(str == null) return false;
        str = str.trim();
        return !str.isEmpty();
    }
    public static boolean checkIntegerNumber(String str) {
        if(str == null || str.isEmpty()) return false;
        if(str.contains(".")) return false;
        for(int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if(i == 0 && tmp == '-') continue;
            if((tmp < '0' || tmp > '9') && tmp != '.') return false;
        }
        return true;
    }

}
