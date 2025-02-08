package main_objects;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.*;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_csv_file.CSVReader;
import io_utilities.working_with_csv_file.CSVWriter;
import io_utilities.working_with_input.InputChecker;
import packets.Request;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CollectionManager {
    private static LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private static final String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    private static String fileName;
    public CollectionManager() {}
    public static void init(String name) {
        fileName = name;
    }

    public static void info() {
        RainbowPrinter.printResult("Type of DS: LinkedHashMap");
        RainbowPrinter.printResult("Moment of Initialization: " + startTime);
        RainbowPrinter.printResult("Number of elements: " + collection.size());
    }

    public static void clear() {
        collection.clear();
    }

    public static void insert(Request request) throws UserException {
        String key = request.getArgument();
        if(key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) throw new WrongKeyException();
        if(collection.containsKey(key)) {
            throw new KeyTakenException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setId(Integer.parseInt(key));
            collection.put(key, studyGroup);
        }
    }

    public static void show() {
        if(collection.isEmpty()) {
            RainbowPrinter.printInfo("The collection is empty.");
        } else {
            collection.values().forEach(StudyGroup::printEverything);
        }
    }



    public static void update(Request request) throws UserException {
        String key = request.getArgument();
        if(key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) throw new WrongKeyException();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            collection.remove(key);
            collection.put(key, request.getStudyGroup());
        }
    }

    public static void remove_key(Request request) throws UserException {
        String key = request.getArgument();
        if(!InputChecker.checkIntegerNumber(key)) throw new WrongInputException();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            collection.remove(key);
            RainbowPrinter.printInfo("Removed key: " + key);
        }
    }

    public static void replace_if_lower(Request request) throws UserException {
        String key = request.getArgument();
        if(key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) throw new WrongKeyException();
        if(!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            StudyGroup studyGroup = request.getStudyGroup();
            if(collection.get(key).compareTo(studyGroup) < 0) {
                collection.remove(key);
                collection.put(key, studyGroup);
                RainbowPrinter.printResult("Replaced the key: " + key);
            } else {
                RainbowPrinter.printResult("The key: " + key + " was not replaced.");
            }
        }
    }

    public static void remove_if_greater(Request request) throws UserException {
        if(request.getArgument() != null || !request.getArgument().isEmpty()) throw new WrongInputException();
        StudyGroup studyGroup = request.getStudyGroup();
        Iterator<StudyGroup> iterator = collection.values().iterator();
        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.compareTo(studyGroup) < 0) {
                String key = group.getId().toString();
                RainbowPrinter.printResult("Removed the key: " + key);
                iterator.remove();
            }
        }
    }

    public static void print_ascending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result);
        result.forEach(StudyGroup::printEverything);
    }

    public static void print_descending()  {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Collections.reverseOrder());
        result.forEach(StudyGroup::printEverything);
    }

    public static void print_field_descending_semester_enum() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Comparator.comparing(StudyGroup::getSemesterEnum));
        Collections.reverse(result);
        result.forEach(studyGroup -> RainbowPrinter.printResult(studyGroup.getSemesterEnum()));
    }

    public static void save() throws LogException  {
        try {
            CSVWriter writer = new CSVWriter(fileName);
            for(String key : collection.keySet()) {
                writer.writeLines(collection.get(key).getAllFields());
            }
        } catch(IOException e) {
            e.printStackTrace();
            throw new LogException();
        }
    }

    public static void uploadData() throws WrongUploadingDataException, LogException {
        try {
            CSVReader reader = new CSVReader(fileName);
            List<StudyGroup> groups = reader.loadObjList();
            for(StudyGroup studyGroup : groups) {
                String key = studyGroup.getId().toString();
                if(collection.containsKey(key)) {
                    throw new KeyTakenException();
                } else {
                    collection.put(key, studyGroup);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            throw new LogException();
        } catch(KeyTakenException e) {
            RainbowPrinter.printError(e.toString());
            throw new WrongUploadingDataException();
        }
    }
}

