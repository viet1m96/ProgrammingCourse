package enums;

/**
 * The {@code TypeOfFormatCmd} enum represents the different types of command formats based on their arguments.
 * It defines whether a command requires no arguments, a numerical argument, or a string argument.
 */
public enum TypeOfFormatCmd {
    /**
     * Represents a command that does not require any arguments.
     */
    WITHOUT_ARG,

    /**
     * Represents a command that requires a numerical argument.
     */
    WITH_NUMERAL_ARG,

    /**
     * Represents a command that requires a string argument.
     */
    WITH_STRING_ARG
}
