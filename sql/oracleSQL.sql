-- 유저생성
CREATE USER book_ex IDENTIFIED BY book_ex
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;
-- 권한
GRANT CONNECT, DBA TO BOOK_EX;

--포트 변경
-- exec dbms_xdb.sethttpport(9090);

--포트 확인
select dbms_xdb.gethttpport() from dual;


-- page 173 테이블 생성과 Dummby(더미) 데이터 생성
CREATE sequence seq_board;

create table tbl_board
(
    bno number(10, 0),
    title varchar2(200) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
);

alter table tbl_board add constraint pk_board primary key (bno);

-- 더미 데이터 추가
insert into tbl_board (bno, title, content, writer)
values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');