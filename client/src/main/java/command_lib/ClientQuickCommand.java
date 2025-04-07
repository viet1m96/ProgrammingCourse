package command_lib;

import authorization_lib.Account;
import logging.LogUtil;
import network.CommandHistory;
import printer_options.RainbowPrinter;

public class ClientQuickCommand {
    public ClientQuickCommand() {}

    public void signOut(Account account) {
        account.setToken(null);
        LogUtil.logInfo("The " + account.getUsername() + " has been signed out.");
        account.setUsername(null);
        account.setPassword(null);
        RainbowPrinter.printInfo("You are signed out!");
    }

    public void history(CommandHistory commandHistory) {
        commandHistory.printHistory();
    }

    public void exit() {
        LogUtil.logInfo("Client side was closed.");
        System.exit(0);
    }
}
