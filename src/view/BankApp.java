package view;

import java.util.Scanner;

import Dao.CreateTable;
import controller.MemberATM_Impl;

public class BankApp  {
	static Scanner sc = new Scanner(System.in); // 스캐너 객체생성
	static MemberATM_Impl m = new MemberATM_Impl(); // 회원가입 및 회원리스트 조회
	
	public static void main(String[] args) {
		//DB테이블 생성
		CreateTable ct = new CreateTable();
		ct.createMemberTable();
		ct.createAccountTable();
		boolean run = true;
		System.out.println("A____A				    	   		ᘏ_____ᘏ\r\n"
				+ "|　・ㅅ・ |						        |　・ㅅ・ |\r\n"
				+ "|っ　 ｃ|						        |っ　 ｃ|\r\n"
				+ "|　　 　|						        |　　 　|");
		System.out.println("███████╗ ██████╗  ██████╗     ██████╗  █████╗ ███╗   ██╗██╗  ██╗\r\n"
				+ "╚══███╔╝██╔═══██╗██╔═══██╗    ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝\r\n"
				+ "  ███╔╝ ██║   ██║██║   ██║    ██████╔╝███████║██╔██╗ ██║█████╔╝ \r\n"
				+ " ███╔╝  ██║   ██║██║   ██║    ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗ \r\n"
				+ "███████╗╚██████╔╝╚██████╔╝    ██████╔╝██║  ██║██║ ╚████║██║  ██╗\r\n"
				+ "╚══════╝ ╚═════╝  ╚═════╝     ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝\r\n"
				+ "                                                                ");
		do {
			System.out.println("✿​━━∞━━∞━━∞━━∞━━∞━━∞━━∞━━∞━━✿ MAIN MENU ✿​━━∞━━∞━━∞━━∞━━∞━━∞━━∞━━∞━━∞✿");
			System.out.println("1.회원가입  | 2.로그인  | 3.종료");
			System.out.println("-----------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt();
			System.out.println("");
			switch (menuNum) {
			case 1:// 회원가입
				m.memberJoin();
				break;
			case 2:// 로그인
				m.login();

				break;
			case 3: // 종료
				run = false;
				break;
			default:
				System.out.println("");
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
