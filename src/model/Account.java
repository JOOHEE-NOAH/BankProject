package model;

public class Account {
	private String accountNo;
	private String id;
	private long balance;
	private String oadate;
	
	public Account(String accountNo ,String id,long balance,String oadate) {
		super();
		this.accountNo = accountNo;
		this.id = id;
		this.balance = balance;
		this.oadate = oadate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getId() {
		return id;
	}

	public long getBalance() {
		return balance;
	}

	public String getOadate() {
		return oadate;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public void setOadate(String oadate) {
		this.oadate = oadate;
	}

	@Override
	public String toString() {
		return "AccountDTO [accountNo=" + accountNo + ", id=" + id + ", balance=" + balance + ", oadate=" + oadate
				+ "]";
	}
	
	
}
