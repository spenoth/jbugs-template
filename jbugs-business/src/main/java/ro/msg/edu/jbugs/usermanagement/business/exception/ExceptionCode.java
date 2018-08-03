package ro.msg.edu.jbugs.usermanagement.business.exception;

/**
 * Provides exception codes and description.
 */
public enum ExceptionCode {
     USER_VALIDATION_EXCEPTION(1000,"Validation exception"),
     EMAIL_EXISTS_ALLREADY(1001, "Email allready exists exception"),
     PASSWORD_NOT_VALID(1002, "Password is incorrect."),
     USERNAME_NOT_VALID(1003, "Username is not valid.");

    int id;
     String message;

     ExceptionCode(int id, String message) {
          this.id = id;
          this.message = message;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getMessage() {
          return message;
     }

     public void setMessage(String message) {
          this.message = message;
     }
}