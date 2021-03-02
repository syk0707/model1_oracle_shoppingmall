CREATE TABLE shoppingproduct( 

  productname 		VARCHAR(50)    NOT NULL,  -- 내용  
  productID  		NUMBER(20)     NOT NULL,  -- 일련번호, -999999 ~ +999999 
  producttype   	VARCHAR(100)   NOT NULL,  -- 메모 제목, 한글 50자 
  explanation   	VARCHAR(1000)  NOT NULL,  -- 등록 날짜  
  price				NUMBER(20)     DEFAULT 0, -- 조회수, -99999 ~ +99999
  inventory			NUMBER(20)     DEFAULT 0, -- 수량
  uploadbtn  		VARCHAR(50)	   DEFAULT 'product.jpg',
  detailupload  	VARCHAR(50)	   DEFAULT 'product.jpg',
  mdate  	    DATE	   	   NOT NULL,
  PRIMARY KEY(productname)              -- 고유한 값, 중복 안됨    
);

DROP TABLE shoppingproduct

DELETE FROM SHOPPINGPRODUCT WHERE productid = '9';

ALTER TABLE shoppingproduct;
ADD(		explanation   VARCHAR(4000)          NOT NULL
	)	

ALTER TABLE shoppingproduct
ADD(		detailupload   VARCHAR(50)         DEFAULT 'product.jpg'
	);	
	
SELECT * FROM SHOPPINGPRODUCT WHERE productid = '4';
SELECT count(*) FROM shoppingproduct WHERE producttype='과자간식';

INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '공기청정기', '알로 A7', 'airCleaner_ALLO_A7.jsp',
'원통형 디자인의 공기청정기', 59900, 150,'airCleaner_ALLO_A7.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '공기청정기', '클레어 B1BU0533', 'airCleaner_clair_B1BU0533.jsp',
'깔끔한 미니 공기청정기', 49900, 150,'airCleaner_clair_B1BU0533.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '공기청정기', '아이엠헬스케어 브리지온', 'airCleaner_imhealthcare_BridgeOn.jsp',
'미래형 디자인의 공기청정기', 49900, 150,'airCleaner_imhealthcare_BridgeOn.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '공기청정기', '아이닉스 닥터V', 'airCleaner_ionix_DocterV.jsp',
'넥밴드형 휴대공기청정기', 59900, 150,'airCleaner_ionix_DocterV.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '가습기', '키친아트 KAH-601ME', 'humidifier_Kah-601me.jsp',
'청소가 간편한 미니가습기', 19900, 150,'humidifier_Kah-601me.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '과자간식', '포테이토크리스프', 'food_potatocrisp.jsp',
'맛있는 감자스낵', 10900, 150,'food_potatocrisp.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '과자간식', '양갱', 'food_yangang.jsp',
'쫀득쫀득한 그 감촉', 29900, 150,'food_yangang.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '견과류', '아몬드', 'food_almond.jsp',
'건강한 아몬드', 19900, 200,'food_almond.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '방향제탈취제', '페브리즈 순(무향)', 'deodorant_febreze.jsp',
'냄새없는 탈취효과', 9900, 200,'deodorant_febreze.png',sysdate);
INSERT INTO shoppingproduct values((SELECT NVL(MAX(productID),0) + 1 as productid FROM shoppingproduct), '보디케어', '블렉베리 바디스퀘어 1+1', 'bodycare_blackberry.jsp',
'하루종일 촉촉해요', 25900, 200,'bodycare_blackberry.png',sysdate);

SELECT productid, producttype, productname,  productaddress, explanation, price, inventory, uploadbtn
		FROM shoppingproduct
		    WHERE producttype = '과자간식' OR producttype = '견과류';


SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, r
FROM(
	SELECT productid, producttype, productname, productaddress, explanation, price, inventory, uploadbtn, rownum r
	FROM(  ");
		SELECT productid, producttype, productname,  productaddress, explanation, price, inventory, uploadbtn
		FROM shoppingproduct
		    WHERE producttype LIKE "+producttype+"AND" +col+" LIKE '%'||?||'%'
		)
		)
		WHERE r >= ? and r <= ?

SELECT productaddress FROM shoppingproduct
where productname = '아이닉스 닥터V';
		
		
SELECT S.*
FROM(
	SELECT *
	FROM shoppingproduct
	ORDER BY DBMS_RANDOM.VALUE
)S
WHERE ROWNUM<=8

