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
		members.add(new MemberDTO("user1", "name1", "123", 800000, 123456789));
		members.add(new MemberDTO("user2", "name2", "123", 200000, 123456787));
		members.add(new MemberDTO("user3", "name3", "123", 300000, 123456786));
	}
	
	//회원가입
	public void memberJoin() {
		boolean run = true;
		Random 	ran = new Random();
		
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
				members.add(new MemberDTO(id, name, pw, 0, 123 ));
				System.out.println(id + "님 회원가입이 완료되었습니다.");
				
				run = false;
				// 배열에 잘 입력되었는지 전체 멤버 확인
				
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
			System.out.println();
		}
	}
		
			
			
//			Member member = new Member(id,name,pw,balance);
//			members.add(member);
//			
//			System.out.println(members.get(0).toString());
//			
			
	
//	Member[] member = new Member[3];
//		Scanner sc = new Scanner(System.in);
//		//2.값을 입력받아 ArrayList에 새로운 Member객체 추가
//		System.out.print("가입하실 아이디를 입력하세요");
//		String id = sc.next();
//		System.out.print("이름를 입력하세요");
//		String name = sc.next();
//		System.out.print("비밀번호를 입력하세요");
//		String pw = sc.next();
//		long balance = 0; //회원가입시 잔고는 0을 디폴트로	
//		
//		Member memberInfo = new Member(id,name,pw,balance);
		
		
//	Member memberInfo = new Member("joohee","김주희","123",800000);
//	Member memberInfo2 = new Member("joohee2","김주희2","123",800000);
//	arr.add(memberInfo);
//	arr.add(memberInfo2);
//	
//	for(Member item:arr) {
//		System.out.println(item.getId());
//		System.out.println(item.getName());
//		System.out.println(item.getPw());
//		System.out.println(item.getBalance());
//	}
//	}
	
//	public Join(String id, String name, String pw, long balance) {
//		Scanner sc = new Scanner(System.in);
//	//2.값을 입력받아 ArrayList에 새로운 Member객체 추가
////		System.out.print("가입하실 아이디를 입력하세요");
////		id = sc.next();
////		System.out.print("이름를 입력하세요");
////		name = sc.next();
////		System.out.print("비밀번호를 입력하세요");
////		pw = sc.next();
////		balance = 0; //회원가입시 잔고는 0을 디폴트로
//	
//		Member memberInfo = new Member(id,name,pw,balance);
//		members.add(memberInfo);
//		
//		for(Member item:members) {
//			System.out.println(item.getId());
//			System.out.println(item.getName());
//			System.out.println(item.getPw());
//			System.out.println(item.getBalance());
//		}
//		//System.out.println(members);
//		
//	}
	
}
