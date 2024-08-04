use library;

INSERT INTO 'librarybranch' VALUES (1, 'Dr. Martin Luther King, Jr. Libary', '150 E San Fernando St., San Jose, CA 95112');
INSERT INTO 'librarybranch' VALUES (2, 'Cupertino Library', '10800 Torre Ave, Cupertino, Cupertino, CA 95014');
INSERT INTO 'librarybranch' VALUES (3, 'Sunnyvale Public Library', '665 W Olive Ave, Sunnyvale, CA 94086');
INSERT INTO 'librarybranch' VALUES (4, 'Mountain View Public Library', '585 Franklin St, Mountain View, CA 94041');
INSERT INTO 'librarybranch' VALUES (5, 'Milpitas Library', '160 N Main St, Milpitas, CA 95035');

insert into person(uname,usertype,preferredbranch) values('Ivan','U','1');
insert into person(uname,usertype,preferredbranch) values('Tom','U','2');
insert into person(uname,usertype,preferredbranch) values('Amy','U','3');
insert into person(uname,usertype,preferredbranch) values('Bob','U','4');
insert into person(uname,usertype,preferredbranch) values('Alice','U','5');

insert into item(title,author,edition,libraryBranchID,ItemType,standardNumber,copies) values();

insert into rating(ratingdate,itemid,personid,stars) values();
