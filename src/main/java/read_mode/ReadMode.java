package read_mode;

import exceptions.log_exceptions.LogException;
import exceptions.user_exceptions.UserException;
import iostream.Invoker;

public interface ReadMode {

    void executeMode(Invoker invoker, String nameCommand, String arg) throws UserException, LogException;
}