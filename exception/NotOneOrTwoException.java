package exception;

public class NotOneOrTwoException extends Exception{

	public NotOneOrTwoException(String message) {
	}
	
	public String getMessage(String m) {
		return "1 또는 2만 입력하여 주십시오.";
	}
}

