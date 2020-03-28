import java.util.Properties; 
import java.io.*; 

/** 	
	Class to define user I/F for Transaction data
	This is not to have any SQL details - no awareness of which DB server is being used.
*/

public class TransData
{ 
	protected int TransID = 0;		// TransID (PK) if known	
	protected int UserID =0;		// UserID (PK) if known
	protected int CustID = 0;		// CustID (PK) if known\
	protected String CreateTime = null; // YYYYMMDDhhmmss
	protected String UpdateTime = null; // YYYYMMDDhhmmss
	protected int Method = 0;	
	protected String CreditInfo = null;	// currently max of 64 chars
	protected double amount = 0.0;
	
	
/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{	
		TransID = 0;
		UserID = 0;
		CustID = 0;
		CreateTime = null; 
		UpdateTime = null;
		Method = 0;
		CreditInfo = null;
		amount = 0.0;
		
		return true;
	}


	
/*
	Replicate.
*/

	public TransData Replicate()
	{
		TransData replicated = new TransData();
		
		replicated.TransID = this.TransID;
		replicated.UserID = this.UserID;
		replicated.CustID = this.CustID;
		if (this.CreateTime != null)
			replicated.CreateTime = new String(this.CreateTime);
		if (this.UpdateTime != null)
			replicated.UpdateTime = new String(this.UpdateTime);	
		replicated.Method = this.Method;				
		if (this.CreditInfo != null)
			replicated.CreditInfo = new String(this.CreditInfo);		
		replicated.amount = this.amount;
						
		return replicated;
	}

} 
