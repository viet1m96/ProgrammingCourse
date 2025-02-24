package io_utilities.working_with_csv_file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The {@code CSVWriter} class is responsible for writing data to a CSV file.
 * It takes a list of {@link StringBuilder} objects, representing the data to be written, and writes them to the specified file.
 */
public class CSVWriter {

    private final FileWriter fileWriter;

    /**
     * Constructs a new {@code CSVWriter} and initializes the {@link FileWriter} with the given file name.
     *
     * @param fileName The name of the CSV file to be written to.
     * @throws IOException If an I/O error occurs during the creation of the {@link FileWriter}.
     */
    public CSVWriter(String fileName) throws IOException {
        fileWriter = new FileWriter(fileName);
    }

    /**
     * Writes a list of {@link StringBuilder} objects to the CSV file.
     * Each {@link StringBuilder} represents a line in the CSV file.
     * The method appends a comma to each line except the last one, and appends a newline character to the last line.
     *
     * @param studyGroups A list of {@link StringBuilder} objects representing the data to be written to the CSV file.
     * @throws IOException If an I/O error occurs during the writing process.
     */
    public void writeLines(List<StringBuilder> studyGroups) throws IOException {
        for (int i = 0; i < studyGroups.size(); i++) {
            if (i < studyGroups.size() - 1) {
                fileWriter.write(studyGroups.get(i).append(',').toString());
                fileWriter.flush();
            } else {
                fileWriter.write(studyGroups.get(i).append(System.lineSeparator()).toString());
                fileWriter.flush();
            }
        }
    }
}
