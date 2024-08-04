unlock tables;
drop database if exists library ;
CREATE DATABASE  IF NOT EXISTS `library` ;
USE `library`;

CREATE TABLE `librarybranch` (
  `LibraryBranchID` int(11) NOT NULL AUTO_INCREMENT,
  `BranchName` varchar(120) DEFAULT NULL,
  `Address` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`LibraryBranchID`)
); 


CREATE TABLE `item` (
  `title` varchar(150) DEFAULT NULL,
  `author` varchar(120) DEFAULT NULL,
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `standardNumber` varchar(150) DEFAULT '-1',
  `Edition` varchar(50) DEFAULT NULL,
  `LibraryBranchID` int(11) DEFAULT NULL,
  `ItemType` varchar(50) DEFAULT NULL,
  `copies` int(11) DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ItemId`),
  KEY `item_ibfk_1` (`LibraryBranchID`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`LibraryBranchID`) REFERENCES `librarybranch` (`LibraryBranchID`)
) ;


CREATE TABLE `archiveitem` (
  `title` varchar(150) DEFAULT NULL,
  `author` varchar(120) DEFAULT NULL,
  `ItemId` int(11) NOT NULL AUTO_INCREMENT,
  `standardnumber` varchar(20) DEFAULT NULL,
  `Edition` varchar(50) DEFAULT NULL,
  `LibraryBranchID` int(11) DEFAULT NULL,
  `ItemType` varchar(50) DEFAULT NULL,
  `copies` int(11) DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ItemId`)
) ;


CREATE TABLE `person` (
  `PersonId` int(11) NOT NULL AUTO_INCREMENT,
  `uNAME` varchar(30) NOT NULL,
  `UserType` varchar(30) DEFAULT NULL,
  `PreferredBranch` int(11) DEFAULT NULL,
  `TotalLoansMade` int(11) DEFAULT NULL,
  PRIMARY KEY (`PersonId`),
  KEY `person_ibfk_1` (`PreferredBranch`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`PreferredBranch`) REFERENCES `librarybranch` (`LibraryBranchID`)
) ;


CREATE TABLE `loan` (
  `LoanId` int(11) NOT NULL AUTO_INCREMENT,
  `Pid` int(11) DEFAULT NULL,
  `Itemid` int(11) DEFAULT NULL,
  `loanDate` date DEFAULT '0000-00-00',
  `overdue` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`LoanId`),
  KEY `loan_ibfk_1` (`Pid`),
  KEY `loan_ibfk_2` (`Itemid`),
  CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`Pid`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `loan_ibfk_2` FOREIGN KEY (`Itemid`) REFERENCES `item` (`ItemId`)
) ;


CREATE TABLE `rating` (
  `ratingid` int(11) NOT NULL AUTO_INCREMENT,
  `ratingdate` date DEFAULT NULL,
  `ItemId` int(11) DEFAULT NULL,
  `PersonId` int(11) DEFAULT NULL,
  `stars` int(11) DEFAULT NULL,
  PRIMARY KEY (`ratingid`),
  KEY `rating_ibfk_1` (`PersonId`),
  KEY `rating_ibfk_2` (`ItemId`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`PersonId`) REFERENCES `person` (`PersonId`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`)
); 
