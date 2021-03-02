---관리자 계정 등록
INSERT INTO member(	id, 		passwd, 	mname,			tel,			 email, 
					zipcode, 	address1, 	address2, 		job,
					mdate, 		uploadbtn,		grade)
VALUES			  (	'admin',	'1234',		'관리자1',		'010-123-1234',	'admin@mail.com',	
					'123-123',	'용인시',	'남동구',		'A01',
					sysdate,	'member.jpg',	'A');

DROP TABLE member

SELECT passwd
FROM member
WHERE id='user1';

CREATE TABLE member(

	id			VARCHAR(10)		NOT NULL,
	passwd		VARCHAR(20)		NOT NULL,
	mname		VARCHAR(20)		NOT NULL,				--성명
	tel			VARCHAR(14)		NULL,
	email		VARCHAR(50)		NOT NULL UNIQUE,
	zipcode		VARCHAR(7)		NULL,
	address1	VARCHAR(150)	NULL,
	address2	VARCHAR(50)		NULL,
	job			VARCHAR(20)		NOT NULL,
	mdate		DATE			NOT NULL,				--가입일
	uploadbtn	VARCHAR(50)		DEFAULT 'member.jpg',	--회원 사진
	grade		CHAR(1)			DEFAULT 'H',			--일반회원: H, 정지: Y, 손님:Z
	PRIMARY KEY (id)
);

SELECT * FROM member;

DELETE FROM member where id like 's';

--create

INSERT INTO member(	id, 		passwd, 	mname,			tel,			 email, 
					zipcode, 	address1, 	address2, 		job,
					mdate, 		uploadbtn,		grade)
VALUES			  (	'user1',	'1234',		'개발자1',		'123-1234',		'email@mail.com',	
					'123-123',	'광명시',	'남동구',		'A01',
					sysdate,	'man.jpg',	'H');
					
INSERT INTO member(	id, 		passwd, 	mname,			tel,			 email, 
					zipcode, 	address1, 	address2, 		job,
					mdate, 		uploadbtn, 		grade)
VALUES			  (	'user2',	'1234',		'개발자2',		'123-1234',		'email2@mail.com',	
					'123-123',	'광명시',	'남동구',		'A01',
					sysdate,	'man.jpg',	'H');
					
INSERT INTO member(	id, 		passwd, 	mname,			tel,			 email, 
					zipcode, 	address1, 	address2, 		job,
					mdate, 		uploadbtn,		grade)
VALUES			  (	'user3',	'1234',		'개발자3',		'123-1234',		'email3@mail.com',	
					'123-123',	'용인시',	'남동구',		'A01',
					sysdate,	'myface.jpg',	'H');
					
ALTER TABLE member
ADD(uploadbtn		VARCHAR(50)		DEFAULT 'member.jpg')		

ALTER TABLE member
DROP COLUMN fname;

SELECT 	COUNT(id)
FROM	member
WHERE id='user1';


SELECT	COUNT(email) AS cnt
FROM 	member
WHERE 	email='email3@mail.com';

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, job, mdate, uploadbtn, grade
FROM	member
ORDER BY id ASC;

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, 
    job, mdate, uploadbtn, grade 
FROM member 
WHERE id LIKE '%%' -- 검색어가 없음으로 전체 레코드 출력 
ORDER BY id ASC;   -- id 컬럼값으로 오름차순 정렬, DESC: 내림 차순

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, job, mdate, uploadbtn
FROM member  
ORDER BY mdate DESC; 

--회원 이미지의 수정
UPDATE  member
SET 	uploadbtn=''
WHERE 	id='user1';

--패스워드 변경
UPDATE member
SET passwd='1234'
WHERE id='user1';

UPDATE member  
SET		passwd='TEST',	tel='123-123',	email='email10',	zipcode='TEST',
		address1='수원',address2='팔달구', job='TEST'  
WHERE id='user3';

--user3 회원 삭제
DELETE FROM member WHERE id='user3';

SELECT * FROM member;


--모든 회원 삭제
DELETE FROM member;

SELECT COUNT(id) AS cnt
FROM member
WHERE id = 'user1' AND passwd = '1234';

--list
SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn, r
FROM(
	SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn, rownum r
	FROM(
		SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn
		FROM member
		WHERE mname LIKE '%'
		ORDER BY mdate DESC
		)
	)
WHERE r >= 1 and r <= 5