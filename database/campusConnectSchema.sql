DROP DATABASE IF EXISTS campus_connect;
CREATE DATABASE campus_connect;
USE campus_connect;

DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
  email varchar(64) NOT NULL,
  name varchar(100) NOT NULL,
  password varchar(50) NOT NULL,
  flag smallint CHECK(flag > 0 AND flag < 4),
  PRIMARY KEY (email, flag)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS Events;
CREATE TABLE Events (
  ID int unsigned NOT NULL AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  description text NOT NULL,
  hostEmail varchar(100) NOT NULL,
  location varchar(100) NOT NULL,
  date_featured DATE NOT NULL,
  time_featured TIME NOT NULL,
  capacity int unsigned NOT NULL,
  attending int unsigned,
  invite smallint CHECK(invite > 0 AND invite < 3),
  PRIMARY KEY (id),
  FOREIGN KEY (hostEmail) REFERENCES Users(Email)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS RSVP;
CREATE TABLE RSVP (
  useremail varchar(64) NOT NULL,
  rid int unsigned NOT NULL,
  rsvp smallint CHECK(rsvp > 0 AND rsvp < 5),
  primary key (useremail, rid),
  foreign key (useremail) references Users(email),
  FOREIGN KEY (rid) REFERENCES Events(ID)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS Invite;
CREATE TABLE Invite (
  useremail varchar(64) NOT NULL,
  iid int unsigned NOT NULL,
  primary key (useremail, iid),
  foreign key (useremail) references Users(email),
  FOREIGN KEY (iid) REFERENCES Events(ID)
) ENGINE=InnoDB;

INSERT INTO Users (email, name, password, flag) VALUES ("riya@gatech.edu", "Riya", "123456", 1),
("james@gatech.edu", "James", "123456", 1),
("kelly@gatech.edu", "Kelly", "123456", 1),
("eringe3@gatech.edu", "Emma", "123456", 2),
("josh@gatech.edu", "Josh", "123456", 1),
("somto@gatech.edu", "Somto", "123456", 1);

INSERT INTO Events (title, description, hostEmail, location, date_featured, time_featured, capacity, invite) VALUES
("Trick or Treat", "Go trick or treating in the library", "riya@gatech.edu", "Skiles", '2022-11-15', '23:59:59', 10, 1),
("CS 2340 Demo", "Go trick or treating in the library", "riya@gatech.edu", "Skiles", '2022-10-31', '23:59:59', 2, 1),
("Party", "Go trick or treating in the library", "riya@gatech.edu", "Klaus", '2022-10-31', '23:59:59', 11, 1),
("Football game", "Go trick or treating in the library", "riya@gatech.edu", "Klaus", '2022-10-31', '23:59:59', 13, 1),
("Dinner", "Go trick or treating in the library", "riya@gatech.edu", "Tech Tower", '2022-10-31', '23:59:59', 17, 2),
("Lunch", "Go trick or treating in the library", "riya@gatech.edu", "Tech Tower", '2022-10-31', '23:59:59', 27, 2),
("Breaky", "Go trick or treating in the library", "riya@gatech.edu", "Ferst Center", '2022-10-31', '23:59:59', 42, 1);

