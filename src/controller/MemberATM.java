package controller;

public interface MemberATM {
	public void memberJoin(); //회원가입

	public void saveMemberAll(); //DB에서 회원정보 가져와서 ArrayList에 담기

	public void login();// 로그인
	
	public void setMemberInfo(String id); //해당 id 에 대한 회원정보 객체 저장

	public void memberDrop(String id);//회원탈퇴
	
	public void updateGradeVip();//회원별 등급 조회시 상위%에 따른 회원등급 업데이트(C)
	public void updateGradeA();	//회원별 등급 조회시 상위%에 따른 회원등급 업데이트(A)
	public void updateGradeB();	//회원별 등급 조회시 상위%에 따른 회원등급 업데이트(B)
	public void updateGradeC();	//회원별 등급 조회시 상위%에 따른 회원등급 업데이트(C)
	
}
