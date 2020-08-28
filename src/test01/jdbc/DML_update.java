package test01.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DML_update {
	public static void main(String[] args) {
		
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
	
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			System.out.print("▷ 연결할 오라클 서버의 IP주소: ");
			String ip = sc.nextLine();
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:xe", "HR", "cclass");
			
			// select 용 sql쿼리
			String sql = "select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday from jdbc_tbl_memo order by no desc";
			
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);	// 중간에 사용자가 취소를 원하는 경우가 있기 때문에 자동커밋을 수동커밋으로 변경
			
			rs = pstmt.executeQuery();
			
			System.out.println("----------------------------------------------------------------");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("----------------------------------------------------------------");
			
			StringBuilder sb = new StringBuilder();
			
			while(rs.next()) {
				
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
			// 출력 끝--------------------------------------------------------------------
			
			System.out.print("▷ 수정할 글번호: ");
			String editNo = sc.nextLine();
			System.out.print("▷ 글쓴이: ");
			String editName = sc.nextLine();
			System.out.print("▷ 글내용: ");
			String editMessage = sc.nextLine();
			
			// update용 sql 쿼리
			String sql1 = "update jdbc_tbl_memo set name = ?, msg = ? where no = ?";
			
			pstmt.close();	// 새로운 pstmt를 써야할 때는 기존의 것을 close시키고 진행하는게 좋음
			pstmt = conn.prepareStatement(sql1);
			
			pstmt.setString(1, editName);
			pstmt.setString(2, editMessage);
			pstmt.setInt(3, Integer.parseInt(editNo));	// 오라클은 자동으로 형변환을 해주기 때문에 굳이 int로 안바꿔줘도 잘 됨
			

			// 업데이트 된 값을 다 받고 나서
			int n = pstmt.executeUpdate();
			
			if(n == 1) {
				
				do {
					System.out.print("▷ 정말로 수정하시겠습니까?[Y/N] ");
					String yn = sc.nextLine();
					
					if("y".equalsIgnoreCase(yn)) {
						conn.commit();	// commit
						System.out.println(">> 데이터 입력 성공!!! <<");
						break;
					}else if("n".equalsIgnoreCase(yn)) {
						conn.rollback();	// rollback
						System.out.println(">> 데이터 입력 취소!!! <<");
						break;
					}else {
						System.out.println("다시 입력");
					}
					
				}while(true);
				
				
			}else if(n == 0) {
				System.out.println(">> 변경하시려는 글번호는 존재하지 않는 글번호입니다. <<");
			}
			else {
				System.out.println(">> 데이터 입력에 오류가 발생함 <<");
			}		
				
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		sc.close();
	}
}
