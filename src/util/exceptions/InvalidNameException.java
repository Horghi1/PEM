package util.exceptions;

public class InvalidNameException extends Exception {

    private String message;

    public InvalidNameException(String message) {
        super(message);
        this.message = message;
    }
}
