package test06.board;

import java.util.Map;
import java.util.Scanner;

public interface InterMemberDAO {

	// 회원가입 메서드
	int memberRegister(MemberDTO member, Scanner sc);

	// 로그인처리 메서드
	MemberDTO login(Map<String, String> paraMap);

}
