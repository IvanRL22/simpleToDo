-- Basic tasks table
CREATE TABLE IF NOT EXISTS TASKS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    DONE BOOLEAN
);

-- Users and roles tables
CREATE TABLE IF NOT EXISTS USERS (
	USERNAME VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
	PASSWORD VARCHAR_IGNORECASE(500) NOT NULL,
	ENABLED BOOLEAN NOT NULL
);

CREATE TABLE AUTHORITIES (
	USERNAME VARCHAR_IGNORECASE(50) NOT NULL,
	AUTHORITY VARCHAR_IGNORECASE(50) NOT NULL,
	CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME)
);

CREATE UNIQUE INDEX IX_AUTH_USERNAME ON AUTHORITIES (USERNAME,AUTHORITY);

INSERT INTO "PUBLIC"."USERS" VALUES
    ('ivanrl', '{noop}mypassword', TRUE),
    ('test', '{noop}test', TRUE);
INSERT INTO "PUBLIC"."AUTHORITIES" VALUES
    ('ivanrl', 'ROLE_USER'),
    ('test', 'ROLE_USER');

-- Test users
ALTER TABLE TASKS ADD COLUMN USERNAME VARCHAR_IGNORECASE(50) NOT NULL USING 'ivanrl';
ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME);

-- Link users to tasks
ALTER TABLE TASKS ADD COLUMN USERNAME VARCHAR_IGNORECASE(50) NOT NULL USING 'ivanrl';
ALTER TABLE TASKS ADD CONSTRAINT FK_TASKS_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME);