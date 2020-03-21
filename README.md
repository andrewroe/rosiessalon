# rosiessalon
This repo is for the SQL project for TeamMAR

Overview:
This readme includes 4 or 5 sections -
  repository layout
  installation
    java
    mysql
  DB setup
    general stuff
    rosiessalon database specific
  testing
    build or compilation 
    testing
  to do or current problems (may or may not do)

repository layout:
at https://github.com/andrewroe/rosiessalon
there are 4 catagories of *.java (class) files -
  1) DB interface classes, which meant to have the "how" to get and put data from/to the rosiessalon DB
  2) User interface classes, which meant to interact with a user, CLI or GUI or just for testing
  3) An abstract class meant to keep both the DB side and User I/F in sync. 
  4) testing or some style sheet files; pretty much ignore for now

The purpose of the code layout was to keep the "how" (DB I/F) away from the "what" (User I/F).
This is partly because the underlying DB tool, Oradle, MySQL, etc. could change and also for structure of code
organization. 

  <repo>/<documents> a number of files, the RosieSalonDataModel.pptx is a DB model of rosiessalon database
  <repo>/java/
    1)       /CustDBaccess.java - DB I/F helper, for accessing Customer table
             /CustInfoDB.java - DB I/F helper, for accessing CustInfo table
             /EmpDBaccess.java - DB I/F helper, for accessing Employee (and EmpInfo) table
             /CustData.java - both User and DB I/F helper, describes data class to use for info exchange with DB I/F classes
             /EmpData.java - both User and DB I/F helper, describes data class to use for info exchange with DB I/F classes
             /StartTransaction.java - DB I/F helper, needed to initialte TransactionApp.java (I have some mis-understanding...)

    2)       /AddCustomer.java - User I/F helper to add a customer to the Customer table in DB (part of TransactionApp javafx)
             /FindCustomer.java - User I/F helper to find a customer in the Customer table in DB (part of TransactionApp javafx
             /TransactionApp.java - User I/F javafx application for all that UserApp did not cover
             /UserApp.java - User I/F CLI was the inital user tool to show that DB model would work, covers the Employee stuff

    3)       /RosiesSalon.java - DB I/F abstract "mother" class

    4)       /stylesheet.css  (I don't remember why this is here and probably should be deleted)
             /Transaction.css  (tried to add usage of style sheets to TransactionApp.java, didn't seem to work well)

  <repo>/scripts/
                /
                /mysql/alter.sql
                /mysql/create.sql
                /mysql/drop.sql
                /mysql/initadmin.sql
                /mysql/load.sql
                /oracle/ApexAlter.sql
                /oracle/ApexCreate.sql
                /oracle/ApexDrop.sql
                /oracle/ApexInitAdmin.sql
                /oracle/ApexLoad.sql

installation:

DB setup:

testing:

