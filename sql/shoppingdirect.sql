create table shoppingdirect()

CREATE TABLE shoppingdirect(
	
	directID 		NUMBER(20)     	NOT NULL,
	email			VARCHAR2(50)		NOT NULL,				--성명
	productname		VARCHAR2(50)		NOT NULL,
	productaddress	VARCHAR2(50)		NOT NULL,
	numbers			number(20)		NOT NULL,
	price			number(20)		NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	productsum		number(20)		NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	PRIMARY KEY (directID)
);
ALTER TABLE shoppingdirect
ADD(		
	uploadbtn		VARCHAR(50)	NULL)		

select * from shoppingdirect

SELECT sum(productsum) FROM shoppingdirect
WHERE email = 'user2@mail.com'

select sum(productsum) from shoppingdirect

DELETE from shoppingdirect WHERE email = 'user2@mail.com'

DROP TABLE shoppingdirect


SELECT count(*) FROM shoppingdirect
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'

DELETE FROM shoppingdirect
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'
	