CREATE TABLE image( 

  imgno		NUMBER(6)     	NOT NULL,  -- 일련번호, -999999 ~ +999999 
  name		VARCHAR(10)  	NOT NULL,  -- 메모 제목, 한글 50자 
  title 	VARCHAR(100) 	NOT NULL,  -- 내용  
  contents  VARCHAR(4000)	NOT NULL,
  passwd	VARCHAR(20)		NOT NULL,	-- 등록 날짜  
  viewcnt	NUMBER(5)		DEFAULT 0, -- 조회수, -99999 ~ +99999
  fname		VARCHAR(50)		DEFAULT 'image.jpg',
  mdate 	DATE     		NOT NULL,
  grpno		NUMBER(7)		DEFAULT 0,
  indent	NUMBER(2)		DEFAULT 0,
  ansnum	NUMBER(5)		DEFAULT 0,-- 조회수, -99999 ~ +99999
  PRIMARY KEY(imgno)              -- 고유한 값, 중복 안됨    
);

ALTER TABLE image
ADD(filesize	number(20)	NULL)

DELETE FROM image;

DROP TABLE image;

SELECT * FROM image;

INSERT INTO image(	imgno, 		name, 		title,			contents,		passwd, 
					mdate)
VALUES			  (	(SELECT NVL(MAX(imgno),0) + 1 as imgno FROM image),		'user1',		'테스트',		'테스트',  '1234',	
					sysdate);