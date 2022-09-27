package view;

import java.util.Scanner;

import Dao.CreateTable;
import controller.AccountATM_Impl;
import controller.MemberATM_Impl;
import model.Account;
import model.Member;

public class BankApp  {
	static Scanner sc = new Scanner(System.in); // 스캐너 객체생성
	static MemberATM_Impl m = new MemberATM_Impl(); // 회원가입 및 회원리스트 조회
	static AccountATM_Impl a = new AccountATM_Impl();
	
	public static void main(String[] args) {
		//Scanner sc = new Scanner(System.in); // 스캐너 객체생성
		BankApp app = new BankApp();
		CreateTable ct = new CreateTable();
		ct.createMemberTable();
		ct.createAccountTable();
		boolean run = true;

		do {
//			프로그램 시작과 동시에 테이블 정보를 객체에 담기
	//		m.saveMemberAll(); 
//			m.saveAccountAll(); 
			//m.fileOutput();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.회원가입  | 2.로그인  | 3.종료");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int menuNum = sc.nextInt(); // 선택 번호
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
				System.out.println("Wrong Answer");
			}
			System.out.println();

		} while (run);

	}

}
