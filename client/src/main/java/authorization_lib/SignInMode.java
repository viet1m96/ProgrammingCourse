package authorization_lib;

import exceptions.log_exceptions.LogException;
import exceptions.network_exception.NetworkException;
import exceptions.user_exceptions.AccountInputException;
import exceptions.user_exceptions.UserException;
import goods.Request;
import goods.Response;
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

public class SignInMode implements AuthMode{
    public SignInMode() {}

    @Override
    public Account executeAcc(ClientTransporter transporter, Renderer renderer) throws LogException, NetworkException {
        String username = getInputUsername("Please enter your username(the username must not be empty): ");
        String password = getInputPassword("Please enter your password(the password must not be empty): ");
        password = SHA1Hashing.hashStringSHA1(password);
        List<String> arguments = new ArrayList<>();
        arguments.add(username);
        arguments.add(password);
        Request request = new Request("sign_in", arguments, null);
        Response response = transporter.transport(request);
        List<String> result = response.getResult();
        Account newAccount = null;
        if(result == null) {
            renderer.printNotice(response);
            return null;
        } else {
            renderer.printNotice(response);
            newAccount = new Account(username, password, result.get(0));
        }
        return newAccount;
    }

    public String getInputUsername(String notice) throws LogException {
        String username = "";
        while(username.isEmpty()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                RainbowPrinter.printCondition(notice);
                username = br.readLine();
                if (username.isEmpty()) {
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

    public String getInputPassword(String notice) {
        String password = "";
        while(password.isEmpty()) {
            try {
                Console console = System.console();
                RainbowPrinter.printCondition(notice);
                char[] pwdArray = console.readPassword();
                password = new String(pwdArray);
                if(password.isEmpty()) {
                    throw new AccountInputException();
                }
            } catch (AccountInputException e) {
                RainbowPrinter.printError(e.toString());
            }
        }
        return password;
    }
}
