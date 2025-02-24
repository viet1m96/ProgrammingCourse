package iostream;


import command_utilities.CommandManager;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.printers.RainbowPrinter;
import main_objects.CollectionManager;
import packets.Request;

/**
 * The {@code Receiver} class contains the actual implementation of the commands. It interacts with the
 * {@link CollectionManager} to perform operations on the collection and the {@link CommandManager} to access
 * command information.  It handles user requests by delegating to the appropriate methods of the CollectionManager.
 */
public class Receiver {

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    /**
     * Constructs a new {@code Receiver} with the specified {@link CollectionManager} and {@link CommandManager}.
     *
     * @param collectionManager The {@link CollectionManager} to interact with.
     * @param commandManager    The {@link CommandManager} to access command information.
     */
    public Receiver(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    /**
     * Exits the application.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Prints information about all available commands.
     */
    public void help() {
        commandManager.getCommandCollection().forEach((name, command) -> {
            command.getCommandInfo();
        });
    }

    /**
     * Prints the history of executed commands.
     */
    public void history() {
        commandManager.getHistory().forEach(command -> {
            RainbowPrinter.printResult(command.getName());
        });
    }

    /**
     * Prints information about the collection.
     */
    public void info() {
        collectionManager.info();
    }

    /**
     * Clears the collection.
     */
    public void clear() {
        collectionManager.clear();
    }

    /**
     * Inserts a new element into the collection.
     *
     * @param request The request containing the data for the new element.
     * @throws UserException If there is an issue with the user-provided data.
     */
    public void insert(Request request) throws UserException {
        collectionManager.insert(request);
    }

    /**
     * Shows all elements in the collection.
     */
    public void show() {
        collectionManager.show();
    }

    /**
     * Updates an existing element in the collection.
     *
     * @param request The request containing the data for the updated element.
     * @throws UserException If there is an issue with the user-provided data.
     */
    public void update(Request request) throws UserException {
        collectionManager.update(request);
    }

    /**
     * Removes an element from the collection by its key.
     *
     * @param request The request containing the key of the element to remove.
     * @throws UserException If there is an issue with the user-provided data.
     */
    public void remove_key(Request request) throws UserException {
        collectionManager.remove_key(request);
    }

    /**
     * Replaces an element in the collection if the new element is lower than the existing element.
     *
     * @param request The request containing the data for the new element.
     * @throws UserException If there is an issue with the user-provided data.
     */
    public void replace_if_lower(Request request) throws UserException {
        collectionManager.replace_if_lower(request);
    }

    /**
     * Removes all elements from the collection that are greater than the specified element.
     *
     * @param request The request containing the data for the element to compare against.
     * @throws UserException If there is an issue with the user-provided data.
     */
    public void remove_if_greater(Request request) throws UserException {
        collectionManager.remove_if_greater(request);
    }

    /**
     * Prints the elements of the collection in ascending order.
     */
    public void print_ascending() {
        collectionManager.print_ascending();
    }

    /**
     * Prints the elements of the collection in descending order.
     */
    public void print_descending() {
        collectionManager.print_descending();
    }

    /**
     * Prints the semester numbers of the elements in the collection in descending order.
     */
    public void print_field_descending_semester_enum() {
        collectionManager.print_field_descending_semester_enum();
    }

    /**
     * Executes a script from a file.
     */
    public void execute_script() {
        RainbowPrinter.printCondition("The file is being executed...");
    }

    /**
     * Saves the collection to a file.
     * @throws LogException if there is an error during the saving process.
     */
    public void save() throws LogException {
        collectionManager.save();
    }
}
