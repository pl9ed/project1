DROP TABLE IF EXISTS ERS_REIMBURSEMENT, ERS_REIMBURSEMENT_STATUS, ERS_REIMBURSEMENT_TYPE CASCADE;
DROP TABLE IF EXISTS ERS_USERS, ERS_ROLES CASCADE;

CREATE TABLE ERS_REIMBURSEMENT_STATUS(
	STATUS_ID INTEGER PRIMARY KEY,
	STATUS VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE ERS_REIMBURSEMENT_TYPE(
	TYPE_ID INTEGER PRIMARY KEY,
	REIMB_TYPE VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE ERS_ROLES(
	USER_ROLE_ID INTEGER PRIMARY KEY,
	USER_ROLE VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE ERS_USERS(
	USER_ID INTEGER PRIMARY KEY,
	USERNAME VARCHAR(50) UNIQUE NOT NULL,
	PASSHASH VARCHAR(250) NOT NULL,
	FIRST_NAME VARCHAR(100) NOT NULL,
	LAST_NAME VARCHAR(100) NOT NULL,
	EMAIL VARCHAR(150) NOT NULL,
	ROLE_ID INTEGER NOT NULL,
	FOREIGN KEY (ROLE_ID) REFERENCES ERS_ROLES(USER_ROLE_ID)
);

CREATE TABLE ERS_REIMBURSEMENT(
	REIMB_ID INTEGER PRIMARY KEY,
	AMOUNT DECIMAL(50,2) NOT NULL,
	SUBMITTED DATE NOT NULL,
	RESOLVED DATE,
	DESCRIPTION VARCHAR(250),
	RECEIPT BYTEA NOT NULL,
	AUTHOR INTEGER NOT NULL,
	RESOLVER INTEGER,
	TYPE_ID INTEGER NOT NULL,
	STATUS_ID INTEGER NOT NULL,
	FOREIGN KEY (STATUS_ID) REFERENCES ERS_REIMBURSEMENT_STATUS(STATUS_ID),
	FOREIGN KEY (TYPE_ID) REFERENCES  ERS_REIMBURSEMENT_TYPE(TYPE_ID),
	FOREIGN KEY (AUTHOR) REFERENCES ERS_USERS(USER_ID),
	FOREIGN KEY (RESOLVER) REFERENCES ERS_USERS(USER_ID)
);