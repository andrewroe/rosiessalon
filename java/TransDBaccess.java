import java.sql.*;
import java.util.Properties; 
import java.util.ArrayList;
import java.io.*;

/** 	
	Java Program to add/find/update customer in DB
*/

public class TransDBaccess extends RosiesSalon
{ 
	
	
/** 	
	addTransaction method
	Expects a completed TransData class as input, but TransID is 0
	and filled after transaction added to Transaction table.
	
	@param data TransData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns boolean of success and the TransID is updated else 0
*/
	public boolean addTransaction(TransData Tdata) throws SQLException 
	{
		int rows;
	
		String createtime = readfullDateTime();
		String updatetime = createtime;

		ResultSet result;
		boolean returnValue = false;
			
		System.out.println("addTransaction() - userid = " + Tdata.UserID);

		String sqlcmd = "INSERT INTO Transaction (UserID, CustID)";
		sqlcmd += ", CreateTime, UpdateTime)";
		sqlcmd += " VALUES (";
		sqlcmd += Tdata.UserID;
		sqlcmd += ", " + Tdata.CustID;
		sqlcmd += ", '" + createtime + "'";
		sqlcmd += ", '" + updatetime + "'";
		sqlcmd += " )";
				
		rows = doRowsCmd(sqlcmd);		
								
		if (rows == 0)
		{
			System.out.println("addTransaction() - returned 0 rows added!");
			return false;
		}
		
        Tdata.TransID = getLastAutoIncrement();
        System.out.println("Transaction PK = " + Tdata.TransID);
		
        if (Tdata.TransID > 0) 
        {
        	return true;
        }
        else 
        {
            throw new SQLException("Creating transaction failed, no TransID obtained.");
        }
        
	} // End of addTransaction()
 

	
/** 	
	addTransDetail method
	Expects a completed TransDetailData class as input, but TinfoID is 0
	and filled in after transaction detail added to TransactionDetail table.
	
	@param TDdata TransDetailData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns boolean of success and the TransID is updated else 0
*/
	public boolean addTransDetail(TransDetailData TDdata) throws SQLException 
	{
		int rows;
	
		String createtime = readfullDateTime();
		String updatetime = createtime;

		ResultSet result;
		boolean returnValue = false;
			
		System.out.println("addTransDetail() - UserID = " + 
			TDdata.UserID + " TransID = " + TDdata.TransID);

		String sqlcmd = "INSERT INTO TransactionDetails (UserID, TransID)";
		sqlcmd += ", CreateTime, UpdateTime";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", Nbr1Parm";
		sqlcmd += ", Nbr2Parm";
		sqlcmd += ", CharBig";
		sqlcmd += " )";
		sqlcmd += " VALUES (";
		sqlcmd += TDdata.UserID;
		sqlcmd += ", " + TDdata.TransID;
		sqlcmd += ", '" + createtime + "'";
		sqlcmd += ", '" + updatetime + "'";
		sqlcmd += ", " + TDdata.InfoType;
		sqlcmd += ", " + TDdata.InfoSubType;
		sqlcmd += ", " + TDdata.Validity;
		sqlcmd += ", " + TDdata.Nbr1Parm;
		sqlcmd += ", " + TDdata.Nbr2Parm;
		sqlcmd += ", '" + TDdata.CharBig + "'";
		sqlcmd += " )";
				
		rows = doRowsCmd(sqlcmd);		
								
		if (rows == 0)
		{
			System.out.println("addTransaction() - returned 0 rows added!");
			return false;
		}
		
        TDdata.TinfoID = getLastAutoIncrement();
        System.out.println("Transaction Detail PK = " + TDdata.TinfoID);
		
        if (TDdata.TinfoID > 0) 
        {
        	return true;
        }
        else 
        {
            throw new SQLException("Creating transaction detail failed, no TinfoID obtained.");
        }
        
	} // End of addTransaction()
 


/** 	
	deactivateTransaction method
	Expects ... fill in
	
	@param tinfoid CinfoID 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateTransaction(int transid) 
		throws SQLException, FileNotFoundException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
		
		//CustDBaccess CustInfoDBaccess = new CustDBaccess();
		
								
		sqlcmd = "UPDATE Transaction SET Method = " + -1;
		sqlcmd += " WHERE CinfoID = " + transid; 
				
		rows = doRowsCmd(sqlcmd);
			
		if (rows > 0)	
			return true;
		else
			return false;
						
	} // End of deactivateTransaction()



/** 	
	deactivateTransDetail method
	Expects ... fill in
	
	@param tinfoid CinfoID 
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean true for success, else false
*/
	public boolean deactivateTransDetail(int tinfoid) 
		throws SQLException, FileNotFoundException 
	{
		int rows = 0;
		String updatetime = readfullDateTime();
		String sqlcmd;
								
		sqlcmd = "UPDATE TransactionDetail SET Validity = " + 0;
		sqlcmd += " WHERE TinfoID = " + tinfoid; 
				
		rows = doRowsCmd(sqlcmd);
			
		if (rows > 0)	
			return true;
		else
			return false;
						
	} // End of deactivateTransDetail()



 
/** 	
	fetchAllProductData method..
	
	@throws SQLException if there is an error with some SQL command
	@return Returns an ArrayList of ProductData that applies,
	 
*/
	public ArrayList<ProductData> fetchAllProductData() throws SQLException 
	{
		ArrayList<ProductData> productsList = new ArrayList<>();
		ProductData Pdata;
		
		ResultSet result;
		
		String sqlcmd = "SELECT ProdID, UserID";
		sqlcmd += ", CreateTime";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", Pname";
		sqlcmd += ", Price";
		sqlcmd += " FROM Product"; 
								
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllProductData() - " +
				"could not find any Product Details!");
			//return null;
			return productsList;
		}
					 	
		while (result.next())
		{
			Pdata = new ProductData();
				
			Pdata.ProdID = result.getInt("ProdID");
			Pdata.UserID = result.getInt("UserID");
			Pdata.CreateTime = result.getString("CreateTime");
			Pdata.UpdateTime = result.getString("UpdateTime");
			Pdata.Pname = result.getString("Pname");
			Pdata.Price = result.getDouble("Price");
						
			productsList.add(Pdata);
		}		 
		 
		return productsList;
	} // End of fetchAllProductDetails()



 
/** 	
	fetchAllServiceData method
	.
	@throws SQLException if there is an error with some SQL command
	@return Returns an ArrayList of Service Data that applies,
	 
*/
	public ArrayList<ServiceData> fetchAllServiceData() throws SQLException 
	{
		ArrayList<ServiceData> servicesList = new ArrayList<>();
		ServiceData Sdata;
		
		ResultSet result;
		
		String sqlcmd = "SELECT ServID, UserID";
		sqlcmd += ", CreateTime";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", Sname";
		sqlcmd += ", Price";
		sqlcmd += " FROM Service"; 
								
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllServiceData() - " +
				"could not find any Service Details!");
			//return null;
			return servicesList;
		}
					 	
		while (result.next())
		{
			Sdata = new ServiceData();
				
			Sdata.ServID = result.getInt("ServID");
			Sdata.UserID = result.getInt("UserID");
			Sdata.CreateTime = result.getString("CreateTime");
			Sdata.UpdateTime = result.getString("UpdateTime");
			Sdata.Sname = result.getString("Sname");
			Sdata.Price = result.getDouble("Price");
						
			servicesList.add(Sdata);
		}		 
		 
		return servicesList;
	} // End of fetchAllServiceDetails()



 
/** 	
	fetchAllTransDetails method
	Expects an TransData class as input, which will have ...
	
	@param data TransData object 
	@throws SQLException if there is an error with some SQL command
	@return Returns an ArrayList of TransDetailsData that applies,
	 
*/
	public ArrayList<TransDetailData> fetchAllTransDetails(TransData Tdata) throws SQLException 
	{
		ArrayList<TransDetailData> detailsList = null;
		TransDetailData TDdata = null;
		ResultSet result;
		int infotype;
		int infosubtype;
		int validity;
		int nbr1parm;
		double nbr2parm;
		String charparm;
				
		if (Tdata.TransID == 0) 
		{
			throw new SQLException
				("Attempting fetch TransDetails and TransID equals 0!"); 
		}
		
		String sqlcmd = "SELECT TinfoID, UserID, TransID ";
		sqlcmd += ", InfoType";
		sqlcmd += ", InfoSubType";
		sqlcmd += ", Validity";
		sqlcmd += ", Nbr1Parm";
		sqlcmd += ", Nbr2Parm";
		sqlcmd += ", CharBig"; 
		sqlcmd += " FROM TransactionDetails"; 
		sqlcmd += " where TransID = " + Tdata.TransID; 
								
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("fetchAllTransDetails() - " +
				"could not find any Transaction Details!");
			return null;
		}
		
		detailsList = new ArrayList<TransDetailData>();
					 	
		while (result.next())
		{
			TDdata = new TransDetailData();
				
			TDdata.TinfoID = result.getInt("TinfoID");
			TDdata.TinfoID = result.getInt("UserID");
			TDdata.TransID = result.getInt("TransID");
			TDdata.UpdateTime = result.getString("UpdateTime");
			TDdata.InfoType = result.getInt("InfoType");
			TDdata.InfoSubType = result.getInt("InfoSubType");
			TDdata.Nbr1Parm = result.getInt("Nbr1Parm");
			TDdata.Nbr2Parm = result.getDouble("Nbr2Parm");
			TDdata.CharBig = result.getString("CharBig");
						
			detailsList.add(TDdata);
		}		 
		 
		return detailsList;
	} // End of fetchAllTransDetails()


  
 /** 	
	findTransaction method
	Searches for a Transaction record based on TransID 
	available in the TransData object.
	
	@param Tdata TransData object
	@throws SQLException if there is an error with some SQL command 
	@return Returns boolean true if record found
	and also updates the supplied TransData object with values.
*/
	public boolean findTransaction(TransData Tdata) 
				throws SQLException 
	{		
		ResultSet result;

		if (Tdata.TransID == 0)
		{
			throw new SQLException("Attempting search of DB for " + 
				"Transaction and no TransID supplied");
		}
				
		String sqlcmd = "SELECT UserID, CustID";
		sqlcmd += ", CreateTime";
		sqlcmd += ", UpdateTime";
		sqlcmd += ", Method";
		sqlcmd += ", CreditInfo";
		sqlcmd += ", amount";
		sqlcmd += " FROM Customer where TransID = " + Tdata.TransID;
				
		result = doCmd(sqlcmd);
		
		if (result == null)
		{ 
			System.out.println("Can not find Transaction record");
			return false;
		}
			 	
		Tdata.UserID = result.getInt("UserID");
		Tdata.CustID = result.getInt("CustID");
		Tdata.CreateTime = result.getString("CreateTime");
		Tdata.UpdateTime = result.getString("UpdateTime");
		Tdata.Method = result.getInt("Method");
		Tdata.CreditInfo = result.getString("CreditInfo");
		Tdata.amount = result.getDouble("amount");

		if (Tdata.Method == -1)
		{ 
			System.out.println("found Transaction record has been deactivated!");
			return false;
		}
		
		return true;				
	}  // End of findTransaction
									
}  // End of class

