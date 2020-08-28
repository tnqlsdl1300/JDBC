package test01.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * 프로그램이 실행되면
 * 모든 데이터를 조회해서 보여주도록 한다
 * 
 * 
 * --- >>> 조회할 대상 <<< ----
 * 1. 글번호  2. 글쓴이  3. 글내용
 * ------------------------
 * > 선택번호: 1
 * > 검색어: 7
 * 
 * ====출력====
 * 
 * --- >>> 조회할 대상 <<< ----
 * 1. 글번호  2. 글쓴이  3. 글내용
 * ------------------------
 * > 선택번호: 1
 * > 검색어: 박수빈
 * 
 * ====출력====
 * 
 * --- >>> 조회할 대상 <<< ----
 * 1. 글번호  2. 글쓴이  3. 글내용
 * ------------------------
 * > 선택번호: 3
 * > 검색어: 배고파
 * 
 * ====출력====
 * 
 * --- >>> 조회할 대상 <<< ----
 * 1. 글번호  2. 글쓴이  3. 글내용   4. 종료
 * ------------------------
 * > 선택번호: 4
 * 
 * ~~~~ 프로그램 종료 ~~~~
 * 
 */

public class DQL_select_where {
	public static void main(String[] args) {

		Connection conn = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.print("▷ 연결할 오라클 서버의 IP주소: ");
			String ip = sc.nextLine();

			conn = DriverManager.getConnection("jdbc:oracle:thin:@ " + ip + ":1521:xe", "HR", "cclass");

			// select 용 sql
			String sql = "select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday from jdbc_tbl_memo order by no desc";

			pstmt = conn.prepareStatement(sql);
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

			sb = new StringBuilder(); // 새로운 StringBuilder 생성
			sb.append("--------- >>> 조회할 대상 <<< ----------\n");
			sb.append("1. 글번호  2. 글쓴이  3. 글내용   4. 종료\n");
			sb.append("------------------------------------\n");
			String menu = sb.toString(); // menu를 저장

			String menuNo = null;
			do {
				System.out.print(menu);
				System.out.print("▷ 선택번호: ");
				menuNo = sc.nextLine();

				String colName = ""; // where 절에 들어올 컬럼명

				switch (menuNo) {
				case "1":
					colName = "no";
					break;
				case "2":
					colName = "name";
					break;

				case "3":
					colName = "msg";
					break;

				case "4":

					break;

				default:
					System.out.println("~~~~ 메뉴에 없는 번호 입니다 ~~~~");
					System.out.println();
					break;

				}// end of switch()-----------------------------------------------------------

				if ("1".equals(menuNo) || "2".equals(menuNo) || "3".equals(menuNo)) {
					System.out.print("▷ 검색어: ");
					String search = sc.nextLine();

					
					// sql문을 조건에 맞게 += 로 이어 붙임
					sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday "
							+ " from jdbc_tbl_memo ";
					
					if(!"3".equals(menuNo)) {	// 1, 2번일 때
						sql += "where " + colName + " = ? ";
					}else {	// 3번일 때
						sql += " where " + colName + " like '%' || ? || '%' ";	// like를 쓰는법 (like에서 ?를 쓸 때는 사이에 ||를 해줘야 함)
					}
					
					sql += " order by no desc ";

					/*
					 * 위치홀더(?)에는 오로지 데이터 값만 들어와야 하고, 컬럼명 또는 테이블명에는 위치홀더(?)가 오면 오류가 발생한다 변수로 선언해서
					 * 처리해줘야 한다
					 */

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, search);

					rs = pstmt.executeQuery(); // 조건에 맞게 검색해서 나온 행들을 rs 객체에 넣어줌

					System.out.println("----------------------------------------------------------------");
					System.out.println("글번호\t글쓴이\t글내용\t작성일자");
					System.out.println("----------------------------------------------------------------");

					// StringBuilder 초기화하기//
					// sb.length(); // StringBuilder sb 에 append 되어진 개수 ==> 3
					// sb.delete(0, sb.length());
					// 또는
					sb.setLength(0);
					sb = new StringBuilder();
					while (rs.next()) { // select 되어져 나온 개수만큼 뺑뺑이
						int no = rs.getInt(1);
						String name = rs.getString(2);
						String msg = rs.getString(3);
						String writeday = rs.getString(4);

						sb.append(no + "\t");
						sb.append(name + "\t");
						sb.append(msg + "\t");
						sb.append(writeday + "\n");

					} // end of while ------------------------------------------

					System.out.println(sb.toString());
				}

				System.out.println();
			} while (!menuNo.equals("4")); // 메뉴에서 4번을 입력 시 빠져나감

			/*
			 * --- >>> 조회할 대상 <<< ---- 1. 글번호 2. 글쓴이 3. 글내용 4. 종료 ------------------------
			 */

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sc.close();
		System.out.println("\n~~~~ 프로그램 종료 ~~~~");

	}// end of main() --------------------------------------------
}
