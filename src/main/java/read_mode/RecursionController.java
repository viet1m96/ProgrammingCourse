package read_mode;

import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;

import java.io.IOException;
import java.util.HashSet;

public class RecursionController {

    private static final HashSet<String> fileSaver = new HashSet<>();
    private static Integer RecursionCount = null;
    private static String RecursionName = null;

    private RecursionController() {}

    public static boolean controlRecursion(String arg) throws IOException {
        if(fileSaver.isEmpty()) {
            System.out.println("Empty");
        } else {
            fileSaver.forEach(System.out::println);
        }
        if (!fileSaver.contains(arg)) {
            System.out.println("added");
            fileSaver.add(arg);
            return true;
        } else {
            if (RecursionCount == null) {
                detectRecursion(arg);
            } else {
                if (RecursionCount == 0) {
                    RecursionCount = null;
                    RecursionName = null;
                    System.out.println("reset");
                    return false;
                } else if (RecursionName.equals(arg)) {
                    System.out.println(RecursionCount);
                    RecursionCount--;
                }
            }
        }
        return RecursionCount != null && RecursionCount != 0;
    }

    public static void detectRecursion(String arg) throws IOException {
        RainbowPrinter.printInfo("Recursion detected, do you want to continue?(yes/no)");
        InputReader reader = new InputReader();
        reader.setReader();
        while (RecursionCount == null) {
            try {
                String input = reader.readLine();
                if (!InputChecker.checkString(input) || (!input.equalsIgnoreCase("yes")) && !input.equalsIgnoreCase("no")) {
                    throw new WrongInputException();
                }
                if (input.equals("yes")) {
                    RainbowPrinter.printInfo("How many times would you like to run recursively?(Enter a positive integer)");
                    input = reader.readLine();
                    if (!InputChecker.checkIntegerNumber(input) || Integer.parseInt(input) <= 0) {
                        throw new WrongInputException();
                    } else {
                        RecursionCount = Integer.parseInt(input);
                        RecursionName = arg;
                    }
                } else {
                    RecursionCount = 0;
                }
            } catch (WrongInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
    }

    public static void dropFileName(String arg) {
        fileSaver.remove(arg);
    }
}