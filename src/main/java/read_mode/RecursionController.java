package read_mode;


import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;

import java.io.IOException;
import java.util.*;


public class RecursionController {
    private LinkedHashMap<String, String> edge;
    private LinkedHashMap<String, Integer> cyclePath;

    public RecursionController() {
        edge = new LinkedHashMap<>();
        cyclePath = new LinkedHashMap<>();
    }

    public boolean controlRecursion(String nextFile, String currentFile) {
        if(!edge.containsKey(nextFile)) {
            if(edge.isEmpty()) {
                edge.put(currentFile, null);
            }
            edge.put(nextFile, currentFile);
            return true;
        } else {
            String trace = String.join("\n", traceTheCycle(nextFile, currentFile));
            if(cyclePath.containsKey(trace)) {
                if(cyclePath.get(trace) == 0) {
                    cyclePath.remove(trace);
                    return false;
                }
                cyclePath.put(trace, cyclePath.get(trace) - 1);
                removeCycle(nextFile, currentFile);
                return true;
            } else {
                RainbowPrinter.printCondition("The cycle:");
                RainbowPrinter.printInfo(trace);
                if(InputChecker.yesOrNo("read recursively", "Recursion was detected")) {
                    InputReader inputReader = new InputReader();
                    inputReader.setReader();
                    String count = "";
                    while(count.isEmpty()) {
                        try {
                            RainbowPrinter.printInfo("How many times you have read recursively?(Enter a positive integer)");
                            count = inputReader.readLine();
                            if(!InputChecker.checkIntegerNumber(count) || Integer.parseInt(count) <= 0) throw new WrongInputException();
                            cyclePath.put(trace, Integer.parseInt(count) - 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (WrongInputException e) {
                            RainbowPrinter.printError(e.toString());
                            count = "";
                        }
                    }
                    removeCycle(nextFile, currentFile);
                    return true;
                } else {
                    return false;
                }

            }
        }
    }

    private List<String> traceTheCycle(String nextFile, String currentFile) {
        List<String> trace = new ArrayList<>();
        String currentInTrace = currentFile;
        while(currentInTrace!= null && !currentInTrace.equals(nextFile)) {
            trace.add(currentInTrace);
            currentInTrace = edge.get(currentInTrace);
        }
        trace.add(currentInTrace);
        Collections.reverse(trace);
        trace.add(nextFile);
        return trace;
    }

    private void removeCycle(String nextFile, String currentFile) {
        List<String> trace = traceTheCycle(nextFile, currentFile);
        trace.forEach(edge::remove);
    }



}