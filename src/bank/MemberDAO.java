package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MemberDAO {
	Scanner sc = new Scanner(System.in);
	List<MemberDTO> members = new ArrayList<MemberDTO>();
	MemberDTO member;
	
	public MemberDAO() {
		members.add(new MemberDTO("user1", "name1", "123", 800000, "1234567891234"));
		members.add(new MemberDTO("user2", "name2", "123", 200000, "1234567891235"));
		members.add(new MemberDTO("user3", "name3", "123", 300000, "1234567891236"));
	}
	
	//회원가입
	public void memberJoin() {
		boolean run = true;
		//계좌번호
		Random 	ran = new Random();
		int accountNo1 = ran.nextInt(9999) + 1000; //첫번째 칸 랜덤숫자
		int accountNo2 = ran.nextInt(999) + 100; //두번째 칸 랜덤숫자
		int accountNo3 = ran.nextInt(999999) + 100000; //셋번째 칸 랜덤숫자
		String accountNo = Integer.toString(accountNo1) 
							+ Integer.toString(accountNo2) + Integer.toString(accountNo3);
		
		System.out.print("가입하실 아이디를 입력하세요 >>");
		String 	id = sc.next();
		
		// 아이디 중복검사
		if(idCheck(id) == false) {
			System.out.println("이미 존재하는 ID입니다.");
		}else {//아이디 중복검사 통과 + 비밀번호 일치 시 회원가입 완료 
			System.out.print("이름를 입력하세요 >>");
			String name = sc.next();
			do {
				System.out.print("비밀번호를 입력하세요 >>");
			String pw = sc.next();
			System.out.print("비밀번호를 한번 더 입력하세요 >>");
			String pw2 = sc.next();
			if(pw.equals(pw2)) {
				//계좌번호가 중복하지 않을 때 까지 랜덤 계좌번호 생성
				while (accountNoCheck(accountNo)==false) {
					accountNo1 = ran.nextInt(9999) + 1000; //첫번째 칸 랜덤숫자
					accountNo2 = ran.nextInt(999) + 100; //두번째 칸 랜덤숫자
					accountNo3 = ran.nextInt(99999) + 10000; //셋번째 칸 랜덤숫자
					accountNo = Integer.toString(accountNo1) 
								+ Integer.toString(accountNo2) + Integer.toString(accountNo3);
					System.out.println(accountNo);
					System.out.println("중복됩니다 다시 생성합니다.");
					if (accountNoCheck(accountNo)==true) { 
					 break;	
					}
				}
					members.add(new MemberDTO(id, name, pw, 0, accountNo ));
					System.out.println(id + "님 회원가입이 완료되었습니다.");
					System.out.printf("계좌번호는 %d-%d-%d입니다. \n",accountNo1,accountNo2,accountNo3);
			
				run = false;
			} else {
				System.out.println("비밀번호 확인이 잘못되었습니다. "
						+ "나가시려면 1을, 비밀번호 설정을 다시하시려면 아무거나 눌러주세요.");
				String menuNum = sc.next();
				switch(menuNum) {
				case "1":
					run=false;
					break;
				default:
					run = true;
				}
			}
			} while (run);
			
			
		}			
	}	

	//아이디 중복 확인
	private boolean idCheck(String id) {
		boolean check = true;
		for (int i = 0; i < members.size(); i++) {
			if (id.equals(members.get(i).getId())) {
			check = false;
			}
		}
		return check;
	}
	
	//계좌번호 중복 확인
	private boolean accountNoCheck(String accountNo) {
		boolean check = true;
		for (int i = 0; i < members.size(); i++) {
			if (accountNo.equals(members.get(i).getAccountNo())) {
			check = false;
			}
		}
		return check;
		
	}
	
	//전체 가입자 목록 조회
	public void memberAll() {
		System.out.println("---------------전체 가입자 목록 조회--------------------");
		System.out.println("아이디\t이름\t비번\t잔고\t계좌번호");
		
		for(MemberDTO item:members) {
			System.out.print(item.getId() + "\t");
			System.out.print(item.getName()+ "\t");
			System.out.print(item.getPw()+ "\t");
			System.out.print(item.getBalance()+ "\t");
			System.out.println(item.getAccountNo()+ "\t");
			System.out.println(item.getAccountNo()+ "\t");
			System.out.println(item.getAccountNo()+ "\t");
			System.out.println();
		}
	}
		
			
			
//			Member member = new Member(id,name,pw,balance);
//			members.add(member);
//			
//			System.out.println(members.get(0).toString());
//			
			
	

	
}
