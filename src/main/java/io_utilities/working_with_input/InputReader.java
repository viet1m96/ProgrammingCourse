package io_utilities.working_with_input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The {@code InputReader} class provides methods for reading input from a file or the console.
 * It uses an {@link InputStreamReader} to facilitate reading character streams from either a file specified by its name
 * or from the standard input (console).
 */
public class InputReader {

    private InputStreamReader reader;

    /**
     * Constructs a new {@code InputReader} object. The reader is not initialized by default.
     */
    public InputReader() {
    }

    /**
     * Sets the reader to read from the specified file.
     *
     * @param fileName The name of the file to read from.
     * @throws IOException If an I/O error occurs while opening the file.
     */
    public void setReader(String fileName) throws IOException {
        reader = new InputStreamReader(new FileInputStream(fileName));
    }

    /**
     * Sets the reader to read from the standard input (console).
     */
    public void setReader() {
        reader = new InputStreamReader(System.in);
    }

    /**
     * Reads a line of text from the input stream.
     * The method reads characters until it encounters a newline character ('\n') or the end of the stream.
     *
     * @return The line of text read from the input stream, or an empty string if the stream is empty or the end
     *         has been reached before any characters were read.
     * @throws IOException If an I/O error occurs while reading from the input stream.
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