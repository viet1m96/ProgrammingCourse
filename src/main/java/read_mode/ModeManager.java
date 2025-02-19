package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Invoker;

import java.util.HashMap;

public class ModeManager {
    private HashMap<String, ReadMode> readModes = new HashMap<>();

    public void init() {
        registerReadMode("execute_script", new FileReaderMode());
        registerReadMode("insert", new ConsoleReaderMode());
        registerReadMode("remove_greater", new ConsoleReaderMode());
        registerReadMode("replace_if_lower", new ConsoleReaderMode());
        registerReadMode("update", new ConsoleReaderMode());
    }

    public void registerReadMode(String command, ReadMode readMode) {
        readModes.put(command, readMode);
    }

    public void call(Invoker invoker, String nameCommand, String arguments) throws UserException, LogException {
        readModes.get(nameCommand).executeMode(invoker, nameCommand, arguments);
    }
}