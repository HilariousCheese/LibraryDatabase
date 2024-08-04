
use library;


DROP PROCEDURE IF EXISTS CopyToArchive;
DELIMITER //
CREATE PROCEDURE CopyToArchive ( IN cutoffDate timestamp)
BEGIN
START TRANSACTION;
insert into archiveitem(title,author,standardNumber,edition,libraryBranchID,itemtype,copies,updatedAt) 
select title,author,standardNumber,edition,libraryBranchID,itemtype,copies,updatedAt from item where date(updatedAt)<=date(cutoffdate) ;
commit;
END //
DELIMITER ;

-- select * from archiveitem;

call copyToArchive(current_timestamp()+1);

 select * from archiveitem;
