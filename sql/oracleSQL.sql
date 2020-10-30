-- 유저생성
CREATE USER book_ex IDENTIFIED BY book_ex
    DEFAULT TABLESPACE USERS
    TEMPORARY TABLESPACE TEMP;
-- 권한
GRANT CONNECT, DBA TO BOOK_EX;

--포트 변경
-- exec dbms_xdb.sethttpport(9090);

--포트 확인
select dbms_xdb.gethttpport()
from dual;

-- page 173 테이블 생성과 Dummby(더미) 데이터 생성
CREATE sequence seq_board;

create table tbl_board
(
    bno        number(10, 0),
    title      varchar2(200)  not null,
    content    varchar2(2000) not null,
    writer     varchar2(50)   not null,
    regdate    date default sysdate,
    updatedate date default sysdate
);

alter table tbl_board
    add constraint pk_board primary key (bno);

-- 더미 데이터 추가
insert into tbl_board (bno, title, content, writer)
values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');

-- 재귀 복사를 통해서 데이터의 개수를 늘린다. 반복해서 여러 번 실행
insert into tbl_board(bno, title, content, writer)
        (select seq_board.nextval, title, content, writer from tbl_board);

select count(*)
from tbl_board;

--고의적으로 bno라는 칼럼의 값에다 1을 추가한 값을 역순으로 정렬하는 SQL
SELECT *
FROM tbl_board
ORDER BY BNO + 1 DESC;

SELECT *
FROM tbl_board
ORDER BY BNO DESC;

SELECT *
FROM TBL_BOARD
WHERE BNO > 0;

-- 인덱스와 오라클 힌트  이 둘은 동일한 결과
SELECT *
FROM tbl_board
ORDER BY bno desc;

select /*+INDEX_DESC (tbl_board pk_board) */*
from tbl_board;

-- FULL 힌트
select /*+ FULL(tbl_board) */ *
from tbl_boardorder by bno desc;

-- INDEX_ASC/DESC 힌트
select /*+ INDEX_ASC(tbl_board pk_board) */ *
from tbl_board
where bno > 0;

--인덱스를 이용한 접근 시 ROWNUM
select /*+ INDEX_ASC(tbl_board pk_board) */
    ROWNUM rn,
    bno,
    title,
    content
from tbl_board;

select /*+ INDEX_DESC(tbl_board pk_board) */
    ROWNUM rn,
    bno,
    title,
    content
from tbl_board
where bno > 0;

-- 페이지 번호 1, 2의 데이터
select /*+INDEX_DESC(tbl_board pk_board) */
    ROWNUM rn,
    bno,
    title,
    content
from tbl_board
where rownum <= 10;

-- ROWNUM은 반드시 1이 포함되도록 해야 한다.
select /*+INDEX_DESC(tbl_board pk_board) */
    ROWNUM rn,
    bno,
    title,
    content
from tbl_board
where rownum <= 20;

--인라인뷰를 적용한 2페이지 데이터의 처리
select bno, title, content
from (
         select /*+INDEX_DESC(tbl_board pk_board) */
             ROWNUM rn,
             bno,
             title,
             content
         from tbl_board
         where ROWNUM <= 20
     )
where rn > 10;


-- 댓글 처리를 위한 테이블 생성과 처리
create table tbl_reply(
    rno number(10, 0),
    bno number(10, 0) not null,
    reply varchar2(1000) not null,
    replyer varchar2(50) not null,
    replyDate date default sysdate,
    updateDate date default sysdate
);

create sequence seq_reply;

alter table tbl_reply add constraint pk_reply primary key (rno);

alter table tbl_reply add constraint fk_reply_board
foreign key (bno) references tbl_board(bno);

-- 최신 bno 번호 몇개 예제 확인
select * from tbl_board where rownum < 10 order by bno desc;

--index 생성
create index idx_reply on tbl_reply (bno desc, rno asc);

-- 인덱스를 이용한 페이징 쿼리
select /*_INDEX(tbl_reply idx_reply) */
    rownum rn, bno, rno, reply, replyer, replyDate, updatedate
    from tbl_reply
    where bno = 3145745 --(게시물번호)
    and rno >0

-- 10개씩 2페이지
select rno, bno, reply, replyer, replydate, updatedate
from
    (
        select /*+INDEX(tbl_reply idx_reply) */
            rownum rn, bno, rno, reply, replyer, replyDate, updatedate
        from tbl_reply
        where bno = -- 게시물 번호
            and rno >0
            and rownum <= 20
    ) where rn > 10

--471쪽
create table tbl_sample1(col1 varchar2(500));

create table tbl_sample2(col2 varchar2(50));

--480쪽
alert table tbl_board add(replycnt number default 0);

update tbl_board set replycnt = (select count(rno) from tbl_reply where tbl_reply.bno = tbl_board.bno);
