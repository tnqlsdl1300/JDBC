package test03.objectCreateOrder;

public class MyParent {
	
	int age = 20;	// field 생성이 가장 먼저임
	
	public MyParent() {
		System.out.println("~~~~ 2. 부모클래스 MyParent의 default 생성자 MyParent() 실행됨 ~~~~ \n");
		age = 30;
	}
	
}
