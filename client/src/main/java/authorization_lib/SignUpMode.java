package authorization_lib;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.AccountInputException;
import goods.Request;
import goods.Response;
import input_validators.InputAccountChecker;
import iostream.Renderer;
import logging.LogUtil;
import network.ClientTransporter;
import printer_options.RainbowPrinter;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SignUpMode implements AuthMode {
    public SignUpMode() {}

    @Override
    public Account executeAcc(ClientTransporter transporter, Renderer renderer) throws LogException, NetworkException {
        String username = getInputUsername();
        String password = getInputPassword();
        password = SHA1Hashing.hashStringSHA1(password);
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        Request request = new Request("sign_up", arguments, null);
        Response response = transporter.transport(request);
        List<String> result = response.getResult();
        Account newAccount = null;
        if(result == null) {
            renderer.printNotice(response);
            return null;
        } else {
            newAccount = new Account(username, password, result.get(0));
        }
        return newAccount;
    }


    public String getInputUsername() throws LogException {
        String username = "";
        while(username.isEmpty()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                RainbowPrinter.printCondition("Please enter your desired username (the username must not be empty and shorter than 20 characters): ");
                username = br.readLine();
                if (!InputAccountChecker.checkUser(username)) {
                    throw new AccountInputException();
                }
            } catch (IOException e) {
                LogUtil.logTrace(e);
                throw new LogException();
            } catch (AccountInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
        return username;
    }

    public String getInputPassword() {
        String password = "";
        while(password.isEmpty()) {
            try {
                RainbowPrinter.printCondition("Your password have to follow these following conditions:");
                RainbowPrinter.printInfo("* Must not be longer than 8 characters and shorter than 20 characters");
                RainbowPrinter.printInfo("* Must contain at least one special character");
                RainbowPrinter.printInfo("* Must contain at least one number");
                RainbowPrinter.printInfo("* Must contain at least one uppercase and one lowercase letter");
                Console console = System.console();
                char[] pwdArray = console.readPassword("Please enter your desired password: ");
                password = new String(pwdArray);
                if(!InputAccountChecker.checkPassword(password)) {
                    password = "";
                    throw new AccountInputException();
                }
            } catch (AccountInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
        return password;
    }
}
