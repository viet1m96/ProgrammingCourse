package io_utilities.working_with_input;

/**
 * The {@code InputPartition} class provides static methods for splitting an input string into two parts,
 * based on the presence of a space as a delimiter.
 */
public class InputPartition {

    /**
     * Constructs a new {@code InputPartition} object.
     */
    public InputPartition() {
    }

    /**
     * Extracts the first part of the input string, delimited by a space.
     *
     * @param inp The input string to partition.
     * @return The first part of the input string (the part before the first space).
     */
    public static String part1st(String inp) {
        return inp.split(" ")[0];
    }

    /**
     * Extracts the second part of the input string, delimited by a space.
     * If the input string does not contain a space, or if there is no second part, returns {@code null}.
     *
     * @param inp The input string to partition.
     * @return The second part of the input string (the part after the first space), or {@code null} if it doesn't exist.
     */
    public static String part2nd(String inp) {
        if (inp.split(" ").length < 2) {
            return null;
        }
        return inp.split(" ")[1];
    }
}