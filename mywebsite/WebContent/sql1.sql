show tables;

desc user;

desc bbs;

select * from bbs;

select * from user;


select now()
select SYSDATE()


UPDATE BBS SET bbsTitle = 'modified', bbsContent = 'modified' WHERE bbsID = 18


SELECT * FROM BBS WHERE bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10

SELECT * FROM BBS WHERE bbsID < 10 AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10

SELECT * FROM BBS WHERE bbsID < 100 AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10

update bbs
set bbsID = bbsID - 1

delete from bbs




INSERT INTO BBS (bbsID, bbsTitle, userID, bbsDate, bbsContent, bbsAvailable)
VALUES (11, 'title', 'userID', null, 'content', 1);

CREATE TABLE BBS (

     bbsID INT,

     bbsTitle VARCHAR(50),

     userID VARCHAR(20),

     bbsDate DATETIME,

     bbsContent VARCHAR(2048),

     bbsAvailable INT,

     PRIMARY KEY (bbsID)

     ) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
