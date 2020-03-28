import java.sql.*;
import java.util.Properties;
import java.io.*;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/** 	
	This abstract class will be a base for many of the RosiesSalon DB handling.
*/

public abstract class RosiesSalon 
{ 
	// public final String DBbaseURL = "jdbc:mysql://localhost:3306/";
	public String DBname = null;
	public Connection dbConnection = null;
	
	final int DtypeAdmin = 0;
	final int DtypePhone = 1;
	final int DtypeEmail = 2;
	final int DtypeAddress = 3;
	final int DtypeTimeCard = 4;
	final int DtypePersonal = 5;

	final int SubTypeUserName = 0;
	final int SubTypePassword = 1;
		
	final int SubTypePrimary = 1;
	final int SubTypeSecondary = 2;
	final int SubTypeTertiary = 3;
	final int SubTypeFourth = 4;
	final int SubTypeFifth = 5;
	
	final int SubTypeAddr1 = 1;
	final int SubTypeAddr2 = 2;
	final int SubTypeAddr3 = 3;
	final int SubTypeAddr4 = 4;
	final int SubTypeAddr5 = 5;
	
	final int SubTypeDayIn = 1;
	final int SubTypeDayOut = 2;
	
	final int SubTypeFname = 1;
	final int SubTypeMinit = 2;
	final int SubTypeLname = 3;
	final int SubTypeJob = 4;
	final int SubTypeSalary = 5;
	final int SubTypeCommission = 6;
	final int SubTypeDob = 7;
	final int SubTypePrimaryPhone = 8;
	final int SubTypePrimaryEmail = 9;
	final int SubTypeBalance = 10;
	
	final int ValidInfo1 = 1;
	final int ValidInfo2 = 2;
	final int ValidInfo3 = 4;
					
	
/**
	RosiesSalon method
	This is a constructor method
*/
	// @Override
	public RosiesSalon()
	{
    	//this.DBname = null;
		this.dbConnection = null;
	}

/** 	
	ConnectionToDB method
	Establishes a connection to a Database.
	This could be expanded to allow for multiple DB connections,
	but for now only ONE allowed.
	
	@param credentials has jdbc URL and dbname, SQL user, SQL password
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean, true if connection successful, else false.
*/
	public boolean ConnectToDB(String credentials) 
			throws SQLException, FileNotFoundException 
	{
		File file = new File(credentials);
		Scanner inputFile = new Scanner(file);
		String line = inputFile.nextLine();
		String[] tokens = line.split(",");
		String url = tokens[0]; 
		String user = tokens[1]; 
		String password = tokens[2];
		Properties info = new Properties();
		
		System.out.println("ConnectToDB - DB URL = " + url);

		info.put("user", user);
		info.put("password", password);

		if (dbConnection != null) 
		{
			throw new SQLException("Trying to open a connection to DB when already open");
		}

		try 
		{
			// dbConnection = null;
			dbConnection = DriverManager.getConnection(url, info);
			if (dbConnection != null) 
			{
				System.out.println("Successfully connected to MySQL database " + url);
				DBname = url;
				return(true);
			} 
			else
			{
				System.out.println("Failed to connect to MySQL database " + url);
				DBname = null;
				return(false);
			} 
		} 	
		catch (SQLException ex) 
		{ 
			DBname = null;
			System.out.println("An error occurred while connecting MySQL databse " +
					url);
			ex.printStackTrace();
			return(false);
		} 
	} 

/** 	
	method DisconnectFromDB
	Close connection to Database.
	
	@throws SQLException if there is an error with some SQL command
	@return Returns a boolean, true if connection successful, else false.
*/
	public boolean DisconnectFromDB() throws SQLException
	{
		System.out.println("DisconnectFromDB () ");
		
		if (dbConnection == null) 
		{
			throw new SQLException("Trying to close a connection to DB " +
				"when Not already opened");
		}
		try
		{
			dbConnection.close();
			dbConnection = null;
			DBname = null;
			return(true);
		}
		catch (SQLException ex) 
		{ 
			System.out.println("An error occurred when closing connection" + 
				" to MySQL databse " + DBname);
			return(false);
		}
	} 

/** 	
	method DBconnected
	Checks if connected to DB
	
	@return Returns a boolean, true if connection successful, else false.
*/
	public boolean DBconnected()
	{
		//System.out.println("In the super class RosiesSalon and dbConnection = " + 
		//		this.dbConnection);
		if (this.dbConnection == null) 
		{
			return false;
		}
		else
		{
			return(true);
		}
	} 
	
	
/** 	
	method readClock
	reads local time
	
	@return Returns a Sting hhmmss
*/
	public String readClock()
	{
		LocalDateTime time = LocalDateTime.now();
		String rvalue;
		
		rvalue = String.format("%02d%02d%02d", 
			time.getHour(),time.getMinute(),time.getSecond());
		// System.out.println("local clock value is: " + rvalue);	  
		return(rvalue);	
	} 
	
/** 	
	method readDate
	reads local date
	
	@return Returns a Sting yyyymmdd
*/
	public String readDate()
	{
		// LocalDate time = LocalDate.now();
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		String rvalue;
		
		cal.setTime(today);
			
			
		// rvalue = time.toLocalDate();
		//rvalue = String.format("%4d%2d%2d",
		//	time.getYear(), time.getMonth(), time.getDay());
		
		rvalue = String.format("%4d%02d%02d",
			cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), 
			cal.get(Calendar.DAY_OF_MONTH));
			
		// rvalue = String.format("%8", time);
		// System.out.println("local date is: " + rvalue);
		return(rvalue);	
	} 
	
/** 	
	method readfullDateTime
	reads local datetime in my format
	
	@return Returns a Sting YYYYMMDDhhmmss
*/
	public String readfullDateTime()
	{
		// LocalDateTime time = LocalDateTime.now();
		String rvalue;
			
		rvalue = this.readDate() + this.readClock(); 
		return(rvalue);	
	} 
		
	
/** 	
	doCmd method
	Executes SQL command - does the detail processing
	
	@param sqlcmd a string object containing SQL Command
	@throws SQLException if there is an error with some SQL command
	@return Returns a reference to Results or null
*/	
	
	public ResultSet doCmd(String sqlcmd) throws SQLException 
	{ 
		ResultSet result = null;
		
		if (!DBconnected()) 
		{
			throw new SQLException("Not connected to Database!");
		}
		
		Statement statement = dbConnection.createStatement();
		
					
		System.out.println("SQL statement:");
		System.out.println(sqlcmd);
		
				  
		try 
		{
			result = statement.executeQuery(sqlcmd);
			 
			return result;
		}
		
		catch (SQLException ex) 
		{ 
			System.out.println("An error occurred with SQL command");
			ex.printStackTrace();
			return result;
		} // End of outer try-catch		
		
	} // End of doCmd()
 		
	
/** 	
	doRowsCmd method
	Executes SQL command - does the detail processing
	
	@param sqlcmd a string object containing SQL Command
	@throws SQLException if there is an error with some SQL command
	@return Returns an integer of number of rows inserted
*/	
	
	public int doRowsCmd(String sqlcmd) throws SQLException 
	{ 
		
		if (!DBconnected()) 
		{
			throw new SQLException("Not connected to Database!");
		}
		
		Statement statement = dbConnection.createStatement();
					
		System.out.println("SQL statement:");
		System.out.println(sqlcmd);
									  
		try 
		{
			return(statement.executeUpdate(sqlcmd));
		}
		
		catch (SQLException ex) 
		{ 
			System.out.println("An error occurred with SQL command");
			ex.printStackTrace();
			return 0;
		} // End of outer try-catch		
		
	} // End of doRowsCmd()
 		

	
/** 	
	getLastAutoIncrement method
	Executes SQL command - does the detail processing
	
	@param sqlcmd a string object containing SQL Command
	@throws SQLException if there is an error with some SQL command
	@return Returns an integer of number of rows inserted
*/	
	
	public int getLastAutoIncrement() throws SQLException 
	{ 
		int rvalue = 0;
		
		if (!DBconnected()) 
		{
			throw new SQLException("Not connected to Database!");
		}
		
		Statement statement = dbConnection.createStatement();
					
		System.out.println("get last Auto Increment value");
									  
		try 
		{
			ResultSet generatedKeys = statement.getGeneratedKeys();
        	if (generatedKeys.next()) 
        	{
        		long id = generatedKeys.getLong(1);
        		rvalue = (int)id;
        		System.out.println("auto Key = " + id);
        	}
        	else 
        	{
            	throw new SQLException("Creating transaction failed, no TransID obtained.");
        	}
			
			return rvalue; 
		}
		
		catch (SQLException ex) 
		{ 
			System.out.println("An error occurred with SQL command");
			ex.printStackTrace();
			return 0;
		} // End of outer try-catch		
		
	} // End of doRowsCmd()
 		




} 
