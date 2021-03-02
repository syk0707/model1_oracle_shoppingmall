select * from memo;
DELETE FROM memo;

DELETE FROM memo
WHERE		indent > 0;

ALTER TABLE memo
ADD(refnum    NUMBER    DEFAULT 0);	

SELECT count(refnum)
FROM memo
WHERE refnum = 411;

--select list
SELECT memono, title, wdate, viewcnt, grpno, indent, ansnum, r
FROM(
	SELECT memono, title, wdate, viewcnt, grpno, indent, ansnum, rownum r
	FROM(
		SELECT memono, title, wdate, viewcnt, grpno, indent, ansnum
		FROM memo
		ORDER BY grpno DESC, ansnum
		)	
	)
WHERE r>=1 and r<=5

--답변 read(memono, grpno, indent, ansnum, title)
SELECT memono, grpno, indent, ansnum, title
FROM memo
WHERE memono = 203


--답변 ansnum 증가(grpno = 2 and ansnum > 0)
UPDATE memo
SET ansnum = ansnum + 1
WHERE grpno = 2
AND ansnum > 0;


--답변 create

INSERT INTO memo(memono, title, content, wdate, 
grpno, indent, ansnum)
VALUES (memo_seq.nextval, '읽을 도서', '가메출판사 오라클 11g', sysdate,
2,		1,		1);


-- 임시 영역으로 테이블 삭제, 복구 가능한 휴지통으로 삭제 
DROP TABLE memo; 
 
-- 테이블을 복구 불가능 하도록 삭제 
DROP TABLE memo PURGE; 

-- 테이블 생성 
CREATE TABLE memo( 

  memono  NUMBER(6)     NOT NULL,  -- 일련번호, -999999 ~ +999999 
  title   VARCHAR(100)  NOT NULL,  -- 메모 제목, 한글 50자 
  content VARCHAR(4000) NOT NULL,  -- 내용  
  wdate   DATE          NOT NULL,  -- 등록 날짜  
  viewcnt NUMBER(5)     DEFAULT 0, -- 조회수, -99999 ~ +99999
  PRIMARY KEY(memono)              -- 고유한 값, 중복 안됨    
);

ALTER TABLE memo
ADD(
	grpno		number	default 0,
	indent		number	default 0,
	ansnum		number	default 0
);

SELECT * FROM memo;

-- SEQUENCE 객체 삭제 
DROP SEQUENCE memo_seq; 
  
-- SEQUENCE 객체 생성 
CREATE SEQUENCE memo_seq 

START WITH 1 
INCREMENT BY 1 
MINVALUE 0 
CACHE 100; 


-- dual: 가상 테이블, SQL형식 맞출경우 임시 테이블 사용, 변경하면 안됨
SELECT memo_seq.nextval FROM dual;



  
-- create 

-- memo_seq.nextval: 일련번호 자동 생성하여 할당 

--                   (MySQL은 컬럼명을 명시하지 않음). 

-- sysdate: 현재 날짜와 시간(MySQL에서는 now()를 써야한다). 오라클에서는 sysdate쓴다. 

INSERT INTO memo(memono, title, content, wdate) 

VALUES(memo_seq.nextval, '오늘의 메모', '이번주 토익 시험 준비', sysdate); 


-- 답변 추가 시 CREATE
INSERT INTO memo(
memono, 			title,		content,	wdate,	grpno,	indent,		ansnum) 
VALUES
(memo_seq.nextval, '저녁약속',	'프로젝트 관련', sysdate, (SELECT NVL(MAX(grpno),0) + 1 FROM memo), ?, ?); 


-- select list, 자주 읽는 메모 먼저 출력  
-- DESC: Descending, ASC: Ascending 


INSERT INTO memo(memono, title, content, wdate) 
VALUES(memo_seq.nextval, '읽을 도서', '가메출판사 오라클 11g', sysdate); 

 
INSERT INTO memo(memono, title, content, wdate) 
VALUES(memo_seq.nextval, '수업 복습', '메모 제작', sysdate); 

 
INSERT INTO memo 
VALUES(memo_seq.nextval, '도서구입', 'JSP Web Programming', sysdate, 0); 



SELECT memono, title, wdate, viewcnt 
FROM memo  
ORDER BY memono ASC; 


delete from memo;

-----------------------------------------------------------------------