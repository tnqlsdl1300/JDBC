package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DML_delete {
	public static void main(String[] args) {

		Connection conn = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "HR", "cclass");

			// select 용 sql
			String selectSql = "select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday from jdbc_tbl_memo order by no desc";

			pstmt = conn.prepareStatement(selectSql);
			conn.setAutoCommit(false); // 오토커밋 off

			rs = pstmt.executeQuery();

			System.out.println("----------------------------------------------------------------");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("----------------------------------------------------------------");

			StringBuilder sb = new StringBuilder();

			while (rs.next()) {

				int no = rs.getInt(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String writeday = rs.getString(4);

				sb.append(no);
				sb.append("\t " + name);
				sb.append("\t " + message);
				sb.append("\t " + writeday);
				sb.append("\n");

			}
			System.out.println(sb.toString());
			// 출력 끝 ------------------------------------------------------------

			System.out.print("▷ 삭제할 글번호: ");
			String no = sc.nextLine();

			String deleteSql = "delete jdbc_tbl_memo where no = ?";
			pstmt.close();
			pstmt = conn.prepareStatement(deleteSql);

			pstmt.setString(1, no);

			int n = pstmt.executeUpdate();	// 삭제되어진 행 개수를 알려줌

			if (n == 1) {

				do {
					System.out.print("▷ 정말로 삭제하시겠습니까?[Y/N] ");
					String yn = sc.nextLine();

					if ("y".equalsIgnoreCase(yn)) {
						conn.commit(); // commit
						System.out.println(">> 데이터 삭제 성공!!! <<");
						break;
					} else if ("n".equalsIgnoreCase(yn)) {
						conn.rollback(); // rollback
						System.out.println(">> 데이터 삭제 취소!!! <<");
						break;
					} else {
						System.out.println("다시 입력");
					}

				} while (true);

			} else if (n == 0) {
				System.out.println(">> 삭제하시려는 글번호는 존재하지 않는 글번호입니다. <<");
			} else {
				System.out.println(">> 데이터 입력에 오류가 발생함 <<");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sc.close();
	}
}
