package view;

import java.util.Scanner;

import controller.AccountATM_Impl;
import controller.MemberATM_Impl;


public class AdminPage {
	static Scanner sc = new Scanner(System.in);
	static MemberATM_Impl m = new MemberATM_Impl();// 회원가입 및 회원리스트 조회
	static AccountATM_Impl a = new AccountATM_Impl();
	
	public void adminPageView() {
		System.out.println("관리자님 반갑습니다.");
		
		boolean run = true;
		do {
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.전체 사용자 조회  | 2.등급별 회원 수 조회  | 3.회원 검색 |4.로그아웃");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); // 선택 번호
			switch (menuNum) {
			case 1://전체 사용자 조회
				m.memberInfoAllforAdmin();
				break;
			case 2:// 등급별 회원 수 조회
				m.gradeCountView();
				break;
			case 3:// 회원 검색
				m.searchMember();
				break;
			case 4: //로그아웃/처음으로
				run = false;
				break;
			default:
				System.out.println("잘못 선택하셨습니다. 올바른 번호를 입력해주세요.");
			}
			System.out.println();

		} while (run);
		
	}

	
}
