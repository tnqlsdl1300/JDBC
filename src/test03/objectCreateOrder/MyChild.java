package test03.objectCreateOrder;

public class MyChild extends MyParent {
	
	String name = "이순신";	// field 생성이 제일 먼저임
	static String address = "경기도";	// field 생성이 제일 먼저임 
	
	// **** static 초기화 블럭 **** //
	static {
		System.out.println("### 1. 자식클래스 MyChild의 static 포기화 블럭 실행됨(초기 1번만 실행됨) ### \n");
		address = "서울시 강남구 도곡동";
	}	// 초기 한번만 실행되기 때문에 보통 instance 초기화 블럭의 환경설정을 static 포기화 블럭에서 해준다
	
	// **** instance 초기화 블럭 **** //
	{
		System.out.println("### 3. 자식클래스 MyChild의 instance 초기화 블럭 실행됨 ### \n");
		name = "엄정화";
	}
	
	public MyChild() {
		System.out.println("### 4. 자식클래스 MyChild의 default 생성자 MyChild() 실행됨 ### \n");
		name = "몰라요";
	}
	
}
