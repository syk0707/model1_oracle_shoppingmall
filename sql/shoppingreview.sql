
CREATE table SHOPPINGREVIEW
(
    productID 		NUMBER 			NOT NULL,
    reviewnum		NUMBER			PRIMARY KEY,
    email 			VARCHAR2(50) 	NOT NULL,
    name 			VARCHAR2(20)	NOT NULL,
    content 		VARCHAR2(4000) 	NOT NULL,
   	mdate			DATE			NOT NULL	
);

DROP TABLE shoppingreview


SELECT email
FROM	shoppingreview
WHERE reviewnum=7

SELECT * FROM shoppingreview;



INSERT INTO shoppingreview(productid, reviewnum, email, name, content, mdate)
VALUES			  (7,   (SELECT NVL(MAX(reviewnum),0) + 1 as reviewnum FROM shoppingreview),    'admin',       'admin',    '넘어간다.', sysdate     )

SELECT productid, reviewnum, email, name, content, mdate, r
 FROM(
 	SELECT productid, reviewnum, email, name, content, mdate, rownum r
 	FROM(  
	    SELECT productid, reviewnum, email, name, content, mdate 
	 	FROM shoppingreview
 		ORDER BY reviewnum DESC
 		)
 	)
 WHERE r >= 1 and r <= 5;
 
SELECT num, id, content, mdate
FROM shoppingreview
WHERE num = 18;

DELETE FROM shoppingreview
WHERE reviewnum = ;
