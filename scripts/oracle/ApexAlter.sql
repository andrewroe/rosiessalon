ALTER TABLE Employee ADD CONSTRAINT Cemployee_PK PRIMARY KEY ( EmpID );
ALTER TABLE Employee ADD CONSTRAINT Cemployee_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE Customer ADD CONSTRAINT Ccustomer_PK PRIMARY KEY ( CustID );
ALTER TABLE Customer ADD CONSTRAINT Ccustomer_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE Product ADD CONSTRAINT Cproduct_PK PRIMARY KEY ( ProdID );
ALTER TABLE Product ADD CONSTRAINT Cproduct_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE Service ADD CONSTRAINT Cservice_PK PRIMARY KEY ( ServID );
ALTER TABLE Service ADD CONSTRAINT Cservice_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE Supplier ADD CONSTRAINT Csupplier_PK PRIMARY KEY ( SuppID );
ALTER TABLE Supplier ADD CONSTRAINT Csupplier_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE Transaction ADD CONSTRAINT Ctrans_PK PRIMARY KEY ( TransID );
ALTER TABLE Transaction ADD CONSTRAINT Ctrans_CustID_FK FOREIGN KEY (CustID) REFERENCES Customer (CustID);
ALTER TABLE Transaction ADD CONSTRAINT Ctrans_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE EmpInfo ADD CONSTRAINT Cempinfo_PK PRIMARY KEY ( EinfoID );
ALTER TABLE EmpInfo ADD CONSTRAINT Cempinfo_EmpID_FK FOREIGN KEY (EmpID) REFERENCES Employee (EmpID);
ALTER TABLE EmpInfo ADD CONSTRAINT Cempinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE CustInfo ADD CONSTRAINT Ccustinfo_PK PRIMARY KEY ( CinfoID );
ALTER TABLE CustInfo ADD CONSTRAINT Ccustinfo_EmpID_FK FOREIGN KEY (CustID) REFERENCES Customer (CustID);
ALTER TABLE CustInfo ADD CONSTRAINT Ccustinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE SupplierInfo ADD CONSTRAINT Csuppinfo_PK PRIMARY KEY ( SinfoID );
ALTER TABLE SupplierInfo ADD CONSTRAINT Csuppinfo_SuppID_FK FOREIGN KEY (SuppID) REFERENCES Supplier (SuppID);
ALTER TABLE SupplierInfo ADD CONSTRAINT Csuppinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE ProductInfo ADD CONSTRAINT Cprodinfo_PK PRIMARY KEY ( PinfoID );
ALTER TABLE ProductInfo ADD CONSTRAINT Cprodinfo_ProdID_FK FOREIGN KEY (ProdID) REFERENCES Product (ProdID);
ALTER TABLE ProductInfo ADD CONSTRAINT Cprodinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE TransactionDetails ADD CONSTRAINT Ctdet_PK PRIMARY KEY ( TinfoID );
ALTER TABLE TransactionDetails ADD CONSTRAINT Ctdet_TransID_FK FOREIGN KEY (TransID) REFERENCES Transaction (TransID);
ALTER TABLE TransactionDetails ADD CONSTRAINT Ctdet_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
  
CREATE SEQUENCE EmpID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE CustID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE ProdID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE ServID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE SuppID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE TransID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE EinfoID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE CinfoID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE SinfoID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE PinfoID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;
CREATE SEQUENCE TinfoID_Seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 5;

