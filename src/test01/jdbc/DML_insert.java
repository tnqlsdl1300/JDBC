package test01.jdbc;

import java.sql.*;	// sql 관련 것들은 다 이곳에 있음
import java.util.Scanner;

public class DML_insert {
	public static void main(String[] args) {
		// 오라클 연결은 공식같이 외워둬야 함
		
		// Connection conn 은 오라클 데이터베이스 서버와 연결을 맺어주는 객체이다
		Connection conn = null;
		
		// Connection conn(특정 오라클 서버)에 전송할 SQL문(편지)을 전달할 객체
		PreparedStatement pstmt = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			
			/*
			 *  OracleDriver(오라클 드라이버)의 역할
			 *  1) .OracleDriber 를 메모리에 로딩시켜준다
			 *  2) .OracleDriber 객체를 생성해준다
			 *  3) .OracleDriber 객체를 DriverManager에 등록시켜준다
			 *  	--> DriverManager는 여러  드라이버들을 Vector에 저장하여 관리해주는 클래스이다
			 */
			
			Class.forName("oracle.jdbc.driver.OracleDriver");	// ojdbc6.jar에 들어있음
			
			// >>> 2. 어떤 오라클 서버에 접속할지 설정 <<<
			System.out.print("▷ 연결할 오라클 서버의 IP 주소: ");
			String ip = sc.nextLine();
			
			// 127.0.0.1은 자기 자신을 뜻함(local host), 1521은 HR의 허용 포트번호
			// Connection conn의 기본값은 auto commit 이다(rollback 불가)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:xe", "HR" , "cclass");
			conn.setAutoCommit(false);	// 수동 commit으로 바꾸기(rollback 가능)
			
			// >>> 3. SQL문(편지)을 작성한다 <<<
			System.out.print("▷ 글쓴이: ");	// 자바로 입력받은 값을 sql의 테이블에 insert 해줌
			String name = sc.nextLine();
			
			System.out.print("▷ 내용: ");
			String msg = sc.nextLine();
			
			String sql = "insert into jdbc_tbl_memo(no, name, msg)"
					+ "values(jdbc_seq_memo.nextval, ?, ?)";	// ? => 위치홀더
			
			
			// >>> 4. 연결한 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			
			// sql문을 전달해줄 객체인 pstmt에게 sql(편지)를 넣어줌
			pstmt = conn.prepareStatement(sql);	// 객체이름은 prepared지만 함수는 prepare임
			pstmt.setString(1, name);	// 1 은 sql에서 첫번째 ?를 말한다	=> name을 넣음
			pstmt.setString(2, msg);	// 2 은 sql에서 두번째 ?를 말한다	=> msg를 넣음
			
			
			// >>> 5. PreparedStatement pstmt 객체(우편배달부)가 작성된 SQL문(편지)을 오라클서버에 보내서 실행시킴  <<<
			int n = pstmt.executeUpdate();	// DML문
			
			// 1개의 행을 입력받았을 때
			if(n == 1) {
				
				do {
					System.out.print("▷ 정말로 입력하시겠습니까?[Y/N] ");
					String yn = sc.nextLine();
					
					if(yn.equalsIgnoreCase("y")) {
						conn.commit();	// commit
						System.out.println(">> 데이터 입력 성공!!! <<");
						break;
					} else if(yn.equalsIgnoreCase("n")) {
						conn.rollback();	// rollback
						System.out.println(">> 데이터 입력 취소!!! <<");
						break;
					} else {
						System.out.println("제대로 입력하셈");
					}
				}while(true);
				
			}else {		// 잘못된 값을 입력받았을 때
				System.out.println(">> 데이터 입력에 오류가 발생함 <<");
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally{	// 앞에서 성공하던 실패하던 무조건 실행
			// >>> 6. 사용했던 자원 반납(sc, conn, pstmt 등등) <<<
			// 선언한 순서의 역순으로 닫아줘야 함
			
			if(pstmt != null) {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		

		}
		sc.close();
		System.out.println("~~~~ 프로그램 종료 ~~~~");
	}	// end of main() ------------------------------------------------------------------
}


































