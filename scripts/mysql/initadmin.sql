USE rosiessalon;
INSERT INTO Employee (UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (1, '20200101000001', '20200101000001', 'Admin', 'A', 'Admin', 
	'Administrator', 200.00, 1000000.00);
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (1, 1, '20200101000000', 0, 0, 4, 'admin');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (1, 1, '20200101000000', 0, 1, 4, 'password');
select * from Employee;
select * from EmpInfo;
