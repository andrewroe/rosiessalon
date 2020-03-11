// import java.sql.*;
import java.util.Properties; 
import java.io.*; 

/** 	
	Class to define user I/F for Employee data
	This is not to have any SQL details - no awareness of which DB server is being used.
	Note: this will probably need a copy constructor that does a deep copy.
*/

public class EmpData
{ 
	protected int EmpIDData = 0;		// EmpID (PK) if known
	protected int UserIDData =0;		// UpdaterID (FK) - could be 0 on initial add
	protected String DateTimeData = null; // YYYYMMDDhhmmss
	protected String FnameData = null;	// currently max of 64 chars
	protected String LnameData = null;	// currently max of 64 chars
	protected String MinitData = null;	// 64 chars
	protected String DobData = null;	// yyyymmdd
	protected String Job = null;		// whatever
	protected double Salary = 0.0;
	protected double Commission = 0.0;
	protected String[] PhoneData = new String[5]; 
	protected String[] EmailData = new String[5]; 
	protected String[] AddrData = new String[5];	

/*
	All the set data field's methods.
*/

	public boolean setEmpID(int id)
	{
		EmpIDData = id;
		return true;
	}

	public boolean setUserID(int id)
	{
		UserIDData = id;
		return true;
	}

	public boolean setDateTime(String datetime)
	{
		String tempString = "YYYYMMDDhhmmss";
		if (datetime.length() != tempString.length())
		{
			System.out.println("setDateTime() - string size of input incorrect!");
			return false;
		}

		DateTimeData = datetime;
		return true;
	}

	public boolean setFname(String fname)
	{
		if (fname.length() > 32)
		{
			System.out.println("setFname() - string size of input too big!");
			return(false);
		}

		FnameData = fname;	
		return(true);
	}

	public boolean setLname(String lname)
	{
		if (lname.length() > 32)
		{
			System.out.println("setLname() - string size of input too big!");
			return false;
		}

		LnameData = lname;	
		return true;
	}

	public boolean setMinit(String minit)
	{
		MinitData = minit;	
		return true;
	}

	public boolean setDob(String dob)
	{
		String tempString = "yyyymmdd";
		if (dob.length() != tempString.length())
		{
			System.out.println("setDob() - string size of input incorrect!");
			return false;
		}

		DobData = dob;
		return true;
	}
	
	public boolean setJob(String job)
	{
		Job = job;
		return true;
	}
	
	public boolean setSalary(double salary)
	{
		Salary = salary;
		return true;
	}
	
		public boolean setCommission(double commission)
	{
		Commission = commission;
		return true;
	}


/*
	public boolean addPhone(String phone) 
	{
		System.out.println("addPhone() - entry, input = :" + phone + ":");

		String tempString = "ccaaalllnnnn"; // country code, area code, local exchange, number
		if (phone.length() != tempString.length())
		{
			System.out.println("addPhone() - string size of input incorrect!");
			return false;
		}

		// System.out.println("addPhone() - arraySize of PhoneDate = " + PhoneData.length);

		int i = 0;
		while ((i < PhoneData.length) && PhoneData[i] != null)
		{
			i++;
		}

		if (i > 4)
		{
			System.out.println("addPhone() - no more room for phone numbers!");
			return false;
		}
		else
		{
			PhoneData[i] = phone;
		}

		System.out.println("addPhone() - about to exit ");
		return true;
	}

	public boolean addEmail(String email)
	{
		if (email.length() > 32)
		{
			System.out.println("addEmail() - string size of input too big!");
			return false;
		}

		int i = 0;
		while ((i < EmailData.length) && EmailData[i] != null)
		{
			i++;
		}

		if (i > 4)
		{
			System.out.println("addEmail() - no more room for any email addresses!");
			return false;
		}
		else
		{
			EmailData[i] = email;
			return true;
		}
	}


	public boolean addAddressLine(String address)
	{
		if (address.length() > 32)
		{
			System.out.println("addAddressLine() - string size of input too big!");
			return false;
		}

		int i = 0;
		while ((i < AddrData.length) && AddrData[i] != null)
		{
			i++;
		}

		if (i > 4)
		{
			System.out.println("addAddressLine() - no more room for any address lines!");
			return false;
		}
		else
		{
			AddrData[i] = address;
			return true;
		}

	}

*/

	public boolean setPhone(String phone, int index) 
	{
		System.out.println("addPhone() - entry, input = :" + 
			phone + ": at index " + index);
			
		// country code, area code, local exchange, number
		String tempString = "ccaaalllnnnn"; 
		if (phone.length() > tempString.length())
		{
			System.out.println("addPhone() - string size of input incorrect!");
			return false;
		}
		
		if (index > 4)
		{
			System.out.println("addPhone() - index too big!");
			return false;
		}
		
		PhoneData[index] = phone;
		
		System.out.println("addPhone() - about to exit ");
		return true;
	}

	public boolean setEmail(String email, int index)
	{
		if (email.length() > 64)
		{
			System.out.println("addEmail() - string size of input too big!");
			return false;
		}

		if (index > 4)
		{
			System.out.println("addEmail() - index too big!");
			return false;
		}
		
		EmailData[index] = email;
		return true;	
	}


	public boolean setAddress(String address, int index)
	{
		if (address.length() > 64)
		{
			System.out.println("addAddress() - string size of input too big!");
			return false;
		}

		if (index > 4)
		{
			System.out.println("addAddress() - index too big!");
			return false;
		}
		
		AddrData[index] = address;
		return true;
	}


/*
	All the clear data field's methods.
*/

	public boolean clearEmpID()
	{
		EmpIDData = 0;
		return true;
	}

	public boolean clearUserID()
	{
		UserIDData = 0;
		return true;
	}

	public boolean clearDateTime()
	{
		DateTimeData = null;
		return true;
	}

	public boolean clearFname()
	{
		FnameData = null;	
		return true;
	}

	public boolean clearLname()
	{
		LnameData = null;	
		return true;
	}

	public boolean clearMinit()
	{
		MinitData = null;	
		return true;
	}
	
	public boolean clearJob()
	{
		Job = null;
		return true;
	}
	
	public boolean clearSalary()
	{
		Salary = 0.0;
		return true;
	}
	
		public boolean clearCommission()
	{
		Commission = 0.0;
		return true;
	}

	public boolean clearDob()
	{
		DobData = null;
		return true;
	}

/*
	public boolean clearPhone()
	{
		int arraySize = PhoneData.length;
		while (arraySize > 0)
		{
			PhoneData[--arraySize] = null;
		}

		return true;
	}

	public boolean clearEmail()
	{
		int arraySize = EmailData.length;
		while (arraySize > 0)
		{
			EmailData[--arraySize] = null;
		}

		return true;
	}

	public boolean clearAddress()
	{
		int arraySize = AddrData.length;
		while (arraySize > 0)
		{
			AddrData[--arraySize] = null;
		}

		return true;
	}
*/

	public boolean clearPhone()
	{
		for (int i = 0; i < 5; i++)
		{
			PhoneData[i] = null;
		}

		return true;
	}

	public boolean clearEmail()
	{
		for (int i = 0; i < 5; i++)
		{
			EmailData[i] = null;
		}
		
		return true;
	}

	public boolean clearAddress()
	{
		for (int i = 0; i < 5; i++)
		{
			AddrData[i] = null;
		}		
		
		return true;
	}

	public boolean clearAll()
	{
		if (!clearEmpID()) return(false);
		if (!clearUserID()) return(false);
		if (!clearDateTime()) return(false);
		if (!clearFname()) return(false);
		if (!clearLname()) return(false);
		if (!clearMinit()) return(false);
		if (!clearJob()) return(false);
		if (!clearSalary()) return(false);
		if (!clearCommission()) return(false);
		if (!clearDob()) return(false);
		if (!clearPhone()) return(false);
		if (!clearAddress()) return(false);

		return true;
	}



/*
	All of the get data field's methods.
*/

	public int getEmpID()
	{
		return(this.EmpIDData);
	}

	public int getUserID()
	{
		return(this.UserIDData);
	}

	public String getDateTime()
	{
		return(this.DateTimeData);
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
		
	public String getJob()
	{
		return Job;
	}
	
	public double getSalary()
	{
		return Salary;
	}
	
	public double getCommission()
	{
		return Commission;
	}

	public String getDob()
	{
		return(this.DobData);
	}

	public String getPhone(int index)
	{
		System.out.println("getPhone() - entry, index = " + index);
		if (index > 4)
		{
			System.out.println("getPhone() - can't read past the maximum phone index!");
			return(null);
		}
		else
		{
			System.out.println("getPhone() - exit, phone # = " + 
				PhoneData[index] + " at index = " + index);
			return(PhoneData[index]);
		}
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

	public String getAddressLine(int index)
	{
		if (index > 4)
		{
			System.out.println("getAddressLine() - can't read past the maximum Address index!");
			return(null);
		}
		else
		{
			return(this.EmailData[index]);
		}
	}
} 
