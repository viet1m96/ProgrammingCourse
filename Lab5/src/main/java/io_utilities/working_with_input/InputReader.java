package io_utilities.working_with_input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code InputReader} class provides a convenient way to read input from either a file or the standard input (console).
 * It uses an {@link InputStreamReader} to read characters from the input stream and provides a method to read a line of text.
 */
public class InputReader {

    private InputStreamReader reader;

    /**
     * Constructs a new {@code InputReader}.
     */
    public InputReader() {
    }

    /**
     * Sets the input stream reader to read from the specified file.
     *
     * @param fileName The name of the file to read from.
     * @throws IOException If an error occurs while opening or reading the file.
     */
    public void setReader(String fileName) throws IOException {
        reader = new InputStreamReader(new FileInputStream(fileName));
    }

    /**
     * Sets the input stream reader to read from the standard input (console).
     */
    public void setReader() {
        reader = new InputStreamReader(System.in);
    }

    /**
     * Reads a line of text from the input stream.
     * It reads characters until a newline character ('\n') is encountered or the end of the stream is reached.
     *
     * @return The line of text read from the input stream, or an empty string if the end of the stream is reached before any characters are read.
     * @throws IOException If an error occurs while reading from the input stream.
     */
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
