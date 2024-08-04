use library;
/*Create a stored procedure that returns the total number of copies of a book our library owns, given a title 
A. First,find the standard number(S) of A. 
Then, go through the ITEM table to find tuples where standard Number= S. 
Then, add the number of copies together to find the total number of copies the whole library system owns.*/


DROP PROCEDURE IF EXISTS FindNumberOfCopies;
DELIMITER //
CREATE PROCEDURE FindNumberOfCopies ( IN itemTitle varchar(150),  IN itemEdition varchar(150), IN itemAuthor varchar(150), OUT result int(11))
BEGIN
 select sum(copies) into result from item where title = itemTitle and edition=itemEdition and author=itemAuthor;
END //
DELIMITER ;

call FindNumberOfCopies( 'Venezuela, a democracy', '1st ed.','Allen, Henry Justin, 1868-1950.', @result);
select @result;

