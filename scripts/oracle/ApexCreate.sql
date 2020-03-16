CREATE TABLE Employee (
  EmpID 	NUMBER NOT NULL,
  UserID        NUMBER,
  CreateTime    VARCHAR2(32),
  UpdateTime    VARCHAR2(32),
  Fname         VARCHAR2(64),
  Minit         VARCHAR2(64),
  Lname         VARCHAR2(64),
  job           VARCHAR2(16),
  salary        NUMBER(15,2),
  commission    NUMBER(15,2)
);
CREATE TABLE Customer (
  CustID        NUMBER NOT NULL,
  UserID        NUMBER,
  CreateTime    VARCHAR2(32),
  UpdateTime    VARCHAR2(32),
  Fname         VARCHAR2(64),
  Minit         VARCHAR2(64),
  Lname         VARCHAR2(64),
  phone         VARCHAR2(16),
  email         VARCHAR2(64)
);
CREATE TABLE Supplier (
  SuppID 	    NUMBER NOT NULL,
  UserID        NUMBER,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Bname         VARCHAR(64),
  phone         VARCHAR(16),
  email         VARCHAR(64)
);
CREATE TABLE Product (
  ProdID        NUMBER NOT NULL,
  UserID        NUMBER,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Pname         VARCHAR(64),
  Price         NUMBER(15,2)
);
CREATE TABLE Service (
  ServID        NUMBER NOT NULL,
  UserID        NUMBER,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Sname         VARCHAR(64),
  Price         NUMBER(15,2)
);
CREATE TABLE Transaction (
  TransID       NUMBER NOT NULL,
  CustID        NUMBER,
  UserID        NUMBER,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Method        NUMBER,
  CreditInfo    VARCHAR(32),
  amount        NUMBER(15,2)
);
CREATE TABLE EmpInfo (
  EinfoID       NUMBER NOT NULL,
  EmpID         NUMBER,
  UserID        NUMBER,
  UpdateTime    VARCHAR(32),
  InfoType      NUMBER,
  InfoSubType   NUMBER,
  Validity      NUMBER,
  Nbr1Parm      NUMBER,
  Nbr2Parm      NUMBER(15,2),
  CharBig       VARCHAR(64)
);
CREATE TABLE CustInfo (
  CinfoID       NUMBER NOT NULL,
  CustID        NUMBER,
  UserID        NUMBER,
  UpdateTime    VARCHAR(32),
  InfoType      NUMBER,
  InfoSubType   NUMBER,
  Validity      NUMBER,
  Nbr1Parm      NUMBER,
  Nbr2Parm      NUMBER(15,2),
  CharBig       VARCHAR(64)
);
CREATE TABLE SupplierInfo (
  SinfoID       NUMBER NOT NULL,
  SuppID        NUMBER,
  UserID        NUMBER,
  UpdateTime    VARCHAR(32),
  InfoType      NUMBER,
  InfoSubType   NUMBER,
  Validity      NUMBER,
  Nbr1Parm      NUMBER,
  Nbr2Parm      NUMBER(15,2),
  CharBig       VARCHAR(64)
);
CREATE TABLE ProductInfo (
  PinfoID       NUMBER NOT NULL,
  ProdID        NUMBER,
  UserID        NUMBER,
  UpdateTime    VARCHAR(32),
  InfoType      NUMBER,
  InfoSubType   NUMBER,
  Validity      NUMBER,
  Nbr1Parm      NUMBER,
  Nbr2Parm      NUMBER(15,2),
  CharBig       VARCHAR(64)
);
CREATE TABLE TransactionDetails (
  TinfoID       NUMBER NOT NULL,
  TransID       NUMBER,
  UserID        NUMBER,
  UpdateTime    VARCHAR(32),
  InfoType      NUMBER,
  InfoSubType   NUMBER,
  Validity      NUMBER,
  Nbr1Parm      NUMBER,
  Nbr2Parm      NUMBER(15,2),
  CharBig       VARCHAR(64)
);

