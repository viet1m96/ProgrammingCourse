package file_processor.logic;

import enums.NeedInput;
import file_processor.gui_file.results.VboxOutput;

import java.util.HashMap;

public class CommandTypeClassifier {
    private static final HashMap<String, NeedInput> commandTypes = new HashMap<>();


    public static void init() {
        registerCommandTypes("help", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("clear", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("exit", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("history", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("info", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("remove_key", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("update", NeedInput.NEED_INPUT);
        registerCommandTypes("execute_script", NeedInput.NEED_INPUT);
        registerCommandTypes("insert", NeedInput.NEED_INPUT);
        registerCommandTypes("remove_greater", NeedInput.NEED_INPUT);
        registerCommandTypes("replace_if_lower", NeedInput.NEED_INPUT);
        registerCommandTypes("show", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("print_descending", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("print_ascending", NeedInput.NO_NEED_INPUT);
        registerCommandTypes("print_field_descending_semester_enum", NeedInput.NO_NEED_INPUT);
    }
    
    public static void registerCommandTypes(String command, NeedInput needInput) {
        commandTypes.put(command, needInput);
    }


    public static NeedInput getNeedInput(String command) {
        return commandTypes.get(command);
    }
}
