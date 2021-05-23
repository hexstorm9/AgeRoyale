-- Create MySQL Database with default root user and password and run this script ->
CREATE USER 'ageroyale'@'%' IDENTIFIED BY 'DPOcracks123!';

CREATE DATABASE ageroyaleDB;
GRANT ALL PRIVILEGES ON ageroyaleDB.* TO 'ageroyale'@'%';

USE ageroyaleDB;


CREATE TABLE user (
    name VARCHAR (20),
    mail VARCHAR (64) UNIQUE NOT NULL,
    hash VARCHAR (300) NOT NULL,
	  PRIMARY KEY (name)
);


CREATE TABLE player (
    name VARCHAR (20),
	  crowns INTEGER DEFAULT 0,
	  battle_wins INTEGER DEFAULT 0,
    battle_plays INTEGER DEFAULT 0,
	  PRIMARY KEY (name),
    FOREIGN KEY (name) REFERENCES user(name)
);


CREATE TABLE user_cards (
	  name VARCHAR (20),
	  adams INTEGER DEFAULT 1,
	  trump INTEGER DEFAULT 1,
	  david INTEGER DEFAULT 1,
  	rafa INTEGER DEFAULT 1,
  	saula INTEGER DEFAULT 1,
  	cano INTEGER DEFAULT 1,
  	malé INTEGER DEFAULT 1,
  	carpi INTEGER DEFAULT 1,
  	PRIMARY KEY (name),
  	FOREIGN KEY (name) REFERENCES user(name)
); 


CREATE TABLE battle_history(
    id INT AUTO_INCREMENT,
		name VARCHAR(20), 
	  player_name VARCHAR(20) DEFAULT NULL,
	  won BOOLEAN NOT NULL,
	  battle_date DATETIME,
	  movements VARCHAR(16384), 
	  PRIMARY KEY (battle_id),
	  FOREIGN KEY (player_name) REFERENCES user(name)
);

