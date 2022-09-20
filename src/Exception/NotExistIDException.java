package Exception;

import view.BankApp;

public class NotExistIDException extends Exception{
	public NotExistIDException() {}
	public NotExistIDException(String message) {
		super(message);
		//new BankApp();
}
}
