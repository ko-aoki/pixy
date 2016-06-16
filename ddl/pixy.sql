SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS m_customer;
DROP TABLE IF EXISTS m_business_connection;
DROP TABLE IF EXISTS m_employee;
DROP TABLE IF EXISTS m_department;
DROP TABLE IF EXISTS t_attendees;
DROP TABLE IF EXISTS t_resource;
DROP TABLE IF EXISTS t_event_tmp;




/* Create Tables */

CREATE TABLE m_business_connection
(
	business_connection_id varchar(16) NOT NULL,
	business_connection_name varchar(128),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (business_connection_id)
);


CREATE TABLE m_customer
(
	customer_id varchar(16) NOT NULL,
	business_connection_id varchar(16),
	family_name varchar(128),
	first_name varchar(128),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (customer_id)
);


CREATE TABLE m_department
(
	department_id varchar(128) NOT NULL,
	department_name1 varchar(128),
	department_name2 varchar(128),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (department_id)
);


CREATE TABLE m_employee
(
	employee_id varchar(10) NOT NULL,
	calendar_id varchar(128),
	department_id varchar(128),
	family_name varchar(128),
	first_name varchar(128),
	cellphone_number varchar(11),
	extension_number varchar(5),
	image_file_path varchar(256),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (employee_id)
);


CREATE TABLE t_attendees
(
	id int NOT NULL AUTO_INCREMENT,
	t_event_tmp_id int NOT NULL,
	event_id varchar(128),
	email varchar(128),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (id)
);


CREATE TABLE t_event_tmp
(
	t_event_tmp_id int NOT NULL AUTO_INCREMENT,
	event_id varchar(128),
	calendar_id varchar(128),
	summary varchar(128),
	start_datetime datetime,
	end_datetime datetime,
	description varchar(128),
	admission_datetime datetime,
	leaving_datetime datetime,
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (t_event_tmp_id)
);


CREATE TABLE t_resource
(
	id int NOT NULL AUTO_INCREMENT,
	t_event_tmp_id int NOT NULL,
	event_id varchar(128),
	email varchar(128),
	update_datetime datetime,
	update_id varchar(128),
	version int,
	PRIMARY KEY (id)
);



/* Create Foreign Keys */

ALTER TABLE m_customer
	ADD FOREIGN KEY (business_connection_id)
	REFERENCES m_business_connection (business_connection_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE m_employee
	ADD FOREIGN KEY (department_id)
	REFERENCES m_department (department_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_attendees
	ADD FOREIGN KEY (t_event_tmp_id)
	REFERENCES t_event_tmp (t_event_tmp_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE t_resource
	ADD FOREIGN KEY (t_event_tmp_id)
	REFERENCES t_event_tmp (t_event_tmp_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



