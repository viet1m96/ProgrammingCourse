package io_utilities.working_with_csv_file;

import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.user_exceptions.WrongUploadingDataException;
import io_utilities.working_with_input.InputReader;
import main_objects.StudyGroup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CSVReader} class is responsible for reading data from a CSV file and converting it into a list of {@link StudyGroup} objects.
 * It utilizes an {@link InputReader} to read the file line by line and a {@link DataFromFileProcessor} to parse and validate the data.
 */
public class CSVReader {

    private InputReader inputReader;
    private DataFromFileProcessor processor;

    /**
     * Constructs a new {@code CSVReader} with the specified file name.
     * Initializes the {@link InputReader} and {@link DataFromFileProcessor} objects.
     *
     * @param fileName The name of the CSV file to read.
     * @throws IOException If an I/O error occurs while opening or reading the file.
     */
    public CSVReader(String fileName) throws IOException {
        inputReader = new InputReader();
        inputReader.setReader(fileName);
        processor = new DataFromFileProcessor();
    }

    /**
     * Loads a list of {@link StudyGroup} objects from the CSV file.
     * Reads the file line by line, parses the data, and creates a {@link StudyGroup} object for each valid line.
     *
     * @return A list of {@link StudyGroup} objects read from the CSV file.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws WrongUploadingDataException If the data in the file is invalid or improperly formatted.
     */
    public List<StudyGroup> loadObjList() throws IOException, WrongUploadingDataException {
        String str;
        List<StudyGroup> studyGroups = new ArrayList<>();
        while ((str = inputReader.readLine()) != null && !str.isEmpty()) {
            List<String> groupInfo = processor.checkGroupInfo(str);
            List<String> adminInfo = processor.checkPerInfo(str);
            StudyGroup studyGroup = new StudyGroup(groupInfo, adminInfo);
            studyGroups.add(studyGroup);
        }
        return studyGroups;
    }
}