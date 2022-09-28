package model;


public class Member {
	//변수 선언
	private String id;
	private String name;
	private String pw;
	private String grade;
	private String rdate;
	
	//외래변수(account 사용)
	private String accountNo;
	private long balance;
	
	//회원전체정보 조회용
	public Member(String id, String name, String grade, String rdate, String accountNo, long balance) {
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.rdate = rdate;
		this.accountNo = accountNo;
		this.balance = balance;
	}
	public Member(String id, String name, String pw, String grade, String rdate) {
		super();
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.grade = grade;
		this.rdate = rdate;
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
	public String getGrade() {
		return grade;
	}
	public String getRdate() {
		return rdate;
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
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public Long getBalance() {
		return balance;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", name=" + name + ", pw=" + pw + ", grade=" + grade + ", rdate=" + rdate + "]";
	}
	
	
	
	
	
	
	
	
}
