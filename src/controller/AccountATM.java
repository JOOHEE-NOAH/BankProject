package controller;

import java.util.List;

import model.Account;

public interface AccountATM {

	/* 계좌생성 */
	void createAccount(String id);

	/* 계좌생성 insert */
	void createAccountInsert(String accountNo,String id);
	
	/* 계좌번호 존재확인(중복확인) */
	Account existAccount(String accountNo);

	/* 아이디의 계좌정보 Account객체에 값 넣기 */
	void setAccountInfo(String id);

	/* 로그인 회원 계좌정보 조회 */
	void viewBalance(String id, String name);

	/* 예금 */
	void deposit(String id);

	/* 출금 */
	void withdraw(String id);

	/* 송금 */
	void send(String id);

	/* 입금,출금,송금 발생 시 (id, 거래일 ,거래종류 ,출금액,입금액,잔액 입력)하여 메모장 저장 */
	void fileInput(String id, String type, String accountNo, long withdraw, long deposit, long balance);

	// BankHistory.text파일에 저장되어 있는 정보 꺼내 List<Account>에 저장
	public void fileOutput(String account);

	// 거래내역조회
	public void bankHisotry(String id);

}
