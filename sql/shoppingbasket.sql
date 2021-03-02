create table shoppingbasket()

CREATE TABLE shoppingbasket(
	
	basketID 		NUMBER(20)     	NOT NULL,
	email			VARCHAR2(50)		NOT NULL,				--성명
	productname		VARCHAR2(50)		NOT NULL,
	productaddress	VARCHAR2(50)		NOT NULL,
	numbers			number(20)		NOT NULL,
	price			number(20)		NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	productsum		number(20)		NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	PRIMARY KEY (basketID)
);

select * from SHOPPINGBASKET

SELECT sum(productsum) FROM shoppingbasket
WHERE email = 'user2@mail.com'

select sum(productsum) from shoppingbasket

DELETE from shoppingbasket WHERE email = 'user2@mail.com'

DROP TABLE shoppingbasket

ALTER TABLE shoppingbasket
ADD(		
	uploadbtn		VARCHAR(50)	NULL)		

SELECT count(*) FROM shoppingbasket
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'

DELETE FROM shoppingbasket
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'
	