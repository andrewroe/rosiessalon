import java.sql.*;

import java.util.Properties; 
import java.util.ArrayList;
import java.io.*;

/** 	
	Java Program to I/f with CustInfo table in DB
*/

public class CustInfoDB extends RosiesSalon
{ 
	 
  /** 	
	addCustInfoRecorde method
	Expects a completed CustData class as input
	
	@param data CustData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addCustInfoRecord(int dtype, int subtype, CustData data) 
		throws SQLException 
	{
		int cinfoid = data.getCustID();
		int userid = data.getUserID();
		int rows;	
		String updatetime = readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
			
		sqlcmd = "INSERT INTO CustInfo (CustID, UserID";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", CharBig)";
		sqlcmd += " VALUES (";
		sqlcmd += cinfoid;
		sqlcmd += ", " + userid;
		sqlcmd += ", '" + updatetime + "'";
		sqlcmd += ", " + dtype;
		sqlcmd += ", " + subtype;
		switch (dtype)
		{
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
					case SubTypeBalance:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", '" + data.getBalanceDue() + "'";
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
			System.out.println("addCustIntoRecord() - returned neither 0 " +
				"nor 1 rows added? But rather " + 
				rows + " rows added??");
			return false;
		}
				
	} // End of addCustInfoRecord()


/** 	
	deactivateCustInfoRecord method
	Expects ... fill in
	
	@param cinfoid CinfoID 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateCustInfoRecord(int cinfoid) throws SQLException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
							
		sqlcmd = "UPDATE CustInfo SET Validity = " + 0;
		// sqlcmd += ", UpdateTime = '" + updatetime + "'";
		// sqlcmd += ", UserID = " + userid;
		sqlcmd += " WHERE CinfoID = " + cinfoid; 
				
		rows = doRowsCmd(sqlcmd) ;
		if (rows > 0)	
			return true;
		else
			return false;		
	} // End of deactivateCustInfoRecord()



	/** 	
	findCustInfoRecord method
	Expects an user name, e.g. admin, or joe@myemail.com, etc.
	
	@param dtype Data Type
	@param subtype Data Sub Tupe
	@param data EmpData object reference
	@throws SQLException if there is an error with some SQL command 
	@return Returns a integer of EmpID if successful, else 0 
	 
	*/
	public int findCustInfoRecord(int dtype, int subtype, CustData data) throws SQLException 
	{ 
		int custid = data.getCustID();
		int einfoid = 0;
		ResultSet result;
						
		String sqlcmd = "SELECT EinfoID, CustID, InfoType, InfoSubType, Validity ";
		sqlcmd += "FROM CustInfo ";
		sqlcmd += "WHERE CustID = " + custid;
		sqlcmd += " AND InfoType = " + dtype;
		sqlcmd += " AND InfoSubType = " + subtype;
		sqlcmd += " AND Validity <> 0 ";
			
		result = doCmd(sqlcmd); 
		if (result != null)
		{
			if (!result.next())
				return 0; // not found	
					
			einfoid = Integer.parseInt(result.getString("CinfoID"));
			return einfoid;
		}
		
		else
		{ 
			System.out.println("Can not find CustInfo record");
			return 0;
		} 	
		
	} // End of findCustInfoRecord()
 

 
/** 	
	addCustInfoPhone method
	Expects a completed EmpData class as input
	
	@param data CustData object 
	@param index which phone slot primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addCustInfoPhone(CustData data, int index) throws SQLException 
	{
		int cinfoid = data.getCustID();
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

		sqlcmd = "INSERT INTO CustInfo (CustID, UserID";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", CharBig)";
		sqlcmd += " VALUES (";
		sqlcmd += cinfoid;
		sqlcmd += ", " + userid;
		sqlcmd += ", '" + updatetime + "'";
		sqlcmd += ", " + DtypePhone;
		sqlcmd += ", " + (index + 1);
		sqlcmd += ", " + ValidInfo3;
		sqlcmd += ", '" + phone + "'";
		sqlcmd += ")";
					
		//System.out.println("addCustPhone() - doing insert"); 	
		rows = doRowsCmd(sqlcmd);		
		//System.out.println("number of rows inserted = " + rows);
				
		if (rows == 1)
			return true;		
		else if (rows == 0)
			return false;	
		else
		{
			System.out.println("addCustPhone() - returned neither 0 or 1 rows" +
				"added? But rather " + rows + " rows added??");
			return false;
		}
				
	} // End of addCustPhone()


 
/** 	
	deactivateCustInfoPhone method
	Expects a completed EmpData class as input
	
	@param data CustData object 
	@param index for which phone primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateCustInfoPhone(CustData data, int index) throws SQLException 
	{
		int custid = data.getCustID();
		int userid = data.getUserID();
		int cinfoid = 0;
		int rows = 0;
		String updatetime = readfullDateTime();
		boolean rvalue = false;
		ResultSet result;
		String sqlcmd;
			
		sqlcmd = "SELECT CinfoID, InfoType, InfoSubType, Validity, CharBig";
		sqlcmd += " FROM CustInfo where CustID = " + custid;
		sqlcmd += " AND InfoType = " + DtypePhone;
		sqlcmd += " AND InfoSubType = " + (index+1);
		sqlcmd += " And Validity = " + ValidInfo3;  
			
		//System.out.println("deactivateCustInfoPhone() - will do CustInfo query");
		result = doCmd(sqlcmd);
			
		if (result == null)
		{ 
			System.out.println("deactivateCustInfoPhone() - could not find phone match!");
			return false;
		}
		
		while (result.next())	
		{
			System.out.println("deactivateCustInfoPhone() - found records in query");
			// There is at least one prior active phonerecord,
			// we need to mark that as invalid.
			rvalue = true;
			cinfoid = result.getInt("CinfoID");
			// System.out.println("deactivateCustInfoPhone() - CinfoID = " + cinfoid);
					
			sqlcmd = "UPDATE CustInfo SET Validity = " + 0;
			sqlcmd += ", UpdateTime = '" + updatetime + "'";
			sqlcmd += " WHERE CinfoID = " + cinfoid; 
				
			//result = doCmd(sqlcmd);
			rows = doRowsCmd(sqlcmd) ;
		}	
			
		return rvalue;		
	} // End of deactivateCustInfoPhone()


	
   
	
	public boolean updateCustomerAddress(CustData data,int index) 
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
		int key = findCustInfoRecord(DtypeAddress, which, data);	
		if (key > 0)	
			rvalue = deactivateCustInfoRecord(key);
		if (data.getAddr(index) != null)	
			return addCustInfoRecord(DtypeAddress, which, data);
		
		return rvalue;		
	}
	
	public boolean updateCustInfoPhone(CustData data, int index) 
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
		int key = findCustInfoRecord(DtypePhone, which, data);	
		if (key > 0)	
			rvalue = deactivateCustInfoRecord(key);
		if (data.getPhone(index) != null)	
			return addCustInfoRecord(DtypePhone, which, data);
			
		return rvalue;							
	}
	
	public boolean updateCustInfoEmail(CustData data,int index) 
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
		int key = findCustInfoRecord(DtypeEmail, which, data);	
		if (key > 0)	
			rvalue = deactivateCustInfoRecord(key);	
		if (data.getEmail(index) != null)	
			return addCustInfoRecord(DtypeEmail, which, data);
			
		return rvalue;					
	}
						
}  // End of class

