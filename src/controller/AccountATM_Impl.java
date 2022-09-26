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

import Dao.Jdbc;
import model.Account;
import model.Member;



public class AccountATM_Impl implements AccountATM{
	Scanner sc = new Scanner(System.in);
	LocalDate nowdate = LocalDate.now();
	List<Account> accounts = new ArrayList<Account>();
	Account account;
	Connection conn= Jdbc.getInstance().getConnection();
	List<Member> members;
	Member member;
	//계좌생성
	@Override
	public void createAccount(String id, List<Account> accounts) {
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

			//계좌중복 확인
			Account account = existAccount(accountNo);
			if(account != null) {
				System.out.println(accountNo);
				System.out.println("중복됩니다 다시 생성합니다.");
			}else {
				PreparedStatement pstmt = null;
				try {
					System.out.println("account Table Insert Start...");
					pstmt = conn.prepareStatement("insert into account values(?,?,0,now())");
					pstmt.setString(1, accountNo);
					pstmt.setString(2, id);
//					pstmt.setLong(3, balance);
					int result = pstmt.executeUpdate();
					System.out.println("result -> " +result);
					String msg = result > -1 ? "계좌번호는 "+ accountNo1 + "-" +accountNo2+"-"+accountNo3+"입니다. \n" : "계좌생성 실패";
					System.out.println(msg);
					
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
			}
			
		}
	}
	
	
		@Override //계좌 존재확인
		public Account existAccount(String accountNo) {
			conn = Jdbc.getInstance().getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement("select * from account where accountNo = ?");
				pstmt.setString(1, accountNo);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3),rs.getString(4));
				}

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return account;

		}
	
	
//	//List에 Account 객체 정보 담기 -- select
//	public void saveAccountAll() {
//		
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement(selectAccountAll());
//			rs = pstmt.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int cols =rsmd.getColumnCount();
////			for (int i = 1; i <= cols ; i++) {
////				System.out.print(rsmd.getColumnName(i) + "\t"); //컬럼수만큼 반복문 돌려 컬럼이름 가져오기
////			}
//			System.out.println();
//			while (rs.next()) {
//				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
//				accounts.add(account);
//			}
////			for (int i = 0; i < accounts.size(); i++) {
////				System.out.println(accounts.get(i).getAccountNo());
////				System.out.println(accounts.get(i).getId());
////				System.out.println(accounts.get(i).getBalance());
////				System.out.println(accounts.get(i).getOadate());
////			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}finally {
//			try {
//				if (rs != null) rs.close();
//				if (pstmt != null) pstmt.close();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//	}
//	//account table의 모든 정보 가져오기
//	private String selectAccountAll() {
//		String sql = "Select * from account";
//		return sql;
//	}
	
	
	//1.잔고조회
	//로그인한 아이디의 계좌정보 Account객체에 값 넣기
	@Override
	public void setAccountInfo(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from account where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3),rs.getString(4));
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	//계좌정보 조회
	@Override
	public void viewBalance(String id) {
		//로그인 아이디의 객체정보
		setAccountInfo(id);
		System.out.println(account.getBalance());
		System.out.println("--------------------"+id+"님의 계좌정보입니다.--------------------");
		System.out.println("계좌정보\t\t잔고\t생성일\t");
		System.out.printf("%s\t%d원\t%s\t",account.getAccountNo(),account.getBalance(),account.getOadate());
		
		
	}

	//예금
	@Override
	public void deposit(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		System.out.print("예금하실 금액을 입력하세요.");
		long money = sc.nextLong();
		try {
			pstmt = conn.prepareStatement("update account set balance = balance + ? where id=?");
			pstmt.setLong(1, money);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			setAccountInfo(id);
			String msg = result > -1 ? money + "원 예금이 완료되었습니다. 예금 후 잔액은 "+ account.getBalance()+ "원 입니다." : "예금 실패";
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

	@Override
	public void withdraw(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		System.out.println(account.getBalance());
		setAccountInfo(id);
		try {
			System.out.print("출금하실 금액을 입력하세요.");
			long money = sc.nextLong();
			
			if (money <= account.getBalance()) {//출금할 금액이 예금액보다 같거나 작으면 출금 정상실행
				pstmt = conn.prepareStatement("update account set balance = balance - ? where id=?");
				pstmt.setLong(1, money);
				pstmt.setString(2, id);
				int result = pstmt.executeUpdate();
				setAccountInfo(id);
				String msg = result > -1 ? money + "원 출금이 완료되었습니다. 예금 후 잔액은 "+ account.getBalance()+ "원 입니다." : "예금 실패";
				System.out.println(msg);
			}else {// 출금액이 예금액보다 클때
				System.out.println("잔액이 부족합니다. 잔액을 확인 후 다시 시도해주세요.");
			}
			
			
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



	@SuppressWarnings({ "resource", "unused" })
	public void send(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {
			System.out.print("이체하실 계좌번호를 입력하세요.(띄어쓰기와 '-'없이 입력해주세요)");
			String otheraccountNo = sc.next();
			Account otheraccount = existAccount(otheraccountNo);//계좌 존재 확인
			otheraccountNo = otheraccount.getAccountNo();
			String otherid = otheraccount.getId();
			setAccountInfo(id);
			if(otheraccount != null) {//상대방의 계좌가 존재하면,
				System.out.println(otherid+"님에게 이체합니다.");
				System.out.print("금액을 입력하세요.");
				long money = sc.nextLong();
				if (money <= account.getBalance()) {//출금할 금액이 예금액보다 같거나 작으면 출금 정상실행
					pstmt = conn.prepareStatement("update account set balance = balance - ? where id=?");
					pstmt.setLong(1, money);
					pstmt.setString(2, id);
					int result = pstmt.executeUpdate();
					setAccountInfo(id);
					pstmt = conn.prepareStatement("update account set balance = balance + ? where accountNo=?");
					pstmt.setLong(1, money);
					pstmt.setString(2, otheraccountNo);
					int result2 = pstmt.executeUpdate();
					String msg = result > -1 ? otherid+ "님에게 "+ money + "원 이체 완료되었습니다. 이체 후 잔액은 "+ account.getBalance()+ "원 입니다." : "송금 실패";
					System.out.println(msg);
				}else {// 출금액이 예금액보다 클때
					System.out.println("잔액이 부족합니다. 잔액을 확인 후 다시 시도해주세요.");
				}
			
			}else {//계좌가 존재하지 않을 시
				System.out.println("계좌가 존재하지 않습니다. 다시 확인 후 시도해주세요.");
			}
			
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
	
	
	//송금
	//계좌별 거래내역 조회

}
