INSERT INTO Employee (EmpID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (EmpID_Seq.nextval, 1, '20200101000001', '20200101000001', 'Admin', 'A', 'Admin', 
	'Administrator', 200.00, 1000000.00);

INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 1, 1, '20200101000000', 0, 0, 4, 'admin');
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 1, 1, '20200101000000', 0, 1, 4, 'password');

SELECT * FROM EMPLOYEE;
select * from EMPINFO;
