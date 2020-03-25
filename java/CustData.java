// import java.sql.*;
import java.util.Properties; 
import java.io.*; 

/** 	
	Class to define user I/F for Customer data
	This is not to have any SQL details - no awareness of which DB server is being used.
*/

public class CustData
{ 
	protected int CustIDData = 0;		// CustID (PK) if known
	protected int UserIDData =0;		// UpdaterID (FK) - could be 0 on initial add
	protected String CreateTimeData = null; // YYYYMMDDhhmmss
	protected String UpdateTimeData = null; // YYYYMMDDhhmmss
	protected String FnameData = null;	// currently max of 64 chars
	protected String LnameData = null;	// currently max of 64 chars
	protected String MinitData = null;	// 64 chars
	protected String PrimaryPhone = null;
	protected String PrimaryEmail = null;
	protected String DobData = null;	// yyyymmdd
	protected double BalanceDue = 0.0;
	protected String[] PhoneData = new String[5]; 
	protected String[] EmailData = new String[5]; 
	protected String[][] AddrData = new String[5][5];	
	

/*
	Replicate Constructor.
*/

	public CustData Replicate()
	{
		int i, j;
		CustData replicated = new CustData();
		
		replicated.CustIDData = this.CustIDData;
		replicated.UserIDData = this.UserIDData;
		if (this.CreateTimeData != null)
			replicated.CreateTimeData = new String(this.CreateTimeData);
		if (this.UpdateTimeData != null)
			replicated.UpdateTimeData = new String(this.UpdateTimeData);		
		if (this.FnameData != null)
			replicated.FnameData = new String(this.FnameData);
		if (this.MinitData != null)
			replicated.MinitData = new String(this.MinitData);
		if (this.LnameData != null)
			replicated.LnameData = new String(this.LnameData);
		if (this.PrimaryPhone != null)
			replicated.PrimaryPhone = new String(this.PrimaryPhone);
		if (this.PrimaryEmail != null)
			replicated.PrimaryEmail = new String(this.PrimaryEmail);
		if (this.DobData != null)
			replicated.DobData = new String(this.DobData);
		replicated.BalanceDue = this.BalanceDue;
		
		
		if (this.PhoneData[0] != null)
			replicated.PhoneData[0] = new String(this.PhoneData[0]);
			
		if (this.PhoneData[1] != null)
			replicated.PhoneData[1] = new String(this.PhoneData[1]);
		
		if (this.PhoneData[2] != null)
			replicated.PhoneData[2] = new String(this.PhoneData[2]);
		
		if (this.PhoneData[3] != null)
			replicated.PhoneData[3] = new String(this.PhoneData[3]);
		
		if (this.PhoneData[4] != null)
			replicated.PhoneData[4] = new String(this.PhoneData[4]);
			
		if (this.EmailData[0] != null)
			replicated.EmailData[0] = new String(this.EmailData[0]);
			
		if (this.EmailData[1] != null)
			replicated.EmailData[1] = new String(this.EmailData[1]);
			
		if (this.EmailData[2] != null)
			replicated.EmailData[2] = new String(this.EmailData[2]);
			
		if (this.EmailData[3] != null)
			replicated.EmailData[3] = new String(this.EmailData[3]);
			
		if (this.EmailData[4] != null)
			replicated.EmailData[4] = new String(this.EmailData[4]);
			
		for (i = 0; i < 5; i++)
		{
			for (j = 0; j < 5; j++)
			{
				if ( (this.AddrData[i][j] != null) && 
					(!this.AddrData[i][j].equals("")) )
					replicated.AddrData[i][j] = new String(this.AddrData[i][j]);
				else
				{
					replicated.AddrData[i][j] = null;			
				}
			}
		}
		
		return replicated;
	}


/*
	All the set data field's methods.
*/

	public boolean setCustID(int id)
	{
		CustIDData = id;
		return true;
	}

	public boolean setUserID(int id)
	{
		UserIDData = id;
		return true;
	}

	public boolean setCreateTime(String datetime)
	{
		CreateTimeData = datetime;
		return true;
	}

	public boolean setUpdateTime(String datetime)
	{
		UpdateTimeData = datetime;
		return true;
	}

	public boolean setFname(String fname)
	{
		FnameData = fname;	
		return(true);
	}

	public boolean setLname(String lname)
	{
		LnameData = lname;	
		return true;
	}

	public boolean setMinit(String minit)
	{
		MinitData = minit;	
		return true;
	}

	public boolean setPrimaryPhone(String phone)
	{
		PrimaryPhone = phone;
		return true;
	}

	public boolean setPrimaryEmail(String email)
	{
		PrimaryEmail = email;
		return true;
	}
		
	public boolean setDob(String dob)
	{
		DobData = dob;
		return true;
	}
		
	public boolean setBalanceDue(double balance)
	{
		BalanceDue = balance;
		return true;
	}
	

	public boolean setPhone(String phone, int index) 
	{
		if (index > 4)
		{
			System.out.println("addPhone() - index too big!");
			return false;
		}
		
		PhoneData[index] = phone;
		
		System.out.printf("addPhone(%s,%d) - about to exit", phone, index);
		return true;
	}

	public boolean setEmail(String email, int index)
	{
		if (index > 4)
		{
			System.out.println("setEmail() - index too big!");
			return false;
		}
		
		EmailData[index] = email;
		return true;	
	}

	public boolean setAddr(String[] addressArray, int row)
	{
		if (row > 4)
		{
			System.out.println("setAddr() - index too big!");
			return false;
		}
		
		for (int i = 0; i < 5; i++)
			AddrData[row][i] = addressArray[i];
		return true;
	}


/*
	All of the get data field's methods.
*/

	public int getCustID()
	{
		return(this.CustIDData);
	}

	public int getUserID()
	{
		return(this.UserIDData);
	}

	public String getCreateTime()
	{
		return(this.CreateTimeData);
	}

	public String getUpdateTime()
	{
		return(this.UpdateTimeData);
	}

	public String getFname()
	{
		return(this.FnameData);
	}

	public String getLname()
	{
		return(this.LnameData);
	}

	public String getMinit()
	{
		return(this.MinitData);
	}

	public String getPrimaryPhone()
	{
		return this.PrimaryPhone;
	}
	
	public String getPrimaryEmail()
	{
		return this.PrimaryEmail;
	}
				
	public double getBalanceDue()
	{
		return BalanceDue;
	}
	
	public String getDob()
	{
		return(this.DobData);
	}

	public String getPhone(int index)
	{
		// System.out.println("getPhone() - entry, index = " + index);
		if (index > 4)
		{
			System.out.println("getPhone() - can't read past the maximum phone index!");
			return(null);
		}
		
		//System.out.println("getPhone() - exit, phone # = " + 
		//		PhoneData[index] + " at index = " + index);
		return(PhoneData[index]);
		
	}

	public String getEmail(int index)
	{
		if (index > 4)
		{
			System.out.println("getEmail() - can't read past the maximum Email index!");
			return(null);
		}
		else
		{
			return(this.EmailData[index]);
		}
	}

	public String[] getAddr(int row)
	{
		String[] address = new String[5];
		if (row > 4)
		{
			System.out.println("getAddr() - can't read past the maximum Address index!");
			return(null);
		}
		else
		{
			for (int col = 0; col < 5; col++)
				address[col] = this.AddrData[row][col];
			return address;
		}	
	}
	
/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{
		if (!setCustID(0)) return(false);
		if (!setUserID(0)) return(false);
		if (!setCreateTime(null)) return(false);
		if (!setUpdateTime(null)) return(false);
		if (!setFname(null)) return(false);
		if (!setLname(null)) return(false);
		if (!setMinit(null)) return(false);
		if (!setPrimaryPhone(null)) return(false);
		if (!setPrimaryEmail(null)) return(false);
		if (!setBalanceDue(0.0)) return(false);
		if (!setDob(null)) return(false);
		
		if (!setEmail(null,0)) return(false);
		if (!setEmail(null,1)) return(false);
		if (!setEmail(null,2)) return(false);
		if (!setEmail(null,3)) return(false);
		if (!setEmail(null,4)) return(false);
		
		if (!setPhone(null,0)) return(false);
		if (!setPhone(null,1)) return(false);
		if (!setPhone(null,2)) return(false);
		if (!setPhone(null,3)) return(false);
		if (!setPhone(null,4)) return(false);
		
		int row;
		String[] stringArray = {null, null, null, null, null};
		for (row = 0; row < 5; row++)
		{
			setAddr(stringArray,row);
		}

		return true;
	}
	
} 
