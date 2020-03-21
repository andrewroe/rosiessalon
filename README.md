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
Java

I don't recall installing java, but it wasn't much I don't think.

MySQL

I originally MySQL some time ago and then upgraded later.
Originally around 12/12/2017 I used brew to install on my MacBook, e.g.
brew install mysql

use
brew info mysql

to obtain good info about configuration and running.

after install, according to my notes the output
2017-12-07T21:27:51.686974Z 1 [Note] A temporary password is generated for root@localhost: <some characters>

then (from notes)
$ ./mysql -u root      // BEFORE you set a password for root
// OR
$ ./mysql -u root -p   // AFTER you set a password for root

You can use "Activity Monitor" (under Applications/Utilities) to check if the MySQL Server is running. 
Look for process starting with mysqld.
The log messages are written to /usr/local/mysql/data/xxx.err, where xxx denotes your machine name. 
Issue "sudo cat /usr/local/mysql/data/xxx.err" to view the messages.
If you get the following error message when starting a client: 
"Can't connect to local MySQL server through socket '....', 
check your "Activity Monitor" to see if the MySQL server has been started.
Step 5: (For Java Programmers) Install MySQL JDBC Driver
Download the latest JDBC driver from http://www.mysql.com/downloads 
⇒ MySQL Connectors ⇒ Connector/J ⇒ Compressed TAR archive 
(e.g., mysql-connector-java-{5.x.xx}.tar.gz, where {5.x.xx} is the latest release number).
Double-click on the downloaded TAR file to expand into folder "mysql-connector-java-{5.x.xx}".
Open the expanded folder. Copy the JAR file "mysql-connector-java-{5.x.xx}-bin.jar" to JDK's extension directory 
at "/Library/Java/Extension".

6.2  How to Uninstall and Remove MySQL 5
Open a Terminal ⇒ Run the "nano" editor to edit /etc/hostconfig, as follows:
sudo nano /etc/hostconfig
Enter your password: ....
Delete this line if present: "MYSQLCOM=-YES-". Press cntl-x to exit "nano" and enter "Y" to save the file. 
The line "MYSQLCOM=-YES-" starts MySQL automatically during startup.
Make sure that MySQL is not running (Open the "Activity Monitor" under the "Applications/Utilities", 
and check for the process "mysqld"). 
Open a Terminal and issue "rm -r" to remove these directories and their sub-directories 
(with "f" indicating no confirmation prompt).
sudo rm /usr/local/mysql
sudo rm -rf /usr/local/mysql*
sudo rm -rf /Library/StartupItems/MySQLCOM
sudo rm -rf /Library/PreferencePanes/My*
sudo rm -rf /Library/Receipts/mysql*
sudo rm -rf /Library/Receipts/MySQL*
sudo rm /etc/my.cnf
That's all!

Then I started mysql
cd /usr/local/mysql/bin
sudo ./mysql -u root 

needed to enter my password for the sudo of course and then got the mysql prompt

Password:   (this needed to be the password initially set on install, see above)
Logging to '/usr/local/mysql-5.7.20-macos10.12-x86_64/data/Andrews-iMac.local.err'.
2017-12-07T21:59:40.6NZ mysqld_safe Starting mysqld daemon with databases from /usr/local/mysql-5.7.20-macos10.12-x86_64/data

to set up some mysql passwords . . .

mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'pwsql';
Query OK, 0 rows affected (0.27 sec)

$ ./mysql -u root -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 5
Server version: 5.7.20

mysql> connect
Connection id:    6
Current database: *** NONE ***

mysql> show databases;
ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this statement.

1/17/2018 -
sudo -u root ls -alt /usr/local/mysql-5.7.20-macos10.12-x86_64/data
Password:  (used <some value>)

Then could;
mysql -u root -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 22
Server version: 5.7.20

mysql> use mysql;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql>

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
4 rows in set (0.00 sec)

Then I set myself up as a mysql user -

mysql> ALTER USER 'andrewroe'@'localhost' IDENTIFIED BY '';
Query OK, 0 rows affected (0.00 sec)

mysql> select Host,User,authentication_string from user;
+-----------+---------------+-------------------------------------------+
| Host      | User          | authentication_string                     |
+-----------+---------------+-------------------------------------------+
| localhost | root          | *ED7B56F77D9F5F70875A61EBEFA8E79D3159C088 |
| localhost | mysql.session | *THISISNOTAVALIDPASSWORDTHATCANBEUSEDHERE |
| localhost | mysql.sys     | *THISISNOTAVALIDPASSWORDTHATCANBEUSEDHERE |
| localhost | andrewroe     |                                           |
+-----------+---------------+-------------------------------------------+
4 rows in set (0.00 sec)

then . . .
user 'andrewroe' could enter mysql without password prompt. :- )

in root mysql pane -:
mysql> create database aroetest;
Query OK, 1 row affected (0.05 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| aroetest           |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.00 sec)

mysql> GRANT ALL ON aroetest TO 'andrew'@'localhost';
ERROR 1133 (42000): Can't find any matching row in the user table
mysql> GRANT ALL ON aroetest TO 'andrewroe'@'localhost';
Query OK, 0 rows affected (0.04 sec)

somewhere along the line I had upgraded mysql 8.0.13
and then need to run

sudo mysql_upgrade --upgrade-system-tables

then could reset the root user password to "pwd"

sudo mysqladmin -u root -h localhost password "pwd"

brew services restart mysql

then in a different window did
mysql -u root -p
entered pwd

show databases;

Later, and after the upgrade, I updated mysql user password and access rights -

mysql -u root -p
<enter 'pwd'>
create user 'andrewroe' identified by 'ChangeMe' password expire;
create database rosiessalon;
create ROLE developer_users;
grant alter,create,delete,drop,index,insert,select,update,trigger,alter routine,create routine,create temporary tables on rosiessalon.* to 'developer_users';
grant 'developer_users' to 'andrewroe';

grant alter,create,delete,drop,index,insert,select,update,trigger,alter routine,create routine,create temporary tables on rosiessalon.* to 'andrewroe';

show grants;

on a different window -
mysql -u andrewroe -pChangeMe -h localhost
  or just
mysql -p
<enter ChangeMe>

select now();
  this forced me to update my password
set password='andysql';

show grants;

---- end of mysql setup and install --------

DB setup:

testing:

For running the SQL set up scripts against my 'rosiessalon' DB

mysql -p <scripts/get_dbs.sql >/tmp/output.txt
for batch worked

or just
mysql -p <scripts/create.sql
 etc.

Now with a database 'rosiessalon' one can run the setup scripts (see above repo notes)
to create the 'rosiessalon' DB.
Then to compile and test -
javac <whatever>.java
and run with
java <whatever>

Initially the only "App" to update the rosiessalon DB was
java UserApp
and respond to the prompts

This was used for all of the additional Employee users of "Rosie's Salon"

for the rest of the database the goal is to run a GUI App "TransactionApp".
Thus
several *.java files need to be compiled and then to run the TransactionApp
one needs to (at least for now) run
java StartTransaction


