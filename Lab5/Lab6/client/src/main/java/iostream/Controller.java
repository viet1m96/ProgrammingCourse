package iostream;

import enums.NeedInput;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.InputFormatException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import command_lib.CommandClassifier;
import command_lib.FormatChecker;
import input_validators.InputChecker;
import input_validators.InputPartition;
import logging.watcher.LogUtil;
import network.CommandHistory;
import network.Transporter;
import printer_options.RainbowPrinter;
import read_mode.ModeManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private FormatChecker formatChecker;
    private CommandClassifier commandClassifier;
    private Transporter transporter;
    private Renderer renderer;
    private ModeManager modeManager;
    private CommandHistory commandHistory;
    public Controller() {}

    public void init() {
        commandHistory = new CommandHistory();
        modeManager = new ModeManager();
        modeManager.init();
        renderer = new Renderer();
        commandClassifier = new CommandClassifier();
        commandClassifier.init();
        formatChecker = new FormatChecker();
        transporter = new Transporter();
        formatChecker.init();
        LogUtil.logInfo("Client side was initialized.");
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String str = reader.readLine();
                str = str.trim();
                validateInput(str);
                commandHistory.addHistory(str);
                switch (str) {
                    case "history":
                        commandHistory.printHistory();
                        break;
                    case "exit":
                        LogUtil.logInfo("Client side was closed.");
                        System.exit(0);
                        break;
                    default:
                        processInput(str);
                }
            } catch (IOException e) {
                RainbowPrinter.printError("There was an error with reading input!");
                RainbowPrinter.printError(new LogException().toString());
                LogUtil.logTrace(e);
            } catch (UserException | LogException | NetworkException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
    }

    public void validateInput(String input) throws InputFormatException {
        if(!InputChecker.checkInput(input)) throw new InputFormatException();
        formatChecker.checkFormat(InputPartition.part1st(input), InputPartition.part2nd(input));
    }

    public void processInput(String input) throws UserException, LogException, NetworkException {
        String nameCommand = InputPartition.part1st(input);
        String argumentsCommand = InputPartition.part2nd(input);
        NeedInput needInput = commandClassifier.getCommandClassifier(nameCommand);
        switch (needInput) {
            case NO_NEED_INPUT -> {
                Response response = transporter.transport(new Request(nameCommand, argumentsCommand, null));
                renderer.printResponse(response);
            }
            case NEED_INPUT -> modeManager.call(renderer, transporter, nameCommand, argumentsCommand);
        }
    }
}
