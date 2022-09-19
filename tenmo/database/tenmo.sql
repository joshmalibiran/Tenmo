BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;
  
  
CREATE TABLE transfer(
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	from_user_id int NOT NULL,
	to_user_id int NOT NULL,
	from_account_id int NOT NULL,
	to_account_id int NOT NULL,
	amt_transferred decimal(13, 2) NOT NULL,
	
	CONSTRAINT PK_transfer PRIMARY KEY(transfer_id),
	CONSTRAINT FK_transfer_account_from_user FOREIGN KEY(from_user_id) REFERENCES tenmo_user(user_id),
	CONSTRAINT FK_transfer_account_to_user FOREIGN KEY(to_user_id) REFERENCES tenmo_user(user_id),
	CONSTRAINT FK_transfer_account_from_account FOREIGN KEY(from_account_id) REFERENCES account(account_id),
	CONSTRAINT FK_transfer_account_to_account FOREIGN KEY(to_account_id) REFERENCES account(account_id)
);

INSERT INTO tenmo_user(user_id, username, password_hash) VALUES(1, 'tom', 'sdfasdf');
INSERT INTO tenmo_user(user_id, username, password_hash) VALUES(2, 'john', 'sdfasdfsa');
INSERT INTO tenmo_user(user_id, username, password_hash) VALUES(3, 'josh', 'sdfsadfasf');
INSERT INTO tenmo_user(user_id, username, password_hash) VALUES(4, 'pav', 'dsfasdfgs');
INSERT INTO tenmo_user(user_id, username, password_hash) VALUES(5, 'nick', 'sadgsaggs');

INSERT INTO account(account_id, user_id, balance) VALUES(10, 5, 2500);
INSERT INTO account(account_id, user_id, balance) VALUES(11, 5, 500);
INSERT INTO account(account_id, user_id, balance) VALUES(12, 4, 1200);
INSERT INTO account(account_id, user_id, balance) VALUES(13, 3, 800);
INSERT INTO account(account_id, user_id, balance) VALUES(14, 2, 100);

INSERT INTO transfer(transfer_id, from_user_id, to_user_id, from_account_id, to_account_id, amt_transferred)
VALUES(100, 5, 2, 10, 14, 300), 
(101, 4, 3, 12, 13, 400),
(102, 2, 5, 14, 11, 900),
(103, 4, 2, 12, 14, 4400),
(104, 5, 3, 11, 13, 75);

COMMIT;

SELECT user_id FROM tenmo_user WHERE username ILIKE 'john'
select * from tenmo_user
select * from account
select * from transfer
SELECT transfer_id, from_user_id, to_user_id, from_account_id, to_account_id, amt_transferred FROM transfer WHERE (from_user_id = 1001) OR (to_user_id = 1001);
