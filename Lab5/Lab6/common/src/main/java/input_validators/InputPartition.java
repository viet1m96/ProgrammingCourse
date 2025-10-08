package input_validators;

/**
 * The {@code InputPartition} class provides static utility methods for partitioning an input string into two parts,
 * typically representing a command and its argument.
 */
public class InputPartition {

    /**
     * Constructs a new {@code InputPartition}.
     */
    public InputPartition() {
    }

    /**
     * Extracts the first part of the input string, which is assumed to be the command.
     * It splits the input string by spaces and returns the first element.
     *
     * @param inp The input string to be partitioned.
     * @return The first part of the input string (the command).
     */
    public static String part1st(String inp) {
        return inp.split(" ")[0];
    }

    /**
     * Extracts the second part of the input string, which is assumed to be the argument.
     * It splits the input string by spaces and returns the second element if it exists.
     * If the input string contains only one part (i.e., only the command), it returns null.
     *
     * @param inp The input string to be partitioned.
     * @return The second part of the input string (the argument), or null if it does not exist.
     */
    public static String part2nd(String inp) {
        if (inp.split(" ").length < 2) {
            return null;
        }
        return inp.split(" ")[1];
    }
}
