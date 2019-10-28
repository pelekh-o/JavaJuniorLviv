CREATE DATABASE IF NOT EXISTS javavacancies;
USE javavacancies;

CREATE TABLE IF NOT EXISTS vacancy (
	vacancyId INT AUTO_INCREMENT,
	companyName VARCHAR(255) NOT NULL,
	vacancyTitle VARCHAR(255) NOT NULL,
	updated DATE NOT NULL,
	link TEXT NOT NULL,
	PRIMARY KEY (vacancyId)
) ENGINE=INNODB;