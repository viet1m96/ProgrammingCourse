package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Invoker;

import java.util.HashMap;

public class ModeManager {
    private HashMap<String, ReadMode> readModes = new HashMap<>();

    public void init() {
        readModes.put("execute_script", new FileReaderMode());
        readModes.put("insert", new ConsoleReaderMode());
        readModes.put("remove_greater", new ConsoleReaderMode());
        readModes.put("replace_if_lower", new ConsoleReaderMode());
    }

    public void call(Invoker invoker, String nameCommand, String arguments) throws UserException, LogException {
        readModes.get(nameCommand).executeMode(invoker, nameCommand, arguments);
    }
}