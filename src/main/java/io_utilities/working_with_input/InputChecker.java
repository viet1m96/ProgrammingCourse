package io_utilities.working_with_input;

import exceptions.LogUtil;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;

import java.io.IOException;

public class InputChecker {

    public static boolean checkInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.split(" ").length <= 2;
    }

    public static boolean checkString(String str) {
        if (str == null) {
            return false;
        }
        return !str.isEmpty();
    }

    public static boolean checkIntegerNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean maybeEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean yesOrNo(String action, String notice) {
        try {
            RainbowPrinter.printInfo(notice);
            RainbowPrinter.printInfo("Do you want to " + action + "?" + "(yes/no)");
            InputReader inputReader = new InputReader();
            inputReader.setReader();
            String input = "";
            while(input.isEmpty()) {
                input = inputReader.readLine();
                if(input.equalsIgnoreCase("yes")) {
                    return true;
                } else if(input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    try {
                        throw new WrongInputException();
                    } catch (WrongInputException e) {
                        input = "";
                        RainbowPrinter.printError(e.toString());
                    }
                }
            }
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
        }
        return false;
    }
}