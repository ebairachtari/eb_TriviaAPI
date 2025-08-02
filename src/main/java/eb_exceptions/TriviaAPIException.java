package eb_exceptions;

public class TriviaAPIException extends Exception {

    // Default Constructor
    public TriviaAPIException() {
        super();
    }

    // Constructor with error message
    public TriviaAPIException(String message) {
        super(message);
    }

    // Constructor with error message and cause
    public TriviaAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with only the cause of the error
    public TriviaAPIException(Throwable cause) {
        super(cause);
    }

    // Constructor with all options
    protected TriviaAPIException(String message, Throwable cause, 
                                  boolean enableSuppression, 
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
