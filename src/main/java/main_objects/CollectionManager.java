package main_objects;

import exceptions.LogUtil;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.*;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_csv_file.CSVReader;
import io_utilities.working_with_csv_file.CSVWriter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputReader;
import packets.Request;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {

    private LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    private String fileName;

    public CollectionManager(String fileName) {
        this.fileName = fileName;
    }

    public void info() {
        RainbowPrinter.printResult("Type of DS: LinkedHashMap");
        RainbowPrinter.printResult("Moment of Initialization: " + startTime);
        RainbowPrinter.printResult("Number of elements: " + collection.size());
    }

    public void clear() {
        collection.clear();
    }

    public void insert(Request request) throws UserException {
        String key = request.getArgument();
        if (key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) {
            throw new WrongKeyException();
        }
        if (collection.containsKey(key)) {
            throw new KeyTakenException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setId(Integer.parseInt(key));
            collection.put(key, studyGroup);
        }
    }

    public void show() {
        if (collection.isEmpty()) {
            RainbowPrinter.printInfo("The collection is empty.");
        } else {
            collection.values().forEach(StudyGroup::printEverything);
        }
    }

    public void update(Request request) throws UserException {
        String key = request.getArgument();
        if (key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) {
            throw new WrongKeyException();
        }
        if (!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setId(Integer.parseInt(key));
            collection.remove(key);
            collection.put(key, request.getStudyGroup());
        }
    }

    public void remove_key(Request request) throws UserException {
        String key = request.getArgument();
        if (!InputChecker.checkIntegerNumber(key)) {
            throw new WrongInputException();
        }
        if (!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            collection.remove(key);
            RainbowPrinter.printInfo("Removed key: " + key);
        }
    }

    public void replace_if_lower(Request request) throws UserException {
        String key = request.getArgument();
        if (key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) {
            throw new WrongKeyException();
        }
        if (!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            if (collection.get(key).compareTo(studyGroup) < 0) {
                collection.remove(key);
                collection.put(key, studyGroup);
                RainbowPrinter.printResult("Replaced the key: " + key);
            } else {
                RainbowPrinter.printResult("The key: " + key + " was not replaced.");
            }
        }
    }

    public void remove_if_greater(Request request) throws UserException {
        StudyGroup studyGroup = request.getStudyGroup();
        Iterator<StudyGroup> iterator = collection.values().iterator();
        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.compareTo(studyGroup) < 0) {
                String key = group.getId().toString();
                RainbowPrinter.printResult("Removed the key: " + key);
                iterator.remove();
            }
            if (!iterator.hasNext() && studyGroup.compareTo(group) < 0) {
                String key = group.getId().toString();
                collection.remove(key);
                RainbowPrinter.printResult("Removed the key: " + key);
                break;
            }
        }
    }

    public void print_ascending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result);
        result.forEach(StudyGroup::printEverything);
    }

    public void print_descending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Collections.reverseOrder());
        result.forEach(StudyGroup::printEverything);
    }

    public void print_field_descending_semester_enum() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Comparator.comparing(StudyGroup::getSemesterEnum));
        Collections.reverse(result);
        result.forEach(studyGroup -> RainbowPrinter.printResult(studyGroup.getSemesterEnum()));
    }

    public void save() throws LogException {
        try {
            CSVWriter writer = new CSVWriter(fileName);
            for (String key : collection.keySet()) {
                writer.writeLines(collection.get(key).getAllFields());
            }
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
            throw new LogException();
        }
    }

    public void uploadData() throws LogException {
        try {
            CSVReader reader = new CSVReader();
            InputReader inputReader = new InputReader();
            inputReader.setReader(fileName);
            String str;
            int counter = 1;
            while((str = inputReader.readLine()) != null && !str.isEmpty()) {
                try {
                    StudyGroup studyGroup = reader.loadObj(str);
                    String key = studyGroup.getId().toString();
                    if(collection.containsKey(key)) {
                        throw new KeyTakenException();
                    } else {
                        collection.put(key, studyGroup);
                    }
                } catch(UserException e) {
                    RainbowPrinter.printCondition("-----");
                    RainbowPrinter.printError(e.toString());
                    RainbowPrinter.printError("Can not load the information of the group on the"  + " line " + String.valueOf(counter));
                    RainbowPrinter.printCondition("-----");
                }
                counter++;

            }
        } catch (IOException e) {
            LogUtil.logStackTrace(e);
            throw new LogException();
        }
        RainbowPrinter.printInfo("Loaded successfully " + collection.size() + " groups into the file.");
    }
}