import java.sql.*;
// import java.sql.Connection; 
// import java.sql.DriverManager; 
// import java.sql.SQLException; 
import java.util.Properties; 
import java.util.ArrayList;
import java.io.*;

/** 	
	Java Program to add employee to DB
	and see what can I do.
*/

public class EmpDBaccess extends RosiesSalon
{ 
	
/** 	
	searchEmployeeByFullName method
	Searches for any Employee infomation based on information available in 
	the EmpData object.
	If a match is found, EmpID is updated in the EmpData and 1 is returned.
	If multiple matches found, EmpID is updated with first match but -1 is returned.
	that is VERY unusual?!
	Else, when no match found, 0 is returned and EmpID is left untouched.
	
	@param EmpData object 
	@return Returns an integer 1, 0, or -1 and may update EmpID in EmpData object
*/
	public int searchEmployeeByFullName(EmpData data) throws SQLException 
	{
		String fname = data.getFname();
		String lname = data.getLname();
		String minit = data.getMinit();
		ResultSet result;
		int rvalue = 0; 

		if ((lname == null) || (fname == null) || (minit == null))
		{
			throw new SQLException("Attempting full name search of DB and " + 
				"not all of first, last and middle initial names supplied");
		}
		
		String sqlcmd = "SELECT EmpID, UserID, job, salary, commission " +
			"FROM Employee WHERE Lname = '" + lname + "' AND Fname = '" + 
			fname + "' AND Minit = '" + minit + "'";
			
		System.out.println("searchEmployee() - search DB using user's " + 
			"Fname, Lname, and Minit.");
						
		result = doCmd(sqlcmd);
			
		if (result == null) 
		{ 
			System.out.println("searchEmployee() - can not find employee in DB");
			return 0;
		} 	
					
		if (result.next())
		{
			System.out.println("searchEmployee() - it should get here");
			data.setEmpID(result.getInt("EmpID"));
			data.setJob(result.getString("job"));
			data.setSalary(result.getDouble("Salary"));
			data.setCommission(result.getDouble("Commission"));
						
			if (result.next())	// at least 2 matches! THis should be very rare.
				rvalue = -1;
			else					// just 1 match :-)
				return 1;
		}
				
		else
		{
			System.out.println("Failed to find any match when using " + 
				"user's Fname, Lname, and Minit.");
			return 0;
		} 
			 		
		return rvalue;
	}  // End of searchEmployeeByFullName
	

/** 	
	addEmployee method
	Expects a completed EmpData class as input
	
	@param EmpData object 
	@return Returns a boolean true for success, else false
*/
	public boolean addEmployee(EmpData data) throws SQLException 
	{
		int i;
		int rows;
		String lname = data.getLname();
		String fname = data.getFname();
		String minit = data.getMinit();
		// int empid = data.getEmpID();
		int userid = data.getUserID();
		String createtime = super.readfullDateTime();
		String updatetime = createtime;
		// updatetime = data.getUpdateTime();
		String job = data.getJob();
		double salary = data.getSalary();
		double commission = data.getCommission();
		ResultSet result;
		boolean returnValue = false;
		
		System.out.println("the UserID is = " + userid);
		System.out.println("the Fname is = " + fname);
		System.out.println("the Minit is = " + minit);
		System.out.println("the Lname is = " + lname);
		System.out.println("the Job is = " + job);
		System.out.println("the Salary is = " + salary);
		System.out.println("the Commission is = " + commission);
		
		if ((userid == 0) ||
			(lname == null) || (fname == null) || (minit == null) ||
			(job == null))
		{
			throw new SQLException("Attempting to add Employee without specifying " + 
				"user id, full name, job, salary, and commission"); 
		}

		String sqlcmd = "INSERT INTO Employee (UserID, CreateTime, UpdateTime," +
			"Fname, Minit, Lname, job, salary, commission)" +
			" VALUES " +
			"(" + userid + "," + "'" + createtime + "', " +
			"'" + updatetime +  "', " +
			"'" + fname + "', " + "'" + minit + "', " +
			"'" + lname + "', " + "'" + job + "', " + 
			salary + ", " + commission + ")";
				
		System.out.println("addEmployee() - doing insert"); 	
		rows = doRowsCmd(sqlcmd);		
		System.out.println("number of rows inserted = " + rows);
				
		if (rows == 1)
			return true;		
		else if (rows == 0)
			return false;	
		else
		{
			System.out.println("addEmployee() - returned neither 0 or 1 rows" +
				"added? But rather " + rows + " rows added??");
			return false;
		}
				
	} // End of addEmployee()
 
 
/** 	
	addEmpPhone method
	Expects a completed EmpData class as input
	
	@param EmpData object 
	@param index for which phone primary - fifth (1 - 5) to be added
	@return Returns a boolean true for success, else false
*/
	public boolean addEmpPhone(EmpData data, int index) throws SQLException 
	{
		int empid = data.getEmpID();
		int userid = data.getUserID();
		int rows;
		String phone = data.getPhone(index);	
		String updatetime = super.readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
			
		if (phone == null) 
		{
			throw new SQLException("addEmpPhone() - No phone # supplied!");  
		}

		sqlcmd = "INSERT INTO EmpInfo (EmpID, UserID";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", CharBig)";
		sqlcmd += " VALUES (";
		sqlcmd += empid;
		sqlcmd += ", " + userid;
		sqlcmd += ", '" + updatetime + "'";
		sqlcmd += ", " + DtypePhone;
		sqlcmd += ", " + (index + 1);
		sqlcmd += ", " + ValidInfo3;
		sqlcmd += ", '" + phone + "'";
		sqlcmd += ")";
					
		System.out.println("addEmpPhone() - doing insert"); 	
		rows = doRowsCmd(sqlcmd);		
		System.out.println("number of rows inserted = " + rows);
				
		if (rows == 1)
			return true;		
		else if (rows == 0)
			return false;	
		else
		{
			System.out.println("addEmpPhone() - returned neither 0 or 1 rows" +
				"added? But rather " + rows + " rows added??");
			return false;
		}
				
	} // End of addEmpPhone()

 
/** 	
	deactivateEmpPhone method
	Expects a completed EmpData class as input
	
	@param EmpData object 
	@param index for which phone primary - fifth (1 - 5) to be added
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateEmpPhone(EmpData data, int index) throws SQLException 
	{
		int empid = data.getEmpID();
		int userid = data.getUserID();
		int einfoid = 0;
		int rows = 0;
		String updatetime = super.readfullDateTime();
		boolean rvalue = false;
		ResultSet result;
		String sqlcmd;
			
		sqlcmd = "SELECT EinfoID, InfoType, InfoSubType, Validity, CharBig";
		sqlcmd += " FROM EmpInfo where EmpID = " + empid;
		sqlcmd += " AND InfoType = " + DtypePhone;
		sqlcmd += " AND InfoSubType = " + (index+1);
		sqlcmd += " And Validity = " + ValidInfo3;  
			
		System.out.println("deactivateEmpPhone() - will do EmpInfo query");
		result = doCmd(sqlcmd);
			
		if (result == null)
		{ 
			System.out.println("deactivateEmpPhone() - could not find phone match!");
			return false;
		}
		
		while (result.next())	
		{
			System.out.println("deactivateEmpPhone() - found records in EmpInfo query");
			// There is at least one prior active phonerecord,
			// we need to mark that as invalid.
			rvalue = true;
			einfoid = result.getInt("EinfoID");
			System.out.println("deactivateEmpPhone() - EinfoID = " + einfoid);
					
			sqlcmd = "UPDATE EmpInfo SET Validity = " + 0 +
				", UpdateTime = '" + updatetime + "'" +
				" WHERE EinfoID = " + einfoid; 
				
			//result = doCmd(sqlcmd);
			rows = doRowsCmd(sqlcmd) ;
		}	
			
		return rvalue;		
	} // End of deactivateEmpPhone()


/** 	
	fetchAllEmployeeInfo method
	Expects an EmpData class as input, which will have EmpID and then 
	other data within the EmmData class will be 
	overlayed into it upon return.
	
	@param EmpData object 
	@return Returns a boolean for success or not and updated the supplied EmpData,
	 
*/
	public boolean fetchAllEmployeeInfo(EmpData data) throws SQLException 
	{
		int empid = data.getEmpID();
		ResultSet result;
		int infotype;
		int infosubtype;
		int validity;
		int nbr1parm;
		double nbr2parm;
		String charparm;
		
		if (empid == 0) 
		{
			throw new SQLException("Attempting fetch Employee and EmpID equals 0!"); 
		}
		
		String sqlcmd = "SELECT EmpID, UserID, Fname, Lname, Minit, job, " +
			"salary, commission FROM Employee where EmpID = " + empid; 
				
	
		System.out.println("fetchAllEmployeeInfo() - search DB using user's " + 
			"EmpID as supplied.");
					
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllEmployeeInfo() - could not find employee match!");
			return false;
		}
		
		if (result.next())
		{
			// data.setEmpID(result.getInt("EmpID")); // could verify match
			data.setUserID(result.getInt("UserID"));
			data.setFname(result.getString("Fname"));
			data.setMinit(result.getString("Minit"));
			data.setLname(result.getString("Lname"));
			data.setJob(result.getString("job"));
			data.setSalary(result.getDouble("salary"));
			data.setCommission(result.getDouble("commission"));			
		}
		else
		{
			System.out.println("Failed to find any match when using user's EmpID.");
			return false;
		} 	
		 	
		// Add in any EmpInfo data
		
		sqlcmd = "SELECT EmpID, UserID, InfoType, InfoSubType, Validity, " +
			"Nbr1Parm, Nbr2Parm, CharBig FROM EmpInfo where EmpID = " + empid; 
					
		System.out.println("fetchAllEmployeeInfo() - search EmpInfo DB record " + 
				"using EmpID as supplied.");
				
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllEmployeeInfo() - can not find " +
				"additional Employee info");
		} 
			
		while (result.next())
		{
			infotype = result.getInt("InfoType");
			infosubtype = result.getInt("InfoSubType");
			validity = result.getInt("Validity");
			nbr1parm = result.getInt("Nbr1Parm");
			nbr2parm = result.getDouble("Nbr2Parm");
			charparm = result.getString("CharBig");
				
			// Dob
			if ((infotype == DtypePersonal) && (infosubtype == SubTypeDob) &&
				(validity == ValidInfo3))			
				data.setDob(charparm);
				
			// Phone		
			else if ((infotype == DtypePhone) && (infosubtype == SubTypePrimary) &&
				(validity == ValidInfo3))		
				data.setPhone(charparm,0);
					
			else if ((infotype == DtypePhone) && (infosubtype == SubTypeSecondary) &&
				(validity == ValidInfo3))
				data.setPhone(charparm,1);	
									
			else if ((infotype == DtypePhone) && (infosubtype == SubTypeTertiary) &&
				(validity == ValidInfo3))
				data.setPhone(charparm,2);	
	
			else if ((infotype == DtypePhone) && (infosubtype == SubTypeFourth) &&
				(validity == ValidInfo3))
				data.setPhone(charparm,3);
				
			else if ((infotype == DtypePhone) && (infosubtype == SubTypeFifth) &&
				(validity == ValidInfo3))
				data.setPhone(charparm,4);

			// Email		
			else if ((infotype == DtypeEmail) && (infosubtype == SubTypePrimary) &&
				(validity == ValidInfo3))		
				data.setEmail(charparm,0);
					
			else if ((infotype == DtypeEmail) && (infosubtype == SubTypeSecondary) &&
				(validity == ValidInfo3))
				data.setEmail(charparm,1);	
									
			else if ((infotype == DtypeEmail) && (infosubtype == SubTypeTertiary) &&
				(validity == ValidInfo3))
				data.setEmail(charparm,2);	
	
			else if ((infotype == DtypeEmail) && (infosubtype == SubTypeFourth) &&
				(validity == ValidInfo3))
				data.setEmail(charparm,3);
				
			else if ((infotype == DtypeEmail) && (infosubtype == SubTypeFifth) &&
				(validity == ValidInfo3))
				data.setEmail(charparm,4);

			// Address		
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypePrimary) &&
				(validity == ValidInfo3))		
				data.setAddress(charparm,0);
					
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeSecondary) &&
				(validity == ValidInfo3))
				data.setAddress(charparm,1);	
									
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeTertiary) &&
				(validity == ValidInfo3))
				data.setAddress(charparm,2);	
	
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFourth) &&
				(validity == ValidInfo3))
				data.setAddress(charparm,3);
				
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFifth) &&
				(validity == ValidInfo3))
				data.setAddress(charparm,4);
																
			else
				System.out.println("encountered unknown EmpInfo record??!");				
			
		} 	
		return true;	
		
	} // End of fetchAllEmployeeInfo()
 
 
 /** 	
	searchEmployeeByLastName method
	Searches for any Employee infomation based only on the last name information 
	available in the EmpData object.
	For each possible match the EmpID is addded into the ArrayListsupplied by caller.
	
	@param EmpData object
	@param ArrayList<Integer> 
	@return Returns boolean true if one or more Employees could match the criteria
	and also updates the supplied Arraylist of EmpID values of possible matches.
*/
	public boolean searchEmployeeByLastName(EmpData data, ArrayList<Integer> EmpIDlist) 
				throws SQLException 
	{
		int i;
		String lname = data.getLname();
		ResultSet result;
		boolean returnValue = false;

		if (lname == null)
		{
			throw new SQLException("Attempting last name search of DB and " + 
				"no last name supplied");
		}
			
		System.out.println("searchEmployee() - search DB using user's Lname.");
			
		String sqlcmd = "SELECT EmpID, UserID, Fname, Lname, Minit, job " +
			"FROM Employee where Lname = '" + lname + "'";
				
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("Can not find Employee using Lname");
			return(false);
		}
			 	
		returnValue = false;
		while (result.next())
		{
			EmpIDlist.add(result.getInt("EmpID"));
			returnValue = true;
		}
		
		return(returnValue);
		
		/* Note:
			Integer X = new Integer(EmpID);
			int EmpID = X.valueOf();
		*/
			
	}  // End of searchEmployeeByLastName
	 
  
/** 	
	updateEmployeeFullName method
	Expects an EmpData class as input, which should contain EmpID and
	Fname, Lname and Minit.
	
	@param EmpData object 
	@return Returns a boolean for success or not.
	 
*/
	public boolean updateEmployeeFullName(EmpData data) throws SQLException 
	{
		return true;  // for now
	}
 
  
/** 	
	updateEmployeeFname method
	Expects an EmpData class as input, which will have EmpID and other data
	overlayed into it upon return.
	
	@param EmpData object 
	@return Returns a boolean for success or not and updated supplied EmpData,
	 
*/
	public boolean updateEmployeeFname(EmpData data) throws SQLException 
	{
		return true;  // for now
	}
 
 
  
/** 	
	updateEmployeeLname method
	Expects an EmpData class as input, which will have EmpID and other data
	that is needed.
	
	@param EmpData object 
	@return Returns a boolean for success or not
	 
*/
	public boolean updateEmployeeLname(EmpData data) throws SQLException 
	{ 
		int empid = data.getEmpID();
		int einfoid;
		String lname = data.getLname();
		int user = data.getUserID();
		int olduser;
		String oldlname = null;
		int rows;
		ResultSet result;
		String updatetime = super.readfullDateTime();
		
		if (empid == 0)
		{
			throw new SQLException("Attempting update Employee Lname " +
					"when no EmpID supplied!");
		} 
		
		Statement statement = dbConnection.createStatement();
			
		// Ensure there is a DB record for Employee
		System.out.println("updateEmployeeLname() - confirming that DB " +
				"has that Employee using EmpID");
				
		String sqlcmd = "SELECT EmpID, UserID, Lname" +
				" FROM Employee where EmpID = " + empid; 
		
		result = doCmd(sqlcmd);
		 
		if (!result.next())
		{
			throw new SQLException("Attempting update Employee Lname " +
				"when no such Employee exits!");
		}	
					
		// we want to update Employee table with new Lname
		// But also, update history of change into EmpInfo table
		oldlname = result.getString("Lname");
		olduser = result.getInt("UserID");
		
		sqlcmd = "UPDATE Employee SET UserID = " + user +
			", Lname = '" + lname + "'" +
			", UpdateTime = '" + updatetime + "'" +
			" WHERE EmpID = " + empid;
				
		rows = doRowsCmd(sqlcmd);
		System.out.println("updateEmployeeLname() - just attempted Employee update");
				
		if (rows != 1)
		{
			throw new SQLException("Attempting update Employee Lname " +
				"but the update step failed!");
		}	
			
		
		// now update EmpInfo
		// 1st find any old Lname record and de-validate it
		sqlcmd = "SELECT EinfoID, InfoType, InfoSubType, Validity, CharBig" +
			" FROM EmpInfo where EmpID = " + empid +
			" AND InfoType = " + DtypePersonal + 
			" AND InfoSubType = " + SubTypeLname +
			" And Validity = '" + ValidInfo3 + "'";  
			
		
		System.out.println("updateEmployeeLname() - will do EmpInfo query");
		result = doCmd(sqlcmd);
			
		while (result.next())	
		{
			// There is at least one prior active last name record,
			// we need to mark that as invalid before adding the new EmpInfo record.
			einfoid = result.getInt("EinfoID");
			// einfolname = result.getString("CharBig"));
					
			sqlcmd = "UPDATE EmpInfo SET Validity = " + 0 +
				", UpdateTime = '" + updatetime + "'" +
				" WHERE EinfoID = " + einfoid;   
		}	
				
		// now we can insert EmpInfo record with old Lname and not valid	
			
		sqlcmd = "INSERT INTO EmpInfo (EmpID, UserID, " +
			"UpdateTime, " +
			"InfoType, InfoSubType, Validity, CharBig)" +
			" VALUES ( " + 
			empid + ", " + user + ", " +
			"'" + updatetime + "'," +
			DtypePersonal + ", " +
			SubTypeLname + ", " +
			0 + ", " +
			"'" + oldlname + "' )";
		
		System.out.println("addEmployee() - add EmpInfo Lname record into DB.");
				
		rows = doRowsCmd(sqlcmd);
				
		if (rows == 1)
			return true;
				
		else
		{
				System.out.println("addEmployee() - returned " + rows +
					"added! Not good!");
				return false;
		}
				
	} // End of updateEmployeeLname()
 
 
/** 	
	updateEmployeeJob method
	Expects an EmpData class as input, which will have EmpID and other data
	that is needed.
	
	@param EmpData object 
	@return Returns a boolean for success or not
	 
*/
	public boolean updateEmployeeJob(EmpData data) throws SQLException 
	{
		return true;  // for now
	}
 
 
  
/** 	
	updateEmployeePhone method
	Expects an EmpData class as input, which will have EmpID and other data
	needed data.
	
	@param EmpData object 
	@return Returns a boolean for success or not 
	 
*/
	public boolean updateEmployeePhone(EmpData data) throws SQLException 
	{
		return true;  // for now
	}
 
   
/** 	
	fetchUser method
	Expects an user name, e.g. admin, or joe@myemail.com, etc.
	
	@param user a string object 
	@return Returns a integer of EmpID if successful, else 0 
	 
*/
	public int fetchUser(String user) throws SQLException 
	{ 
		int empid = 0;
		ResultSet result;
				
		// Ensure there is a DB record for Employee
		System.out.println("findUser() - searching for UserName");
		
		String sqlcmd = "SELECT EmpID, Validity " +
				"FROM EmpInfo " +
				"WHERE InfoType = 0 AND InfoSubType = 0 AND Validity <> 0 " +
				"AND CharBig = '" + user + "'";
			
		result = doCmd(sqlcmd); 
		if (result != null)
		{
			if (!result.next())
				return 0; // not found	
					
			empid = Integer.parseInt(result.getString("EmpID"));
			return empid;
		}
		
		else
		{ 
			System.out.println("Can not find UserName");
			return 0;
		} 	
		
	} // End of fetchUser()
 
 
 	   
/** 	
	fetchPassword method
	Expects a EmpID and searches for Password of that user.
	
	@param empid the employee primary key
	@return Returns a String of the password for that user, if successful else null 
	 
*/
	public String fetchPassword(int empid) throws SQLException 
	{ 
		String password = null;
		String sqlcmd = null;
		ResultSet result;
		
		System.out.println("in EmpDBaccess - fetchPassword");
			
		sqlcmd = "SELECT Validity, CharBig ";
		sqlcmd += "FROM EmpInfo ";
		sqlcmd += "WHERE EmpID = " + empid; 
		sqlcmd += " AND InfoType = 0 AND InfoSubType = 1 AND Validity <> 0";
	
		result = doCmd(sqlcmd);
		if (result != null)
		{		 
			if (!result.next())
				return password; // not found, leave as null
						
			password = result.getString("CharBig");
			return password;
		}
		
		else
		{ 
			System.out.println("An error occurred while finding UserName's password");
			return null;
		} 	
		
	} // End of fetchPassword()
 
 
}  // End of class

