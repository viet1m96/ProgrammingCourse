package iostream;

import authorization_lib.Account;
import authorization_lib.AuthorizationChecker;
import command_lib.ClientQuickCommand;
import command_lib.CommandClassifier;
import command_lib.FormatChecker;
import enums.NeedInput;
import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.InputFormatException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
import input_validators.InputChecker;
import input_validators.InputPartition;
import logging.LogUtil;
import network.CommandHistory;
import network.ClientTransporter;
import printer_options.RainbowPrinter;
import read_mode.ModeManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Account account;
    private AuthorizationChecker authorizationChecker;
    private FormatChecker formatChecker;
    private CommandClassifier commandClassifier;
    private ClientTransporter clientTransporter;
    private Renderer renderer;
    private ModeManager modeManager;
    private CommandHistory commandHistory;
    private ClientQuickCommand quickCommand;

    public Controller() {
    }

    public void init() {
        account = new Account();
        authorizationChecker = new AuthorizationChecker();
        authorizationChecker.init();
        commandHistory = new CommandHistory();
        modeManager = new ModeManager();
        modeManager.init();
        renderer = new Renderer();
        commandClassifier = new CommandClassifier();
        commandClassifier.init();
        formatChecker = new FormatChecker();
        clientTransporter = new ClientTransporter();
        quickCommand = new ClientQuickCommand();
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
                        quickCommand.history(commandHistory);
                        break;
                    case "exit":
                        quickCommand.exit();
                        break;
                    case "sign_out":
                        quickCommand.signOut(this.account);
                        break;
                    default:
                        if (authorizationChecker.checkAuthorization(account, clientTransporter, renderer, str)) {
                            processInput(str);
                        }
                        break;
                }
            } catch (IOException e) {
                RainbowPrinter.printError("There was an error with input reading!");
                LogUtil.logTrace(e);
            } catch (UserException | NetworkException e) {
                RainbowPrinter.printError(e.toString());
            } catch (LogException e) {
                RainbowPrinter.printError(e.getMessage());
                RainbowPrinter.printError(e.toString());
            }
        }
    }

    public void validateInput(String input) throws InputFormatException {
        if (!InputChecker.checkInput(input)) throw new InputFormatException();
        formatChecker.checkFormat(InputPartition.part1st(input), InputPartition.part2nd(input));
    }


    public void processInput(String input) throws UserException, LogException, NetworkException {
        String nameCommand = InputPartition.part1st(input);
        String argumentsCommand = InputPartition.part2nd(input);
        List<String> arguments = new ArrayList<>();
        arguments.add(argumentsCommand);
        NeedInput needInput = commandClassifier.getCommandClassifier(nameCommand);
        switch (needInput) {
            case NO_NEED_INPUT -> {
                Response response = clientTransporter.transport(new Request(nameCommand, arguments, account.getToken()));
                renderer.printResult(response);
                renderer.printNotice(response);
            }
            case NEED_INPUT -> modeManager.call(renderer, clientTransporter, account, nameCommand, argumentsCommand);
        }
    }
}
