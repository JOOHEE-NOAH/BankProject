package controller;

public interface MemberATM {
	public void memberJoin(); //회원가입

	public void saveMemberAll(); //DB에서 회원정보 가져와서 ArrayList에 담기

	public void memberDrop();// 회원탈퇴
	
	public void login();// 로그인
	
	public void setMemberInfo(String id);
	
}
