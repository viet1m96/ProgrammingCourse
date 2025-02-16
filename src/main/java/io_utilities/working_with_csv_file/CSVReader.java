package io_utilities.working_with_csv_file;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.working_with_input.StudyGroupBuilder;
import main_objects.StudyGroup;
import java.io.IOException;
import java.util.List;

public class CSVReader {


    private final DataFromFileProcessor processor;

    public CSVReader() throws IOException {
        processor = new DataFromFileProcessor();
    }

    public StudyGroup loadObj(String str) throws IOException, UserException, LogException {
        List<String> groupInfo = processor.checkGroupInfo(str);
        List<String> adminInfo = processor.checkPerInfo(str);
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }
}