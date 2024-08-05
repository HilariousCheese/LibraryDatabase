use library;
-- 1. User shall sign in (by userID)

Select PersonID, uName, UserType, PreferredBranch From person Where PersonID = ?;

-- 2. Admin shall be able to add new user:

insert into person(uname,usertype,preferredbranch,totalLoansMade) values(?,?,?,?);

-- 3. Admin shall be able to delete a user:

DELETE FROM person where PersonId = ?;

-- 4. Users shall be able to update information. [preferred branch]

update person set PreferredBranch = (select libraryBranchID from librarybranch where BranchName=?) where person.PersonId = ?;

-- 5. Users shall be able to search for a book using the title, author or category. Search can be done in any combination i.e. only one or two or three of these parameters can be given. Users should be able to search this in combination.

SELECT * FROM item WHERE title Like '% ? %' and author = ? and itemType =?;

-- 6. User shall be able to rent a new book (Display books on for the library).

update item set copies = copies -1 where item.ItemId = ?;
insert into loan SET pid = ?, ItemId = ?, loandate = ?,overdue = false ON DUPLICATE KEY UPDATE ItemId = ?, loandate = ?,overdue = false;

-- 7.Users shall be able to return a book.

update item set copies = copies +1 where item.ItemId = ?;
delete from loan where loan.LoanId = ?;

-- 8. Admins shall be able to see all distinct users who have rented a book.

select distinct title , uNAME 
from person join loan on person.PersonId = loan.pid join item on Loan.itemid = Item.itemid
where UserType ='U'" + "order by title;

-- 9.Users can rate a book.

insert into rating set ratingdate = ?,itemid = ?,personid = ?,stars =?;

-- 10.Users can update the rating for a book later.

update rating set Stars = ? where RatingId = ?;

-- 11.Users shall be able to see the most popular books (top 5).

select title,count(*) total 
from loan join item on Loan.itemid=Item.itemid 
where ItemType='BOOK' 
group by title,author,edition
order by total desc 
Limit 5;

-- 12.Users shall be able to see the highest rated books (top 5).

select title,avg(stars) 
from rating join item on rating.itemid=Item.itemid 
where ItemType='Book' 
group by title,author,edition
order by avg(stars) desc
limit 5;

-- 13.Admin shall be able to retrieve users who have several [two or more] overdue books.

select PersonId,uname,count(*) from Person join Loan on Person.PersonId=Loan.Pid 
where overdue = true group by personid having count(*)>=2;

-- 14. Admins can find the total number of loans made at a library. 

select count(*) 
from loan
 join 
item on item.itemid = loan.itemid 
where libraryBranchID = 
(select librarybranchid from libraryBranch where branchname=?);

-- 15.Admin can find users who have given items a higher rating the second time he/she rated it.

select distinct r1.personid,i1.title,i1.author,i1.edition,r1.stars oldRating,r2.stars newRating
from rating r1 join rating r2 on r1.personid=R2.PERSONID 
join Item i1 on 
(select distinct title,edition,author from item where itemid=i1.itemid) 
= (select distinct title,edition,author from item where itemid=r1.itemid)
join item i2 on 
(select distinct title,edition,author from item where itemid=i2.itemid)
= (select distinct title,edition,author from item where itemid=r2.itemid) 
where r1.ratingDate<r2.ratingDate and r1.stars<=r2.stars and i1.title=i2.title;

-- 16.Users can find other books that have the same author as a given book.

select distinct title,edition from item old where author = (select distinct author  from item  where title= ? );

-- 17. Admin can see users who have not loaned any books.

SELECT * FROM PERSON left outer JOIN LOAN ON person.personid = LOAN.pid where loanid is null;

-- 18. Users can view all items that are of audio/video media [including e-books].

select distinct title,author,edition,itemtype from item where itemtype like '%ebook%'
union
select distinct title,author,edition,itemtype from item where itemtype like '%audio%'
union
Select  distinct title,author,edition,itemtype from item where itemtype like '%cd%'
union 
distinct select title,author,edition,itemtype from item where itemtype like '%dvd%';


