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


/*
	All of the equals data field's methods.
*/

	public boolean equalsEmpID(EmpData other)
	{
		return this.EmpIDData == other.EmpIDData;
	}

	public boolean equalsUserID(CustData other)
	{
		return this.UserIDData == other.UserIDData;
	}

	public boolean equalsCreateTime(EmpData other)
	{
		if ( this.DateTimeData != null)
		{
			if ( other.DateTimeData != null
				&& DateTimeData.equals(other.DateTimeData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.DateTimeData == null)
				return true;
			else
				return false;
		} 
	}

	public boolean equalsUpdateTime(EmpData other)
	{
		if ( this.DateTimeData != null)
		{
			if ( other.DateTimeData != null
				&& DateTimeData.equals(other.DateTimeData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.DateTimeData == null)
				return true;
			else
				return false;
		} 
	}

	public boolean equalsFname(EmpData other)
	{
		if ( this.FnameData != null)
		{
			if ( other.FnameData != null
				&& FnameData.equals(other.FnameData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.FnameData == null)
				return true;
			else
				return false;
		} 		
	}

	public boolean equalsLname(EmpData other)
	{
		if ( this.LnameData != null)
		{
			if ( other.LnameData != null
				&& LnameData.equals(other.LnameData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.LnameData == null)
				return true;
			else
				return false;
		} 				
	}

	public boolean equalsMinit(EmpData other)
	{
		if ( this.MinitData != null)
		{
			if ( other.MinitData != null
				&& MinitData.equals(other.MinitData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.MinitData == null)
				return true;
			else
				return false;
		} 				
	}


	public boolean equalsJob(EmpData other)
	{
		if ( this.Job != null)
		{
			if ( other.Job != null
				&& Job.equals(other.Job) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.Job == null)
				return true;
			else
				return false;
		} 				
	}


	public boolean equalsPrimaryPhone(EmpData other)
	{
		if ( this.PhoneData[0] != null)
		{
			if ( other.PhoneData[0] != null
				&& PhoneData[0].equals(other.PhoneData[0]) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.PhoneData[0] == null)
				return true;
			else
				return false;
		} 				
	}
	
	public boolean equalsPrimaryEmail(EmpData other)
	{
		if ( this.EmailData[0] != null)
		{
			if ( other.EmailData[0] != null
				&& EmailData[0].equals(other.EmailData[0]) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.EmailData[0] == null)
				return true;
			else
				return false;
		} 				
	}
				
	public boolean equalsSalary(EmpData other)
	{
		return this.Salary == other.Salary;
	}
				
	public boolean equalsCommission(EmpData other)
	{
		return this.Commission == other.Commission;
	}
	
	public boolean equalsDob(EmpData other)
	{
		if ( this.DobData != null)
		{
			if ( other.DobData != null
				&& DobData.equals(other.DobData) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.DobData == null)
				return true;
			else
				return false;
		} 				
		
	}

	public boolean equalsPhone(EmpData other,int index)
	{
		if (index > 4)
		{
			System.out.println("equalsPhone() - can't read past the maximum index!");
			return true;  // !!!!!
		}
		
		if ( this.PhoneData[index] != null)
		{
			if ( other.PhoneData[index] != null
				&& PhoneData[index].equals(other.PhoneData[index]) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.PhoneData[index] == null)
				return true;
			else
				return false;
		} 				
	}

	public boolean equalsEmail(EmpData other,int index)
	{
		if (index > 4)
		{
			System.out.println("equalsEmail() - can't read past the maximum index!");
			return true;
		}
		
		if ( this.EmailData[index] != null)
		{
			if ( other.EmailData[index] != null
				&& EmailData[index].equals(other.EmailData[index]) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.EmailData[index] == null)
				return true;
			else
				return false;
		} 	
	}

	public boolean equalsAddr(EmpData other,int index)
	{		
		if (index > 0)
		{
			System.out.println("equalsAddr() - can't read past the maximum index!");
			return true;
		}
		
		for (int row = 0; row < 5; row++)
		{
			if (!equalsAddrRow(other, row))
				return false;	
		}
		
		return true;		
	}

	public boolean equalsAddrRow(EmpData other, int row)
	{	
		if ( this.AddrData[row] != null)
		{
			if ( other.AddrData[row] != null
				&& AddrData[row].equals(other.AddrData[row]) )
				return true;
			else
				return false;		
		}
		else
		{
			if (other.AddrData[row] == null)
				return true;
			else
				return false;
		} 	
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
				//address[col] = this.AddrData[row][col];
				address[col] = this.AddrData[col];
			return address;
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
	Replicate Constructor.
*/

	public EmpData Replicate()
	{
		EmpData replicated = new EmpData();
		
		replicated.EmpIDData = this.EmpIDData;
		replicated.UserIDData = this.UserIDData;
		if (this.DateTimeData != null)
			replicated.DateTimeData = new String(this.DateTimeData);
		if (this.FnameData != null)
			replicated.FnameData = new String(this.FnameData);
		if (this.MinitData != null)
			replicated.MinitData = new String(this.MinitData);
		if (this.LnameData != null)
			replicated.LnameData = new String(this.LnameData);
		if (this.DobData != null)
			replicated.DobData = new String(this.DobData);
		if (this.Job != null)
			replicated.Job = new String(this.Job);
		replicated.Salary = this.Salary;
		replicated.Commission = this.Commission;
		
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
			
		if (this.AddrData[0] != null)
			replicated.AddrData[0] = new String(this.AddrData[0]);
		if (this.AddrData[1] != null)
			replicated.AddrData[1] = new String(this.AddrData[1]);
		if (this.AddrData[2] != null)
			replicated.AddrData[2] = new String(this.AddrData[2]);
		if (this.AddrData[3] != null)
			replicated.AddrData[3] = new String(this.AddrData[3]);
		if (this.AddrData[4] != null)
			replicated.AddrData[4] = new String(this.AddrData[4]);
			
		if (this.UserName != null)
			replicated.UserName = new String(this.UserName);
		if (this.Password != null)
			replicated.Password = new String(this.Password);
		
		return replicated;
	}



	
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

	
} 
