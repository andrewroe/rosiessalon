import java.util.Properties; 
import java.io.*; 

/** 	
	Class to define user I/F for Customer data
	This is not to have any SQL details - no awareness of which DB server is being used.
*/

public class ServiceData
{ 
	protected int ServID = 0;		// CustID (PK) if known
	protected int UserID =0;		// UserID (PK) if known
	protected String CreateTime = null; // YYYYMMDDhhmmss
	protected String UpdateTime = null; // YYYYMMDDhhmmss
	protected String Sname = null;	
	protected double Price = 0.0;


/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{
		ServID = 0;
		UserID = 0; 
		CreateTime = null; 
		UpdateTime = null;
		Sname = null;
		Price = 0.0;
		
		return true;
	}

	
/*
	Replicate.
*/

	public ServiceData Replicate()
	{
		ServiceData replicated = new ServiceData();
		
		replicated.ServID = this.ServID;
		replicated.UserID = this.UserID;
		if (this.CreateTime != null)
			replicated.CreateTime = new String(this.CreateTime);
		if (this.UpdateTime != null)
			replicated.UpdateTime = new String(this.UpdateTime);			
		if (this.Sname != null)
			replicated.Sname = new String(this.Sname);		
		replicated.Price = this.Price;
				
		return replicated;
	}
		
} 
