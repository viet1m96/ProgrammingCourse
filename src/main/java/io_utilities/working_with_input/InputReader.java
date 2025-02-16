package io_utilities.working_with_input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {

    private InputStreamReader reader;

    public InputReader() {
    }

    public void setReader(String fileName) throws IOException {
        reader = new InputStreamReader(new FileInputStream(fileName));
    }

    public void setReader() {
        reader = new InputStreamReader(System.in);
    }

    public String readLine() throws IOException {
        StringBuilder currentLine = new StringBuilder();
        int character;
        while ((character = reader.read()) != -1) {
            char currentChar = (char) character;
            if (currentChar == '\n') {
                break;
            } else {
                currentLine.append(currentChar);
            }
        }
        return currentLine.toString();
    }
}