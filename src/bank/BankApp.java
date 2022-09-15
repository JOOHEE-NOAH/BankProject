package bank;

import java.util.Scanner;

public class BankApp implements Bank {
	static Scanner sc = new Scanner(System.in); //스캐너 객체생성
	static MemberDAO m = new MemberDAO(); //회원가입 및 회원리스트 조회
	static Login login = new Login();
	MemberDTO member; 
	
	
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in); //스캐너 객체생성
		BankApp app = new BankApp();
		
		boolean run = true;
		
		do {
			m.fileOutput();
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("1.회원가입  | 2.로그인  | 3.잔고 | 4.가입자 조회	| 5.예금| 6.로그인 | 7.출금 |8.종료 | 9.관리자");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.print("원하시는 번호를 선택하세요");
		
		int menuNum = sc.nextInt(); //선택 번호
		switch (menuNum) {
		case 1://회원가입
			m.memberJoin();
			break;
		case 2://로그인
			login.login();
			
			break;
		case 5:
			//System.out.println("예금액>");
			//balance += sc.nextInt();
			app.deposit();
			break;
		case 7:
			//System.out.println("출금액>");
			//balance -= sc.nextInt();
			app.withDraw();
			break;
		case 3:
			System.out.println("잔고>");
			System.out.println();
			break;
		case 4:
			m.memberAll();
			break;
		
		case 6:
			
			
			break;
		
		case 8:
			run = false;
			break;	
		default:
			System.out.println("Wrong Answer");
		}
		System.out.println();
		System.out.println("선택->"+menuNum);
		
		}
		while(run);
	
	}
	
	private void viewInfo() {
		System.out.println(member.getName());
		System.out.println(member.getBalance());
		
	}
	@Override
	public void deposit() {
		
		//예금액 입력하고 member객체 balance에 값 더해주기
		System.out.println("원하시는 예금액을 입력하세요");
		long input = sc.nextInt();
		member.setBalance(member.getBalance()+input);
		System.out.println("입금되었습니다. 잔고: " + member.getBalance() );
		
		
		
	}
	@Override
	public void withDraw() {
		//예금액 입력하고 member객체 balance에 값 더해주기
		System.out.println("원하시는 출금액을 입력하세요");
		long input = sc.nextInt();
		member.setBalance(member.getBalance()-input);
		System.out.println("출금되었습니다. 잔고: " + member.getBalance());
				
		
		
	}

	

}
