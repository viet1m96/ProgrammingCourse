package enums;

/**
 * The {@code NeedInput} enum indicates whether a command requires additional input from the user.
 * It provides a type-safe way to represent the input requirement status of a command.
 */
public enum NeedInput {

    /**
     * Indicates that the command requires additional input from the user to execute successfully.
     */
    NEED_INPUT,

    /**
     * Indicates that the command does not require any additional input from the user to execute.
     */
    NO_NEED_INPUT,
}