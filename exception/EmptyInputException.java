package exception;

public class EmptyInputException extends Exception {


    @Override
    public String getMessage() {
        return "Content is empty!";
    }

}
