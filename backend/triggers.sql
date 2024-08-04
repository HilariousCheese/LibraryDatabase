use library;


-- If a user's name has been changed to paid guy, add "5" star reviews from that person for all items.
-- The logic is that if this person has been paid to write good reviews, we just add his/her "5 star" reviews to all books.
DROP TRIGGER IF EXISTS SuperReviewer;
delimiter //
CREATE TRIGGER SuperReviewer
AFTER UPDATE ON person
FOR EACH ROW
BEGIN
IF (new.uname='PaidGuy') then
     insert into rating(personid,ratingdate,ItemId,stars)
     select new.personid,current_date(),itemid,5 from item;
     END IF;
END//
delimiter ;

--  select * from person join rating using(personid) where personid=8;
--  update person set uname="PaidGuy" where personid=8;
-- select * from person join rating using(personid) where PersonId=8;



-- when a loan is made, update the number of copies as well as the total loans made by user.
DROP TRIGGER IF EXISTS UpdateUserTotalLoans;
delimiter //
CREATE TRIGGER UpdateUserTotalLoans
BEFORE INSERT ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade+1 WHERE Personid = new.pid;


END//
delimiter ;

-- when a loan is closed, update the number of copies as well as the total loans made by user.
DROP TRIGGER IF EXISTS UpdateUserTotalLoansOnDelete;
delimiter //
CREATE TRIGGER UpdateUserTotalLoansOnDelete
BEFORE DELETE ON loan
FOR EACH ROW
BEGIN
UPDATE person SET TotalLoansMade = TotalLoansMade-1 WHERE Personid = old.pid;

END//
delimiter ;


