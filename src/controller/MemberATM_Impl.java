package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import Dao.Jdbc;
import model.Account;
import model.Member;
import view.AdminPage;
import view.MemberPage;

public class MemberATM_Impl implements MemberATM {
	Scanner sc = new Scanner(System.in);
	LocalDate nowdate = LocalDate.now();
	List<Member> members = new ArrayList<Member>();
	List<Account> accounts = new ArrayList<Account>();
	AccountATM_Impl a = new AccountATM_Impl();
	Account account;
	Member member;
	Connection conn;

	// 회원가입
	public void memberJoin() {
		member = null;
		conn = Jdbc.getInstance().getConnection();
		String grade = "C";// 회원가입시 기본 등급(C) 설정
		System.out.print("가입하실 아이디를 입력하세요 >>");
		String id = sc.next();
		boolean run = true;

		// 아이디 중복검사
		Member member = existMember(id);
		if (member != null) { // 아이디 중복됨
			System.out.println("이미 존재하는 ID입니다.");
			System.out.println("중복되는 아이디 이름" + member.getId());
		} else {// 아이디 중복검사 통과 + 비밀번호 일치 시 회원가입 완료
			System.out.print("이름를 입력하세요 >>");
			String name = sc.next();
			do {
				System.out.print("비밀번호를 입력하세요 >>");
				String pw = sc.next();
				System.out.print("비밀번호를 한번 더 입력하세요 >>");
				String pw2 = sc.next();
				if (pw.equals(pw2)) {

					// 데이터베이스 테이블 member에 추가하기
					PreparedStatement pstmt = null;
					try {
						pstmt = conn.prepareStatement("insert into member values(?,?,?,?,now())");
						pstmt.setString(1, id);
						pstmt.setString(2, name);
						pstmt.setString(3, pw);
						pstmt.setString(4, grade);
						int result = pstmt.executeUpdate();
						String msg = result > -1 ? id + "님 회원가입이 완료되었습니다." : "회원정보 추가실패";
						System.out.println(msg);
						//회원가입과 동시에 자동계좌생성
						a.createAccount(id);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (pstmt != null)
								pstmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

//					// members.add(new MemberDTO(id, name, pw, 0, accountNo )); 원래라면 객체를 담는 List에
//					// 저장하지만, 이를 파일에 바로 저장해 나중에 이 파일로 List 생성할것.
//					member = new MemberDTO(id, name, pw, grade,rdate);
//					System.out.println(id + "님 회원가입이 완료되었습니다.");
//					System.out.printf("계좌번호는 %d-%d-%d입니다. \n", accountNo1, accountNo2, accountNo3);
//					fileInput(member);// 입력한 회원정보 파일에 담기
////					fileOutput();// 반대로 파일에 담겨있던 회원정보를 List에 담기
//					// members.add(member);
//					for (int i = 0; i < members.size(); i++) {
//						System.out.println(members.get(i).toString());
//					}

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

//	// 회원가입시 회원정보를 파일에 차곡차곡 저장
//	private void fileInput(Member member) {
//		System.out.println("fileInputstart.....");// 확인
//		try {// 파일에 중첩해서 회원정보 저장
//			PrintWriter pw = new PrintWriter(new FileWriter(new File("BankMembers.text"), true));
//			StringBuilder sb = new StringBuilder();
//			sb.append(member.getId());
//			sb.append("#");
//			sb.append(member.getName());
//			sb.append("#");
//			sb.append(member.getPw());
//			sb.append("#");
////			sb.append(member.getBalance());
////			sb.append("#");
////			sb.append(member.getAccountNo());
//			pw.println(sb.toString());
//			pw.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	@Override // 회원탈퇴
	public void memberDrop(String id) {
		System.out.println("회원탈퇴를 시작합니다.");
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		System.out.println("비밀번호를 입력하세요 : ");
		String pw = sc.next();
		try {
			pstmt = conn.prepareStatement("Delete From member Where id = ? and pw =?");
			pstmt.setString(1, id); // 첫번째 물음표는 id로 설정
			pstmt.setString(2, pw);
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? "성공적으로 회원탈퇴 되었습니다." : "실패";
			System.out.println(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	// 로그인
	public void login() {
		System.out.println("id와 pw를 입력하세요");
		System.out.print("id : ");
		String id = sc.next();
		System.out.print("pw : ");
		String pw = sc.next();
		// 로그인 검사
		Member member = loginCheck(id, pw);
		if (member != null) {
			if (id.equals("admin")) {
				AdminPage adminPage = new AdminPage();
				adminPage.adminPageView();
			} else {
				MemberPage memberPage = new MemberPage();
//				setMemberInfo(id);
//				setAccountInfo(id);
//				a.setAccountInfo(id);
				memberPage.memberPageView(id);// myPage로 넘어가기
			}
		} else {
			System.out.println("아이디 또는 패스워드 오류입니다.");
		}
		;

	}

	// 로그인 아이디/비번 체크
	private Member loginCheck(String id, String pw) {
		System.out.println("로그인 검사 시작");
		System.out.println("아이디=" + id);
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member where id = ? and pw = ?");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return member;
	}

	// 회원 존재확인(중복확인) 및 입력한 id정보 Member객체 담기
	public Member existMember(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("가입하려는 id="+id);
		try {
			pstmt = conn.prepareStatement("select * from member where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return member;

	}

	// Member정보 List 저장
	@Override
	public void saveMemberAll() {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select * from member");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				members.add(member);
			}
//			for (int i = 0; i < members.size(); i++) {
//				System.out.println(members.get(i).getId());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//Account정보 List 저장
	public void saveAccountAll() {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select * from account");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
				accounts.add(account);
			}
//			for (int i = 0; i < accounts.size(); i++) {
//				System.out.println(accounts.get(i).getId());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// 전체 사용자 정보 조회
	public void memberInfoAllforAdmin() {
		//전체 조회 전 등급 업데이트
		updateGradeVip();
		updateGradeA();
		updateGradeB();
		updateGradeC();
		
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select  rdate, m.id, name, accountno, balance, grade\r\n"
					+ "from member m\r\n"
					+ "inner join account a\r\n"
					+ "on m.id = a.id\r\n"
					+ "and m.id != 'admin'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(1), rs.getString(4), rs.getLong(5));
				members.add(member);
				
				
			}
			if (members.size() != 0) {

				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println("가입일\t\t아이디\t\t이름\t\t계좌번호\t\t등급\t\t잔액\t");
				System.out.println("-----------------------------------------------------------------------------------");
				for (int i = 0; i < members.size(); i++) {
					System.out.println(members.get(i).getRdate() + "\t" + members.get(i).getId() + "\t\t"
							+ members.get(i).getName() + "\t\t" + members.get(i).getAccountNo() + "\t"
							+ members.get(i).getGrade() + "\t\t" + members.get(i).getBalance() + "\t");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		

	}
	
	//등급별 회원 수 조회
	public void gradeCount() {
		System.out.println("등급별 회원수를 조회합니다.");
		boolean run = true;
		String grade = null;
		do {
			System.out.println("------------------");
			System.out.println("1.VIP 2.A 3.B 4.C 5.뒤로가기 ");
			System.out.println("------------------");
			System.out.print("원하시는 번호를 선택하세요");
			int pickNum = sc.nextInt(); // 선택 번호
			switch (pickNum) {
			case 1://VIP선택
				grade = "VIP";
				updateGradeVip();
				break;
			case 2://A선택
				grade = "A";
				updateGradeA();
				break;
			case 3://B선택
				grade = "B";
				updateGradeB();
				break;
			case 4://C선택
				grade = "C";
				updateGradeC();
				break;
			case 5: //뒤로가기
				grade = null;
				run = false;
				break;
			default:
				System.out.println("Wrong Answer");
				break;
			}
			if (grade != null) {
				selectGradeCount(grade);
			}
			
		} while (run);

	}
	
	//등급별 회원 수 조회
	public void selectGradeCount(String grade) {
		System.out.println("넘어온 grade 값"+ grade);
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int gradeCount = 0;
		try {
			pstmt = conn.prepareStatement("SELECT grade, COUNT(*) FROM member\r\n"
										+ " GROUP BY grade\r\n"
										+ " having grade = ?");
			pstmt.setString(1, grade);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				gradeCount = rs.getInt(2);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("--------------"+grade+"등급 회원수------------------");
		System.out.println(grade + " : " + gradeCount +"명");
	}

	// 등급별 회원 조회 시 VIP 등급으로 업데이트하는 메소드(잔고기준 상위 0~10%)
	@Override
	public void updateGradeVip() {
		System.out.println("Vip등급 먹이기 시작");
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update member m, account a set m.grade = 'VIP'\r\n"
					+ "where m.id = a.id\r\n"
					+ "and m.id in (SELECT a.id\r\n"
					+ "FROM (SELECT account.id, PERCENT_RANK() OVER (ORDER BY account.balance DESC) as per_rank FROM account) a\r\n"
					+ "WHERE a.per_rank <= 0.1)");
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? "회원등급 자동업데이트완료" : "등급 변경 실패";
			System.out.println(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 등급별 회원 조회 시 A 등급으로 업데이트하는 메소드(잔고기준 상위 10~20%)
	@Override
	public void updateGradeA() {
		System.out.println("A등급 먹이기 시작");
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {

			pstmt = conn.prepareStatement("update member m, account a set m.grade = 'A'\r\n"
					+ "where m.id = a.id\r\n"
					+ "and m.id in (SELECT a.id\r\n"
					+ "FROM (SELECT account.id, PERCENT_RANK() OVER (ORDER BY account.balance DESC) as per_rank FROM account) a\r\n"
					+ "WHERE a.per_rank > 0.1 and a.per_rank <=0.3)");
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? "회원등급 자동업데이트완료" : "등급 변경 실패";
			System.out.println(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 등급별 회원 조회 시 B 등급으로 업데이트하는 메소드(잔고기준 상위 20~30%)
	@Override
	public void updateGradeB() {
		System.out.println("C등급 먹이기 시작");
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update member m, account a set m.grade = 'B'\r\n"
					+ "where m.id = a.id\r\n"
					+ "and m.id in (SELECT a.id\r\n"
					+ "FROM (SELECT account.id, PERCENT_RANK() OVER (ORDER BY account.balance DESC) as per_rank FROM account) a\r\n"
					+ "WHERE a.per_rank > 0.3 and a.per_rank <=0.5)");
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? "회원등급 자동업데이트완료" : "등급 변경 실패";
			System.out.println(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 등급별 회원 조회 시 C 등급으로 업데이트하는 메소드(잔고기준 상위 30%~100%)
	@Override
	public void updateGradeC() {
		System.out.println("C등급 먹이기 시작");
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update member m, account a set m.grade = 'C'\r\n"
					+ "where m.id = a.id\r\n"
					+ "and m.id in (SELECT a.id\r\n"
					+ "FROM (SELECT account.id, PERCENT_RANK() OVER (ORDER BY account.balance DESC) as per_rank FROM account) a\r\n"
					+ "WHERE a.per_rank > 0.5)");
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? "회원등급 자동업데이트완료" : "등급 변경 실패";
			System.out.println(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	//
//		// 파일에 저장되어 있는 정보 꺼내 List에 저장
//		public void fileOutput() {
//			System.out.println("fileOutputStart....");
//			try {
//				BufferedReader br = new BufferedReader(new FileReader(new File("BankMembers.text")));
//				while (br.ready()) {
//					StringTokenizer st = new StringTokenizer(br.readLine(), "#");// #을 기준으로 분리
//					String id = st.nextToken();
//					String name = st.nextToken();
//					String pw = st.nextToken();
//					String grade = st.nextToken();
//					String rdate = st.nextToken();
	//
//					members.add(new MemberDTO(id, name, pw, grade, rdate));
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	//
//		}



}
