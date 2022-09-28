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
		
		do {
			//m.fileOutput();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.회원가입  | 2.로그인  | 3.종료");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); 
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
				System.out.println("잘못 선택하셨습니다. 올바른 번호를 입력해주세요.");
			}
			System.out.println();

		} while (run);

	}

}
