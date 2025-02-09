package iostream;


import command_utilities.CommandManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import main_objects.CollectionManager;
import packets.Request;

/**
 * {@code Receiver} class encapsulates the logic for executing commands.  It interacts with the
 * {@code CollectionManager} to perform operations on the main data collection.
 */
public class Receiver {

    /**
     * Constructs a new {@code Receiver} instance.
     */
    public Receiver() {}

    /**
     * Exits the application.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Displays help information for all available commands.
     */
    public void help() {
        CommandManager.getCommandCollection().forEach((name, command) -> {command.getCommandInfo();} );
    }

    /**
     * Displays the history of executed commands.
     */
    public void history() {
        CommandManager.getHistory().forEach(command -> {RainbowPrinter.printResult(command.getName());});
    }

    /**
     * Displays information about the collection.
     */
    public void info() {
        CollectionManager.info();
    }

    /**
     * Clears the collection.
     */
    public void clear() {
        CollectionManager.clear();
    }

    /**
     * Inserts a new element into the collection.
     *
     * @param request The request containing the data for the new element.
     * @throws UserException If there's an error with the user-provided data.
     */
    public void insert(Request request) throws UserException {
        CollectionManager.insert(request);
    }

    /**
     * Displays the elements in the collection.
     */
    public void show() {
        CollectionManager.show();
    }

    /**
     * Updates an existing element in the collection.
     *
     * @param request The request containing the data to update the element with key.
     * @throws UserException If there's an error with the user-provided data.
     */
    public void update(Request request) throws UserException {
        CollectionManager.update(request);
    }

    /**
     * Removes an element from the collection by its key.
     *
     * @param request The request containing the key of the element to remove.
     * @throws UserException If the specified key is invalid or an error occurs during removal.
     */
    public void remove_key(Request request) throws UserException {
        CollectionManager.remove_key(request);
    }

    /**
     * Replaces an element in the collection if it is lower than the specified element.
     *
     * @param request The request containing the element to compare against and potentially replace with.
     * @throws UserException If the specified element is invalid or an error occurs during replacement.
     */
    public void replace_if_lower(Request request) throws UserException {
        CollectionManager.replace_if_lower(request);
    }

    /**
     * Removes all elements from the collection that are greater than the specified element.
     *
     * @param request The request containing the element to compare against.
     * @throws UserException If an error occurs while removing elements.
     */
    public void remove_if_greater(Request request) throws UserException {
        CollectionManager.remove_if_greater(request);
    }

    /**
     * Prints the elements of the collection in ascending order.
     */
    public void print_ascending() {
        CollectionManager.print_ascending();
    }

    /**
     * Prints the elements of the collection in descending order.
     */
    public void print_descending() {
        CollectionManager.print_descending();
    }

    /**
     * Prints the semester enums of the elements in the collection in descending order.
     */
    public void print_field_descending_semester_num() {
        CollectionManager.print_field_descending_semester_enum();
    }

    /**
     * Executes a script from a file.  (The details of script execution are not implemented here.)
     */
    public void execute_script() {
        RainbowPrinter.printCondition("The file is being executed...");
    }

    /**
     * Saves the current state of the collection to a file.
     *
     * @throws LogException If an error occurs during the save operation.
     */
    public void save() throws LogException {
        CollectionManager.save();
    }

}