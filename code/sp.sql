CREATE DEFINER=`cs3230f24b`@`%` PROCEDURE `GetVisitDetailsByAppointmentId`(IN appointmentId INT)
BEGIN
    SELECT 
        v.id AS visit_id,
        v.appointment_id,
        v.nurse_id,
        v.doctor_id,
        v.patient_id,
        v.diagnosis_id,
        v.testresults_id,
        v.date,
        v.systolic_bp,
        v.diastolic_bp,
        v.temp,
        v.pulse,
        v.height,
        v.weight,
        v.symptoms,
        v.is_editable,
        CONCAT(p.first_name, " ", p.last_name) AS patient_full_name,
        p.dob AS patient_dob,
        CONCAT(d.first_name, " ", d.last_name) AS doctor_full_name,
        d.dob AS doctor_dob,
        lt.name,
        tr.result,
        dia.initial_diagnosis,
        dia.final_diagnosis
    FROM 
        visit v
    JOIN 
        patient p ON v.patient_id = p.id
    JOIN 
        doctor d ON v.doctor_id = d.id
	JOIN
		test_result tr on v.id = tr.visit_id
	JOIN
		lab_test lt on tr.lab_code = lt.labe_code
	JOIN
		diagnosis dia on v.id = dia.visit_id
    WHERE 
        v.appointment_id = appointmentId;
END

CREATE DEFINER=jg00242@% PROCEDURE GetVisitByAppointmentId(IN appointmentId INT) BEGIN SELECT * FROM cs3230f24b.visit WHERE appointment_id = appointmentId; END