package io_utilities.working_with_csv_file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    private final FileWriter fileWriter;

    public CSVWriter(String fileName) throws IOException {
        fileWriter = new FileWriter(fileName);
    }

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