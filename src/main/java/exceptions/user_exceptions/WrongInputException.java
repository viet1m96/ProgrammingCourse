package exceptions.user_exceptions;

/**
 * The {@code WrongInputException} class represents an exception that is thrown when the user provides invalid input.
 * It extends the {@link UserException} class, indicating that it's a user-related error resulting from incorrect input data.
 */
public class WrongInputException extends UserException {

    /**
     * Constructs a new {@code WrongInputException} with no specific detail message.
     */
    public WrongInputException() {
    }

    /**
     * Returns a string representation of the {@code WrongInputException}.
     *
     * @return A string that informs the user that the provided input was invalid and prompts them to try again.
     */
    @Override
    public String toString() {
        return "Wrong input, please try again";
    }
}