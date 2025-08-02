package eb_erwtisidb;

public class ErrorResponse {

    private int responseCode; // Error code
    private String message;   // Error message

    // Constructor that initializes the error code and message
    public ErrorResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    // Getters for retrieving the error code & message
    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    // Method to print the error details
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "responseCode=" + responseCode +
                ", message='" + message + '\'' +
                '}';
    }
}
