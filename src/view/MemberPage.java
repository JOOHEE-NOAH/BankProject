package view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import controller.AccountATM_Impl;
import controller.MemberATM_Impl;
import model.Account;
import model.Member;


public class MemberPage {
	static Scanner sc = new Scanner(System.in);
	static MemberATM_Impl m;// 회원가입 및 회원리스트 조회
	static AccountATM_Impl a = new AccountATM_Impl();
	
	public void memberPageView(Member member, List<Member> members,Account account,List<Account> accounts) {
		System.out.println(member.getId()+"님 반갑습니다.");
		String id = member.getId(); //로그인한 회원 id
		String accountNo = account.getAccountNo();
		System.out.println("id는"+id);
		boolean run = true;
		do {
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println("1.나의 계좌정보  | 2.예금 | 3.출금 | 4.송금 | 5.거래내역 조회 | 6.종료");
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
				System.out.println("2선택");
				break;	
			case 6: //처음으로 
				run = false;
				break;
			default:
				System.out.println("Wrong Answer");
			}
			System.out.println();

		} while (run);
		
	}

	
}
