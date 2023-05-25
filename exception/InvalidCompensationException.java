package exception;

public class InvalidCompensationException extends Exception{

    @Override
    public String getMessage() {
        return "Invalid compensation!";
    }
}
