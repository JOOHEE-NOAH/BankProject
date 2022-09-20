package Exception;

import view.BankApp;

public class WrongPasswordException extends Exception{
	public WrongPasswordException() {}
	public WrongPasswordException(String message) {
		super(message);
		//new BankApp();
		
}
}
