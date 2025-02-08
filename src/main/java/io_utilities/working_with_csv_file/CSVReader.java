package io_utilities.working_with_csv_file;

import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.user_exceptions.WrongUploadingDataException;
import io_utilities.working_with_input.InputReader;
import io_utilities.working_with_input.ObjInputChecker;
import main_objects.StudyGroup;


import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class CSVReader {
    private InputReader inputReader;
    private DataFromFileProcessor processor;
    public CSVReader(String fileName) throws IOException {
        inputReader = new InputReader();
        inputReader.setReader(fileName);
        processor = new DataFromFileProcessor();
    }

    public List<StudyGroup> loadObjList() throws IOException, WrongUploadingDataException {
        String str;
        List<StudyGroup> studyGroups = new ArrayList<>();
        while((str = inputReader.readLine()) != null && !str.isEmpty()) {
            List<String> groupInfo = processor.checkGroupInfo(str);
            List<String> adminInfo = processor.checkPerInfo(str);
            StudyGroup studyGroup = new StudyGroup(groupInfo, adminInfo);
            studyGroups.add(studyGroup);

        }
        return studyGroups;
    }


}
