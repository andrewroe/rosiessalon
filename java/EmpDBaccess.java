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
	
	@param data EmpData object 
	@throws SQLException if there is an error with some SQL command
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
			
		//System.out.println("searchEmployee() - search DB using user's " + 
		//	"Fname, Lname, and Minit.");
						
		result = doCmd(sqlcmd);
			
		if (result == null) 
		{ 
			System.out.println("searchEmployee() - can not find employee in DB");
			return 0;
		} 	
					
		if (result.next())
		{
			//System.out.println("searchEmployee() - it should get here");
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
	
	@param data EmpData object 
	@throws SQLException if there is an error with some SQL command
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
		String createtime = readfullDateTime();
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
		// System.out.println("number of rows inserted = " + rows);
				
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
	
	@param data EmpData object 
	@param index which phone slot primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addEmpPhone(EmpData data, int index) throws SQLException 
	{
		int empid = data.getEmpID();
		int userid = data.getUserID();
		int rows;
		String phone = data.getPhone(index);	
		String updatetime = readfullDateTime();
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
		//System.out.println("number of rows inserted = " + rows);
				
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
	addEmpPhone method
	Expects a completed EmpData class as input
	
	@param data EmpData object 

	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addEmpInfoRecord(int dtype, int subtype, EmpData data) 
		throws SQLException 
	{
		int empid = data.getEmpID();
		int userid = data.getUserID();
		int rows;	
		String updatetime = readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
			
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
		sqlcmd += ", " + dtype;
		sqlcmd += ", " + subtype;
		switch (dtype)
		{
			case DtypeAdmin:
				sqlcmd += ", " + ValidInfo3;
				switch (subtype)
				{
					case SubTypeUserName:
						sqlcmd += ", '" + data.getUname() + "'";
						break;
					case SubTypePassword:
						sqlcmd += ", '" + data.getPassword() + "'";
						break;
					default:
						return false;				
				}
				break;
			case DtypePhone:
				sqlcmd += ", " + ValidInfo3;
				switch (subtype)
				{
					case SubTypePrimary:
						sqlcmd += ", '" + data.getPhone(0) + "'";
						break;
					case SubTypeSecondary:
						sqlcmd += ", '" + data.getPhone(1) + "'";
						break;
					case SubTypeTertiary:
						sqlcmd += ", '" + data.getPhone(2) + "'";
						break;
					case SubTypeFourth:
						sqlcmd += ", '" + data.getPhone(3) + "'";
						break;
					case SubTypeFifth:
						sqlcmd += ", '" + data.getPhone(4) + "'";
						break;
					default:
						return false;	
				}
				break;
			case DtypeEmail:
				sqlcmd += ", " + ValidInfo3;
				switch (subtype)
				{
					case SubTypePrimary:
						sqlcmd += ", '" + data.getEmail(0) + "'";
						break;
					case SubTypeSecondary:
						sqlcmd += ", '" + data.getEmail(1) + "'";
						break;
					case SubTypeTertiary:
						sqlcmd += ", '" + data.getEmail(2) + "'";
						break;
					case SubTypeFourth:
						sqlcmd += ", '" + data.getEmail(3) + "'";
						break;
					case SubTypeFifth:
						sqlcmd += ", '" + data.getEmail(4) + "'";
						break;
					default:
						return false;	
				}
				break;									
			case DtypeAddress:
				sqlcmd += ", " + ValidInfo3;
				switch (subtype)
				{
					case SubTypeAddr1:
						sqlcmd += ", '" + data.getAddr(0) + "'";
						break;
					case SubTypeAddr2:
						sqlcmd += ", '" + data.getAddr(1) + "'";
						break;
					case SubTypeAddr3:
						sqlcmd += ", '" + data.getAddr(2) + "'";
						break;
					case SubTypeAddr4:
						sqlcmd += ", '" + data.getAddr(3) + "'";
						break;
					case SubTypeAddr5:
						sqlcmd += ", '" + data.getAddr(4) + "'";
						break;
					default:
						return false;	
				}
				break;
			case DtypeTimeCard:
				sqlcmd += ", " + ValidInfo3;
				switch (subtype)
				{
					case SubTypeDayIn:
						sqlcmd += ", '" + updatetime + "'";
						break;
					case SubTypeDayOut:
						sqlcmd += ", '" + updatetime + "'";
						break;
					default:
						return false;	
				}
				break;				
			case DtypePersonal:
				switch (subtype)
				{	
					case SubTypeFname:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", '" + data.getFname() + "'";
						break;
					case SubTypeMinit:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", '" + data.getMinit() + "'";
						break;
					case SubTypeLname:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", '" + data.getLname() + "'";
						break;
					case SubTypeJob:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", '" + data.getJob() + "'";
						break;
					case SubTypeSalary:
						sqlcmd += ", " + ValidInfo2;
						sqlcmd += ", " + data.getSalary();
						break;
					case SubTypeCommission:
						sqlcmd += ", " + ValidInfo2;
						sqlcmd += ", commission = " + data.getCommission();
						break;													
					default:
						return false;
				}
			default:		
				return false;
		}
					
		sqlcmd += ")";
						
		rows = doRowsCmd(sqlcmd);		
		//System.out.println("number of rows inserted = " + rows);
				
		if (rows == 1)
			return true;		
		else if (rows == 0)
			return false;	
		else
		{
			System.out.println("addEmpIntoRecord() - returned neither 0 " +
				"nor 1 rows added? But rather " + 
				rows + " rows added??");
			return false;
		}
				
	} // End of addEmpInfoRecord()

 
/** 	
	deactivateEmpPhone method
	Expects a completed EmpData class as input
	
	@param data EmpData object 
	@param index for which phone primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateEmpPhone(EmpData data, int index) throws SQLException 
	{
		int empid = data.getEmpID();
		int userid = data.getUserID();
		int einfoid = 0;
		int rows = 0;
		String updatetime = readfullDateTime();
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
	deactivateEmpInfoRecord method
	Expects ... fill in
	
	@param einfoid EinfoID 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateEmpInfoRecord(int einfoid) throws SQLException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
							
		sqlcmd = "UPDATE EmpInfo SET Validity = " + 0;
		sqlcmd += ", UpdateTime = '" + updatetime + "'";
		sqlcmd += " WHERE EinfoID = " + einfoid; 
				
		rows = doRowsCmd(sqlcmd) ;
		if (rows > 0)	
			return true;
		else
			return false;		
	} // End of deactivateEmpInfoRecord()

/** 	
	fetchAllEmployeeInfo method
	Expects an EmpData class as input, which will have EmpID and then 
	other data within the EmmData class will be 
	overlayed into it upon return.
	
	@param data EmpData object 
	@throws SQLException if there is an error with some SQL command
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
				
	
		//System.out.println("fetchAllEmployeeInfo() - search DB using user's " + 
		//	"EmpID as supplied.");
					
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllEmployeeInfo() - " +
				"could not find employee match!");
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
					
		//System.out.println("fetchAllEmployeeInfo() - search EmpInfo DB record " + 
		//		"using EmpID as supplied.");
				
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
				data.setAddr(charparm,0);
					
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeSecondary) &&
				(validity == ValidInfo3))
				data.setAddr(charparm,1);	
									
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeTertiary) &&
				(validity == ValidInfo3))
				data.setAddr(charparm,2);	
	
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFourth) &&
				(validity == ValidInfo3))
				data.setAddr(charparm,3);
				
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFifth) &&
				(validity == ValidInfo3))
				data.setAddr(charparm,4);
																
			else
				// System.out.println("encountered unknown EmpInfo record??!");	
				continue;					
		} 	
		
		return true;	
		
	} // End of fetchAllEmployeeInfo()

/** 	
	fetchUser method
	Expects an user name, e.g. admin, or joe@myemail.com, etc.
	
	@param user a string object
	@throws SQLException if there is an error with some SQL command 
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
	@throws SQLException if there is an error with some SQL command
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
 
 
 /** 	
	searchEmployeeByLastName method
	Searches for any Employee infomation based only on the last name information 
	available in the EmpData object.
	For each possible match the EmpID is addded into the ArrayListsupplied by caller.
	
	@param data EmpData object
	@param EmpIDlist an ArrayList
	@throws SQLException if there is an error with some SQL command 
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
			
		//System.out.println("searchEmployee() - search DB using user's Lname.");
			
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
	findEmpInfoRecord method
	Expects an user name, e.g. admin, or joe@myemail.com, etc.
	
	@param dtype Data Type
	@param subtype Data Sub Tupe
	@param data EmpData object reference
	@throws SQLException if there is an error with some SQL command 
	@return Returns a integer of EmpID if successful, else 0 
	 
*/
	public int findEmpInfoRecord(int dtype, int subtype, EmpData data) throws SQLException 
	{ 
		int empid = data.getEmpID();
		int einfoid = 0;
		ResultSet result;
						
		String sqlcmd = "SELECT EinfoID, EmpID, InfoType, InfoSubType, Validity ";
		sqlcmd += "FROM EmpInfo ";
		sqlcmd += "WHERE EmpID = " + empid;
		sqlcmd += " AND InfoType = " + dtype;
		sqlcmd += " AND InfoSubType = " + subtype;
		sqlcmd += " AND Validity <> 0 ";
			
		result = doCmd(sqlcmd); 
		if (result != null)
		{
			if (!result.next())
				return 0; // not found	
					
			einfoid = Integer.parseInt(result.getString("EinfoID"));
			return einfoid;
		}
		
		else
		{ 
			System.out.println("Can not find EmpInfo record");
			return 0;
		} 	
		
	} // End of findEmpInfoRecord()
 
	 
  
/** 	
	updateEmployeeFullName method
	Expects an EmpData class as input, which should contain EmpID and
	Fname, Lname and Minit.
	
	@param data EmpData object
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean for success or not.
	 
*/
	public boolean updateEmployeeFullName(EmpData data) throws SQLException 
	{
		return false;  // for now
	}
   
/** 	
	updateEmployeeLname method
	Expects an EmpData class as input, which will have EmpID and other data
	that is needed.
	
	@param which integer of which name field to update - 
	see DtypePersonal SubType... 
	
	@param data EmpData object
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean for success or not
	 
*/
	public boolean updateEmployee(int subtype, EmpData data) throws SQLException 
	{ 
		int empid = data.getEmpID();
		int user = data.getUserID();
		int einfoid;
		int olduser;
		String oldfname = null;
		String oldlname = null;
		String oldminit = null;
		String oldjob = null;
		double oldsalary = 0.0;
		double oldcommission = 0.0;
		int Nbr1Parm;
		double Nbr2Parm;
		String CharBig;
		int rows;
		ResultSet result;
		String updatetime = readfullDateTime();
		
		if (empid == 0)
		{
			throw new SQLException("Attempting update Employee Lname " +
					"when no EmpID supplied!");
		} 
			
		// Ensure there is a DB record for Employee
		// System.out.println("updateEmployeeLname() - confirming that DB " +
		//		"has that Employee using EmpID");
				
		String sqlcmd = "SELECT EmpID, UserID, Fname, Minit, Lname" +
				" FROM Employee where EmpID = " + empid; 
		
		result = doCmd(sqlcmd);
		 
		if (!result.next())
		{
			throw new SQLException("Attempting update Employee " +
				"when no such Employee exits!");
		}	
					
		// we want to update Employee table with new record
		// But also, update history of change into EmpInfo table
		oldfname = result.getString("Fname");
		oldminit = result.getString("Minit");
		oldlname = result.getString("Lname");
		olduser = result.getInt("UserID");
		
		sqlcmd = "UPDATE Employee SET UserID = " + user;
		sqlcmd += ", UpdateTime = '" + updatetime + "'";
		switch (subtype)
		{
			case SubTypeFname:
				sqlcmd += ", Fname = '" + data.getFname() + "'";
				break;
			case SubTypeMinit:
				sqlcmd += ", Minit = '" + data.getMinit() + "'";
				break;
			case SubTypeLname:
				sqlcmd += ", Lname = '" + data.getLname() + "'";
				break;
			case SubTypeJob:
				sqlcmd += ", job = '" + data.getJob() + "'";
				break;
			case SubTypeSalary:
				sqlcmd += ", salary = " + data.getSalary();
				break;
			case SubTypeCommission:
				sqlcmd += ", commission = " + data.getCommission();
				break;
										
			default:
				break;
		}
		
		sqlcmd += " WHERE EmpID = " + empid;
				
		rows = doRowsCmd(sqlcmd);
		// System.out.println("updateEmployee() - just attempted Employee update");
				
		if (rows != 1)
		{
			throw new SQLException("Attempting update Employee" +
				"but the update step failed!");
		}	



		return true;				
	} // End of updateEmployeeLname()
 

	public boolean updateEmployeeFname(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeFname,data);	
	}
	
	public boolean updateEmployeeMinit(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeMinit,data);	
	}
	
	public boolean updateEmployeeLname(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeLname,data);	
	}
	
	public boolean updateEmployeeJob(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeJob,data);	
	}

	public boolean updateEmployeeSalary(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeSalary,data);	
	}
		
	public boolean updateEmployeeCommission(EmpData data) throws SQLException 
	{ 
		return updateEmployee(SubTypeCommission,data);	
	}
	
	public boolean updateEmployeeDob(EmpData data) throws SQLException 
	{ 
		int key = findEmpInfoRecord(DtypePersonal, SubTypeDob, data);	
		if (key > 0)	
			deactivateEmpInfoRecord(key);	
		return addEmpInfoRecord(DtypePersonal,SubTypeDob,data);	
	}
	
	public boolean updateEmployeeAddress(EmpData data,int index) 
		throws SQLException 
	{ 
		boolean rvalue = true;
		int which;
		switch (index)
		{
			case 0:
				which = SubTypePrimary;
				break;
			case 1:
				which = SubTypeSecondary;
				break;				
			case 2:
				which = SubTypeTertiary;
				break;
			case 3:
				which = SubTypeFourth;
				break;
			case 4:
				which = SubTypeFifth;
				break;
			default:
				return false;
		}
		int key = findEmpInfoRecord(DtypeAddress, which, data);	
		if (key > 0)	
			rvalue = deactivateEmpInfoRecord(key);
		if (data.getAddr(index) != null)	
			return addEmpInfoRecord(DtypeAddress, which, data);
		
		return rvalue;		
	}
	
	public boolean updateEmployeePhone(EmpData data, int index) 
		throws SQLException 
	{ 
		boolean rvalue = true;		
		int which;
		switch (index)
		{
			case 0:
				which = SubTypePrimary;
				break;
			case 1:
				which = SubTypeSecondary;
				break;				
			case 2:
				which = SubTypeTertiary;
				break;
			case 3:
				which = SubTypeFourth;
				break;
			case 4:
				which = SubTypeFifth;
				break;
			default:
				return false;
		}
		int key = findEmpInfoRecord(DtypePhone, which, data);	
		if (key > 0)	
			rvalue = deactivateEmpInfoRecord(key);
		if (data.getPhone(index) != null)	
			return addEmpInfoRecord(DtypePhone, which, data);
			
		return rvalue;							
	}
	
	public boolean updateEmployeeEmail(EmpData data,int index) 
		throws SQLException 
	{ 
		boolean rvalue = true;		
		int which;
		
		switch (index)
		{
			case 0:
				which = SubTypePrimary;
				break;
			case 1:
				which = SubTypeSecondary;
				break;				
			case 2:
				which = SubTypeTertiary;
				break;
			case 3:
				which = SubTypeFourth;
				break;
			case 4:
				which = SubTypeFifth;
				break;
			default:
				return false;
		}
		int key = findEmpInfoRecord(DtypeEmail, which, data);	
		if (key > 0)	
			rvalue = deactivateEmpInfoRecord(key);	
		if (data.getEmail(index) != null)	
			return addEmpInfoRecord(DtypeEmail, which, data);
			
		return rvalue;					
	}
						
}  // End of class

