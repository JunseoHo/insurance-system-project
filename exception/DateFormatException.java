package exception;

public class DateFormatException extends Exception {

    @Override
    public String getMessage() {
        return "Date is invalid!";
    }

}
