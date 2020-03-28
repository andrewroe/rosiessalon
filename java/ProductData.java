import java.util.Properties; 
import java.io.*; 

/** 	
	Class to define user I/F for Customer data
	This is not to have any SQL details - no awareness of which DB server is being used.
*/

public class ProductData
{ 
	protected int ProdID = 0;		// CustID (PK) if known
	protected int UserID =0;		// UserID (PK) if known
	protected String CreateTime = null; // YYYYMMDDhhmmss
	protected String UpdateTime = null; // YYYYMMDDhhmmss
	protected String Pname = null;	
	protected double Price = 0.0;


/*
	All the clear data field's methods.
*/

	public boolean clearAll()
	{
		ProdID = 0;
		UserID = 0; 
		CreateTime = null; 
		UpdateTime = null;
		Pname = null;
		Price = 0.0;
		
		return true;
	}

	
/*
	Replicate.
*/

	public ProductData Replicate()
	{
		ProductData replicated = new ProductData();
		
		replicated.ProdID = this.ProdID;
		replicated.UserID = this.UserID;
		if (this.CreateTime != null)
			replicated.CreateTime = new String(this.CreateTime);
		if (this.UpdateTime != null)
			replicated.UpdateTime = new String(this.UpdateTime);			
		if (this.Pname != null)
			replicated.Pname = new String(this.Pname);		
		replicated.Price = this.Price;
				
		return replicated;
	}
		
} 
