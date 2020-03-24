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
		CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
        try
		{
			if (!CustInfoDBaccess.ConnectToDB(TransactionApp.CredentialsFile))
   			{
   				System.out.println("Can not connect to DB for CustInfo DB, Bye.");	
   				return false;
   			} 
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }		

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
						
		rows = CustInfoDBaccess.doRowsCmd(sqlcmd);		
		//System.out.println("number of rows inserted = " + rows);
		
		CustInfoDBaccess.DisconnectFromDB();
				
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
		int cinfoid = data.getCustID();
		int userid = data.getUserID();
		String[] address = data.getAddr(subtype - 1);
		int rows;	
		String updatetime = readfullDateTime();
		ResultSet result;
		String sqlcmd;
		boolean returnValue = false;
		CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
        try
		{
			if (!CustInfoDBaccess.ConnectToDB(TransactionApp.CredentialsFile))
   			{
   				System.out.println("Can not connect to DB for CustInfo DB, Bye.");	
   				return false;
   			} 
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }		
		
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
			sqlcmd += cinfoid;
			sqlcmd += ", " + userid;
			sqlcmd += ", '" + updatetime + "'";
			sqlcmd += ", " + DtypeAddress;
			sqlcmd += ", " + subtype;
			sqlcmd += ", " + (ValidInfo1 + ValidInfo3);
			sqlcmd += ", " + i;			
			sqlcmd += ", '" + address[i] + "'";		
			sqlcmd += ")";
					
			rows = CustInfoDBaccess.doRowsCmd(sqlcmd);
			
			System.out.println("addCustInfoAddress after insert loop index = " + i);		
		
			if (rows != 1)
			{
				CustInfoDBaccess.DisconnectFromDB();
				return false;		
			}
			
			System.out.println("addCustInfoAddress after rows check loop index = " + i);
		} // End of for loop
		
		CustInfoDBaccess.DisconnectFromDB();		
		return true;
				
	} // End of addCustInfoAddress()


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
		
		CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
        try
		{
			if (!CustInfoDBaccess.ConnectToDB(TransactionApp.CredentialsFile))
   			{
   				System.out.println("Can not connect to DB for CustInfo DB, Bye.");	
   				return false;
   			} 
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }		
							
		sqlcmd = "UPDATE CustInfo SET Validity = " + 0;
		// sqlcmd += ", UpdateTime = '" + updatetime + "'";
		// sqlcmd += ", UserID = " + userid;
		sqlcmd += " WHERE CinfoID = " + cinfoid; 
				
		rows = CustInfoDBaccess.doRowsCmd(sqlcmd);
		
		CustInfoDBaccess.DisconnectFromDB();	
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
		
		CustDBaccess CustInfoDBaccess = new CustDBaccess();
	
        try
		{
			if (!CustInfoDBaccess.ConnectToDB(TransactionApp.CredentialsFile))
   			{
   				System.out.println("Can not connect to DB for CustInfo DB, Bye.");	
   				return 0;
   			} 
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }			
								
		String sqlcmd = "SELECT CinfoID, CustID, InfoType, InfoSubType, Validity ";
		sqlcmd += "FROM CustInfo ";
		sqlcmd += "WHERE CustID = " + custid;
		sqlcmd += " AND InfoType = " + dtype;
		sqlcmd += " AND InfoSubType = " + subtype;
		sqlcmd += " AND Validity <> 0 ";
			
		result = CustInfoDBaccess.doCmd(sqlcmd); 
		if (result != null)
		{
			if (!result.next())
			{
				CustInfoDBaccess.DisconnectFromDB();
				return 0; // not found
			}
					
			cinfoid = Integer.parseInt(result.getString("CinfoID"));
			return cinfoid;
		}
		
		else
		{ 
			System.out.println("Can not find CustInfo record");
			CustInfoDBaccess.DisconnectFromDB();
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
		
		CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
        try
		{
			if (!CustInfoDBaccess.ConnectToDB(TransactionApp.CredentialsFile))
   			{
   				System.out.println("Can not connect to DB for CustInfo DB, Bye.");	
   				return cinfoIDs;
   			} 
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }		


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
			
			result = CustInfoDBaccess.doCmd(sqlcmd); 
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
		
		CustInfoDBaccess.DisconnectFromDB();
		
		return cinfoIDs;		
		
	} // End of findCustInfoRecord()


 
/** 	
	addCustInfoPhone method
	Expects a completed EmpData class as input
	
		SHOULD BE ABLE TO UTILIXE addCustInfoRecord instead 
	
	@param data CustData object 
	@param index which phone slot primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addCustInfoPhone(CustData data, int index) 
		throws SQLException, FileNotFoundException 
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
	
		SHOULD NOT NEED THIS
	
	@param data CustData object 
	@param index for which phone primary - fifth (1 - 5) to be added
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateCustInfoPhone(CustData data, int index) 
		throws SQLException, FileNotFoundException 
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
		throws SQLException, FileNotFoundException 
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
		throws SQLException, FileNotFoundException 
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
		throws SQLException, FileNotFoundException 
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

