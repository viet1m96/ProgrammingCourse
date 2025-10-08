package io_utilities.working_with_csv_file;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.working_with_input.StudyGroupBuilder;
import main_objects.StudyGroup;

import java.io.IOException;
import java.util.List;

/**
 * The {@code CSVReader} class is responsible for reading data from a CSV file and creating {@link StudyGroup} objects.
 * It utilizes the {@link DataFromFileProcessor} to validate and process the data extracted from the CSV file.
 */
public class CSVReader {

    private final DataFromFileProcessor processor;

    /**
     * Constructs a new {@code CSVReader} and initializes the {@link DataFromFileProcessor}.
     *
     * @throws IOException If an I/O error occurs during the initialization of the {@link DataFromFileProcessor}.
     */
    public CSVReader() throws IOException {
        processor = new DataFromFileProcessor();
    }

    /**
     * Loads a {@link StudyGroup} object from a given string (representing a line from the CSV file).
     * This method processes the string, extracts the necessary information, and uses the {@link StudyGroupBuilder}
     * to construct the {@link StudyGroup} object.
     *
     * @param str The string containing the data for a single {@link StudyGroup} object.
     * @return The constructed {@link StudyGroup} object.
     * @throws IOException  If an I/O error occurs during the data processing.
     * @throws UserException If there are issues with the user-provided data in the CSV file.
     * @throws LogException  If an error occurs while logging the exception.
     */
    public StudyGroup loadObj(String str) throws IOException, UserException, LogException {
        List<String> groupInfo = processor.checkGroupInfo(str);
        List<String> adminInfo = processor.checkPerInfo(str);
        return StudyGroupBuilder.parseStudyGroup(groupInfo, adminInfo);
    }
}
