---- *** SYS 로 접속한다 *** ----

show user;
-- USER이(가) "HR"입니다.

---- *** 오라클 일반 사용자 계정(계정명: myorauser, 암호: cclass)을 생성해준다 *** ----
create user  myorauser IDENTIFIED by cclass;

--- *** myorauser 에게 오라클서버에 접속해서 작업을 할 수 있도록 권한을 부여해 준다 *** ---
grant connect, resource to myorauser;
-- Grant을(를) 성공했습니다. => 개발자에게 권한을 줌

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--- *** myorauser 로 접속한다 *** ---
show user;
-- USER이(가) "MYORAUSER"입니다.

select * from tab;

        ------ ***  회원 테이블 생성하기  *** ------
drop table jdbc_member purge; 
        
create table jdbc_member
(userseq       number        not null    -- 회원번호, primary key
,userid        varchar2(30)  not null    -- 회원아이디, unique key
,passwd        varchar2(30)  not null    -- 회원암호
,name          varchar2(20)  not null    -- 회원명
,mobile        varchar2(20)              -- 연락처
,point         number(10) default 0      -- 포인트
,registerday   date default sysdate      -- 가입일자 
,status        number(1) default 1       -- status 컬럼의 값이 1 이면 정상, 0 이면 탈퇴 
,constraint PK_jdbc_member primary key(userseq)
,constraint UQ_jdbc_member unique(userid)
,constraint CK_jdbc_member check( status in(0,1) )
);

create sequence userseq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


select *
from jdbc_member
order by userseq desc;

--------------- **** 도서대여 프로그램 테이블 생성하기 **** ---------------
---- 테이블 생성 시 제약조건을 모두 만들어야 합니다
---- 테이블명은 접두어로 hw_를 붙이도록 한다. 
---- 예: hw_member, hw_isbn

회원
ISBN
각각의도서
대여

drop table hw_rental purge;

comment on column hw_rental.arrears
is '도서대여 연체료';

select column_name, comments
from user_col_comments
where table_name = 'HW_RENTAL';

create table hw_member
(userid    varchar2(30) -- 기본키
, userPwd  varchar2(30) not null
, userName  varchar2(30)
, userPhone  varchar2(40) not null  -- 대체키
, constraint PK_hw_member_userid primary key(userid)
, constraint UQ_hw_member_userPhone unique(userPhone)
);

create table hw_isbn
(bookISBN  varchar2(30) -- 기본키
, bookCategory  varchar2(30)
, bookName    varchar2(30)
, bookAuthorName  varchar2(30)
, bookPublisher    varchar2(30)
, bookPrice         varchar2(30)
, constraint PK_hw_isbn_isbn primary key(bookISBN)
);

create table hw_bookid
(bookid   varchar2(30) -- 기본키
, bookisbn varchar2(30) -- 참조
, isrent  number(2)
, constraint PK_hw_bookid_bookid primary key(bookid)
, constraint FK_hw_bookid_bookisbn foreign key(bookisbn) references hw_isbn(bookisbn) on delete cascade    -- isbn이 사라지면 해당 책을 반입?하지 않는거기 때문에 개별 책들의 정보도 삭제
);

create table hw_lib
(libId   varchar2(30)   -- 기본키
, libPwd  varchar2(30)
, constraint PK_hw_lib_libId primary Key(libId)
);

create table hw_rental
(userid   varchar2(30)  -- 참조
, bookid  varchar2(30)  -- 참조
, rentalday   date default sysdate
, returnday  date default sysdate + 7
, arrears  number(5)
, constraint FK_hw_rental_userid foreign key(userid) references hw_member(userid)   -- userid와 bookid는 대여중인 회원이 있을 때 도서 정보와 회원정보를 삭제하면 안되므로 옵션을 주지 않음
, constraint FK_hw_rental_bookid foreign key(bookid) references hw_bookid(bookid)   -- 위와 같음
);

















--자바에 sql로 입력할 쿼리들  (드래그 - 우클릭 - 포함/표시 - java)
String sql = "insert into jdbc_member(userseq, userid, passwd, name, mobile)\n"+
"values(userseq.nextval, ?, ?, ?, ?)";     -- ;쓰면 안됨


String sql = "select userseq, userid, passwd, name, mobile, point\n"+
"        , to_char(registerday, 'yyyy-mm-dd') as registerday, status\n"+
"from jdbc_member\n"+
"where userid = 'leess' and passwd = '1234'";











