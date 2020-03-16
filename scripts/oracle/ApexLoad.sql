INSERT INTO Employee (EmpID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (EmpID_Seq.nextval, 1, '20200102000001', '20200102000001', 'Rosemarie', 'A', 'Ruiz', 
	'Manager', 20.00, 0.00);
INSERT INTO Employee (EmpID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (EmpID_Seq.nextval, 1, '20200102000001', '20200102000001', 'Mohamed', 'A', 'Ismail', 
	'Stylist', 10.00, 0.00);
INSERT INTO Employee (EmpID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (EmpID_Seq.nextval, 1, '20200102000001', '20200102000001', 'Andrew', 'L', 'Roe', 
	'DataEngineer', 200.00, 1000000.00);
INSERT INTO Employee (EmpID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, job, salary, commission)
	VALUES  (EmpID_Seq.nextval, 1, '20200102000001', '20200102000001', 'Kheit', 'A', 'Nguyan', 
	'Receptionist', 10.00, 1000.00);

INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Rosemarie' AND MINIT='A' AND LNAME='Ruiz'), 
             1, '20200102000001', 0, 0, 4, 'rosie');    
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Rosemarie' AND MINIT='A' AND LNAME='Ruiz'), 
             1, '20200102000001', 0, 1, 4, 'rosiepwd');  
 
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Mohamed' AND MINIT='A' AND LNAME='Ismail'), 
             1, '20200102000001', 0, 0, 4, 'mohamed');  
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Mohamed' AND MINIT='A' AND LNAME='Ismail'), 
             1, '20200102000001', 0, 1, 4, 'mohamedpwd');  

INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Andrew' AND MINIT='L' AND LNAME='Roe'), 
             1, '20200102000001', 0, 0, 4, 'andy');  
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Andrew' AND MINIT='L' AND LNAME='Roe'), 
             1, '20200102000001', 0, 1, 4, 'andypwd');  

INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Kheit' AND MINIT='A' AND LNAME='Nguyan'), 
             1, '20200102000001', 0, 0, 4, 'kheit');  
INSERT INTO EmpInfo (EinfoID, EmpID, UserID, UpdateTime, InfoType, InfoSubType, Validity, CharBig)
	VALUES  (EinfoID_Seq.nextval, 
             (SELECT EmpID FROM Employee WHERE FNAME='Kheit' AND MINIT='A' AND LNAME='Nguyan'), 
             1, '20200102000001', 0, 1, 4, 'kheitpwd');  

INSERT INTO Customer (CustID, UserID, CreateTime, UpdateTime, Fname, Minit, Lname, phone, email)
	VALUES  (CustID_Seq.nextval, 1, '20200102000001', '20200102000001', 'Jane', 'D', 'Doe', 
	'651222333', 'jane.doe@mymail.com');

INSERT INTO Product (ProdID, UserID, CreateTime, UpdateTime, Pname, Price)
	VALUES  (ProdID_Seq.nextval, (SELECT EmpID FROM Employee WHERE FNAME='Admin' AND MINIT='A' AND LNAME='Admin'), 
             '20200102000001', '20200102000001', 'Shampoo by Rosie', 25);
INSERT INTO Product (ProdID, UserID, CreateTime, UpdateTime, Pname, Price)
	VALUES  (ProdID_Seq.nextval, (SELECT EmpID FROM Employee WHERE FNAME='Admin' AND MINIT='A' AND LNAME='Admin'), 
             '20200102000001', '20200102000001', 'Conditioner by Rosie', 28.99);
INSERT INTO Product (ProdID, UserID, CreateTime, UpdateTime, Pname, Price)
	VALUES  (ProdID_Seq.nextval, (SELECT EmpID FROM Employee WHERE FNAME='Admin' AND MINIT='A' AND LNAME='Admin'), 
             '20200102000001', '20200102000001', 'Softskin lotion by Rosie', 14.99);
                 
INSERT INTO Supplier (SuppID, UserID, CreateTime, UpdateTime, Bname, phone, email)
        VALUES  (SuppID_Seq.nextval, (SELECT EmpID FROM Employee WHERE FNAME='Admin' AND MINIT='A' AND LNAME='Admin'), 
                 '20200102000001', '20200102000001', 'Rosie Inc.', '6517778888', 'rosie.ruiz@ruiz.com' );

