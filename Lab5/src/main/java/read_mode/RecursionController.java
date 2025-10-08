package read_mode;


import exceptions.LogUtil;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;


import java.io.IOException;
import java.util.*;


/**
 * The {@code RecursionController} class manages recursion detection and handling in the context of file processing.
 * It helps prevent infinite loops when executing scripts that call each other recursively.
 */
public class RecursionController {
    private final LinkedHashMap<String, Integer> cyclePath = new LinkedHashMap<>();

    /**
     * Creates a new trace (a map of file paths) by removing the files that form a cycle.
     * This helps break the cycle and prevent infinite loops.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return A new trace with the files forming a cycle removed.
     */
    public LinkedHashMap<String, String> getNewTrace(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        List<String> needRemovingVertices = new ArrayList<>();
        for (int i = 1; i < cycle.size() - 1; i++) {
            needRemovingVertices.add(cycle.get(i));
        }
        LinkedHashMap<String, String> newTrace = new LinkedHashMap<>();
        currentTrace.forEach((key, value) -> {
            if (!needRemovingVertices.contains(key)) {
                newTrace.put(key, value);
            }
        });
        return newTrace;
    }

    /**
     * Determines whether to stop processing a file in a recursive cycle based on a counter.
     * If the counter for the cycle is 0, the processing is stopped. Otherwise, the counter is decremented.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return {@code true} if the processing should be stopped, {@code false} otherwise.
     */
    public boolean stopOrNot(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        if (cyclePath.get(trace) == 0) {
            cyclePath.remove(trace);
            return true;
        } else {
            cyclePath.put(trace, cyclePath.get(trace) - 1);
        }
        return false;
    }

    /**
     * Constructs a list representing the cycle path in the current trace.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return A list of file paths representing the cycle.
     */
    private List<String> getCyclePath(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> trace = new ArrayList<>();
        String currentInTrace = currentFile;
        while (currentInTrace != null && !currentInTrace.equals(nextFile)) {
            trace.add(currentInTrace);
            currentInTrace = currentTrace.get(currentInTrace);
        }
        trace.add(currentInTrace);
        Collections.reverse(trace);
        trace.add(nextFile);

        return trace;
    }

    /**
     * Checks if a file is already present in the current trace, indicating a potential recursion.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return {@code true} if the file is already in the trace, {@code false} otherwise.
     */
    public boolean isRecursion(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        return currentTrace.containsKey(nextFile);
    }


    /**
     * Checks if a recursive cycle is detected for the first time.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return {@code true} if the cycle is detected for the first time, {@code false} otherwise.
     */
    public boolean isFirstTimeDetected(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        return !cyclePath.containsKey(trace);
    }

    /**
     * Asks the user how to handle a recursive cycle: either to read recursively a specified number of times or to stop.
     *
     * @param currentTrace The current trace of file paths.
     * @param currentFile  The currently processed file.
     * @param nextFile     The file that is about to be processed.
     * @return The number of times to read recursively, or {@code null} if the user chooses to stop.
     */
    public Integer askAction(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        RainbowPrinter.printCondition("The cycle:");
        RainbowPrinter.printInfo(trace);
        String count = "";
        if (InputChecker.yesOrNo("read recursively", "Recursion detected")) {
            InputReader inputReader = new InputReader();
            inputReader.setReader();
            while (count.isEmpty()) {
                try {
                    RainbowPrinter.printInfo("How many times do you want to read recursively?(Enter a positive integer)");
                    count = inputReader.readLine();
                    if (!InputChecker.checkIntegerNumber(count) || Integer.parseInt(count) <= 0)
                        throw new WrongInputException();
                    cyclePath.put(trace, Integer.parseInt(count) - 1);
                } catch (IOException e) {
                    LogUtil.logStackTrace(e);
                } catch (WrongInputException e) {
                    RainbowPrinter.printError(e.toString());
                    count = "";
                }
            }
        } else {
            return null;
        }
        return Integer.parseInt(count);
    }


}
