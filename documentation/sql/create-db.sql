use cs3230f24b;

-- Drop tables if they exist
SET FOREIGN_KEY_CHECKS=0;
drop table if exists `user`;
drop table if exists `address`;
drop table if exists `admin`;
drop table if exists `doctor`;
drop table if exists `nurse`;
drop table if exists `patient`;
drop table if exists `appointment`;
drop table if exists `visit`;
drop table if exists `test_result`;
drop table if exists `lab_test`;
drop table if exists `diagnosis`;

create table `user` (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role ENUM('admin', 'doctor', 'nurse') NOT NULL
);

create table `address` (
	address_id INT AUTO_INCREMENT PRIMARY KEY,
	street VARCHAR(100) NOT NULL,
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL,
	zip_code VARCHAR(10) NOT NULL
);

create table `admin` (
	id INT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	gender ENUM('male', 'female') NOT NULL,
	FOREIGN KEY (id) REFERENCES `user`(id) ON DELETE CASCADE
);

create table `doctor` (
	id INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	dob DATE NOT NULL,
	gender ENUM('male', 'female') NOT NULL,
	address_id INT,
	phone VARCHAR(15) NOT NULL,
	FOREIGN KEY (address_id) REFERENCES address(address_id),
	FOREIGN KEY (id) REFERENCES `user`(id) ON DELETE CASCADE
);

create table `nurse` (
	id INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	dob DATE NOT NULL,
	gender ENUM('male', 'female') NOT NULL,
	address_id INT,
	phone VARCHAR(15) NOT NULL,
	FOREIGN KEY (address_id) REFERENCES address(address_id),
	FOREIGN KEY (id) REFERENCES `user`(id) ON DELETE CASCADE
);

create table `patient` (
	id INT PRIMARY KEY,
	last_name VARCHAR(50) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	dob DATE NOT NULL,
	gender ENUM('male', 'female') NOT NULL,
	address_id INT,
	phone VARCHAR(15) NOT NULL,
	status ENUM('active', 'inactive', 'discharged') NOT NULL,
	FOREIGN KEY (address_id) REFERENCES address(address_id)
);

create table `appointment` (
	id INT AUTO_INCREMENT PRIMARY KEY,
	patient_id INT NOT NULL,
	doctor_id INT NOT NULL,
	date DATETIME NOT NULL,
	reason VARCHAR(255),
	status ENUM('scheduled', 'completed', 'cancelled') NOT NULL,
	FOREIGN KEY (patient_id) REFERENCES patient(id),
	FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

create table `diagnosis` (
	id INT AUTO_INCREMENT PRIMARY KEY,
	visit_id INT NOT NULL,
	initial_diagnosis VARCHAR(255) NOT NULL,
	final_diagnosis VARCHAR(255),
	FOREIGN KEY (visit_id) REFERENCES visit(id)
);

create table `visit` (
	id INT AUTO_INCREMENT PRIMARY KEY,
	appointment_id INT NOT NULL,
	nurse_id INT NOT NULL,
	doctor_id INT NOT NULL,
	patient_id INT NOT NULL,
	diagnosis_id INT,
	testresults_id INT,
	date DATETIME NOT NULL,
	systolic_bp INT,
	diastolic_bp INT,
	temp DECIMAL(5, 3),
	pulse INT,
	height DECIMAL(5, 2),
	weight DECIMAL(5, 3),
	symptoms TEXT,
	is_editable BOOLEAN NOT NULL DEFAULT 1,
	FOREIGN KEY (appointment_id) REFERENCES appointment(id),
	FOREIGN KEY (nurse_id) REFERENCES nurse(id),
	FOREIGN KEY (doctor_id) REFERENCES doctor(id),
	FOREIGN KEY (patient_id) REFERENCES patient(id),
	FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
	FOREIGN KEY (testresults_id) REFERENCES test_result(id)
);

create table `test_result` (
	id INT AUTO_INCREMENT PRIMARY KEY,
	visit_id INT NOT NULL,
	lab_code INT NOT NULL,
	date_performed DATE NOT NULL,
	result VARCHAR(255),
	is_abnormal BOOLEAN NOT NULL,
	FOREIGN KEY (visit_id) REFERENCES visit(id),
	FOREIGN KEY (lab_code) REFERENCES lab_test(labe_code)
);

create table `lab_test` (
	labe_code INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	unit_measurement VARCHAR(20) NOT NULL,
	low_value DECIMAL(10, 2) NOT NULL,
	high_value DECIMAL(10, 2) NOT NULL
);
