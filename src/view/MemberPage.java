package view;

import java.util.Scanner;

import controller.AccountATM_Impl;
import controller.MemberATM_Impl;



public class MemberPage {
	static Scanner sc = new Scanner(System.in);
	static MemberATM_Impl m = new MemberATM_Impl();// 회원가입 및 회원리스트 조회
	static AccountATM_Impl a = new AccountATM_Impl();
	
	public void memberPageView(String id,String name) {
		System.out.println(" "+name+"님 반갑습니다!(´･ω･`)");
				
		System.out.println("");
		
		boolean run = true;
		do {System.out.println("");
			System.out.println("✿​━━∞━━∞━━∞━━∞━━∞━━∞━━✿​━━∞━━∞━━∞━━∞━━∞━━∞━━✿ MENU ✿━━∞━━∞━━∞━━∞━━∞━━∞━━✿​━━∞━━∞━━∞━━∞━━∞━━∞━━✿");
			System.out.println("1.나의 계좌정보  | 2.예금 | 3.출금 | 4.송금 | 5.거래내역 조회 | 6.회원탈퇴 |7.로그아웃");
			System.out.println("------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); // 선택 번호
			switch (menuNum) {
			case 1://나의 계좌정보
				a.viewBalance(id,name);
				break;
			case 2:// 예금
				a.deposit(id);
				break;
			case 3:// 출금
				a.withdraw(id);
				break;
			case 4:// 송금
				a.send(id);
				break;
			case 5:// 거래내역 조회
				a.bankHisotry(id);
				break;
			case 6:// 회원탈퇴
				m.memberDrop(id);
				run = false;
				break;	
			case 7: //로그아웃+처음으로
				run = false;
				break;
			default:
				System.out.println("|￣￣￣￣￣￣￣|\r\n"
						+ "|잘못된번호에요|\r\n"
						+ "|＿＿＿＿＿＿＿|\r\n"
						+ "(\\_/)||\r\n"
						+ "(•ㅅ•)||\r\n"
						+ "/....づ\r\n"
						+ "");
			}
			System.out.println();

		} while (run);
		
	}

	
}
