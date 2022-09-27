package controller;

import java.util.List;

import model.Account;

public interface AccountATM {
	void createAccount(String id, List<Account> accounts);
	void setAccountInfo(String id);
	void viewBalance(String id);
	void deposit(String id);
	void withdraw(String id);
	void send(String id);
	Account existAccount(String accountNo);
	
}
