package bank;

public class MemberDTO {
	//변수 선언
	private String id;
	private String name;
	private String pw;
	private long balance;
	private String accountNo;
	
	public MemberDTO(String id, String name, String pw, long balance, String accountNo) {
		super();
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.balance = balance;
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", pw=" + pw + ", balance=" + balance + ", accountNo="
				+ accountNo + "]";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPw() {
		return pw;
	}

	public long getBalance() {
		return balance;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	
	
	
}
