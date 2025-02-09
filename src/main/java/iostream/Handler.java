package iostream;

import command_utilities.CommandClassifier;
import command_utilities.CommandManager;

import enums.NeedInput;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import exceptions.user_exceptions.WrongInputException;
import exceptions.user_exceptions.WrongUploadingDataException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputPartition;
import io_utilities.working_with_input.InputReader;
import main_objects.CollectionManager;
import packets.Request;
import read_mode.ModeManager;


import java.io.IOException;

/**
 * {@code Handler} manages the interaction with the user, processing commands and handling input.
 */
public class Handler {
    private InputReader reader;

    /**
     * Constructs a {@code Handler} and displays initial welcome messages to the user.
     */
    public Handler() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'help' to read the instructions or type 'exit' to exit.");
        RainbowPrinter.printCondition("(All of the following commands must be typed without whitespace at the beginning and between 2 words there is only one whitespace).");
    }

    /**
     * Prepares the application by initializing necessary components and loading data from a file.
     *
     * @param fileName The name of the file to load data from.
     */
    public void prepare(String fileName) {
        try {
            reader = new InputReader();
            reader.setReader();
            Invoker.init();
            CommandClassifier.init();
            ModeManager.init();
            CollectionManager.init(fileName);
            CollectionManager.uploadData();
        } catch(WrongUploadingDataException | LogException e) {
            RainbowPrinter.printError(e.toString());
        }
        RainbowPrinter.printInfo("Data from file was successfully uploaded!");
    }

    /**
     * Runs the main loop of the application, continuously reading, preprocessing, and processing user commands.
     */
    public void run() {
        while(true) {
            try {
                String inp = reader.readLine();
                preprocess(inp);
                process(inp);
                RainbowPrinter.printCondition(">>" +"The " + InputPartition.part1st(inp) + " command was successfully executed!");
            } catch(UserException | LogException e) {
                RainbowPrinter.printError(e.toString());
                RainbowPrinter.printCondition(">>" +"The command could not be executed!");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Preprocesses the user input to ensure it is valid and contains a recognized command.
     *
     * @param input The user input string.
     * @throws WrongInputException If the input is invalid.
     * @throws WrongCommandException If the command is not recognized.
     */
    public void preprocess(String input) throws WrongInputException, WrongCommandException {
        if(!InputChecker.checkInput(input)) {throw new WrongInputException();}
        if(!CommandManager.isCommand(InputPartition.part1st(input.toLowerCase()))) throw new WrongCommandException();
    }

    /**
     * Processes the user input, executing the corresponding command based on whether it requires additional input.
     *
     * @param input The user input string.
     * @throws UserException If an error occurs during command execution related to user data.
     * @throws LogException If an error occurs during command execution related to logging.
     */
    public void process(String input) throws UserException, LogException {
        String nameCommand = InputPartition.part1st(input);
        String argument = InputPartition.part2nd(input);
        NeedInput needInput = CommandClassifier.getCommandClassifier(nameCommand);
        switch (needInput) {
            case NO_NEED_INPUT -> Invoker.call(nameCommand,  new Request(argument, null));
            case NEED_INPUT -> ModeManager.call(nameCommand, argument);
        }
    }
}