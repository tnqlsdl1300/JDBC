package test05.singleton.dbconnection;

import java.sql.*;

import test04.singletonPattern.SingletonNumber;

public class MyDBConnection {
	
	// --> 첫번째로 호출(작동) : static 변수 <-- //
	// 리턴해줄 Connection 객체
	private static Connection conn= null;
	
	static {
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "MYORAUSER", "cclass");
			conn.setAutoCommit(false);	// 수동 commit 으로 전환
		} catch (ClassNotFoundException e) {
			System.out.println(">> ojdbc6.jar 파일이 없습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of static() ---------------------------------------------------
	
	// == 2,
	//	생성자에게 접근제한자를 private로 지정하여 외부에서 절대로 instance를 생성하지 못하도록 막아버린다
	private MyDBConnection() {	}	// 외부에서 객체 생성 절대 불가
	
	// == 3,
	//	static 메서드를 생성[Connection()]하여 외부에서 해당 클래스의 객체를 사용할 수 있도록 해준다
	public static Connection getConn() {
		return conn;	// 메서드에서 만들어둔 객체를 반한(static 초기화 블럭에서 객체를 생성했기 때문에 같은 주소값만 반환시킴)
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	// === Connection conn 객체 자원 반납하기 === //
	public static void closeConnection() {
		
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}// end of closeConnection() ------------------------------------------

}
















