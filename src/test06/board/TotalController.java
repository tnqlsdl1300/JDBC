package test06.board;

import java.util.*;

import test05.singleton.dbconnection.MyDBConnection;

public class TotalController {

	// DAO(Data Access Object) ==> 데이터베이스에 연결하여 관련된 업무(DDL, DML, DQL)를 처리해주는 객체이다
	InterMemberDAO mdao = new MemberDAO();
	InterBoardDAO bdao = new BoardDAO();

	// **** 시작메뉴 **** //
	public void menu_Start(Scanner sc) {
		MemberDTO member = null;
		String sChoice = null;
		do {
			String loginName = (member == null) ? "" : "[" + member.getName() + " 로그인중..]";
			String login_logout = (member == null) ? "로그인" : "로그아웃";

			System.out.println(">>> ----------- 시작메뉴 " + loginName + " ---------- <<<\n" + "1. 회원가입\t 2. "
					+ login_logout + "\t 3. 프로그램 종료\n" + "----------------------------------------------");

			System.out.print("▷ 메뉴번호 선택: ");

			sChoice = sc.nextLine();

			switch (sChoice) {
			case "1": // 1. 회원가입
				memberRegister(sc);
				break;

			case "2": // 2. 로그인 또는 로그아웃
				if (login_logout.equals("로그인")) {
					member = login(sc); // 로그인 시도하기
				} else {
					member = null;
					System.out.println("로그아웃 완료\n");
				}

				break;

			case "3": // 3. 프로그램 종료
				appExit(); // Connection 자원반납
				break;

			default:
				System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택하세요!! <<<");
				break;
			}// end of switch -------------------------------------------------

		} while (!("3".equals(sChoice))); // 3번을 선택하면 do-while문을 탈출

	}

	// **** 1. 회원가입 **** //
	private void memberRegister(Scanner sc) { // 같은 클래스기 때문에 private을 줘도 상관 없음

		System.out.println("\n >>> --- 회원가입 --- <<<");

		System.out.print("1. 아이디: ");
		String userid = sc.nextLine();

		System.out.print("2. 암호: ");
		String passwd = sc.nextLine();

		System.out.print("3. 회원명: ");
		String name = sc.nextLine();

		System.out.print("4. 연락처(휴대폰): ");
		String mobile = sc.nextLine();

		MemberDTO member = new MemberDTO(); // DB로 전송해줄 데이터를 담은 객체
		member.setUserid(userid);
		member.setPasswd(passwd);
		member.setName(name);
		member.setMobile(mobile);

		// MemberCtrl 클래스의 memberRegister()는 사용자로부터 입력받은 값을 MemberDTO 객체에 넣어주는 역할
		// MemberDAO 클래스의 memberRegister()는 Ctrl에서 받은 MemberDTO 객체를 DB에 insert 해주는 역할
		int n = mdao.memberRegister(member, sc);

		if (n == 1) {
			System.out.println("\n>>> 회원가입을 축하드립니다 <<<");
		} else {
			System.out.println("\n>>> 회원가입 취소!! <<<");
		}

		// 데이터베이스와 접속할 수 있는 객체 생성 => DAO(Database Access Object)

	}// end of memberRegister(Scanner sc) -----------------------

	private MemberDTO login(Scanner sc) {

		MemberDTO member = null;

		System.out.println("\n>>> --- 로그인 --- <<<");
		System.out.print("▷ 아이디: ");
		String userid = sc.nextLine();

		System.out.print("▷ 암호: ");
		String passwd = sc.nextLine();

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("passwd", passwd);

		member = mdao.login(paraMap);

		if (member != null) {
			System.out.println(">>> 로그인 성공!! <<<\n");
		} else {
			System.out.println(">>> 로그인 실패!! <<<\n");
		}

		return member;
	}// end of login(Scanner sc)---------------------------------

	// **** Connection 자원반납 **** //
	private void appExit() {
		MyDBConnection.closeConnection();
	}

}
