package bank;

import java.util.ArrayList;
import java.util.List;

public class MemberList {
	List<MemberDTO> arr;
	MemberDTO memberInfo;
	MemberDTO memberInfo2;
	
	public MemberList() {
		arr = new ArrayList<MemberDTO>();
	
		memberInfo = new MemberDTO("joohee","김주희","123",800000,123456);
		memberInfo2 = new MemberDTO("joohee2","김주희2","123",800000,123456);
	
	
	arr.add(memberInfo);
	arr.add(memberInfo2);
	
	System.out.println("---------------전체 가입자 목록 조회--------------------");
	System.out.println("아이디\t이름\t비번\t잔고");
	
	for(MemberDTO item:arr) {
		System.out.print(item.getId() + "\t");
		System.out.print(item.getName()+ "\t");
		System.out.print(item.getPw()+ "\t");
		System.out.print(item.getBalance());
		System.out.println();
	}
	}
	
	
	
}
