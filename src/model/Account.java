package model;

public class Account {
	private String accountNo;
	private String id;
	private long balance;
	private String oadate;
	
	//거래내역 저장용 변수
	private long withdraw;//출금액
	private long deposit;//입금액
	private String tradeDate;//거래일
	String type; //거래종류
	
	public Account(String accountNo ,String id,long balance,String oadate) {
		super();
		this.accountNo = accountNo;
		this.id = id;
		this.balance = balance;
		this.oadate = oadate;
	}
	
	//거래내역 파일에서 꺼내와 List에 저장하기 위함.
		public Account(String id, String tradeDate, String type, String accountNo, long withdraw, long deposit,
				long balance) {
			super();
			this.accountNo = accountNo;
			this.id = id;
			this.balance = balance;
			this.withdraw = withdraw;
			this.deposit = deposit;
			this.tradeDate = tradeDate;
			this.type = type;
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

	//거래내역용
	public long getWithdraw() {
		return withdraw;
	}

	public long getDeposit() {
		return deposit;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public String getType() {
		return type;
	}

	public void setWithdraw(long withdraw) {
		this.withdraw = withdraw;
	}

	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	
	
}
