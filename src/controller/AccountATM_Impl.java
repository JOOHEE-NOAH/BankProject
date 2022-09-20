package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Account;
import model.Member;


public class AccountATM_Impl {
	Scanner sc = new Scanner(System.in);
	LocalDate nowdate = LocalDate.now();
	List<Account> accounts = new ArrayList<Account>();
	Account account;
	Connection conn= Jdbc.getInstance().getConnection();
	List<Member> members;
	Member member;
	
	//계좌생성
	public void createAccount(String id) {
		System.out.println("createAccount Start....");
		// 계좌번호 랜덤생성
		Random ran = new Random();
		int accountNo1 = 1002;
		int accountNo2;
		int accountNo3;
		String accountNo;
//		int accountNo1 = 1002; // 첫번째 칸 랜덤숫자
//		int accountNo2 = ran.nextInt(998) + 100; // 두번째 칸 랜덤숫자
//		int accountNo3 = ran.nextInt(999998) + 100000; // 셋번째 칸 랜덤숫자
//		String accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2) + Integer.toString(accountNo3);
//		System.out.println("accountNo" + accountNo);
		//계좌번호가 중복되지 않는지 확인 하고, DB에 계좌정보 추가
//		while (accountNoCheck(accountNo)) {
//			accountNo2 = ran.nextInt(998) + 100; // 두번째 칸 랜덤숫자
//			accountNo3 = ran.nextInt(99998) + 10000; // 셋번째 칸 랜덤숫자
//			accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2)
//					+ Integer.toString(accountNo3);
//			System.out.println(accountNo);
//			System.out.println("중복됩니다 다시 생성합니다.");
//			if (accountNoCheck(accountNo) == true) {
//				//account 테이블에 insert
//				PreparedStatement pstmt = null;
//				try {
//					System.out.println("account Table Insert Start...");
//					pstmt = conn.prepareStatement(insertAccount());
//					pstmt.setString(1, accountNo);
//					pstmt.setString(2, id);
////					pstmt.setLong(3, balance);
//					int result = pstmt.executeUpdate();
//					System.out.println("result -> " +result);
//					String msg = result > -1 ? "계좌생성 성공" : "계좌생성 실패";
//					System.out.println(msg);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						if (pstmt != null)
//							pstmt.close();
////						if (conn != null) conn.close();
//					} catch (SQLException e) {
//						// TODO: handle exception
//					}
//				System.out.printf("계좌번호는 %d-%d-%d입니다. \n", accountNo1, accountNo2, accountNo3);	
//				} // db insert 기능 끝
//			}
//		}
		
		while (true) {
			accountNo2 = ran.nextInt(998) + 100; // 두번째 칸 랜덤숫자
			accountNo3 = ran.nextInt(99998) + 10000; // 셋번째 칸 랜덤숫자
			accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2)
					+ Integer.toString(accountNo3);
			boolean getResult = accountNoCheck(accountNo);
			
			if(getResult) {
				PreparedStatement pstmt = null;
				try {
					System.out.println("account Table Insert Start...");
					pstmt = conn.prepareStatement(insertAccount());
					pstmt.setString(1, accountNo);
					pstmt.setString(2, id);
//					pstmt.setLong(3, balance);
					int result = pstmt.executeUpdate();
					System.out.println("result -> " +result);
					String msg = result > -1 ? "계좌생성 성공" : "계좌생성 실패";
					System.out.println(msg);
					System.out.printf("계좌번호는 %d-%d-%d입니다. \n", accountNo1, accountNo2, accountNo3);	
					break;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
					System.out.println("계좌등록 에러입니다");
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
//						if (conn != null) conn.close();
					} catch (SQLException e) {

					}
				} // db insert 기능 끝
					

			}else {
				System.out.println(accountNo);
				System.out.println("중복됩니다 다시 생성합니다.");
			}
			
		}
	}
	
	private static String insertAccount() {
		String sql = "insert into account values(?,?,0,now())";
		return sql;
	}

	// 계좌번호 중복 확인
	private boolean accountNoCheck(String accountNo) {
		System.out.println("accountNoCheck Start");
		System.out.println("accountNo = "+ accountNo);
		boolean check = true;
		System.out.println("accounts.size -> " + accounts.size());
		for (int i = 0; i < accounts.size(); i++) {
			if (accountNo.equals(accounts.get(i).getAccountNo())) {
				check = false;
				break;
			}
		}
		return check;
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
//			for (int i = 1; i <= cols ; i++) {
//				System.out.print(rsmd.getColumnName(i) + "\t"); //컬럼수만큼 반복문 돌려 컬럼이름 가져오기
//			}
			System.out.println();
			while (rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
				accounts.add(account);
			}
//			for (int i = 0; i < accounts.size(); i++) {
//				System.out.println(accounts.get(i).getAccountNo());
//				System.out.println(accounts.get(i).getId());
//				System.out.println(accounts.get(i).getBalance());
//				System.out.println(accounts.get(i).getOadate());
//			}
			
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
	
	
	//1.잔고조회
	public void viewBalance(String id) {
		System.out.println(id+"님의 잔고 조회를 시작합니다.");
		
		
	}

	
	//입금
	//출금
	//송금
	//계좌별 거래내역 조회

}
