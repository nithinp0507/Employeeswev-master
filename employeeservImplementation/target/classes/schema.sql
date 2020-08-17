DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
firstName varchar2(255) NOT NULL,
lastName varchar2(255) NOT NULL,
dateofbirth varchar2(10),
line1 varchar2(300) NOT NULL,
line2 varchar2(300),
city varchar2(200) NOT NULL,
state varchar2(100) NOT NULL,
country varchar2(100) NOT NULL,
zipcode varchar2(6) NOT NULL,

PRIMARY KEY (id)
);