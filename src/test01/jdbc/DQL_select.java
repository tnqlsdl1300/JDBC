package test01.jdbc;

import java.sql.*;
import java.util.Scanner;

public class DQL_select {
	public static void main(String[] args) {
		
		// Connection conn 은 오라클 데이터베이스 서버와 연결을 맺어주는 객체이다
		Connection conn = null;
				
		// Connection conn(특정 오라클 서버)에 전송할 SQL문(편지)을 전달할 객체
		PreparedStatement pstmt = null;
		
		// ResultSet rs 은 select 되어진 결과물이 저장되어지는 곳
		ResultSet rs = null;
		
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
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:xe", "HR" , "cclass");
			
			
			// >>> 3. SQL문(편지)을 작성한다 <<<
			String sql = " select no, name, message, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') as writeday " 
					+ " from jdbc_tbl_memo "
					+ " order by no desc ";	// 공백을 꼭 제대로 줘야 함 => 앞뒤로 공백을 주는 습관을 들이자
			
			// >>> 4. 연결한 오라클서버(conn)에 SQL문(편지)을 전달할 PreparedStatement 객체(우편배달부) 생성 <<<
			// sql문을 전달해줄 객체인 pstmt에게 sql(편지)를 넣어줌
			pstmt = conn.prepareStatement(sql);	// 객체이름은 prepared지만 함수는 prepare임
			
			// >>> 5. PreparedStatement pstmt 객체(우편배달부)가 작성된 SQL문(편지)을 오라클서버에 보내서 실행시킴  <<<
			rs = pstmt.executeQuery();	// sql 문이 select 이므로 excuteQuery 메서드를 사용
			
			// pstmt.executeQuery(); 을 실행하면 select 되어진 결과물을 가져오는데 
						// 그 타입은 ResultSet 으로 가져온다.
						/*
						   === 인터페이스 ResultSet 의 주요한 메소드 ===
						   1. next()  : select 되어진 결과물에서 커서를 다음으로 옮겨주는 것             리턴타입은 boolean 
						   2. first() : select 되어진 결과물에서 커서를 가장 처음으로 옮겨주는 것       리턴타입은 boolean
						   3. last()  : select 되어진 결과물에서 커서를 가장 마지막으로 옮겨주는 것    리턴타입은 boolean
						   
						   == 커서가 위치한 행에서 컬럼의 값을 읽어들이는 메소드 ==
						   getInt(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						                  파라미터 숫자는 컬럼의 위치값 
						                 
						   getInt(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						                  파라미터 문자는 컬럼명 또는 alias명 
						                  
						   getLong(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						                     파라미터 숫자는 컬럼의 위치값 
						                 
						   getLong(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
						                     파라미터 문자는 컬럼명 또는 alias명                
						   
						   getFloat(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                      파라미터 숫자는 컬럼의 위치값 
						                 
						   getFloat(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                      파라미터 문자는 컬럼명 또는 alias명 
						                      
						   getDouble(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                        파라미터 숫자는 컬럼의 위치값 
						                 
						   getDouble(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
						                        파라미터 문자는 컬럼명 또는 alias명    
						                        
						   getString(숫자) : 컬럼의 타입이 문자열로 읽어들이때
						                        파라미터 숫자는 컬럼의 위치값 
						                 
						   getString(문자) : 컬럼의 타입이 문자열로 읽어들이때
						                        파라미터 문자는 컬럼명 또는 alias명                                                        
						*/
			
			/*
			 * StringBuffer		==> Multi Thread(게임) 사용, 무거움
			 * StringBuilder 	==> Single Thread(웹) 사용, 비교적 가벼움
			 */
			
			System.out.println("----------------------------------------------------------------");
			
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			
			System.out.println("----------------------------------------------------------------");
			
			StringBuilder sb = new StringBuilder(); 
			
			while(rs.next()) {
				/*
				 * rs.next() 는 select 되어진 결과물에서 커서의 위치(행의 위치)를 다음으로 옮긴 후
				 * 행이 존재하면 true, 행이 없으면 false 를 리턴시켜준다
				 */
				
				int no = rs.getInt(1);// 숫자 1은 컬럼의 셀렉트 되어서 나오는 위치값(순서) 이다. 
				// 또는
				//int no = rs.getInt("NO"); // "no"는 no 컬렴명을 말한다
				
				String name = rs.getString(2);
				String msg = rs.getString(3);
				String writeday =  rs.getString("WRITEDAY");
				
				sb.append(no);
				sb.append("\t" + name);
				sb.append("\t" + msg);
				sb.append("\t" + writeday);
				sb.append("\n");
				
			}// end of while(rs.next()) -------------------------------------------
			
			System.out.println(sb.toString());	// sb에 쌓여있던 데이터들을 String 값으로 변환시켜주고 출력해준다(객체의 모든것을 출력)
			
			
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally{	// 앞에서 성공하던 실패하던 무조건 실행
			// >>> 6. 사용했던 자원 반납(sc, conn, pstmt 등등) <<<
			// 선언한 순서의 역순으로 닫아줘야 함
			
			try {
				if(rs != null) rs.close();
				if(pstmt != null)	pstmt.close();
				if(conn != null); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		sc.close();
		System.out.println("~~~~ 프로그램 종료 ~~~~");
		
	}// end of main() ------------------------------------------
}
