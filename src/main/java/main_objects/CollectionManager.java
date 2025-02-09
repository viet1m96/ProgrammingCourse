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


/**
 * The {@code CollectionManager} class manages a collection of {@link StudyGroup} objects stored in a {@link LinkedHashMap}.
 * It provides methods for initializing the collection, performing operations such as adding, updating, removing,
 * and displaying elements, as well as saving and loading the collection from a CSV file.
 */
public class CollectionManager {

    private static LinkedHashMap<String, StudyGroup> collection = new LinkedHashMap<>();
    private static final String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    private static String fileName;

    /**
     * Constructs a new {@code CollectionManager} object.
     */
    public CollectionManager() {
    }

    /**
     * Initializes the {@code CollectionManager} with the specified file name for data persistence.
     *
     * @param name The name of the file to use for saving and loading the collection data.
     */
    public static void init(String name) {
        fileName = name;
    }

    /**
     * Displays information about the collection, including its type, initialization time, and number of elements.
     */
    public static void info() {
        RainbowPrinter.printResult("Type of DS: LinkedHashMap");
        RainbowPrinter.printResult("Moment of Initialization: " + startTime);
        RainbowPrinter.printResult("Number of elements: " + collection.size());
    }

    /**
     * Clears all elements from the collection.
     */
    public static void clear() {
        collection.clear();
    }

    /**
     * Inserts a new {@link StudyGroup} into the collection with the specified key.
     *
     * @param request The {@link Request} object containing the key and the {@link StudyGroup} to insert.
     * @throws UserException If the key is invalid, already taken, or if there are other input errors.
     */
    public static void insert(Request request) throws UserException {
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
     * Displays all elements in the collection.
     * If the collection is empty, a message is printed to indicate this.
     */
    public static void show() {
        if (collection.isEmpty()) {
            RainbowPrinter.printInfo("The collection is empty.");
        } else {
            collection.values().forEach(StudyGroup::printEverything);
        }
    }


    /**
     * Updates an existing {@link StudyGroup} in the collection with the specified key.
     *
     * @param request The {@link Request} object containing the key and the updated {@link StudyGroup}.
     * @throws UserException If the key is invalid or does not exist in the collection.
     */
    public static void update(Request request) throws UserException {
        String key = request.getArgument();
        if (key == null || key.isEmpty() || !InputChecker.checkIntegerNumber(key)) {
            throw new WrongKeyException();
        }
        if (!collection.containsKey(key)) {
            throw new WrongKeyException();
        } else {
            collection.remove(key);
            collection.put(key, request.getStudyGroup());
        }
    }

    /**
     * Removes the {@link StudyGroup} with the specified key from the collection.
     *
     * @param request The {@link Request} object containing the key to remove.
     * @throws UserException If the key is invalid or does not exist in the collection.
     */
    public static void remove_key(Request request) throws UserException {
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
     * Replaces the {@link StudyGroup} with the specified key if the new {@link StudyGroup} is lower (based on the {@link StudyGroup#compareTo(StudyGroup)} method).
     *
     * @param request The {@link Request} object containing the key and the new {@link StudyGroup}.
     * @throws UserException If the key is invalid or does not exist in the collection.
     */
    public static void replace_if_lower(Request request) throws UserException {
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
     * Removes all {@link StudyGroup} objects from the collection that are greater than the {@link StudyGroup} in the request.
     *
     * @param request The {@link Request} object containing the {@link StudyGroup} to compare against.
     * @throws UserException If the key is invalid or does not exist in the collection.
     */
    public static void remove_if_greater(Request request) throws UserException {
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
     * Prints the elements of the collection in ascending order, based on the natural ordering of {@link StudyGroup} objects.
     */
    public static void print_ascending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result);
        result.forEach(StudyGroup::printEverything);
    }

    /**
     * Prints the elements of the collection in descending order, based on the natural ordering of {@link StudyGroup} objects.
     */
    public static void print_descending() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Collections.reverseOrder());
        result.forEach(StudyGroup::printEverything);
    }

    /**
     * Prints the {@link enums.Semester} of each {@link StudyGroup} in descending order.
     */
    public static void print_field_descending_semester_enum() {
        List<StudyGroup> result = new ArrayList<>(collection.values().stream().toList());
        Collections.sort(result, Comparator.comparing(StudyGroup::getSemesterEnum));
        Collections.reverse(result);
        result.forEach(studyGroup -> RainbowPrinter.printResult(studyGroup.getSemesterEnum()));
    }

    /**
     * Saves the collection to a CSV file.
     *
     * @throws LogException If an I/O error occurs while saving the collection to the file.
     */
    public static void save() throws LogException {
        try {
            CSVWriter writer = new CSVWriter(fileName);
            for (String key : collection.keySet()) {
                writer.writeLines(collection.get(key).getAllFields());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogException();
        }
    }

    /**
     * Uploads data from a CSV file into the collection.
     *
     * @throws WrongUploadingDataException If the data in the file is invalid or if a key is already taken.
     * @throws LogException               If an I/O error occurs while reading the file.
     */
    public static void uploadData() throws WrongUploadingDataException, LogException {
        try {
            CSVReader reader = new CSVReader(fileName);
            List<StudyGroup> groups = reader.loadObjList();
            for (StudyGroup studyGroup : groups) {
                String key = studyGroup.getId().toString();
                if (collection.containsKey(key)) {
                    throw new KeyTakenException();
                } else {
                    collection.put(key, studyGroup);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogException();
        } catch (KeyTakenException e) {
            RainbowPrinter.printError(e.toString());
            throw new WrongUploadingDataException();
        }
    }
}