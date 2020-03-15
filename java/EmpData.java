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
	protected String UserName = null;
	protected String Password = null;

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
		DateTimeData = datetime;
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

	public boolean setDob(String dob)
	{
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

	public boolean setPhone(String phone, int index) 
	{
		if (index > 4)
		{
			System.out.println("addPhone() - index too big!");
			return false;
		}
		
		PhoneData[index] = phone;
		
		// System.out.println("addPhone() - about to exit ");
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


	public boolean setAddr(String address, int index)
	{
		if (index > 4)
		{
			System.out.println("setAddr() - index too big!");
			return false;
		}
		
		AddrData[index] = address;
		return true;
	}
	
	public boolean setUname(String username)
	{
		UserName = username;
		return true;
	}

	public boolean setPassword(String password)
	{
		Password = password;
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

	public String getAddr(int index)
	{
		if (index > 4)
		{
			System.out.println("getAddr() - can't read past the maximum Address index!");
			return(null);
		}
		else
		{
			return(this.AddrData[index]);
		}
	}
	
	public String getUname()
	{
		return UserName;
	}

	public String getPassword()
	{
		return Password;
	}
	
/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{
		if (!setEmpID(0)) return(false);
		if (!setUserID(0)) return(false);
		if (!setDateTime(null)) return(false);
		if (!setFname(null)) return(false);
		if (!setLname(null)) return(false);
		if (!setMinit(null)) return(false);
		if (!setJob(null)) return(false);
		if (!setSalary(0.0)) return(false);
		if (!setCommission(0.0)) return(false);
		if (!setDob(null)) return(false);
		if (!setUname(null)) return(false);
		if (!setPassword(null)) return(false);
		
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
		
		if (!setAddr(null,0)) return(false);
		if (!setAddr(null,1)) return(false);
		if (!setAddr(null,2)) return(false);
		if (!setAddr(null,3)) return(false);
		if (!setAddr(null,4)) return(false);

		return true;
	}
	
} 
