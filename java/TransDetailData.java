import java.util.Properties; 
import java.io.*; 
import java.util.ArrayList;

/** 	
	Class to define user I/F for Customer data
	This is not to have any SQL details - no awareness of which DB server is being used.
*/

public class TransDetailData
{ 
	protected int TinfoID = 0;		// TinfoID (PK) if known
	protected int UserID =0;		// UserID (PK) if known
	protected int TransID = 0;		// TransID (PK) if known
	protected String UpdateTime = null; // YYYYMMDDhhmmss
	protected int InfoType = 0;		
	protected int InfoSubType = 0;
	protected int Validity = 0;
	protected int Nbr1Parm = 0;
	protected double Nbr2Parm = 0.0;
	protected String CharBig = null; 

/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{
		TinfoID = 0;
		UserID = 0;
		TransID = 0;
		UpdateTime = null;
		Nbr1Parm = 0;
		Nbr2Parm = 0.0;			
		CharBig = null;		
		
		return true;
	}

		
/*
	Replicate.
*/

	public TransDetailData Replicate()
	{
		TransDetailData replicated = new TransDetailData();
		
		replicated.TinfoID = this.TinfoID;
		replicated.UserID = this.UserID;
		replicated.TransID = this.TransID;
		if (this.UpdateTime != null)
			replicated.UpdateTime = new String(this.UpdateTime);
		replicated.Nbr1Parm = this.Nbr1Parm;
		replicated.Nbr2Parm = this.Nbr2Parm;			
		if (this.CharBig != null)
			replicated.CharBig = new String(this.CharBig);		
				
		return replicated;
	}

			
} 
