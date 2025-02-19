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

public class Handler {
    private InputReader reader;
    private CollectionManager collectionManager;
    private FormatChecker formatChecker;
    private CommandManager commandManager;
    private CommandClassifier commandClassifier;
    private ModeManager modeManager;
    private Receiver receiver;
    private Invoker invoker;

    public Handler() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'help' to read the instructions or type 'exit' to exit.");
    }

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

    public void run() {
        while(true) {
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

    public void preprocess(String input) throws InputFormatException {
        if(!InputChecker.checkInput(input)) {
            throw new InputFormatException();
        }
        formatChecker.checkFormat(InputPartition.part1st(input), InputPartition.part2nd(input));

    }

    public void process(String input) throws UserException, LogException {
        String nameCommand = InputPartition.part1st(input);
        String argument = InputPartition.part2nd(input);
        NeedInput needInput = commandClassifier.getCommandClassifier(nameCommand);
        switch (needInput) {
            case NO_NEED_INPUT -> invoker.call(nameCommand,  new Request(argument, null));
            case NEED_INPUT -> modeManager.call(invoker, nameCommand, argument);
        }
    }
}