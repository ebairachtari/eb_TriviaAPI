package eb_erwtisidb;

public class ErrorResponse {

    private int responseCode; // Κωδικός του σφάλματος
    private String message;   // Περιγραφή του σφάλματος

    // Constructor της ErrorResponse που αρχικοποιεί τον κωδικό και το μήνυμα του σφάλματος
    public ErrorResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    // Getters για την ανάκτηση κωδικού & μηνύματος του σφάλματος
    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    // Μέθοδος για εκτύπωση του σφάλματος
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "responseCode=" + responseCode +
                ", message='" + message + '\'' +
                '}';
    }
}
