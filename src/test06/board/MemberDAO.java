package test06.board;

import java.sql.*;
import java.util.*;

import test05.singleton.dbconnection.MyDBConnection;

public class MemberDAO implements InterMemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// *** 자원반납 메서드 *** // => 일반적으로 자원반납 메서드는 인터페이스에 쓰지 않음(직접 클래스에서 선언)
	private void close() {

		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end of close()----------------------------------------

	// *** DB에 회원가입 메서드 *** //
	@Override
	public int memberRegister(MemberDTO member) {

		int result = 0;
		try {
			conn = MyDBConnection.getConn();

			String sql = "insert into jdbc_member(userseq, userid, passwd, name, mobile)\n"
					+ "values(userseq.nextval, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			// 플레이스 홀더(?) 에 올바른 값 순서대로 대입
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMobile());

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) { // SQLException 밑에 가있으면 안됨
			System.out.println("에러메시지: " + e.getMessage());
			System.out.println("에러코드번호: " + e.getErrorCode());
			System.out.println("이미 있는 아이디임");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}// end of memberRegister(MemberDTO member)------------------

	@Override
	public MemberDTO login(Map<String, String> paraMap) {
		MemberDTO member = null;

		try {
			conn = MyDBConnection.getConn();
			
			String sql = // "select userseq, userid, passwd, name, mobile, point\n"+
					"select name \n" + "        , to_char(registerday, 'yyyy-mm-dd') as registerday, status\n"
							+ "from jdbc_member\n" + "where userid = ? and passwd = ?";

			pstmt = conn.prepareStatement(sql);
			// 플레이스 홀더(?) 에 올바른 값 순서대로 대입
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, paraMap.get("passwd"));

			rs = pstmt.executeQuery();

			// while문을 쓰지 않은 이유는 로그인이라 행이 1개 이상 나올 수 없기 때문(그럴 시 db에 중복된 데이터가 들어간 것)
			if (rs.next()) { // 값이 나왔을 시(로그인 성공)
				member = new MemberDTO();
				member.setName(rs.getString(1)); // name 대신 1도 가능
			} else { // 값이 없을 시(로그인 실패)

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return member;
	}

}
