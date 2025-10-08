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

/**
 * The {@code CollectionManager} class manages a collection of {@link StudyGroup} objects stored in a
 * {@link LinkedHashMap}. It provides methods for adding, removing, updating, and retrieving elements from
 * the collection. It also handles saving and loading the collection to/from a CSV file.
 */
public class CollectionManager {

    private final LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private final String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    private final String fileName;

    /**
     * Constructs a new {@code CollectionManager} with the specified file name.
     *
     * @param fileName The name of the file to save and load the collection from.
     */
    public CollectionManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Prints information about the collection, including the type of data structure, the moment of initialization,
     * and the number of elements.
     */
    public void info() {
        RainbowPrinter.printResult("Type of DS: LinkedHashMap");
        RainbowPrinter.printResult("Moment of Initialization: " + startTime);
        RainbowPrinter.printResult("Number of elements: " + collection.size());
    }

    /**
     * Clears the collection, removing all elements.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Inserts a new {@link StudyGroup} into the collection with the specified key.
     *
     * @param request The request containing the key and the {@link StudyGroup} to insert.
     * @throws UserException If the key is invalid, already taken, or if there is an issue with the user-provided data.
     */
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

    /**
     * Prints all elements in the collection.
     */
    public void show() {
        if (collection.isEmpty()) {
            RainbowPrinter.printInfo("The collection is empty.");
        } else {
            collection.values().forEach(StudyGroup::printEverything);
        }
    }

    /**
     * Updates an existing {@link StudyGroup} in the collection with the specified key.
     *
     * @param request The request containing the key and the updated {@link StudyGroup}.
     * @throws UserException If the key is invalid, not found, or if there is an issue with the user-provided data.
     */
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
            collection.put(key, studyGroup);
        }
    }

    /**
     * Removes an element from the collection with the specified key.
     *
     * @param request The request containing the key of the element to remove.
     * @throws UserException If the key is invalid or not found.
     */
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

    /**
     * Replaces an element in the collection with the specified key if the new element is lower than the existing element.
     *
     * @param request The request containing the key and the new {@link StudyGroup}.
     * @throws UserException If the key is invalid, not found, or if there is an issue with the user-provided data.
     */
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

    /**
     * Removes all elements from the collection that are greater than the specified {@link StudyGroup}.
     *
     * @param request The request containing the {@link StudyGroup} to compare against.
     * @throws UserException If there is an issue with the user-provided data.
     */
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

    /**
     * Prints the elements of the collection in ascending order.
     */
    public void print_ascending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result);
        result.forEach(StudyGroup::printEverything);
    }

    /**
     * Prints the elements of the collection in descending order.
     */
    public void print_descending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Collections.reverseOrder());
        result.forEach(StudyGroup::printEverything);
    }

    /**
     * Prints the semester enums of the elements in the collection in descending order.
     */
    public void print_field_descending_semester_enum() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Comparator.comparing(StudyGroup::getSemesterEnum));
        Collections.reverse(result);
        result.forEach(studyGroup -> RainbowPrinter.printResult(studyGroup.getSemesterEnum()));
    }

    /**
     * Saves the collection to a CSV file.
     *
     * @throws LogException If there is an I/O error during the saving process.
     */
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

    /**
     * Uploads data from a CSV file and populates the collection.
     *
     * @throws LogException If there is an I/O error during the uploading process.
     */
    public void uploadData() throws LogException {
        try {
            CSVReader reader = new CSVReader();
            InputReader inputReader = new InputReader();
            inputReader.setReader(fileName);
            String str;
            int counter = 1;
            while ((str = inputReader.readLine()) != null && !str.isEmpty()) {
                try {
                    StudyGroup studyGroup = reader.loadObj(str);
                    String key = studyGroup.getId().toString();
                    if (collection.containsKey(key)) {
                        throw new KeyTakenException();
                    } else {
                        collection.put(key, studyGroup);
                    }
                } catch (UserException e) {
                    RainbowPrinter.printCondition("-----");
                    RainbowPrinter.printError(e.toString());
                    RainbowPrinter.printError("Can not load the information of the group on the" + " line " + String.valueOf(counter));
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
