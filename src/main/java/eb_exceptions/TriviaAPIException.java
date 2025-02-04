package eb_exceptions;

public class TriviaAPIException extends Exception {

    // Default Constructor
    public TriviaAPIException() {
        super();
    }

    // Constructor με μήνυμα σφάλματος
    public TriviaAPIException(String message) {
        super(message);
    }

    // Constructor με μήνυμα σφάλματος και αιτία
    public TriviaAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor με μόνο την αιτία του σφάλματος
    public TriviaAPIException(Throwable cause) {
        super(cause);
    }

    // Constructor με όλες τις επιλογές
    protected TriviaAPIException(String message, Throwable cause, 
                                  boolean enableSuppression, 
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
