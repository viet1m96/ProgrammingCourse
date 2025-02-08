package command_utilities;

import enums.NeedInput;
import read_mode.ConsoleReaderMode;
import read_mode.FileReaderMode;

import java.util.HashMap;

public class CommandClassifier {
    private static HashMap<String, NeedInput> commandClassifiers = new HashMap<>();
    
    public static void init() {
        commandClassifiers.put("help", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("clear", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("exit", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("history", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("info", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_descending", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_ascending", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("print_field_descending_semester_enum", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("remove_key", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("save", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("show", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("update", NeedInput.NO_NEED_INPUT);
        commandClassifiers.put("execute_script", NeedInput.NEED_INPUT);
        commandClassifiers.put("insert", NeedInput.NEED_INPUT);
        commandClassifiers.put("remove_greater", NeedInput.NEED_INPUT);
        commandClassifiers.put("replace_if_lower", NeedInput.NEED_INPUT);
    }

    public static NeedInput getCommandClassifier(String command) {
        return commandClassifiers.get(command);
    }
}
