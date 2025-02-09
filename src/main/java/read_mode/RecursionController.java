package read_mode;

import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;

import java.io.IOException;
import java.util.HashSet;

/**
 *  {@code RecursionController} manages recursion detection and control when executing scripts,
 *  preventing infinite loops.
 */
public class RecursionController {

    private static final HashSet<String> fileSaver = new HashSet<>(); // Stores the names of files currently being executed.
    private static Integer RecursionCount = null; // Limits the number of recursive calls. Null means not initialized.
    private static String RecursionName = null;  // Stores the name of the file causing recursion.

    /**
     * Prevents direct instantiation.
     */
    private RecursionController() {}  // Make constructor private to prevent instantiation. This class is a utility.


    /**
     * Checks if a script file is already being executed to prevent infinite recursion.
     *
     * If the file is not already being executed, it adds the filename to the {@code fileSaver} set and returns `true`.
     * If the file *is* already being executed (recursion detected), it prompts the user to decide whether to continue recursively
     * and, if so, how many times.
     *
     * @param arg The name of the script file to be executed.
     * @return `true` if the script can be executed, `false` if recursion is detected and the user chooses not to continue.
     * @throws IOException if an I/O error occurs while reading user input.
     */
    public static boolean controlRecursion(String arg) throws IOException {
        if (!fileSaver.contains(arg)) {
            fileSaver.add(arg);
            return true;
        } else {
            if (RecursionCount == null) {
                detectRecursion(arg); // First time recursion is detected for this file
            } else {
                if (RecursionCount == 0) {
                    // Recursion limit reached
                    RecursionCount = null;
                    RecursionName = null;
                    return false;
                } else if (RecursionName.equals(arg)) {
                    RecursionCount--; // Decrement recursion count for the file causing recursion
                }
            }
        }
        return RecursionCount != null && RecursionCount != 0;  // Returns true if RecursionCount is initialized (recursion allowed) and RecursionCount > 0
    }

    /**
     * Prompts the user to decide whether to continue executing a script recursively.
     * If the user chooses to continue, it asks for the maximum number of recursive calls allowed.
     *
     * @param arg The name of the script file causing the recursion.
     * @throws IOException if an I/O error occurs while reading user input.
     */
    public static void detectRecursion(String arg) throws IOException {
        RainbowPrinter.printInfo("Recursion detected, do you want to continue?(Yes/No)");
        InputReader reader = new InputReader();
        reader.setReader();
        while (RecursionCount == null) {
            try {
                String input = reader.readLine();
                if (!InputChecker.checkString(input) || (!input.equals("Yes") && !input.equals("No"))) {
                    throw new WrongInputException();
                }
                if (input.equals("Yes")) {
                    RainbowPrinter.printInfo("How many times would you like to run recursively?(Enter a positive integer)");
                    input = reader.readLine();
                    if (!InputChecker.checkIntegerNumber(input) || Integer.parseInt(input) <= 0) {
                        throw new WrongInputException();
                    } else {
                        RecursionCount = Integer.parseInt(input) - 1;
                        RecursionName = arg;
                    }
                } else {
                    RecursionCount = 0; // User chooses not to continue
                }
            } catch (WrongInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
    }

    /**
     * Removes a filename from the {@code fileSaver} set when script execution is complete.
     *
     * @param arg The name of the script file to remove.
     */
    public static void dropFileName(String arg) {
        fileSaver.remove(arg);
    }
}