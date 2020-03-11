USE rosiessalon;
INSERT INTO Employee (UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (1, '20200102000001', '20200102000001', 'Rosemarie', 'A', 'Ruiz', 
	'Manager', 20.00, 0.00);
INSERT INTO Employee (UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (1, '20200102000001', '20200102000001', 'Mohamed', 'A', 'Ismail', 
	'Stylist', 10.00, 0.00);
INSERT INTO Employee (UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (1, '20200102000001', '20200102000001', 'Andrew', 'L', 'Roe', 
	'DataEngineer', 200.00, 1000000.00);
INSERT INTO Employee (UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (1, '20200102000001', '20200102000001', 'Kheit', 'A', 'Nguyan', 
	'Receptionist', 10.00, 1000.00);

INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (2, 1, '20200102000001', 0, 0, 4, 'rosie');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (2, 1, '20200102000001', 0, 1, 4, 'rosiepwd');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (3, 1, '20200102000001', 0, 0, 4, 'mohamed');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (3, 1, '20200102000001', 0, 1, 4, 'mohamedpwd');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (4, 1, '20200102000001', 0, 0, 4, 'andy');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (4, 1, '20200102000001', 0, 1, 4, 'andypwd');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (5, 1, '20200102000001', 0, 0, 4, 'kheit');
INSERT INTO EmpInfo (EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (5, 1, '20200102000001', 0, 1, 4, 'kheitpwd');

select * from Employee;
select * from EmpInfo;
