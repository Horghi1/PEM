package util.exceptions;

public class InvalidEmailException extends Exception {

    private String message;

    public InvalidEmailException(String message) {
        super(message);
        this.message = message;
    }
}
