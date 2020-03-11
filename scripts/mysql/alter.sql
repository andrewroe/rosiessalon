USE rosiessalon;

ALTER TABLE Employee ADD CONSTRAINT Cemployee_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE Product ADD CONSTRAINT Cproduct_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE Service ADD CONSTRAINT Cservice_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE Supplier ADD CONSTRAINT Csupplier_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE Transaction ADD CONSTRAINT Ctrans_EmpID_FK FOREIGN KEY (CustID) REFERENCES Customer (CustID);
ALTER TABLE Transaction ADD CONSTRAINT Ctrans_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

ALTER TABLE EmpInfo ADD CONSTRAINT Cempinfo_EmpID_FK FOREIGN KEY (EmpID) REFERENCES Employee (EmpID);
ALTER TABLE EmpInfo ADD CONSTRAINT Cempinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE CustInfo ADD CONSTRAINT Ccustinfo_EmpID_FK FOREIGN KEY (CustID) REFERENCES Customer (CustID);
ALTER TABLE CustInfo ADD CONSTRAINT Ccustinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE SupplierInfo ADD CONSTRAINT Csuppinfo_EmpID_FK FOREIGN KEY (SuppID) REFERENCES Supplier (SuppID);
ALTER TABLE SupplierInfo ADD CONSTRAINT Csuppinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE ProductInfo ADD CONSTRAINT Cprodinfo_EmpID_FK FOREIGN KEY (ProdID) REFERENCES Product (ProdID);
ALTER TABLE ProductInfo ADD CONSTRAINT Cprodinfo_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);
ALTER TABLE TransactionDetails ADD CONSTRAINT Ctdet_TransID_FK FOREIGN KEY (TransID) REFERENCES Transaction (TransID);
ALTER TABLE TransactionDetails ADD CONSTRAINT Ctdet_User_FK FOREIGN KEY (UserID) REFERENCES Employee (EmpID);

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
