package view;

import java.util.Scanner;

import controller.AccountATM_Impl;
import controller.MemberATM_Impl;



public class MemberPage {
	static Scanner sc = new Scanner(System.in);
	static MemberATM_Impl m = new MemberATM_Impl();// 회원가입 및 회원리스트 조회
	static AccountATM_Impl a = new AccountATM_Impl();
	
	public void memberPageView(String id) {
		System.out.println(id+"님 반갑습니다.");
		
		boolean run = true;
		do {
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.나의 계좌정보  | 2.예금 | 3.출금 | 4.송금 | 5.거래내역 조회 | 6.회원탈퇴 |7.로그아웃");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); // 선택 번호
			switch (menuNum) {
			case 1://잔고조회
				a.viewBalance(id);
				break;
			case 2:// 입금
				a.deposit(id);
				break;
			case 3:// 출금
				a.withdraw(id);
				break;
			case 4:// 송금
				a.send(id);
				break;
			case 5:// 입금
				System.out.println("5선택 아직구현안함");
				break;
			case 6:// 회원탈퇴
				m.memberDrop(id);
				run = false;
				break;	
			case 7: //로그아웃/처음으로
				run = false;
				break;
			default:
				System.out.println("Wrong Answer");
			}
			System.out.println();

		} while (run);
		
	}

	
}
