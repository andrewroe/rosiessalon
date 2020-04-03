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
		throws SQLException, FileNotFoundException 
	{
		int cinfoid = data.getCustID();
		int userid = data.getUserID();
		int rows;	
		String updatetime = readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
		System.out.println("addCustInfoRecord() - entry");
		
		sqlcmd = "INSERT INTO CustInfo (CustID, UserID";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", Nbr1Parm";
		sqlcmd += ", Nbr2Parm";
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
				sqlcmd += ", " + 0;
				sqlcmd += ", " + 0.0;
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
				sqlcmd += ", " + 0;
				sqlcmd += ", " + 0.0;				
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

			case DtypePersonal:
				System.out.println("addCustInfoRecord() - DtypePersonal");
				switch (subtype)
				{	
					case SubTypeFname:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", " + 0;
						sqlcmd += ", " + 0.0;						
						sqlcmd += ", '" + data.getFname() + "'";
						break;
					case SubTypeMinit:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", " + 0;
						sqlcmd += ", " + 0.0;
						sqlcmd += ", '" + data.getMinit() + "'";
						break;
					case SubTypeLname:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", " + 0;
						sqlcmd += ", " + 0.0;
						sqlcmd += ", '" + data.getLname() + "'";
						break;
					case SubTypeDob:
						sqlcmd += ", " + ValidInfo3;
						sqlcmd += ", " + 0;
						sqlcmd += ", " + 0.0;
						sqlcmd += ", '" + data.getDob() + "'";
						break;
					case SubTypeBalance:
						sqlcmd += ", " + ValidInfo2;
						sqlcmd += ", " + 0;
						sqlcmd += ", " + data.getBalanceDue();
						sqlcmd += ", ''";
						break;												
					default:						
						return false;
				}
				break;
																																				
			default:		
				return false;
		}
					
		sqlcmd += ")";
						
		rows = doRowsCmd(sqlcmd);		
		//System.out.println("number of rows inserted = " + rows);
				
		if (rows != 1)
		{
			System.out.println("addCustIntoRecord() - returned neither 0 " +
				"nor 1 rows added? But rather " + 
				rows + " rows added??");					
			return false;
		}
			
		return true;
				
	} // End of addCustInfoRecord()



	/** 	
		addCustInfoAddress method
		Expects a completed CustData class as input
	
		@param data CustData object 
		@throws SQLException if there is an error with some SQL command
		@return Returns a boolean true for success, else false
	*/
	public boolean addCustInfoAddress(int subtype, CustData data) 
		throws SQLException, FileNotFoundException 
	{
		int custid = data.getCustID();
		int userid = data.getUserID();
		String[] address = data.getAddr(subtype - 1);
		int rows;	
		String updatetime = readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
		System.out.println("addCustInfoAddress() - entry");

		System.out.println("addCustInfoAddress input address is: " +
			address[0] + "-" +address[1] + "-" +
			address[2] + "-" + address[3] + "-" + address[4]);
		
		for (int i = 0; i < 5; i++)
		{
			if ((address[i] == null) || address[i].equals(""))
			{
				i = 5;
				continue;
			}
			System.out.println("addCustInfoAddress() - Nbr1Parm index = " + i);
			
			sqlcmd = "INSERT INTO CustInfo (CustID, UserID";
			sqlcmd += ", UpdateTime";
			sqlcmd += ", InfoType";
			sqlcmd += ", InfoSubType";
			sqlcmd += ", Validity";
			sqlcmd += ", Nbr1Parm";
			sqlcmd += ", CharBig)";
			sqlcmd += " VALUES (";
			sqlcmd += custid;
			sqlcmd += ", " + userid;
			sqlcmd += ", '" + updatetime + "'";
			sqlcmd += ", " + DtypeAddress;
			sqlcmd += ", " + subtype;
			sqlcmd += ", " + (ValidInfo1 + ValidInfo3);
			sqlcmd += ", " + i;			
			sqlcmd += ", '" + address[i] + "'";		
			sqlcmd += ")";
					
			rows = doRowsCmd(sqlcmd);
			
			System.out.println("addCustInfoAddress after insert loop index = " + i);		
		
			if (rows != 1)
			{
				return false;		
			}
			
			System.out.println("addCustInfoAddress after rows check loop index = " + i);
		} // End of for loop
				
		return true;
				
	} // End of addCustInfoAddress()


/** 	
	changeSubTypeInfoRecord method
	Expects ... fill in
	
	@param cinfoid CinfoID 
	@subtype new subtype value
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean changeSubTypeCustInfoRecord(int cinfoid, int subtype) 
		throws SQLException, FileNotFoundException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
		
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
									
		sqlcmd = "UPDATE CustInfo SET InfoSubType = " + subtype;
		sqlcmd += ", UpdateTime = '" + updatetime + "'";
		sqlcmd += " WHERE CinfoID = " + cinfoid; 
				
		rows = doRowsCmd(sqlcmd);
			
		if (rows > 0)	
			return true;
		else
			return false;
						
	} // End of changeSubTypeCustInfoRecord()





/** 	
	deactivateCustInfoRecord method
	Expects ... fill in
	
	@param cinfoid CinfoID 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateCustInfoRecord(int cinfoid) 
		throws SQLException, FileNotFoundException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
		
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
								
		sqlcmd = "UPDATE CustInfo SET Validity = " + 0;
		// sqlcmd += ", UpdateTime = '" + updatetime + "'";
		// sqlcmd += ", UserID = " + userid;
		sqlcmd += " WHERE CinfoID = " + cinfoid; 
				
		rows = doRowsCmd(sqlcmd);
			
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
	public int findCustInfoRecord(int dtype, int subtype, CustData data) 
		throws SQLException, FileNotFoundException 
	{ 
		int custid = data.getCustID();
		int cinfoid = 0;
		ResultSet result;
		
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
	
											
		String sqlcmd = "SELECT CinfoID, CustID, InfoType, InfoSubType, Validity ";
		sqlcmd += "FROM CustInfo ";
		sqlcmd += "WHERE CustID = " + custid;
		sqlcmd += " AND InfoType = " + dtype;
		sqlcmd += " AND InfoSubType = " + subtype;
		sqlcmd += " AND Validity <> 0 ";
			
		result = doCmd(sqlcmd); 
		if (result != null)
		{
			if (!result.next())
			{
				return 0; // not found
			}
					
			cinfoid = Integer.parseInt(result.getString("CinfoID"));
			return cinfoid;
		}
		
		else
		{ 
			System.out.println("Can not find CustInfo record");
			return 0;
		} 	
		
	} // End of findCustInfoRecord()
 

	/** 	
	findCustInfoAddress method
	Expects an user name, e.g. admin, or joe@myemail.com, etc.
	
	@param subtype Data Sub Tupe (will point to Primary, Secondary, etc.)
	@param data CustData object reference
	@throws SQLException if there is an error with some SQL command 
	@return Returns an integer string of up to 5 einfoid records 
	 
	*/
	public int[] findCustInfoAddress(int subtype, CustData data) 
		throws SQLException, FileNotFoundException 
	{ 
		int custid = data.getCustID();
		int cinfoid = 0;
		int[] cinfoIDs = new int[]{0,0,0,0,0};
		String[] address = data.getAddr(subtype - 1);
		ResultSet result;
		
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
		for (int i = 0; i < 5; i++)
		{
			if (address[i] == null)
			{
				i = 5;
				continue;
			}
								
			String sqlcmd = "SELECT CinfoID, CustID, InfoType, InfoSubType, Validity ";
			sqlcmd += "FROM CustInfo ";
			sqlcmd += "WHERE CustID = " + custid;
			sqlcmd += " AND InfoType = " + DtypeAddress;
			sqlcmd += " AND InfoSubType = " + subtype;
			sqlcmd += " AND Validity <> 0 ";
			
			result = doCmd(sqlcmd); 
			if (result != null)
			{
				if (!result.next())
				{		
					cinfoIDs[i] = 0; // not found
				}
				
				else
				{
					cinfoid = Integer.parseInt(result.getString("CinfoID"));
					cinfoIDs[i] = cinfoid;
				}				
			}
		
			else
			{ 
				cinfoIDs[i] = 0;
			} 
		
		}  // End of for loop	
		
		return cinfoIDs;		
		
	} // End of findCustInfoAddress()

						
}  // End of class

