package exceptions.user_exceptions;

public class WrongUploadingDataException extends UserException {
    public WrongUploadingDataException() {}

    @Override
    public String toString() {
      return "There are problems with uploading data, please check again.";
    }
}
