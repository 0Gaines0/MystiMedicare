USE cs3230f24b;

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE visit;
TRUNCATE TABLE test_result;
TRUNCATE TABLE diagnosis;
TRUNCATE TABLE appointment;
TRUNCATE TABLE patient;
TRUNCATE TABLE doctor;
TRUNCATE TABLE nurse;
TRUNCATE TABLE admin;
TRUNCATE TABLE address;
TRUNCATE TABLE user;
TRUNCATE TABLE lab_test;

INSERT INTO `user` (username, password, role)
VALUES 
('admin1', 'pass123', 'admin'),
('docsmith', 'docpass', 'doctor'),
('nurseray', 'nursepass', 'nurse'),
('tempadmin', 'tempadmin', 'admin'),
('tempdoc', 'tempdoc', 'doctor'),
('tempnur', 'tempnur', 'nurse'),
('michaelbrooks', 'mbrookspass', 'nurse'),
('sarahgreen', 'sgreenpass', 'nurse'),
('laurawhite', 'lwhitepass', 'nurse'),
('danielbrown', 'dbrownpass', 'nurse');

-- Insert rows into `address` table
INSERT INTO `address` (street, city, state, zip_code)
VALUES 
('123 Elm St', 'Springfield', 'IL', '62704'),
('456 Oak St', 'Austin', 'TX', '73301'),
('789 Pine St', 'Madison', 'WI', '53703'),
('101 Maple St', 'Tampa', 'FL', '33602'),
('202 Cedar St', 'Salem', 'OR', '97301');

-- Insert rows into `admin` table
INSERT INTO `admin` (id, username, password, last_name, first_name, gender)
VALUES 
(1, 'admin1', 'pass123', 'Admin', 'One', 'male');

-- Insert rows into `doctor` table
INSERT INTO `doctor` (id, last_name, first_name, dob, gender, address_id, phone)
VALUES 
(2, 'Smith', 'John', '1980-01-01', 'male', 1, '555-1234'),
(3, 'Doe', 'Jane', '1982-03-14', 'female', 2, '555-5678'),
(4, 'Kim', 'James', '1975-07-22', 'male', 3, '555-9012'),
(5, 'Lee', 'Anna', '1986-11-30', 'female', 4, '555-3456'),
(6, 'Carter', 'David', '1990-05-10', 'male', 5, '555-7890');

-- Insert rows into `nurse` table
INSERT INTO `nurse` (id, last_name, first_name, dob, gender, address_id, phone)
VALUES 
(7, 'Ray', 'Emily', '1991-04-21', 'female', 1, '555-8765'),
(8, 'Brooks', 'Michael', '1988-12-12', 'male', 2, '555-4321'),
(9, 'Green', 'Sarah', '1993-06-09', 'female', 3, '555-6543'),
(10, 'White', 'Laura', '1987-08-18', 'female', 4, '555-3210'),
(11, 'Brown', 'Daniel', '1992-02-25', 'male', 5, '555-9876');

-- Insert rows into `patient` table
INSERT INTO `patient` (id, last_name, first_name, dob, gender, address_id, phone, status)
VALUES 
(12, 'Johnson', 'John', '1990-05-10', 'male', 1, '555-0001', 'active'),
(13, 'Doe', 'Jane', '1992-07-19', 'female', 2, '555-0002', 'inactive'),
(14, 'Williams', 'Sam', '1985-03-25', 'male', 3, '555-0003', 'discharged'),
(15, 'Taylor', 'Alex', '1987-12-15', 'female', 4, '555-0004', 'active'),
(16, 'Brown', 'Chris', '1995-01-30', 'male', 5, '555-0005', 'active');

-- Insert rows into `appointment` table
INSERT INTO `appointment` (patient_id, doctor_id, date, reason, status)
VALUES 
(12, 2, '2024-10-16 09:00:00', 'Routine checkup', 'scheduled'),
(13, 3, '2024-10-17 11:00:00', 'Flu symptoms', 'completed'),
(14, 4, '2024-10-18 14:00:00', 'Back pain', 'scheduled'),
(15, 5, '2024-10-19 10:00:00', 'Diabetes follow-up', 'cancelled'),
(16, 6, '2024-10-20 16:00:00', 'Blood pressure check', 'scheduled');

-- Insert rows into `diagnosis` table
INSERT INTO `diagnosis` (visit_id, initial_diagnosis, final_diagnosis)
VALUES 
(1, 'Flu-like symptoms', 'Influenza'),
(2, 'Hypertension', 'Hypertension'),
(3, 'Muscle strain', 'Muscle strain'),
(4, 'Type 2 Diabetes', 'Type 2 Diabetes'),
(5, 'Hypotension', 'Hypotension');

-- Insert rows into `visit` table
INSERT INTO `visit` (appointment_id, nurse_id, doctor_id, patient_id, diagnosis_id, testresults_id, date, systolic_bp, diastolic_bp, temp, pulse, height, weight, symptoms)
VALUES 
(1, 7, 2, 12, 1, 1, '2024-10-16 09:30:00', 120, 80, 98.6, 72, 5.8, 160, 'Cough, fever'),
(2, 8, 3, 13, 2, 2, '2024-10-17 11:30:00', 140, 90, 99.1, 75, 5.9, 170, 'Headache, dizziness'),
(3, 9, 4, 14, 3, 3, '2024-10-18 14:30:00', 130, 85, 98.2, 80, 6.0, 180, 'Back pain'),
(4, 10, 5, 15, 4, 4, '2024-10-19 10:30:00', 110, 70, 97.8, 65, 5.7, 150, 'Fatigue'),
(5, 11, 6, 16, 5, 5, '2024-10-20 16:30:00', 115, 75, 98.5, 68, 5.6, 155, 'Nausea, dizziness');

-- Insert rows into `test_result` table
INSERT INTO `test_result` (visit_id, lab_code, date_performed, result, is_abnormal)
VALUES 
(1, 1, '2024-10-16', 'Positive for flu', 1),
(2, 2, '2024-10-17', 'High blood pressure', 1),
(3, 3, '2024-10-18', 'Normal', 0),
(4, 4, '2024-10-19', 'Elevated blood sugar', 1),
(5, 5, '2024-10-20', 'Normal', 0);

-- Insert rows into `lab_test` table
INSERT INTO `lab_test` (name, unit_measurement, low_value, high_value)
VALUES 
('Flu test', 'Result', 0, 1),
('Blood pressure', 'mmHg', 90, 120),
('X-ray', 'N/A', 0, 0),
('Blood sugar', 'mg/dL', 70, 100),
('Cholesterol', 'mg/dL', 100, 200);
