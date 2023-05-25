package exception;

public class PermissionException extends Exception {

    @Override
    public String getMessage() {
        return "Permission denied!";
    }

}
