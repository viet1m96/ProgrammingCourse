package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import io_utilities.working_with_input.InputReader;

import java.util.Optional;

public interface ReadMode {
    public void executeMode(String nameCommand, String arg) throws UserException, LogException;
}
