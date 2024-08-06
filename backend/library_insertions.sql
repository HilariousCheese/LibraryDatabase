use library;

INSERT INTO librarybranch VALUES (1, 'Dr. Martin Luther King, Jr. Libary', '150 E San Fernando St., San Jose, CA 95112');
INSERT INTO librarybranch VALUES (2, 'Cupertino Library', '10800 Torre Ave, Cupertino, CA 95014');
INSERT INTO librarybranch VALUES (3, 'Sunnyvale Public Library', '665 W Olive Ave, Sunnyvale, CA 94086');
INSERT INTO librarybranch VALUES (4, 'Mountain View Public Library', '585 Franklin St, Mountain View, CA 94041');
INSERT INTO librarybranch VALUES (5, 'Milpitas Library', '160 N Main St, Milpitas, CA 95035');


insert into person(uname,pw,usertype,preferredbranch) values('admin','123','A','1');
insert into person(uname,pw,usertype,preferredbranch) values('Ivan','123','U','1');
insert into person(uname,pw,usertype,preferredbranch) values('Tom','123','U','2');
insert into person(uname,pw,usertype,preferredbranch) values('Amy','123','U','3');
insert into person(uname,pw,usertype,preferredbranch) values('Bob','123','U','4');
insert into person(uname,pw,usertype,preferredbranch) values('Alice','123','U','5');


insert into item(title,author,libraryBranchID,ItemType,copies) values('Dune', 'Frank Herbert', '1', 'Novel', '5');
insert into item(title,author,libraryBranchID,ItemType,copies) values('To Kill a Mockingbird', 'Harper Lee', '4', 'Novel', '2');
insert into item(title,author,libraryBranchID,ItemType,copies) values('Life of Pi', 'Yann Martel', '4', 'Novel','1');
insert into item(title,author,libraryBranchID,ItemType,copies) values('Where is Waldo?', 'Martin Handford', '5','Picture Book', '3' );
insert into item(title,author,libraryBranchID,ItemType,copies) values('The Very Hungry Caterpillar', 'Eric Carle', '5', 'Board Book', '3');
insert into item(title,author,libraryBranchID,ItemType,copies) values('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '2', 'Novel', '5');
insert into item(title,author,libraryBranchID,ItemType,copies) values('If You Give a Mouse a Cookie', 'Laura Numeroff', '1', 'Picture Book', '2');


insert into rating(ratingdate,itemid,personid,stars) values('2020-03-12', '1', '3', '3');
insert into rating(ratingdate,itemid,personid,stars) values('2023-12-12', '1', '4', '3');
insert into rating(ratingdate,itemid,personid,stars) values('2020-08-11', '1', '5', '4');
insert into rating(ratingdate,itemid,personid,stars) values('2021-04-30', '1', '2', '3');
insert into rating(ratingdate,itemid,personid,stars) values('2021-08-06', '4', '3', '3');
insert into rating(ratingdate,itemid,personid,stars) values('2022-09-26', '3', '1', '5');
insert into rating(ratingdate,itemid,personid,stars) values('2024-07-17', '5', '1', '4');
insert into rating(ratingdate,itemid,personid,stars) values('2023-11-24', '3', '5', '5');
insert into rating(ratingdate,itemid,personid,stars) values('2022-06-14', '4', '2', '2');
insert into rating(ratingdate,itemid,personid,stars) values('2021-11-19', '4', '1', '1');
insert into rating(ratingdate,itemid,personid,stars) values('2021-02-02', '3', '3', '2');
insert into rating(ratingdate,itemid,personid,stars) values('2023-12-21', '5', '5', '5');

