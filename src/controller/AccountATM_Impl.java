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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

public class AccountATM_Impl implements AccountATM {
	Scanner sc = new Scanner(System.in);
	LocalDate nowdate = LocalDate.now();
	MemberATM_Impl m = new MemberATM_Impl();
	Member member;
	Account account;
	List<Account> accounts = new ArrayList<Account>();
	List<Member> members;
	Connection conn = Jdbc.getInstance().getConnection();

	// 계좌생성
	@Override
	public void createAccount(String id) {
		// 계좌번호 랜덤생성
		Random ran = new Random();
		int accountNo1 = 1002;
		int accountNo2;
		int accountNo3;
		String accountNo;
		while (true) {
			accountNo2 = ran.nextInt(899) + 100; // 두번째 칸 랜덤숫자
			accountNo3 = ran.nextInt(89999) + 100000; // 셋번째 칸 랜덤숫자
			accountNo = Integer.toString(accountNo1) + Integer.toString(accountNo2) + Integer.toString(accountNo3);

			// 계좌중복 확인
			Account account = existAccount(accountNo);
			if (account != null) {//계좌번호 중복될경우
				System.out.println("중복됩니다 다시 생성합니다.");
			} else {// 계좌번호 중복되지 않으면 계좌생성
				
				createAccountInsert(accountNo, id);//insert실행
				System.out.println("계좌번호는 " + accountNo1 + "-" + accountNo2 + "-" + accountNo3 + "입니다. \n");
				//생성된 계좌번호로 [계좌번호+BankHistory] 파일 만들기(자기 계좌로 된 메모장 최초생성)
				fileInput(id,"계좌생성", accountNo, 0, 0, 0);
				break;
			}

		}
	}
	
	//계좌생성 insert 실행
	public void createAccountInsert(String accountNo, String id) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into account values(?,?,0,now())");
			pstmt.setString(1, accountNo);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			String msg = result > -1 ? " " : "계좌생성 실패";
			System.out.println(msg);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {

			}
		}
	}
	
	//계좌번호 존재확인(중복확인)
	@Override
	public Account existAccount(String accountNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from account where accountNo = ?");
			pstmt.setString(1, accountNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
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
		return account;

	}

	//아이디의 계좌정보 Account객체에 값 넣기
	@Override
	public void setAccountInfo(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from account where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account(rs.getString(1), rs.getString(2), rs.getLong(3), rs.getString(4));
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

	// 로그인 회원 계좌정보 조회
	@Override
	public void viewBalance(String id,String name) {
		// 로그인 아이디의 객체정보 불러오기
		setAccountInfo(id);
		
		System.out.println("--------------------" + name + "님의 계좌정보입니다.--------------------");
		System.out.println("계좌정보\t\t잔고\t생성일\t");
		System.out.printf("%s\t%d원\t%s\t", account.getAccountNo(), account.getBalance(), account.getOadate());

	}

	// 예금
	@Override
	public void deposit(String id) {
		PreparedStatement pstmt = null;
		System.out.print("예금하실 금액을 입력하세요.");
		long money = sc.nextLong();
		try {
			pstmt = conn.prepareStatement("update account set balance = balance + ? where id=?");
			pstmt.setLong(1, money);
			pstmt.setString(2, id);
			int result = pstmt.executeUpdate();
			setAccountInfo(id);
			String msg = result > -1 ? money + "원 예금이 완료되었습니다. 예금 후 잔액은 " + account.getBalance() + "원 입니다." : "예금 실패";
			System.out.println(msg);

			// 입금 완료 후 거래내역에 저장
			/* id , 거래종류, 계좌번호 , 출금액, 입금액, 잔액 메모장 저장 */
			fileInput(id, "입금", account.getAccountNo(), 0, money, account.getBalance());
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

	// 출금
	@Override
	public void withdraw(String id) {
		PreparedStatement pstmt = null;
		setAccountInfo(id);
		try {
			System.out.print("출금하실 금액을 입력하세요.");
			long money = sc.nextLong();
			if (money <= account.getBalance()) {// 출금할 금액이 예금액보다 같거나 작으면 출금 정상실행
				pstmt = conn.prepareStatement("update account set balance = balance - ? where id=?");
				pstmt.setLong(1, money);
				pstmt.setString(2, id);
				int result = pstmt.executeUpdate();
				setAccountInfo(id);
				String msg = result > -1 ? money + "원 출금이 완료되었습니다. 예금 후 잔액은 " + account.getBalance() + "원 입니다."
						: "예금 실패";
				System.out.println(msg);

				// 출금 완료 후 거래내역에 저장
				/* id , 거래종류, 계좌번호 , 출금액, 입금액, 잔액 메모장 저장 */
				fileInput(id, "출금", account.getAccountNo(), money, 0, account.getBalance());
			} else {// 출금액이 예금액보다 클때
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

	// 송금
	@SuppressWarnings({ "resource", "unused" })
	public void send(String id) {
		conn = Jdbc.getInstance().getConnection();
		PreparedStatement pstmt = null;
		Account otheraccount = null;
		try {

			System.out.print("이체하실 계좌번호를 입력하세요.(띄어쓰기와 '-'없이 입력해주세요)");
			String otheraccountNo = sc.next();
			otheraccount = existAccount(otheraccountNo);// 상대계좌 존재 확인
			if (otheraccount != null) {// 상대방의 계좌가 존재하면,
				if (otheraccountNo.equals(otheraccount.getAccountNo())) {

					otheraccountNo = otheraccount.getAccountNo();
					String otherid = otheraccount.getId();
					MemberATM_Impl otherm = new MemberATM_Impl();
					//상대방 정보받기
					Member memberm = otherm.existMember(otherid);
					String othername = memberm.getName();
					// 다시 로그인 회원정보
					setAccountInfo(id);
					m.existMember(id);
					System.out.println(othername + "님에게 이체합니다.");
					System.out.print("금액을 입력하세요.");
					long money = sc.nextLong();
					System.out.println("현재 나의 잔액은?"+account.getBalance());
					if (money <= account.getBalance()) {// 출금할 금액이 예금액보다 같거나 작으면 출금 정상실행
						pstmt = conn.prepareStatement("update account set balance = balance - ? where id=?");
						pstmt.setLong(1, money);
						pstmt.setString(2, id);
						int result = pstmt.executeUpdate();
						setAccountInfo(id);
						pstmt = conn.prepareStatement("update account set balance = balance + ? where accountNo=?");
						pstmt.setLong(1, money);
						pstmt.setString(2, otheraccountNo);
						int result2 = pstmt.executeUpdate();
						String msg = result > -1
								? othername + "님에게 " + money + "원 이체 완료되었습니다. 이체 후 잔액은 " + account.getBalance() + "원 입니다."
								: "송금 실패";
						System.out.println(msg);

						// 송금 완료 후 거래내역에 저장
						/* 1.보낸사람통장 표시 */
						System.out.println("보낸사람 통장 잔액->" + account.getBalance());
						/* id , 거래종류, 계좌번호 , 출금액, 입금액, 잔액 메모장 저장 */
						fileInput(id, "이체", account.getAccountNo(), money, 0, account.getBalance());
						/* 2.받은사람통장 표시 */
						setAccountInfo(otherid);
						System.out.println("받은사람 통장 잔액->" + account.getBalance());
						/* id , 거래종류, 계좌번호 , 출금액, 입금액, 잔액 메모장 저장 */
						fileInput(otherid, "이체", account.getAccountNo(), 0, money, account.getBalance());
					} else {// 출금액이 예금액보다 클때
						System.out.println("잔액이 부족합니다. 잔액을 확인 후 다시 시도해주세요.");
					}
				} else {// 계좌가 존재하지 않을 시
					System.out.println("계좌가 존재하지 않습니다. 다시 확인 후 시도해주세요.");
				}
			} else {
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

	/*-------------------거래내역 조회------------------------------ */
	//1. 입금,출금,송금 발생 시 (id, 거래일 ,거래종류 ,출금액,입금액,잔액 입력)하여 메모장 저장
	public void fileInput(String id,String type,String accountNo,long withdraw, long deposit,long balance) {
		System.out.println("fileInputstart.....");// 확인
		LocalDateTime now = LocalDateTime.now();
	    String date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		try {// 파일에 중첩해서 회원정보 저장
			PrintWriter pw = new PrintWriter(new FileWriter(new File(accountNo+"BankHistory.text"), true));
			StringBuilder sb = new StringBuilder();
			sb.append(id);
			sb.append("#");
			sb.append(date);
			sb.append("#");
			sb.append(type);
			sb.append("#");
			sb.append(accountNo);
			sb.append("#");
			sb.append(withdraw);
			sb.append("#");
			sb.append(deposit);
			sb.append("#");
			sb.append(balance);
			pw.println(sb.toString());
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//2.BankHistory.text파일에 저장되어 있는 정보 꺼내 List<Account>에 저장
	public void fileOutput(String accountNo) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(accountNo+"BankHistory.text")));
			while (br.ready()) {
				StringTokenizer st = new StringTokenizer(br.readLine(), "#");// #을 기준으로 분리
				String id = st.nextToken();
				String date = st.nextToken();
				String type = st.nextToken();
				accountNo = st.nextToken();
				long withdraw = Long.parseLong(st.nextToken());
				long deposit = Long.parseLong(st.nextToken());
				long balance = Long.parseLong(st.nextToken());

				accounts.add(new Account(id, date, type, accountNo, withdraw,deposit,balance));
				for (int i = 0; i < accounts.size(); i++) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 3.거래내역조회
	public void bankHisotry(String id) {
		setAccountInfo(id);
		String accountNo = account.getAccountNo();
		fileOutput(accountNo);
		Member member = m.existMember(id);
		String name = member.getName();
		System.out.println("--------------------" + name + "님의 거래내역입니다.--------------------");
		System.out.println("거래일자\t\t\t\t거래분류\t계좌번호\t\t출금액\t\t입금액\t\t거래후잔액\t");
		for (int i = 0; i < accounts.size(); i++) {
			if (id.equals(accounts.get(i).getId())) {
				
				System.out.printf("%s\t%s\t%s\t%d원\t\t%d원\t\t%d원\t\t\n", accounts.get(i).getTradeDate(), accounts.get(i).getType(),
						accounts.get(i).getAccountNo(), accounts.get(i).getWithdraw(), accounts.get(i).getDeposit(),
						accounts.get(i).getBalance());

			}
		}

	}
	
	

}
