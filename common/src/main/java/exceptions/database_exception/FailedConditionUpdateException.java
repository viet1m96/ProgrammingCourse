package exceptions.database_exception;

public class FailedConditionUpdateException extends PostgresException {
    public FailedConditionUpdateException() {}

   @Override
   public String toString() {
      return "The update action failed because of unsatisfied conditions!";
   }
}
