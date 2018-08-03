package ro.msg.edu.jbugs.usermanagement.business.exception;

public class BuisnissException extends Exception {


    private ExceptionCode exceptionCode;

    public BuisnissException(ExceptionCode code) {
        this.exceptionCode = code;
    }

    public BuisnissException(ExceptionCode code, String message) {
        super(message);
        this.exceptionCode = code;
    }

    public BuisnissException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = code;
    }

    public BuisnissException(ExceptionCode code, Throwable cause) {
        super(cause);
        this.exceptionCode = code;
    }

    public BuisnissException(ExceptionCode code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionCode = code;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}