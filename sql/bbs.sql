CREATE TABLE bbs (
	bbsno		NUMBER(7)		NOT NULL,
	wname		VARCHAR(20)		NOT NULL,
	title		VARCHAR(100)	NOT NULL,
	content		VARCHAR(4000)	NOT NULL,
	passwd		VARCHAR(15)		NOT NULL, -- 게시판 수정 및 삭제
	viewcnt		NUMBER(5)		DEFAULT 0,
	wdate		DATE			NOT NULL,
	grpno		NUMBER(7)		DEFAULT 0,
	indent		NUMBER(2)		DEFAULT 0,
	ansnum		NUMBER(5)		DEFAULT 0,
	PRIMARY KEY (bbsno)
);

--create

--일련번호

ALTER TABLE bbs
ADD(refnum		NUMBER			DEFAULT 0)

ALTER TABLE bbs
ADD(filename	varchar(30),
	filesize	number		default 0);

SELECT count(refnum)
FROM bbs
WHERE refnum = 0

SELECT
NVL(MAX(bbsno), 0) + 1 AS bbsno
FROM
bbs;

INSERT INTO
bbs(	bbsno, 		
		wname, 		title,		content, 	passwd, 	wdate)
VALUES( (SELECT NVL(MAX(bbsno),0) + 1 as bbsno FROM bbs),
		'왕눈이',	'제목',		'내용',		'123',		sysdate);

INSERT INTO
bbs(	bbsno, 		
		wname, 		title,		content, 	passwd, 	wdate)
VALUES( (SELECT NVL(MAX(bbsno),0) + 1 as bbsno FROM bbs),
		'아로미',	'제목',		'내용',		'123',		sysdate);

INSERT INTO
bbs(	bbsno, 		
		wname, 		title,		content, 	passwd, 	wdate)
VALUES( (SELECT NVL(MAX(bbsno),0) + 1 as bbsno FROM bbs),
		'투투',		'제목',		'내용',		'123',		sysdate);

-- 레코드 갯수
SELECT count(*) FROM bbs
WHERE wname like '%김%';		
		
		
--read
SELECT *
FROM bbs
WHERE bbsno = 2;

--update
UPDATE bbs
SET wname = '왕눈이', title = '비 오는 날', content = '개구리 연못'
WHERE bbsno = 1;


--delete
DELETE FROM bbs
WHERE 		bbsno=1;

DELETE FROM bbs
WHERE		indent > 0;

--list(paging)

--rownum 순번을 가져다 쓰기 위해 alias r을 쓴다. mySQL은 limit 속성을 쓰지만 
--오라클에서는 limit 속성이 없기 때문에 rownum이라는 가상 컬럼과 서브 쿼리를 이용해서 DB에서 데이터를 잘라온다. 목록과 검색은 따라다닌다. alias  
--rownum 컬럼의 특징이 앞에서부터 순서를 뽑아내 오는 것... 중간의 값을 가져오는 것이 제한된다.


SELECT 
bbsno, wname, title, content, passwd, viewcnt, wdate, grpno, indent, ansnum, r
FROM(
	SELECT 
	bbsno, wname, title, content, passwd, viewcnt, wdate, grpno, indent, ansnum, rownum r
	FROM(
		SELECT 
	     bbsno, wname, title, content, passwd, viewcnt, wdate, grpno, indent, ansnum 
		FROM     bbs  
		--WHERE if(word.trim().length()>0) {sql.append(" WHERE "+col+" like '%'||?||'%'   " );}
		ORDER BY grpno DESC, ansnum
))
WHERE r >= 3 and r <= 10

--조회수 증가
UPDATE bbs					
SET							
	   viewcnt = viewcnt + 1
WHERE  bbsno = 1;			

--패스워드 검증
SELECT							
	 COUNT(bbsno) AS cnt		
FROM bbs						
WHERE							
	 bbsno=2 AND passwd = '123';

	 
-- 답변

	 
-- 모든 레코드 삭제	 
DELETE FROM bbs; 
           BBSNO  TITLE       GRPNO   INDENT   ANSNUM 
           -----   -----      -----   ------   ------ 
             2    부모글2       2       0        0
           	 1    부모글1       1       0        0 
           	 4      부모1답변2  1       1        1
           	 5         답2답변1 1       2        2
           	 3      부모1답변1  1       1        3

           	 BBSNO  TITLE  GRPNO INDENT ANSNUM 
           -----  -----  ----- ------ ------ 
             2  부모글2      2      0      0 
             1  부모글1      1      0      0 
             
             BBSNO  TITLE  			GRPNO INDENT ANSNUM 
             -----  -----  			----- ------ ------ 
             4  부모글4    	 	   		4      0      0    
     		 10   부모글4의 답변2  		4      1      1
             6	  부모글4의 답변1  		4      1      2
             3  부모글3      	   		3      0      0      
			 9    부모글3의 답변3  		3      1      1
			 11       답변3의 답변1		3	   2      2
			 12           답변1의 답변  3      3      3
             8	  부모글3의 답변2  		3      1      4
             7	  부모글3의 답변1  		3      1  	  5
    		 2  부모글2     	   		2      0      0 
    
             1  부모글1      	   		1      0      0 

--부모글 생성
             
INSERT INTO
bbs(	bbsno, 		
		wname, 		title,		content, 	passwd, 	wdate,		
		grpno)
VALUES( (SELECT NVL(MAX(bbsno),0) + 1 as bbsno FROM bbs),
		'투투',	'제목',		'내용',		'123',		sysdate,	
		(SELECT NVL(MAX(grpno),0)+1 from BBS)  );
		
		SELECT bbsno, wname, title, viewcnt, grpno, indent, ansnum, to_char(wdate,'yyyy-mm-dd') wdate FROM bbs ORDER BY grpno DESC, ansnum;
		
		SELECT 	count(refnum)
		FROM 		bbs
		WHERE 		refnum = 3