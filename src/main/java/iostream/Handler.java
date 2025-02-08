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

public class Handler {
    private InputReader reader;
    public Handler() {
        RainbowPrinter.printInfo("Hello! Welcome to my Application!");
        RainbowPrinter.printInfo("Please type 'help' to read the instructions or type 'exit' to exit.");
        RainbowPrinter.printCondition("(All of the following commands must be typed without whitespace at the beginning and between 2 words there is only one whitespace).");
    }

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

    public void preprocess(String input) throws WrongInputException, WrongCommandException {
        if(!InputChecker.checkInput(input)) {throw new WrongInputException();}
        if(!CommandManager.isCommand(InputPartition.part1st(input.toLowerCase()))) throw new WrongCommandException();
    }

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
