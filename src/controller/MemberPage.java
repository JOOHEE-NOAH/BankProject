package controller;

import java.util.List;
import java.util.Scanner;

import model.Account;
import model.Member;


public class MemberPage {
	static Scanner sc = new Scanner(System.in);
	static MemberATM_Impl m;// 회원가입 및 회원리스트 조회
	static AccountATM_Impl a;
	static Member member;
	Account account;
	
	public void memberPageView(Member member, List<Member> members) {
		System.out.println(member.getId()+"님 반갑습니다.");
		System.out.println("멤버 객체 잘 드갔나?"+ member.getId());
		System.out.println("멤버 객체 잘 드갔나?"+ member.getName());
		System.out.println("멤버 객체 잘 드갔나?"+ member.getPw());
		System.out.println("멤버스 잘 드갔나?" + members.get(0).getId());
		boolean run = true;
		do {
			//m.fileOutput();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.잔고조회  | 2.입금 3.출금 4.송금 5. | 3.종료");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); // 선택 번호
			switch (menuNum) {
			case 1://잔고조회
				//a.viewBalance(id);
				break;
			case 2:// 로그인

				break;
			case 3: // 종료
				run = false;
				break;
			default:
				System.out.println("Wrong Answer");
			}
			System.out.println();

		} while (run);
		
	};
}
