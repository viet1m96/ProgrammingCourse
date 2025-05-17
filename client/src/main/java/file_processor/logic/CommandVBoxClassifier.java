package file_processor.logic;

import enums.TypeOfFormatCmd;
import file_processor.gui_file.results.NoticeResultBoxer;
import file_processor.gui_file.results.TableHelpBoxer;
import file_processor.gui_file.results.TableStudyGroupBoxer;
import file_processor.gui_file.results.VboxOutput;

import java.util.HashMap;

public class CommandVBoxClassifier {
    private static final HashMap<String, VboxOutput> resultMap = new HashMap<>();


    public static void init() {
        if(resultMap.isEmpty()) {
            registerVBox("help", new TableHelpBoxer());
            registerVBox("insert", new NoticeResultBoxer());
            registerVBox("update", new NoticeResultBoxer());
            registerVBox("remove_key", new NoticeResultBoxer());
            registerVBox("replace_if_lower", new NoticeResultBoxer());
            registerVBox("remove_greater", new NoticeResultBoxer());
            registerVBox("info", new NoticeResultBoxer());
            registerVBox("clear", new NoticeResultBoxer());
            registerVBox("show", new TableStudyGroupBoxer());
            registerVBox("print_ascending", new TableStudyGroupBoxer());
            registerVBox("print_descending", new TableStudyGroupBoxer());
            registerVBox("print_field_descending_semester_enum", new TableStudyGroupBoxer());
        }
    }
    
    public static void registerVBox(String command, VboxOutput vboxOutput) {
        resultMap.put(command, vboxOutput);
    }

    public static  VboxOutput getVBox(String command) {
        return resultMap.get(command);
    }

}
