package io_utilities.working_with_csv_file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The {@code CSVWriter} class is responsible for writing a list of {@link StringBuilder} objects to a CSV file.
 * Each {@code StringBuilder} represents a line of data to be written to the file.
 */
public class CSVWriter {

    private FileWriter fileWriter;

    /**
     * Constructs a new {@code CSVWriter} with the specified file name.
     * Initializes the {@link FileWriter} object.
     *
     * @param fileName The name of the CSV file to write to.
     * @throws IOException If an I/O error occurs while opening or creating the file.
     */
    public CSVWriter(String fileName) throws IOException {
        fileWriter = new FileWriter(fileName);
    }

    /**
     * Writes a list of {@link StringBuilder} objects to the CSV file.
     * Each {@code StringBuilder} is written as a line in the file, with values separated by commas.
     *
     * @param studyGroups A list of {@link StringBuilder} objects representing the data to be written to the CSV file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeLines(List<StringBuilder> studyGroups) throws IOException {
        for (int i = 0; i < studyGroups.size(); i++) {
            if (i < studyGroups.size() - 1) {
                fileWriter.write(studyGroups.get(i).append(',').toString());  //**BUG: Should append newline, not comma.**
                fileWriter.flush();
            } else {
                fileWriter.write(studyGroups.get(i).append(System.lineSeparator()).toString());
                fileWriter.flush();
            }
        }
    }
}