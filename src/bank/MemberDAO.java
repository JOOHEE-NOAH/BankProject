package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MemberDAO {
	List<MemberDTO> members = new ArrayList<MemberDTO>();
	MemberDTO member;
	Scanner sc = new Scanner(System.in);
//	public MemberDAO() {
//		members.add(new MemberDTO("user1", "name1", "123", 800000, "1234567891234"));
//		members.add(new MemberDTO("user2", "name2", "123", 200000, "1234567891235"));
//		members.add(new MemberDTO("user3", "name3", "123", 300000, "1234567891236"));
//	}

	// 회원가입
	public void memberJoin() {

		boolean run = true;
		// 계좌번호
		Random ran = new Random();
		int accountNo1 = ran.nextInt(9999) + 1000; // 첫번째 칸 랜덤숫자
		int accountNo2 = ran.nextInt(999) + 100; // 두번째 칸 랜덤숫자
		int accountNo3 = ran.nextInt(999999) + 100000; // 셋번째 칸 랜덤숫자
		String accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2) + Integer.toString(accountNo3);

		System.out.print("가입하실 아이디를 입력하세요 >>");
		String id = sc.next();

		// 아이디 중복검사
		if (idCheck(id) == false) {
			System.out.println("이미 존재하는 ID입니다.");
		} else {// 아이디 중복검사 통과 + 비밀번호 일치 시 회원가입 완료
			System.out.print("이름를 입력하세요 >>");
			String name = sc.next();
			do {
				System.out.print("비밀번호를 입력하세요 >>");
				String pw = sc.next();
				System.out.print("비밀번호를 한번 더 입력하세요 >>");
				String pw2 = sc.next();
				if (pw.equals(pw2)) {
					// 계좌번호가 중복하지 않을 때 까지 랜덤 계좌번호 생성
					while (accountNoCheck(accountNo) == false) {
						accountNo1 = ran.nextInt(9999) + 1000; // 첫번째 칸 랜덤숫자
						accountNo2 = ran.nextInt(999) + 100; // 두번째 칸 랜덤숫자
						accountNo3 = ran.nextInt(99999) + 10000; // 셋번째 칸 랜덤숫자
						accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2)
								+ Integer.toString(accountNo3);
						System.out.println(accountNo);
						System.out.println("중복됩니다 다시 생성합니다.");
						if (accountNoCheck(accountNo) == true) {
							break;
						}
					}
					// members.add(new MemberDTO(id, name, pw, 0, accountNo )); 원래라면 객체를 담는 List에
					// 저장하지만, 이를 파일에 바로 저장해 나중에 이 파일로 List 생성할것.
					member = new MemberDTO(id, name, pw, 0, accountNo);
					System.out.println(id + "님 회원가입이 완료되었습니다.");
					System.out.printf("계좌번호는 %d-%d-%d입니다. \n", accountNo1, accountNo2, accountNo3);
					fileInput(member);// 입력한 회원정보 파일에 담기
					fileOutput();// 반대로 파일에 담겨있던 회원정보를 List에 담기
					// members.add(member);
					for(int i =0; i< members.size(); i++) {
					System.out.println(members.get(i).toString());
					}

					run = false;
				} else {
					System.out.println("비밀번호 확인이 잘못되었습니다. " + "나가시려면 1을, 비밀번호 설정을 다시하시려면 아무거나 눌러주세요.");
					String menuNum = sc.next();
					switch (menuNum) {
					case "1":
						run = false;
						break;
					default:
						run = true;
					}
				}
			} while (run);

		}
	}

	// 회원가입시 회원정보를 파일에 차곡차곡 저장
	private void fileInput(MemberDTO member) {
		System.out.println("fileInputstart.....");// 확인
		try {// 파일에 중첩해서 회원정보 저장
			PrintWriter pw = new PrintWriter(new FileWriter(new File("BankMembers.text"), true));
			StringBuilder sb = new StringBuilder();
			sb.append(member.getId());
			sb.append("#");
			sb.append(member.getName());
			sb.append("#");
			sb.append(member.getPw());
			sb.append("#");
			sb.append(member.getBalance());
			sb.append("#");
			sb.append(member.getAccountNo());
			pw.println(sb.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 파일에 저장되어 있는 정보 꺼내 List에 저장
	public void fileOutput() {
		System.out.println("fileOutputStart....");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("BankMembers.text")));
			while (br.ready()) {
		//		System.out.println(br.readLine());
				StringTokenizer st = new StringTokenizer(br.readLine(),"#");// #을 기준으로 분리
		//		MemberDTO member = new MemberDTO(st.nextToken(), st.nextToken(), st.nextToken(),Long.parseLong(st.nextToken()), st.nextToken());
				String id = st.nextToken();
				String name = st.nextToken();
				String pw = st.nextToken();
				long balance = Long.parseLong(st.nextToken());
				String accountNo = st.nextToken();
//				System.out.print(st.nextToken()+"\t");
//				System.out.print(st.nextToken()+"\t");
//				System.out.print(st.nextToken()+"\t");
//				System.out.print(st.nextToken()+"\t");
//				System.out.print(st.nextToken());
				members.add(new MemberDTO(id,name,pw,balance, accountNo));
		//		members.add(new MemberDTO(st.nextToken(), st.nextToken(), st.nextToken(),Long.parseLong(st.nextToken()), st.nextToken()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 아이디 중복 확인
	private boolean idCheck(String id) {
		boolean check = true;
		for (int i = 0; i < members.size(); i++) {
			if (id.equals(members.get(i).getId())) {
				check = false;
			}
		}
		return check;
	}

	// 계좌번호 중복 확인
	private boolean accountNoCheck(String accountNo) {
		boolean check = true;
		for (int i = 0; i < members.size(); i++) {
			if (accountNo.equals(members.get(i).getAccountNo())) {
				check = false;
			}
		}
		return check;
	}

	// 전체 가입자 목록 조회
	public void memberAll() {
		System.out.println("---------------전체 가입자 목록 조회--------------------");
		System.out.println("아이디\t이름\t비번\t잔고\t계좌번호");
		fileOutput();
		for (MemberDTO item : members) {
			System.out.print(item.getId() + "\t");
			System.out.print(item.getName() + "\t");
			System.out.print(item.getPw() + "\t");
			System.out.print(item.getBalance() + "\t");
			System.out.println(item.getAccountNo() + "\t");
			System.out.println(item.getAccountNo() + "\t");
			System.out.println(item.getAccountNo() + "\t");
			System.out.println();
		}
	}

//			Member member = new Member(id,name,pw,balance);
//			members.add(member);
//			
//			System.out.println(members.get(0).toString());
//			

}
