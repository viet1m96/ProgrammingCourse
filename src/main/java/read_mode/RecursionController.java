package read_mode;


import exceptions.LogUtil;
import exceptions.user_exceptions.WrongInputException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;


import java.io.IOException;
import java.util.*;


public class RecursionController {
    private LinkedHashMap<String, String> edge;
    private LinkedHashMap<String, Integer> cyclePath;
    private LinkedHashMap<String, Integer> timeOfPresent;

    public RecursionController() {
        edge = new LinkedHashMap<>();
        cyclePath = new LinkedHashMap<>();
        timeOfPresent = new LinkedHashMap<>();
    }

    public boolean controlRecursion(String nextFile, String currentFile) {
//        if (nextFile.equals("/home/cun/IdeaProjects/Lab5/src/main/java/data_files/test.txt")) {
//            edge.forEach((key, value) -> {
//                System.out.println("Key: " + key + " Value: " + value);
//            });
//        }
        if(timeOfPresent.isEmpty()) {
            timeOfPresent.put(currentFile, 1);
        }
        if(timeOfPresent.containsKey(nextFile)) {
            timeOfPresent.put(nextFile, timeOfPresent.get(nextFile) + 1);
        } else {
            timeOfPresent.put(nextFile, 1);
        }
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
//                    edge.forEach((key, value) -> {
//                        System.out.println("Key: " + key + " Value: " + value);
//                    });
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
                            RainbowPrinter.printInfo("How many times do you want to read recursively?(Enter a positive integer)");
                            count = inputReader.readLine();
                            if(!InputChecker.checkIntegerNumber(count) || Integer.parseInt(count) <= 0) throw new WrongInputException();
                            cyclePath.put(trace, Integer.parseInt(count) - 1);
                        } catch (IOException e) {
                            LogUtil.logStackTrace(e);
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


    public int timeOP(String fileName) {
        return timeOfPresent.get(fileName);
    }

    public void setTimeOP(String fileName, Integer time) {
        timeOfPresent.put(fileName, timeOfPresent.get(fileName) + time);
    }

    public void reset(String fileName) {
        edge.remove(fileName);
        timeOfPresent.remove(fileName);
    }

}