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
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import Dao.Jdbc;
import model.Account;
import model.Member;
import view.MemberPage;

public class MemberATM_Impl implements MemberATM {
	Scanner sc = new Scanner(System.in);
	LocalDate nowdate = LocalDate.now();
	List<Member> members = new ArrayList<Member>();
	List<Account> accounts = new ArrayList<Account>();
	AccountATM_Impl a = new AccountATM_Impl();
	Member member;
	Account account;
	Connection conn;
	
	
	// 회원가입
	public void memberJoin() {
		//conn= Jdbc.getInstance().getConnection();
		conn = Jdbc.getInstance().getConnection();
		boolean run = true;
		//회원가입일
		//String rdate = nowdate.format(DateTimeFormatter.ofPattern("yyyy년mm월dd일"));
		//회원가입시 기본 등급
		String grade = "C";
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
					
					// 데이터베이스 테이블 member에 추가하기
					PreparedStatement pstmt = null;
					try {
						System.out.println("member Table Insert Start...");
						pstmt = conn.prepareStatement("insert into member values(?,?,?,?,now())");
						pstmt.setString(1, id);
						pstmt.setString(2, name);
						pstmt.setString(3, pw);
						pstmt.setString(4, grade);
						int result = pstmt.executeUpdate();
						System.out.println("result -> " + result);
					
						String msg = result > -1 ? "회원정보 추가성공" : "회원정보 추가실패";
						System.out.println(msg);
						System.out.println(id + "님 회원가입이 완료되었습니다.");
						a.createAccount(id,accounts);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (pstmt != null)
								pstmt.close();
//							if (conn != null) conn.close();
						} catch (SQLException e) {
							// TODO: handle exception
						}
					} // db insert 기능 끝
					System.out.println(id + "님 회원가입이 완료되었습니다.");
					//비밀번호 확인까지 끝나면 기본계좌 자동생성
					
					
					
					
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
					System.out.println("비밀번호 확인이 잘못되었습니다. " 
									+ "나가시려면 1을, 비밀번호 설정을 다시하시려면 아무거나 눌러주세요.");
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
	
	@Override //회원의 모든 정보 가져와 리스트에 담기--select ->아이디 중복확인
	public void saveMemberAll() {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select * from member");
			rs = pstmt.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int cols =rsmd.getColumnCount();
//			for (int i = 1; i <= cols ; i++) {
//				System.out.print(rsmd.getColumnName(i) + "\t"); //컬럼수만큼 반복문 돌려 컬럼이름 가져오기
//			}
//			System.out.println();
//			members = new ArrayList<MemberDTO>();
			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
				members.add(member);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
			//	if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
//
//	// 파일에 저장되어 있는 정보 꺼내 List에 저장
//	public void fileOutput() {
//		System.out.println("fileOutputStart....");
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(new File("BankMembers.text")));
//			while (br.ready()) {
//				StringTokenizer st = new StringTokenizer(br.readLine(), "#");// #을 기준으로 분리
//				String id = st.nextToken();
//				String name = st.nextToken();
//				String pw = st.nextToken();
//				String grade = st.nextToken();
//				String rdate = st.nextToken();
//
//				members.add(new MemberDTO(id, name, pw, grade, rdate));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

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
	// 전체 가입자 목록 조회
//	public void memberAll() {
//		System.out.println("---------------전체 가입자 목록 조회--------------------");
//		System.out.println("아이디\t이름\t비번\t등급\t회원가입일");
////		fileOutput();
//		for (MemberDTO item : members) {
//			System.out.print(item.getId() + "\t");
//			System.out.print(item.getName() + "\t");
//			System.out.print(item.getPw() + "\t");
//			System.out.print(item.getGrade() + "\t");
//			System.out.print(item.getRdate() + "\t");
//			System.out.println();
//		}
//	}

	@Override // 회원탈퇴
	public void memberDrop() {
		System.out.println("회원탈퇴를 시작합니다.");
		conn= Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		System.out.print("id를 입력하세요 : ");
		String id = sc.next();
		System.out.println("비밀번호를 입력하세요 : ");
		String pw = sc.next();
		try {
			pstmt = conn.prepareStatement(delete());
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
//				if (conn != null) conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}

	}

	// 데이터베이스 테이블 member에 회원 삭제하기
	public static String delete() {
		String sql = "Delete From member Where id = ? and pw =?";
		return sql;
	}
	//로그인
	public void login() {
		System.out.println("id와 pw를 입력하세요");
		System.out.print("id : ");
		String id = sc.next();
		System.out.print("pw : ");
		String pw = sc.next();
		//로그인 검사
		if (loginCheck(id,pw) == true ) { 
			if(id == "admin"){
				System.out.println("관리자페이지로 이동합니다.");
			
			}else {
				System.out.println("일반회원페이지로 이동");
				MemberPage memberPage = new MemberPage();
				setMemberInfo(id);
				setAccountInfo(id);
				a.setAccountInfo(id);
				memberPage.memberPageView(member, members, account,accounts );//myPage로 넘어가기
			}
		}else {
			System.out.println("아이디 또는 패스워드 오류입니다.");
		};
	
	}



	//로그인 아이디/비번 체크
	private boolean loginCheck(String id, String pw) {
		boolean check = false;
			for (int i = 0; i < members.size(); i++) {
				if (id.equals(members.get(i).getId())) {
					if(pw.equals(members.get(i).getPw())) { //가져온 인덱스번호로 pw 일치여부 확인
						check = true;
						return check;
					} 
				}
			}
		return check;
	}
	
	//로그인한 아이디로 리스트 검색해 멤버객체에 값 넣기
	public void setMemberInfo(String id) {
		conn = Jdbc.getInstance().getConnection();
		System.out.println("세팅을 시작합니다.");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(selectMemberInfo());
			System.out.println("a");
			System.out.println("b");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
			}
			System.out.println("회원정보"+ member.getId());
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

	private String selectMemberInfo() {
		String sql = "select * from member where id = ?";
		return sql;
	}
	
	//List에 Account 객체 정보 담기 -- select
		public void saveAccountAll() {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(selectAccountAll());
				rs = pstmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int cols =rsmd.getColumnCount();
//				for (int i = 1; i <= cols ; i++) {
//					System.out.print(rsmd.getColumnName(i) + "\t"); //컬럼수만큼 반복문 돌려 컬럼이름 가져오기
//				}
				System.out.println();
				while (rs.next()) {
					account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
					accounts.add(account);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		//account table의 모든 정보 가져오기
		private String selectAccountAll() {
			String sql = "Select * from account";
			return sql;
		}
		
		//로그인한 아이디의 계좌정보 객체에 담아주기
		private void setAccountInfo(String id) {
			conn = Jdbc.getInstance().getConnection();
			System.out.println("개인 계좌세팅을 시작합니다.");
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(selectAccountInfo());
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
				}
				System.out.println("회원계좌정보"+ account.getAccountNo());
				
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		private String selectAccountInfo() {
			String sql = "select * from account where id = ?";
			return sql;
		}

}
