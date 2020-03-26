import java.sql.*;
import java.util.Properties; 
import java.util.ArrayList;
import java.io.*;

/** 	
	Java Program to add/find/update customer in DB
*/

public class CustDBaccess extends RosiesSalon
{ 
	
	
/** 	
	addNewCustomer method
	Expects a completed CustData class as input
	
	@param data CustData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean addNewCustomer(CustData data) throws SQLException 
	{
		int i;
		int rows;
		String lname = data.getLname();
		String fname = data.getFname();
		String minit = data.getMinit();
		int userid = data.getUserID();
		String createtime = readfullDateTime();
		String updatetime = createtime;
		// updatetime = data.getUpdateTime();
		String primaryphone = data.getPrimaryPhone();
		String primaryemail = data.getPrimaryEmail();
		ResultSet result;
		boolean returnValue = false;
		
		/*
		System.out.println("the UserID is = " + userid);
		System.out.println("the Fname is = " + fname);
		System.out.println("the Minit is = " + minit);
		System.out.println("the Lname is = " + lname);
		System.out.println("the Job is = " + job);
		System.out.println("the Salary is = " + salary);
		System.out.println("the Commission is = " + commission);
		*/
		
		System.out.println("addNewCustomer() - userid = " +
			userid + " fname = " + fname + " minit = " +
			minit + " lname = " + lname);
		if ((userid == 0) ||
			(lname == null) || (fname == null) || (minit == null))
		{
			throw new SQLException("Attempting to add Customer without specifying " + 
				"user id and full name"); 
		}

		String sqlcmd = "INSERT INTO Customer (UserID, CreateTime, UpdateTime,";
		sqlcmd += " Fname, Minit, Lname, phone, email)";
		sqlcmd += " VALUES (";
		sqlcmd +=	userid + "," + "'" + createtime + "', ";
		sqlcmd += "'" + updatetime +  "', ";
		sqlcmd += "'" + fname + "', " + "'" + minit + "', ";
		sqlcmd += "'" + lname + "', " + "'" + primaryphone + "', "; 
		sqlcmd += "'" + primaryemail + "')";
				
		// System.out.println("addCustomer() - doing insert"); 	
		rows = doRowsCmd(sqlcmd);		
		// System.out.println("number of rows inserted = " + rows);
				
		if (rows == 1)
			return true;		
		else if (rows == 0)
			return false;	
		else
		{
			System.out.println("addCustomer() - returned neither 0 or 1 rows" +
				"added? But rather " + rows + " rows added??");
			return false;
		}
				
	} // End of addCustomer()
 

 
/** 	
	fetchAllCustomerInfo method
	Expects an CustData class as input, which will have CustID and then 
	other data within the CustData class will be 
	overlayed into it upon return.
	
	@param data CustData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean for success or not and updated the supplied CustData,
	 
*/
	public boolean fetchAllCustomerInfo(CustData data) throws SQLException 
	{
		int custid = data.getCustID();
		ResultSet result;
		int infotype;
		int infosubtype;
		int validity;
		int nbr1parm;
		double nbr2parm;
		String charparm;
		String[] addressparm = new String[5];
		
		if (custid == 0) 
		{
			throw new SQLException("Attempting fetch Customer and CustID equals 0!"); 
		}
		
		String sqlcmd = "SELECT CustID, UserID, Fname, Lname, Minit, phone, ";
		sqlcmd += "email FROM Customer where CustID = " + custid; 
								
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllCustomerInfo() - " +
				"could not find customer match!");
			return false;
		}
		
		if (result.next())
		{
			data.setUserID(result.getInt("UserID"));
			data.setFname(result.getString("Fname"));
			data.setMinit(result.getString("Minit"));
			data.setLname(result.getString("Lname"));
			data.setPrimaryPhone(result.getString("phone"));
			data.setPrimaryEmail(result.getString("email"));			
		}
		else
		{
			System.out.println("Failed to find any match when using user's CustID.");
			return false;
		} 	
		 	
		// Add in any CustInfo data
		
		sqlcmd = "SELECT CustID, UserID, InfoType, InfoSubType, Validity, ";
		sqlcmd += "Nbr1Parm, Nbr2Parm, CharBig FROM CustInfo where CustID = " + custid; 
							
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllCustomerInfo() - can not find " +
				"additional Customer info");
			return true;
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
				(validity == ValidInfo1+ValidInfo3))
				{
					addressparm[nbr1parm] = charparm;		
					data.setAddr(addressparm,0);
				}	
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeSecondary) &&
				(validity == ValidInfo1+ValidInfo3))
				{
					addressparm[nbr1parm] = charparm;
					data.setAddr(addressparm,1);
				}						
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeTertiary) &&
				(validity == ValidInfo1+ValidInfo3))
				{
					addressparm[nbr1parm] = charparm;
					data.setAddr(addressparm,2);	
				}
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFourth) &&
				(validity == ValidInfo1+ValidInfo3))
				{
					addressparm[nbr1parm] = charparm;
					data.setAddr(addressparm,3);
				}
			else if ((infotype == DtypeAddress) && (infosubtype == SubTypeFifth) &&
				(validity == ValidInfo1+ValidInfo3))
				{
					addressparm[nbr1parm] = charparm;
					data.setAddr(addressparm,4);
				}												
			else
				
				continue;					
		} 	
		
		return true;	
		
	} // End of fetchAllCustomerInfo()



/** 	
	searchCustomerByFullName method
	Searches for any Customr infomation based on information available in 
	the CustData object.
	If a match is found, CustID is updated in the CustData and 1 is returned.
	If multiple matches found, CustID is updated with first match but -1 is returned.
	that is VERY unusual?!
	Else, when no match found, 0 is returned and CustID is left untouched.
	
	@param data CustData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns an integer 1, 0, or -1 and may update CustID in CustData object
*/
	public int searchCustomerByFullName(CustData data) throws SQLException 
	{
		String fname = data.getFname();
		String lname = data.getLname();
		String minit = data.getMinit();
		String sqlcmd;
		ResultSet result;
		int rvalue = 0; 

		System.out.println("searchCustomerByFullName() - values = " +
			fname + " " + minit + " " + lname);

		if ((lname == null) || (fname == null) || (minit == null))
		{
			throw new SQLException("Attempting full name search of DB and " + 
				"not all of first, last and middle initial names supplied");
		}
		
		sqlcmd = "SELECT CustID, UserID, CreateTime, UpdateTime, ";
		sqlcmd += "phone, email ";
		sqlcmd += "FROM Customer WHERE Lname = '" + lname + "' AND Fname = '";
		sqlcmd += fname + "' AND Minit = '" + minit + "'";
									
		result = doCmd(sqlcmd);
			
		if (result == null) 
		{ 
			System.out.println("searchCustomer() - can not find customer in DB");
			return 0;
		} 	
					
		if (result.next())
		{
			data.setCustID(result.getInt("CustID"));
			data.setCreateTime(result.getString("CreateTime"));
			data.setUpdateTime(result.getString("UpdateTime"));
			data.setPrimaryPhone(result.getString("phone"));
			data.setPrimaryEmail(result.getString("email"));
			
						
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
	}  // End of searchCustomerByFullName
 
 
 
 /** 	
	searchCustomerByLastName method
	Searches for any Customer infomation based only on the last name information 
	available in the CustData object.
	For each possible match the CustID is addded into the ArrayListsupplied by caller.
	
	@param data CustData object
	@param CustIDlist an ArrayList
	@throws SQLException if there is an error with some SQL command 
	@return Returns boolean true if one or more Customers could match the criteria
	and also updates the supplied Arraylist of CustID values of possible matches.
*/
	public boolean searchCustomerByLastName(CustData data, ArrayList<Integer> CustIDlist) 
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
				
		String sqlcmd = "SELECT CustID, UserID, Fname, Lname, Minit ";
		sqlcmd += "FROM Customer where Lname = '" + lname + "'";
				
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("Can not find any Customers using Lname");
			return(false);
		}
			 	
		returnValue = false;
		while (result.next())
		{
			CustIDlist.add(result.getInt("CustID"));
			returnValue = true;
		}
		
		return(returnValue);
					
	}  // End of searchCustomerByLastName
	

	public boolean slideCustomerField(int dtype, CustData data)
		throws SQLException, FileNotFoundException
	{ 
		int cinfoid = 0;
		
		System.out.println("slideCustomerField - entry");
		
		// slide these fields
		cinfoid = 
			TransactionApp.custinfoDB.findCustInfoRecord(dtype,SubTypeFifth,data);
			
		if (cinfoid > 0)
		{
			TransactionApp.custinfoDB.deactivateCustInfoRecord(cinfoid);
		}

		for (int i = SubTypeFourth; i > 0; i--)
		{
			cinfoid = 
				TransactionApp.custinfoDB.findCustInfoRecord
					(dtype,i,data);
			if (cinfoid > 0)
			{
				TransactionApp.custinfoDB.changeSubTypeCustInfoRecord(cinfoid, i + 1);
			}		
		}
								
		System.out.println("slideCustomerField - exit");
		return true;	
	}

   
/** 	
	updateCustomer method
	Expects an CustData class as input, which will have CustID and other data
	that is needed.
	
	@param subtype which integer of which name field to update - 
	see DtypePersonal SubType... 
	
	@param data CustData object
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean for success or not
	 
*/
	public boolean updateCustomer(int subtype, CustData data) throws SQLException 
	{ 
		int custid = data.getCustID();
		int user = data.getUserID();
		
		int cinfoid;
		int Nbr1Parm;
		double Nbr2Parm;
		String CharBig;
		int rows;
		ResultSet result;
		String updatetime = readfullDateTime();
		
		if (custid == 0)
		{
			throw new SQLException("Attempting update Customer " +
					"when no CustID supplied!");
		} 
					
		String sqlcmd = "SELECT CustID, UserID, Fname, Minit, Lname";
		sqlcmd += " FROM Customer where CustID = " + custid; 
		
		result = doCmd(sqlcmd);
		 
		if (!result.next())
		{
			throw new SQLException("Attempting update Customer " +
				"when no such Customer exits!");
		}	
					
		
		sqlcmd = "UPDATE Customer SET UserID = " + user;
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
			case SubTypePrimaryPhone:
				sqlcmd += ", phone = '" + data.getPrimaryPhone() + "'";
				break;
			case SubTypePrimaryEmail:
				sqlcmd += ", email = '" + data.getPrimaryEmail() + "'";
				break;				
									
			default:
				break;
		}
		
		sqlcmd += " WHERE CustID = " + custid;
				
		rows = doRowsCmd(sqlcmd);
				
		if (rows != 1)
		{
			throw new SQLException("Attempting update Customer" +
				"but the update step failed!");
		}	

		return true;				
	} // End of updateCustomer()
 

	public boolean updateCustomerFname(CustData data) throws SQLException 
	{ 
		return updateCustomer(SubTypeFname,data);	
	}
	
	public boolean updateCustomerMinit(CustData data) throws SQLException 
	{ 
		return updateCustomer(SubTypeMinit,data);	
	}
	
	public boolean updateCustomerLname(CustData data) throws SQLException 
	{ 
		return updateCustomer(SubTypeLname,data);	
	}

	public boolean updateCustomerPrimaryPhone(CustData data, CustData olddata) 
		throws SQLException, FileNotFoundException 
	{ 		
		System.out.println("updateCustomerPrimaryPhone - entry");
		
		// int cinfoid = 0;
		
		// slide these numbers
		slideCustomerField(DtypePhone,data);
				
		if (olddata.getPrimaryPhone() != null)
		{
			CustData tempdata = olddata.Replicate();
			tempdata.setPhone(olddata.getPrimaryPhone(),0); // note Primary -> Phone @ 0
			TransactionApp.custinfoDB.addCustInfoRecord
				(DtypePhone, SubTypePrimary, tempdata);
		}			
						
		System.out.println("updateCustomerPrimaryPhone - exit");
		return updateCustomer(SubTypePrimaryPhone,data);	
	}
	
	public boolean updateCustomerPrimaryEmail(CustData data, CustData olddata) 
		throws SQLException, FileNotFoundException  
	{ 	
		//int cinfoid = 0;
		
		// slide these email addrss
		slideCustomerField(DtypeEmail,data);
		
		if (olddata.getPrimaryEmail() != null)
		{
			CustData tempdata = olddata.Replicate();
			tempdata.setEmail(olddata.getPrimaryEmail(),0); // note Primary -> Email @ 0
			TransactionApp.custinfoDB.addCustInfoRecord
				(DtypeEmail, SubTypePrimary, tempdata);
		}			
		
		return updateCustomer(SubTypePrimaryEmail,data);			
	}
			
	public boolean updateCustomerBalanceDue(CustData data) 
		throws SQLException, FileNotFoundException 
	{ 
		//CustInfoDB TransactionApp.custinfoDB = new CustInfoDB();
		int key = TransactionApp.custinfoDB.findCustInfoRecord(DtypePersonal, SubTypeBalance, data);	
		if (key > 0)	
			TransactionApp.custinfoDB.deactivateCustInfoRecord(key);	
		boolean rvalue = true;
		rvalue = TransactionApp.custinfoDB.addCustInfoRecord(DtypePersonal,SubTypeBalance,data);
		return rvalue;	
	}
		
	public boolean updateCustomerDob(CustData data) 
		throws SQLException, FileNotFoundException 
	{ 
		//CustInfoDB TransactionApp.custinfoDB = new CustInfoDB();
		int key = TransactionApp.custinfoDB.findCustInfoRecord(DtypePersonal, SubTypeDob, data);	
		if (key > 0)	
			TransactionApp.custinfoDB.deactivateCustInfoRecord(key);	
		boolean rvalue = true;
		rvalue = TransactionApp.custinfoDB.addCustInfoRecord(DtypePersonal,SubTypeDob,data);	
		return rvalue;
	}
	
	public boolean updateCustomerAddress(CustData data,int index) 
		throws SQLException, FileNotFoundException 
	{ 
		//CustInfoDB TransactionApp.custinfoDB = new CustInfoDB();
		boolean rvalue = true;
		int which;
		int[] keys;
		 
		String[] addressLines;
		
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
		
		System.out.println("updateCustomerAddress() - find any/all addresses");
		keys = TransactionApp.custinfoDB.findCustInfoAddress(SubTypePrimary, data);
		for ( int key : keys )
		{
			if (key > 0)
			{
				System.out.println("updateCustomerAddress() - " +
					"found an address to deactivate at key: "+ key);		
				rvalue = TransactionApp.custinfoDB.deactivateCustInfoRecord(key);
			}
		}	

		System.out.println("updateCustomerAddress() - add addresses");
		addressLines = data.getAddr(index);
		if (addressLines[0] != null)
		{
			System.out.println("updateCustomerAddress() - add addresses");
			rvalue = TransactionApp.custinfoDB.addCustInfoAddress(SubTypePrimary, data);
		}
		
		return rvalue;		
	}
	
	public boolean updateCustomerPhone(CustData data, int index) 
		throws SQLException, FileNotFoundException 
	{ 
		//CustInfoDB TransactionApp.custinfoDB = new CustInfoDB();
		boolean rvalue = true;		
		int which;
		switch (index)
		{
			case 0:
				which = SubTypePrimary;
				updateCustomer(SubTypePrimaryPhone,data);
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
		int key = TransactionApp.custinfoDB.findCustInfoRecord(DtypePhone, which, data);	
		if (key > 0)	
			rvalue = TransactionApp.custinfoDB.deactivateCustInfoRecord(key);
		if (data.getPhone(index) != null)	
			return TransactionApp.custinfoDB.addCustInfoRecord(DtypePhone, which, data);
			
		return rvalue;							
	}
	
	public boolean updateCustomerEmail(CustData data,int index) 
		throws SQLException, FileNotFoundException 
	{ 
		//CustInfoDB TransactionApp.custinfoDB = new CustInfoDB();
		boolean rvalue = true;		
		int which;
		
		switch (index)
		{
			case 0:
				which = SubTypePrimary;
				updateCustomer(SubTypePrimaryEmail,data);
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
		int key = TransactionApp.custinfoDB.findCustInfoRecord(DtypeEmail, which, data);	
		if (key > 0)	
			rvalue = TransactionApp.custinfoDB.deactivateCustInfoRecord(key);	
		if (data.getEmail(index) != null)	
			return TransactionApp.custinfoDB.addCustInfoRecord(DtypeEmail, which, data);
			
		return rvalue;					
	}
								
}  // End of class

