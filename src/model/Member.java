package model;


public class Member {
	//변수 선언
	private String id;
	private String name;
	private String pw;
	private String grade;
	private String rdate;
	public Member(String id, String name, String pw, String grade2, String rdate2) {
		super();
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.grade = grade2;
		this.rdate = rdate2;
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
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", name=" + name + ", pw=" + pw + ", grade=" + grade + ", rdate=" + rdate + "]";
	}
	
	
	
	
	
	
	
	
}
