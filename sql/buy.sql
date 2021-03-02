create table shoppingbuy()

CREATE TABLE shoppingbuy(
	
	buyID 			NUMBER(20)     		NOT NULL,
	email			VARCHAR2(50)		NOT NULL,				--성명
	productname		VARCHAR2(50)		NOT NULL,
	name			VARCHAR2(20)		NOT NULL,
	price			VARCHAR2(50)		NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	totalprice		VARCHAR2(50)			NOT NULL,			--일반회원: H, 정지: Y, 손님:Z
	numbers			VARCHAR2(50)		NOT NULL,
	phone			VARCHAR2(14)		NOT NULL,
	zipcode			VARCHAR2(7)			NOT NULL,
	address1		VARCHAR2(150)		NOT NULL,
	address2		VARCHAR2(50)		NOT NULL,
	payment			VARCHAR2(50)		NOT NULL,
	mdate			date				NOT NULL,

	PRIMARY KEY (buyID)
);

select * from shoppingbuy

SELECT sum(productsum) FROM shoppingbuy
WHERE email = 'user2@mail.com'

select sum(productsum) from shoppingbuy

DELETE from shoppingbuy WHERE email = 'user2@mail.com'

DROP TABLE shoppingbuy

ALTER TABLE shoppingbuy
ADD(		
	uploadbtn		VARCHAR(50)	NULL)		

SELECT count(*) FROM shoppingbuy
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'

DELETE FROM shoppingbuy
WHERE email = 'user2@mail.com' AND productname = '포테이토크리스프'
	