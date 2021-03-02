UPDATE guest
SET content='수정해보겠습니다', mdate=sysdate
WHERE num = 18;

CREATE table GUEST
(
    num 		NUMBER PRIMARY KEY,
    id			VARCHAR2(20) 	NOT NULL,
    content 	VARCHAR2(4000) 	NOT NULL,
    ipAddr 		VARCHAR2(50)	NOT NULL,
   	mdate		DATE			NOT NULL	
);

DROP TABLE GUEST

ALTER TABLE GUEST
ADD(id		VARCHAR2(10)	NULL)

SELECT * FROM GUEST;

INSERT INTO guest(id, content, ipAddr, mdate)   ");
VALUES			  (?,    ?,       ?,    sysdate     )  

SELECT num, id, content, ipAddr, mdate, r
 FROM(
 	SELECT num, id, content, ipAddr, mdate, rownum r
 	FROM(  
	    SELECT num, id, content, ipAddr, mdate  
	 	FROM guest
 		ORDER BY num DESC
 		)
 	)
 WHERE r >= 1 and r <= 5;
 
SELECT num, id, content, mdate
FROM guest
WHERE num = 18;

