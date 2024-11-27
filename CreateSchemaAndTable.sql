# スキーマの作成
CREATE SCHEMA `helloworld2` DEFAULT CHARACTER SET utf8mb4;

# テーブルの作成
CREATE TABLE `helloworld2`.`Region` 
(
	`Name` CHAR(20) NOT NULL UNIQUE,
	PRIMARY KEY (`Name`)
)
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `helloworld2`.`RegionMap` 
(
	`Region` CHAR(20) NOT NULL,
	`Prefecture` CHAR(20) NOT NULL,
	PRIMARY KEY (`Region`, `Prefecture`),
	FOREIGN KEY (`Region`) REFERENCES `helloworld2`.`Region` (`Name`),
	FOREIGN KEY (`Prefecture`) REFERENCES `helloworld2`.`Region` (`Name`)
)
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `helloworld2`.`FamilyName` 
(
	`Ranking` INT,
	`Name` CHAR(10) NOT NULL,
	`Region` CHAR(20) NOT NULL,
	`Population` INT NOT NULL,
	PRIMARY KEY (`Name`, `Region`),
	FOREIGN KEY (`Region`) REFERENCES `helloworld2`.`Region` (`Name`)
)
DEFAULT CHARACTER SET = utf8mb4;
