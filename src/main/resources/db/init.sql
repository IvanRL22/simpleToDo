-- Authentication/authorization tables
CREATE TABLE users(
    username VARCHAR(30) PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);
CREATE TABLE authorities (
    username VARCHAR(30) PRIMARY KEY REFERENCES users(username),
    authority VARCHAR(30) NOT NULL
);

INSERT INTO users VALUES
('ivanrl', '{noop}mypassword', TRUE),
('test', '{noop}test', TRUE),
('signInTest', '{bcrypt}$2a$10$wTuvIgPXdCYUGXpjR/ZLxOUg4d7WqtAot/vMcEV5peVuHXBWsXDM.', TRUE);
INSERT INTO authorities VALUES
('ivanrl', 'ROLE_USER'),
('test', 'ROLE_USER'),
('signInTest', 'ROLE_USER');

-- Tasks table
CREATE SEQUENCE tasks_id_seq START WITH 104;
CREATE TABLE tasks(
    id INTEGER DEFAULT nextval('tasks_id_seq') PRIMARY KEY ,
    name VARCHAR(30) NOT NULL,
    done BOOLEAN,
    username VARCHAR(30) REFERENCES users(username)
);

INSERT INTO tasks VALUES
(15, 'Adjust style for task input', TRUE, 'ivanrl'),
(21, 'Fix live reload', FALSE, 'ivanrl'),
(29, 'Persist users in DB', TRUE, 'ivanrl'),
(30, 'Tie tasks to user', TRUE, 'ivanrl'),
(31, 'Return only new task as HTML', TRUE, 'ivanrl'),
(34, 'Consider password security', TRUE, 'ivanrl'),
(40, 'test', TRUE, 'ivanrl'),
(41, 'test', TRUE, 'ivanrl'),
(42, 'My own task', TRUE, 'ivanrl'),
(43, 'test task', FALSE, 'test'),
(44, 'test task 2', TRUE, 'test'),
(45, 'Add custom user entity', FALSE, 'ivanrl'),
(46, 'Add sign in form', TRUE, 'ivanrl'),
(47, 'test', TRUE, 'ivanrl'),
(48, 'test', TRUE, 'ivanrl'),
(49, 'test', TRUE, 'ivanrl'),
(50, 'testtest', TRUE, 'ivanrl'),
(51, 'qer', TRUE, 'ivanrl'),
(52, 'qweqwe', TRUE, 'ivanrl'),
(53, 'qwe', TRUE, 'ivanrl'),
(54, 'qqqqqweqwe', TRUE, 'ivanrl'),
(55, 'test', TRUE, 'ivanrl'),
(56, 'fsgfsdgf', TRUE, 'ivanrl'),
(57, 'qweqwe', TRUE, 'ivanrl'),
(58, 'qweqwe', TRUE, 'ivanrl'),
(59, 'qweqweqweqwe', TRUE, 'ivanrl'),
(60, 'qweqweq', TRUE, 'ivanrl'),
(61, 'qweqweqwe', TRUE, 'ivanrl'),
(62, 'qweqweqwe', TRUE, 'ivanrl'),
(63, 'test', TRUE, 'ivanrl'),
(64, 'tr', TRUE, 'ivanrl'),
(65, 'dfsdfs', TRUE, 'ivanrl'),
(66, 'test', TRUE, 'ivanrl'),
(67, 'qweqwe', TRUE, 'ivanrl'),
(68, 't', TRUE, 'ivanrl'),
(69, 'qweqwe', TRUE, 'ivanrl'),
(70, 'Add delete option', TRUE, 'ivanrl'),
(71, 'test', TRUE, 'ivanrl'),
(72, 'qweqwe', TRUE, 'ivanrl'),
(73, 'qwerqwer', TRUE, 'ivanrl'),
(74, 'Adjust task input style', TRUE, 'ivanrl'),
(76, 'This is an attempt to write mo', TRUE, 'ivanrl'),
(77, '1', TRUE, 'ivanrl'),
(86, 'Add style for desktop', FALSE, 'ivanrl'),
(87, 'Learn how to deploy to cloud', FALSE, 'ivanrl'),
(90, 'Add custom userDetailsManager', TRUE, 'ivanrl'),
(91, 'Add custom userDetailsManager', TRUE, 'ivanrl'),
(93, 'Add custom userDetailsManager', FALSE, 'ivanrl'),
(94, 'Authenticate after signing in', TRUE, 'ivanrl'),
(99, 'Fix after-signIn navigation', FALSE, 'ivanrl'),
(101, 'Add custom login page', FALSE, 'ivanrl'),
(102, 'Allow editting tasks', FALSE, 'ivanrl'),
(103, 'Dockerize application?', FALSE, 'ivanrl');
