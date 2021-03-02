---관리자 계정 등록
INSERT INTO mallmember(	id, 		passwd, 	mname,			tel,			 email, 
					zipcode, 	address1, 	address2, 		job,
					mdate, 		uploadbtn,		grade)
VALUES			  (	'admin',	'1234',		'관리자1',		'010-123-1234',	'admin@mail.com',	
					'123-123',	'용인시',	'남동구',		'A01',
					sysdate,	'mallmember.jpg',	'A');

DROP TABLE mallmember

SELECT passwd
FROM mallmember
WHERE id='user1';

SELECT name
FROM mallmember
WHERE email='user1@mail.com'

CREATE TABLE mallmember(

	name		VARCHAR(20)		NOT NULL,				--성명
	email		VARCHAR(50)		NOT NULL,
	password	VARCHAR(20)		NOT NULL,
	phone		VARCHAR(14)		NULL,
	grade		CHAR(1)			DEFAULT 'H',			--일반회원: H, 정지: Y, 손님:Z
	PRIMARY KEY (email)
);

SELECT * FROM mallmember;

DELETE FROM mallmember where id like 's';

--create

INSERT INTO mallmember(name,	email,				password,
					phone,				grade)
VALUES			  (	'user1',	'user1@mail.com',	'1234',	
					'010-1234-5678',	'H');
					
INSERT INTO mallmember(	name, 		email, 				password, 
					phone, 		grade)
VALUES			  (	'admin',	'admin',			'admin',	
					'010-0000-0000',	'A');

INSERT INTO mallmember(name,	email,				password,
					phone,				grade)
VALUES			  (	'user1',	'user1@mail.com',	'1234',	
					'010-1234-5678',	'H');

					
ALTER TABLE mallmember
ADD(		zipcode		VARCHAR(7)		NULL,
	address1	VARCHAR(150)	NULL,
	address2	VARCHAR(50)		NULL)		
	
ALTER TABLE mallmember
ADD(		mdate	VARCHAR(20)	NULL)			

ALTER TABLE mallmember
DROP COLUMN fname;

SELECT 	COUNT(id)
FROM	mallmember
WHERE id='user1';


SELECT	COUNT(email) AS cnt
FROM 	mallmember
WHERE 	email='email3@mail.com';

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, job, mdate, uploadbtn, grade
FROM	mallmember
ORDER BY id ASC;

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, 
    job, mdate, uploadbtn, grade 
FROM mallmember 
WHERE id LIKE '%%' -- 검색어가 없음으로 전체 레코드 출력 
ORDER BY id ASC;   -- id 컬럼값으로 오름차순 정렬, DESC: 내림 차순

SELECT id, passwd, mname, tel, email, zipcode, address1, address2, job, mdate, uploadbtn
FROM mallmember  
ORDER BY mdate DESC; 

--회원 이미지의 수정
UPDATE  mallmember
SET 	uploadbtn=''
WHERE 	id='user1';

--패스워드 변경
UPDATE mallmember
SET passwd='1234'
WHERE id='user1';

UPDATE mallmember  
SET		passwd='TEST',	tel='123-123',	email='email10',	zipcode='TEST',
		address1='수원',address2='팔달구', job='TEST'  
WHERE id='user3';

--user3 회원 삭제
DELETE FROM mallmember WHERE id='user3';

SELECT * FROM mallmember;


--모든 회원 삭제
DELETE FROM mallmember;

SELECT COUNT(id) AS cnt
FROM mallmember
WHERE id = 'user1' AND passwd = '1234';

--list
SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn, r
FROM(
	SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn, rownum r
	FROM(
		SELECT id, mname, tel, email, zipcode, address1, address2, uploadbtn
		FROM mallmember
		WHERE mname LIKE '%'
		ORDER BY mdate DESC
		)
	)
WHERE r >= 1 and r <= 5