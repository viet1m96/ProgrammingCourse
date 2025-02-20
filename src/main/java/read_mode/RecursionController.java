package read_mode;


import exceptions.LogUtil;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;


import java.io.IOException;
import java.util.*;


public class RecursionController {
    private LinkedHashMap<String, Integer> cyclePath = new LinkedHashMap<>();

    public LinkedHashMap<String, String> getNewTrace(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
       List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
       List<String> needRemovingVertices = new ArrayList<>();
       for(int i = 1; i < cycle.size() - 1; i++) {
           needRemovingVertices.add(cycle.get(i));
       }
       LinkedHashMap<String, String> newTrace = new LinkedHashMap<>();
       currentTrace.forEach((key, value) -> {
           if(!needRemovingVertices.contains(key)) {
               newTrace.put(key, value);
           }
       });
       return newTrace;
    }

    public boolean stopOrNot(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        if(cyclePath.get(trace) == 0){
            cyclePath.remove(trace);
            return true;
        } else {
            cyclePath.put(trace, cyclePath.get(trace) - 1);
        }
        return false;
    }

    private List<String> getCyclePath(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> trace = new ArrayList<>();
        String currentInTrace = currentFile;
        while(currentInTrace!= null && !currentInTrace.equals(nextFile)) {
            trace.add(currentInTrace);
            currentInTrace = currentTrace.get(currentInTrace);
        }
        trace.add(currentInTrace);
        Collections.reverse(trace);
        trace.add(nextFile);

        return trace;
    }

    public boolean isRecursion(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        return currentTrace.containsKey(nextFile);
    }


    public boolean isFirstTimeDetected(LinkedHashMap<String, String> currentTrace, String currentFile, String nextFile) {
        List<String> cycle = getCyclePath(currentTrace, currentFile, nextFile);
        String trace = String.join("\n", cycle);
        return !cyclePath.containsKey(trace);
    }

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