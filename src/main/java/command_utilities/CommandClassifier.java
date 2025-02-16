package command_utilities;

import enums.NeedInput;

import java.util.HashMap;

public class CommandClassifier {

    private final HashMap<String, NeedInput> commandClassifiers = new HashMap<>();

    public void init() {
        registerClassifier("help", NeedInput.NO_NEED_INPUT);
        registerClassifier("clear", NeedInput.NO_NEED_INPUT);
        registerClassifier("exit", NeedInput.NO_NEED_INPUT);
        registerClassifier("history", NeedInput.NO_NEED_INPUT);
        registerClassifier("info", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_descending", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_ascending", NeedInput.NO_NEED_INPUT);
        registerClassifier("print_field_descending_semester_enum", NeedInput.NO_NEED_INPUT);
        registerClassifier("remove_key", NeedInput.NO_NEED_INPUT);
        registerClassifier("save", NeedInput.NO_NEED_INPUT);
        registerClassifier("show", NeedInput.NO_NEED_INPUT);
        registerClassifier("update", NeedInput.NEED_INPUT);
        registerClassifier("execute_script", NeedInput.NEED_INPUT);
        registerClassifier("insert", NeedInput.NEED_INPUT);
        registerClassifier("remove_greater", NeedInput.NEED_INPUT);
        registerClassifier("replace_if_lower", NeedInput.NEED_INPUT);
    }

    public void registerClassifier(String command, NeedInput needInput) {
        commandClassifiers.put(command, needInput);
    }
    public NeedInput getCommandClassifier(String command) {
        return commandClassifiers.get(command);
    }
}