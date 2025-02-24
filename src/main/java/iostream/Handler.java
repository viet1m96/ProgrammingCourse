package iostream;

import command_utilities.CommandClassifier;
import command_utilities.CommandManager;

import enums.NeedInput;
import exceptions.LogUtil;
import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.InputFormatException;
import exceptions.user_exceptions.UserException;
import exceptions.user_exceptions.WrongCommandException;
import io_utilities.printers.RainbowPrinter;
import io_utilities.working_with_input.FormatChecker;
import io_utilities.working_with_input.InputChecker;
import io_utilities.working_with_input.InputPartition;
import io_utilities.working_with_input.InputReader;
import main_objects.CollectionManager;
import packets.Request;
import read_mode.ModeManager;


import java.io.IOException;

/**
 * The {@code Handler} class is the main entry point for processing user commands. It initializes and manages
 * various components, including input reading, command classification, execution, and collection management.
 * It interacts with the user through the console, providing instructions and feedback.
 */
public class Handler {
    private InputReader reader;
    private CollectionManager collectionManager;
    private FormatChecker formatChecker;
    private CommandManager commandManager;
    private CommandClassifier commandClassifier;
    private ModeManager modeManager;
    private Receiver receiver;
    private Invoker invoker;

    /**
     * Constructs a new {@code Handler} and prints welcome messages to the console.
     */
    public Handler() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'help' to read the instructions or type 'exit' to exit.");
    }

    /**
     * Initializes the necessary components for the application, including the {@link InputReader},
     * {@link FormatChecker}, {@link CommandClassifier}, {@link ModeManager}, {@link CommandManager},
     * {@link CollectionManager}, {@link Receiver}, and {@link Invoker}. It also uploads data from the
     * specified file.
     *
     * @param fileName The name of the file to upload data from.
     */
    public void prepare(String fileName) {
        try {
            reader = new InputReader();
            reader.setReader();
            formatChecker = new FormatChecker();
            formatChecker.init();
            commandClassifier = new CommandClassifier();
            commandClassifier.init();

            modeManager = new ModeManager();
            modeManager.init();
            commandManager = new CommandManager();
            commandManager.init();
            CollectionManager collectionManager = new CollectionManager(fileName);

            receiver = new Receiver(collectionManager, commandManager);
            invoker = new Invoker(commandManager, receiver);

            collectionManager.uploadData();
            RainbowPrinter.printInfo("The process of uploading data finished!");
        } catch (LogException e) {
            RainbowPrinter.printError(e.toString());
        }
    }

    /**
     * Runs the main loop of the application, reading user input, preprocessing it, processing it, and providing feedback.
     * The loop continues until the application is terminated.
     */
    public void run() {
        while (true) {
            try {
                String inp = reader.readLine();
                inp = inp.trim();
                preprocess(inp);
                process(inp);
                RainbowPrinter.printCondition(">>" + "The " + InputPartition.part1st(inp) + " command was successfully executed!");
            } catch (UserException | LogException e) {
                RainbowPrinter.printError(e.toString());
                RainbowPrinter.printCondition(">>" + "The command could not be executed!");
            } catch (IOException e) {
                LogUtil.logStackTrace(e);
            }
        }
    }

    /**
     * Preprocesses the user input by checking its format and throwing an {@link InputFormatException} if it is invalid.
     *
     * @param input The user input to be preprocessed.
     * @throws InputFormatException If the input format is invalid.
     */
    public void preprocess(String input) throws InputFormatException {
        if (!InputChecker.checkInput(input)) {
            throw new InputFormatException();
        }
        formatChecker.checkFormat(InputPartition.part1st(input), InputPartition.part2nd(input));

    }

    /**
     * Processes the user input by classifying the command, retrieving the necessary input mode, and invoking the command.
     *
     * @param input The user input to be processed.
     * @throws UserException If there is an issue with the user-provided data or command execution.
     * @throws LogException  If there is an issue during logging or command execution.
     */
    public void process(String input) throws UserException, LogException {
        String nameCommand = InputPartition.part1st(input);
        String argument = InputPartition.part2nd(input);
        NeedInput needInput = commandClassifier.getCommandClassifier(nameCommand);
        switch (needInput) {
            case NO_NEED_INPUT -> invoker.call(nameCommand, new Request(argument, null));
            case NEED_INPUT -> modeManager.call(invoker, nameCommand, argument);
        }
    }
}
