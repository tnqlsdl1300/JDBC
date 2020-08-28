package test02.member;

// DTO(Data Transfer Object)는 테이블의 행(ROW) 취급이다. 변수를 컬럼으로 보면 됨

public class MemberDTO {
	private int userseq;		// 회원번호, primary key
	private String userid;		// 회원아이디, unique key
	private String passwd;		// 회원암호
	private String name;		// 회원명
	private String mobile;		// 연락처
	private int point;			// 포인트
	private String registerday;	// 가입일자  => 오라클에서는 date지만 자바에서는 그냥 String 처리 해줘야 함
	private int status;			// status 컬럼의 값이 1 이면 정상, 0 이면 탈퇴 
	
	
	
	public MemberDTO() {	}

	
	
	public int getUserseq() {
		return userseq;
	}

	public void setUserseq(int userseq) {
		this.userseq = userseq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
}
