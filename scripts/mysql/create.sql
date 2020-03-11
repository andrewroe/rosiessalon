USE rosiessalon;
CREATE TABLE Employee (
  EmpID 	INT NOT NULL AUTO_INCREMENT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Fname         VARCHAR(64),
  Minit         VARCHAR(64),
  Lname         VARCHAR(64),
  job           VARCHAR(16),
  salary        DECIMAL(15,2),
  commission    DECIMAL(15,2),
  PRIMARY KEY (EmpID)
);
CREATE TABLE Customer (
  CustID        INT NOT NULL AUTO_INCREMENT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Fname         VARCHAR(64),
  Minit         VARCHAR(64),
  Lname         VARCHAR(64),
  phone         VARCHAR(16),
  email         VARCHAR(64),
  PRIMARY KEY (CustID)
);
CREATE TABLE Supplier (
  SuppID 	INT NOT NULL AUTO_INCREMENT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Bname         VARCHAR(64),
  phone         VARCHAR(16),
  email         VARCHAR(64),
  PRIMARY KEY (SuppID)
);
CREATE TABLE Product (
  ProdID        INT NOT NULL AUTO_INCREMENT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Pname         VARCHAR(64),
  Price         DECIMAL(15,2),
  PRIMARY KEY (ProdID)
);
CREATE TABLE Service (
  ServID        INT NOT NULL AUTO_INCREMENT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Sname         VARCHAR(64),
  Price         DECIMAL(15,2),
  PRIMARY KEY (ServID)
);
CREATE TABLE Transaction (
  TransID       INT NOT NULL AUTO_INCREMENT,
  CustID        INT,
  UserID        INT,
  CreateTime    VARCHAR(32),
  UpdateTime    VARCHAR(32),
  Method        INT,
  CreditInfo    VARCHAR(32),
  amount        DECIMAL(15,2),
  PRIMARY KEY (TransID)
);
CREATE TABLE EmpInfo (
  EinfoID       INT NOT NULL AUTO_INCREMENT,
  EmpID         INT,
  UserID        INT,
  UpdateTime    VARCHAR(32),
  InfoType      INT,
  InfoSubType   INT,
  Validity      INT,
  Nbr1Parm      INT,
  Nbr2Parm      DECIMAL(15,2),
  CharBig       VARCHAR(64),
  PRIMARY KEY (EinfoID)
);
CREATE TABLE CustInfo (
  CinfoID       INT NOT NULL AUTO_INCREMENT,
  CustID        INT,
  UserID        INT,
  UpdateTime    VARCHAR(32),
  InfoType      INT,
  InfoSubType   INT,
  Validity      INT,
  Nbr1Parm      INT,
  Nbr2Parm      DECIMAL(15,2),
  CharBig       VARCHAR(64),
  PRIMARY KEY (CinfoID)
);
CREATE TABLE SupplierInfo (
  SinfoID       INT NOT NULL AUTO_INCREMENT,
  SuppID        INT,
  UserID        INT,
  UpdateTime    VARCHAR(32),
  InfoType      INT,
  InfoSubType   INT,
  Validity      INT,
  Nbr1Parm      INT,
  Nbr2Parm      DECIMAL(15,2),
  CharBig       VARCHAR(64),
  PRIMARY KEY (SinfoID)
);
CREATE TABLE ProductInfo (
  PinfoID       INT NOT NULL AUTO_INCREMENT,
  ProdID        INT,
  UserID        INT,
  UpdateTime    VARCHAR(32),
  InfoType      INT,
  InfoSubType   INT,
  Validity      INT,
  Nbr1Parm      INT,
  Nbr2Parm      DECIMAL(15,2),
  CharBig       VARCHAR(64),
  PRIMARY KEY (PinfoID)
);
CREATE TABLE TransactionDetails (
  TinfoID       INT NOT NULL AUTO_INCREMENT,
  TransID       INT,
  UserID        INT,
  UpdateTime    VARCHAR(32),
  InfoType      INT,
  InfoSubType   INT,
  Validity      INT,
  Nbr1Parm      INT,
  Nbr2Parm      DECIMAL(15,2),
  CharBig       VARCHAR(64),
  PRIMARY KEY (TinfoID)
);
show tables;
describe Employee;
describe Customer;
describe Supplier;
describe Product;
describe Service;
describe Transaction;
describe EmpInfo;
describe CustInfo;
describe SupplierInfo;
describe ProductInfo;
describe TransactionDetails;
