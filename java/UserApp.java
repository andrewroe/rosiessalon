import java.util.Scanner; 
import javax.swing.JOptionPane; 
import java.sql.*; 
import java.io.*; 	

/**		     	
   This program asks user to log in
   Then the program presents a menu:
		1. Create new employee
		2. Find employee
		3. Update employee info
		4. Deactivate employee
		5. Create new Customer
		6. Find Customer
		7. Update Customer info
		8. Deactivate Customer
		9. Create new Transaction
		10. Find existing Transaction
		11. Update existing Transaction
		12. Void existing Transaction
		0. Quit the program (Note the zero (0))
   This program will use separate methods and/or classes for each selection
   There are no input parameters.
*/
		     	
public class UserApp
{
	public enum UsersChoice
	{
		exit,
		createEmp,
		findEmp,
		updateEmp,
		deactivateEmp,
		createCust,
		findCust,
		updateCust,
		deactivateCust,
		createTrans,
		findTrans,
		updateTrans,
		voidTrans,
		invalidChoice
	}
	
	private static Scanner keyboard = new Scanner(System.in);
	private static EmpDBaccess dbaccess = new EmpDBaccess();
	
	public static void main(String[] args) 
			throws SQLException, FileNotFoundException
	{
    	  
        UsersChoice choice = UsersChoice.invalidChoice;	// The user's choice 
        int userid = 0;		// the user's EmpID
        int empid = 0;		// the employee's EmpID
        EmpData userData = new EmpData();
        EmpData employeeData = new EmpData();
        boolean validEmployeeData = false;

        try
		{
			dbaccess.ConnectToDB("/Users/andrewroe/temp/credentials.txt");	
		}
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a File Not Found exception!");
        }	
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println("Got a SQL exception!");
        }			
       
        userid = handlelogin(userData);
        if (userid == 0)
        {
        	System.out.println("Could not log you in - good bye!");
        	dbaccess.DisconnectFromDB();
        	System.exit(0);
        }	
        
        userData.setUserID(userid);

		do
		{
			
			choice = menu();
			switch (choice)
			{
				case exit:
					System.out.println("Good bye.");
					//DisconnectFromDB();
					dbaccess.DisconnectFromDB();
					userid = 0;		// the user's EmpID
        			empid = 0;		// the employee's EmpID
        			System.exit(0);
					break;
				case createEmp:
					// NEED TO check for admin or manager, which means findEmployee()
					employeeData.clearAll();
					employeeData.setUserID(userid);
					validEmployeeData = createEmployee(employeeData);
					break;
				case findEmp:
					employeeData.clearAll();
					employeeData.setUserID(userid);
					validEmployeeData = findEmployee(employeeData);
					break;
				case updateEmp:
					employeeData.clearAll();
					employeeData.setUserID(userid);
					validEmployeeData = updateEmployee(employeeData);
					break;
				case deactivateEmp:
				case createCust:
				case findCust:
				case updateCust:
				case deactivateCust:
				case createTrans:
				case findTrans:
				case updateTrans:
					System.out.println("That choice not yet implemented!");
					break;
				default:
					System.out.println("This program's menu method is broken!");
					System.out.println("Some day in the future this would throw " +
						"an Exception.");
					System.exit(0);
			}
			
		} while ((choice != UsersChoice.invalidChoice) && 
				(choice != UsersChoice.exit));
		
		System.exit(0);
	}
	
	/**		     	
		This method presents a user menu and processes that user's response.
   		@return an enumerator value of 0 - 14  		
	*/
		  
	public static UsersChoice menu()
	{
		int userchoice = 0;
			
		do
		{	
			System.out.println("Enter a task choice.");
			System.out.println("0. Exit this program");
			System.out.println("1. Create a new employee");
			System.out.println("2. Find an employee");
			System.out.println("3. Update an employee record");
			System.out.println("4. Deactivate employee");
			System.out.println("5. Create a new customer");
			System.out.println("6. Find a customer");
			System.out.println("7. Update a customer");
			System.out.println("8. Deactivate a customer");
			System.out.println("9. Create a new transaction");
			System.out.println("10. Find a transaction");
			System.out.println("11. Update a transaction");
			System.out.println("12. Void a transaction");
			userchoice = keyboard.nextInt(); // this may not be int, need checking!!
				
			if ((userchoice < 0) || (userchoice > 12))
			{
				System.out.println("You must enter a number from 0 through 12.");
				System.out.println("So please!");
			}
			
		} while ((userchoice < 0) || (userchoice > 12));
				
		// flush newline, this is necessary, when only one Scanner of keyboard
		keyboard.nextLine(); 
		
		switch (userchoice)
		{
			case 0:
				return UsersChoice.exit;
			case 1:
				return UsersChoice.createEmp;
			case 2:
				return UsersChoice.findEmp;
			case 3:
				return UsersChoice.updateEmp;		
			default:
				return UsersChoice.invalidChoice;
		
		}
		
	}
	
	
	/**		     	
		This method is for login handling of the user.
		
   		@param user which is an EmpData object to be updated when user successfully
   		can be found in the Database.
   		@throws SQLException if there is an error with some SQL command
   		@return UserID of user or 0, if not found.  		
	*/
		  
	public static int handlelogin(EmpData user) throws SQLException
	{
		int trycount = 0;
		String username = null;
		int empid = 0;
		String password = null;
		String userpassword = null;
		
		do
		{
			// add JOption later
			System.out.print("enter user name: ");
			username = keyboard.nextLine();
			// going to need findUser(user) into EmpDBaccess
			/*
			if we find user, then check user password
			*/
			empid = dbaccess.fetchUser(username);
			if (empid != 0) 
			{ 
				password = dbaccess.fetchPassword(empid);
				if (password != null)
				{
					System.out.print("enter password: ");
					userpassword = keyboard.nextLine();
					if (userpassword.compareTo(password) == 0)
					{
						// NEED to fill in all the EmpDater of user !!!
						return empid;  // user login successful
					}
				}
			}
			
		} while (++trycount < 3);
		
		
		System.out.println("Ran out of trys.\n");
		return 0;
	}
	
	
	/**		     	
		This method is for create new employee.
		
   		@param data which is an EmpData object to be updated when employee successfully
   		added to the Database.
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean createEmployee(EmpData data) throws SQLException
	{
		int callingUser = data.getUserID();
		String name = null;
		String dob = null;
		String phone = null;
		String email = null;
		String job = null;
		double dParm = 0.0;
		
		// protected ArrayList<Integer> EmpIDlist = new ArrayList<>();
		
		data.clearAll();
		data.setUserID(callingUser);

		System.out.println("Will now try to add (create) a new employee.");

		System.out.print("First name please: ");
		name = keyboard.nextLine();
		data.setFname(name);

		System.out.print("Last name: ");
		name = keyboard.nextLine();
		data.setLname(name);

		System.out.print("Middle initial or name: ");
		name = keyboard.nextLine();
		//data.setMinit(name.charAt(0));
		data.setMinit(name);
		
		System.out.print("Job? ");
		job = keyboard.nextLine();
		data.setJob(job);
		
		System.out.print("Salary? ");
		dParm = keyboard.nextDouble();
		data.setSalary(dParm);

		System.out.print("Commission? ");
		dParm = keyboard.nextDouble();
		data.setCommission(dParm);
		// flush newline
		keyboard.nextLine(); 
		
		System.out.println("not sure what is happening??");
		
		System.out.print("Date of Birth (yyyymmdd only): ");
		dob = keyboard.nextLine();
		data.setDob(dob);

		System.out.print("Phone # (ccaaalllnnn - cc = country code, aaa = area code," +
				" lll = local exchange, etc.): ");
		phone = keyboard.nextLine();
		data.setPhone(phone,0);
		
		if (dbaccess.addEmployee(data))
		{
			System.out.println("Successfully added a new employee.");
			return true;
		}
		else
		{
			System.out.println("Failed to add a new employee.");
			return false;	
		}
		
	}
	
	
	/**		     	
		This method is for finding an existing employee.
		
   		@param data which is an EmpData object to be updated when employee successfully
   		found in the Database.
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean findEmployee(EmpData data) throws SQLException
	{
		int callingUser = data.getUserID();
		String name = null;
		int gotback = 0;
		
		data.clearAll();
		data.setUserID(callingUser);

		System.out.println("Will now try to find an existing employee.");

		System.out.print("First name please: ");
		name = keyboard.nextLine();
		data.setFname(name);

		System.out.print("Middle initial or name: ");
		name = keyboard.nextLine();
		//data.setMinit(name.charAt(0));
		data.setMinit(name);
		
		System.out.print("Last name: ");
		name = keyboard.nextLine();
		data.setLname(name);
		
		gotback = dbaccess.searchEmployeeByFullName(data);
		
		if (gotback == 1)
		{
			System.out.println("\nSuccessfully found employee.");

			System.out.println("\tName: " + data.getFname() + 
				" " + data.getMinit() + " " + data.getLname());
			System.out.println("\tJob: " + data.getJob());
			System.out.println("\tSalary: " +	data.getSalary());
			System.out.println("\tCommission: " + data.getCommission());
			
			dbaccess.fetchAllEmployeeInfo(data);
			
			if (data.getDob() != null)
				System.out.println("\tDate of Birth: " + data.getDob());
			if (data.getEmail(0) != null)
				System.out.println("\tPrimary Email: " + data.getEmail(0));
			if (data.getEmail(1) != null)
				System.out.println("\tSecondary Email: " + data.getEmail(1));
			if (data.getEmail(2) != null)
				System.out.println("\tTertiary Email: " + data.getEmail(2));
			if (data.getEmail(3) != null)
				System.out.println("\tFourth Email: " + data.getEmail(3));
			if (data.getEmail(4) != null)
				System.out.println("\tFifth Email: " + data.getEmail(4));
				
			if (data.getPhone(0) != null)
				System.out.println("\tPrimary Phone: " + data.getPhone(0));
			if (data.getPhone(1) != null)
				System.out.println("\tSecondary Phone: " + data.getPhone(1));
			if (data.getPhone(2) != null)
				System.out.println("\tTertiary Phone: " + data.getPhone(2));
			if (data.getPhone(3) != null)
				System.out.println("\tFourth Phone: " + data.getPhone(3));
			if (data.getPhone(4) != null)
				System.out.println("\tFifth Phone: " + data.getPhone(4));
				
			if (data.getAddr(0) != null)
				System.out.println("\tAddress: " + data.getAddr(0));
			if (data.getAddr(1) != null)
				System.out.println("\t         " + data.getAddr(1));
			if (data.getAddr(2) != null)
				System.out.println("\t         " + data.getAddr(2));
			if (data.getAddr(3) != null)
				System.out.println("\t         " + data.getAddr(3));
			if (data.getAddr(4) != null)
				System.out.println("\t         " + data.getAddr(4));
															
			System.out.println();
			return true;
		}
		else if (gotback == -1)
		{
			System.out.println("Found multiple possible maytches for employee!");
			System.out.println("That is VERY unusual!");
			return false;
		}
		else if (gotback == 0)
		{
			System.out.println("Failed to find employee.");
			return false;	
		}
		else
		{
			System.out.println("VERY confusing code return value " +
					"and failed to find employee.");
			return false;
		}
	}
	
	
	/**		     	
		This method is for updating an existing employee.
		
   		@param data which is an EmpData object to be updated when employee successfully
   		updated in the Database.
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean updateEmployee(EmpData data) throws SQLException
	{		
		int i = 0;
		int callingUser = data.getUserID();
		int empid = 0;
		String name = null;
		String phone = null;
		String userresponse = null;
		double doubleResponse = 0;
		int gotback = 0;
		boolean rvalue = false;
		boolean another = false;
		EmpData originalData = new EmpData();
		
		data.clearAll();
		data.setUserID(callingUser);

		System.out.println("So you want to update an employee's information.");
		System.out.println("First, who are we talking about?");
		System.out.println("Watch for pop-ups.");

		name = JOptionPane.showInputDialog("First name: ");
		data.setFname(name);		
		name = JOptionPane.showInputDialog("Middle initial or name: ");
		data.setMinit(name);
		name = JOptionPane.showInputDialog("Last name: ");
		data.setLname(name);
		
		gotback = dbaccess.searchEmployeeByFullName(data);
		
		if (gotback == 0)
		{
			System.out.println("Failed to find employee to update.");
			return false;	
		}
		else if (gotback == -1)
		{
			System.out.println("Found multiple possible maytches for employee!");
			System.out.println("That is VERY unusual!");
			return false;
		}
		else if (gotback == 1)
		{
			empid = data.getEmpID();
			System.out.println("Successfully found employee.");
			System.out.println("Employee Job: " + data.getJob());
			System.out.println("Employee Salary: "+	data.getSalary());
			System.out.println("Employee Commission: " + data.getCommission());
			
			originalData.clearAll();
			originalData.setEmpID(empid);
			rvalue = dbaccess.fetchAllEmployeeInfo(originalData);
			
			if (!rvalue)
			{
				System.out.println("Not good, could not fetchAllEmployeeInfo()");
				return false;
			}
			
			System.out.println("Did you want to update one of the name fields " +
				"(y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.length() > 0) &&
				(userresponse.charAt(0) == 'Y') || 
				(userresponse.charAt(0) == 'y'))
			{
				rvalue = setNewNameEmpInfo(data, originalData);							
			} // End of change name fields
			
			System.out.println("Did you want to update job, salary, or commission " +
				"(y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.length() > 0) &&
				(userresponse.charAt(0) == 'Y') || 
				(userresponse.charAt(0) == 'y'))
			{
				rvalue = setNewProfessionalEmpInfo(data, originalData);		
			} // End of job, salary, or commission fields changes
					
			
			System.out.println("Change any email addresses (y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.length() > 0) &&
				(userresponse.charAt(0) == 'Y') || 
				(userresponse.charAt(0) == 'y'))
			{
				rvalue = setNewEmailEmpInfo(data, originalData);		 
			} // End of Email changes
			
			System.out.println("Change phone numbers (y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.length() > 0) &&
				(userresponse.charAt(0) == 'Y') || 
				(userresponse.charAt(0) == 'y'))
			{
				rvalue = setNewPhoneEmpInfo(data, originalData);
			} // End of Phone changes
					
			System.out.println("Change Address (y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.length() > 0) &&
				(userresponse.charAt(0) == 'Y') || 
				(userresponse.charAt(0) == 'y'))
			{
				rvalue = setNewAddrEmpInfo(data, originalData);
			} // End of Address changes
						
			return true;
			
		} // End of found existing Employee record
			
		else
		{
			System.out.println("VERY confusing code return value " +
					"and failed to find employee.");
			return false;
		}
			
	}
	
	/**		     	
		This method is a helper for updating an existing employee name info.
		
   		@param data an EmpData object to be updated
   		@param originalData an EmpData object of existing Employee
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean setNewNameEmpInfo(EmpData data, EmpData originalData) 
		throws SQLException
	{		
		boolean rvalue = false;
		String response = null;
			
		System.out.println("Change first name, which was " +
			originalData.getFname() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.length() > 0) &&
			(response.charAt(0) == 'Y') || 
			(response.charAt(0) == 'y'))
		{
			System.out.println("Look for first name pop-up");
			response = JOptionPane.showInputDialog("new First name?");
			data.setFname(response);
			rvalue = dbaccess.updateEmployeeFname(data);
		}
				
		System.out.println("Change Middle Initial or Name, which was " +
			originalData.getMinit() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.length() > 0) &&
			(response.charAt(0) == 'Y') || 
			(response.charAt(0) == 'y'))

		{
			System.out.println("Look for Middle name pop-up");
			response = JOptionPane.showInputDialog("new Middle name or initial?");
			data.setMinit(response);
			rvalue = dbaccess.updateEmployeeMinit(data);
		}
			
		System.out.println("Change Last Name, which was " +
			originalData.getLname() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.length() > 0) &&
			(response.charAt(0) == 'Y') || 
			(response.charAt(0) == 'y'))

		{
			System.out.println("Look for last name pop-up");
			response = JOptionPane.showInputDialog("new Middle name or initial?");
			data.setLname(response);
			rvalue = dbaccess.updateEmployeeLname(data);
		}
		
		return rvalue;	
	} // End of setNewNameEmpInfo
	
	
	/**		     	
		This method is a helper for updating an existing employee professional info.
		
   		@param data an EmpData object to be updated
   		@param originalData an EmpData object of existing Employee
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean setNewProfessionalEmpInfo(EmpData data, EmpData originalData) 
		throws SQLException
	{		
		boolean rvalue = false;
		String response = null;
		double doubleResponse = 0.0;
					
		System.out.println("Change job description, which was " +
			originalData.getJob() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.charAt(0) == 'Y') || (response.charAt(0) == 'y'))
		{
			System.out.print("new Job? ");
			response = keyboard.nextLine();
			data.setJob(response);
			rvalue = dbaccess.updateEmployeeJob(data);
		}
				
		System.out.println("Change salary, which was " +
			originalData.getSalary() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.length() > 0) &&
			(response.charAt(0) == 'Y') || 
			(response.charAt(0) == 'y'))
		{
			System.out.print("new salary? ");
			doubleResponse = keyboard.nextDouble();
			data.setSalary(doubleResponse);
			keyboard.nextLine(); // flush input of newline
			rvalue = dbaccess.updateEmployeeSalary(data);
		}
				
		System.out.println("Change commission, which was " +
			originalData.getCommission() + " (y or n)?");
		response = keyboard.nextLine();
		if ((response.length() > 0) &&
			(response.charAt(0) == 'Y') || 
			(response.charAt(0) == 'y'))
		{
			System.out.print("new commission? ");
			doubleResponse = keyboard.nextDouble();
			data.setCommission(doubleResponse);
			keyboard.nextLine(); // flush input of newline
			rvalue = dbaccess.updateEmployeeCommission(data);
		}		
		
		return rvalue;	
	} // End of setNewProfessionalEmpInfo
	
	
	/**		     	
		This method is a helper for updating an existing employee Email info.
		
   		@param data an EmpData object to be updated
   		@param originalData an EmpData object of existing Employee
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean setNewEmailEmpInfo(EmpData data, EmpData originalData) 
		throws SQLException
	{		
		boolean rvalue = true;
		String response = null;
		String name = null;
		int i;
											
		System.out.println("\nYou can update up to 5 Email addresses," +
			" starting with the new primary email, etc." +
			" Watch for Pop-up prompts.");
		
		i = 0;
		do
		{
			name = originalData.getEmail(i);
			if (name != null)
			{
				System.out.println("Current Email at index " + i + " is " + name);
			}
			else
			{
				System.out.println("Current Email at index " + i + " is blank");
			}
			
			name = JOptionPane.showInputDialog("New Email address?" +
				" Enter nothing to leave as is." +
				" Enter 'quit' to stop making Email changes.");
			if (name.equals("Quit") || name.equals("quit") || name.equals("QUIT"))
			{
				do // clear the rest out
				{
					data.setEmail(null,i);
					rvalue = dbaccess.updateEmployeeEmail(data,i);	
				} while (++i < 5);
				return rvalue;
			}
					
			if (name.length() > 0)
			{
				data.setEmail(name,i);
				rvalue = dbaccess.updateEmployeeEmail(data,i);		
			}
						
		} while (++i < 5);
			
		return rvalue;	
	} // End of setNewEmailEmpInfo
	

	/**		     	
		This method is a helper for updating an existing employee Email info.
		
   		@param data an EmpData object to be updated
   		@param originalData an EmpData object of existing Employee
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean setNewPhoneEmpInfo(EmpData data, EmpData originalData) 
		throws SQLException
	{		
		boolean rvalue = true;
		String response = null;
		String name = null;
		int i;
													 
		System.out.println("\nYou can update up to 5 Phone numbers," +
			" starting with the new primary phone, etc." +			
			" Watch for Pop-up prompts.");
		
		i = 0;
		do
		{			
			name = originalData.getPhone(i);
			if (name != null)
			{
				System.out.println("Current Phone at index " + i + " is " + name);

			}
			else
			{
				System.out.println("Current Phone at index " + i + " is blank");
			}			
			
			name = JOptionPane.showInputDialog("New Phone address?" +
				" Enter nothing to leave as is." +
				" Enter 'Quit' to stop making phone changes.");
				
			if (name.equals("Quit") || name.equals("quit") || name.equals("QUIT"))
			{
				do // clear the rest out
				{
					data.setEmail(null,i);
					rvalue = dbaccess.updateEmployeePhone(data,i);	
				} while (++i < 5);
				return rvalue; 
			}
				
			if (name.length() > 0)
			{
				data.setPhone(name,i);
				rvalue = dbaccess.updateEmployeePhone(data,i);	
			}

		} while (++i < 5);
			
		return rvalue;	
	} // End of setNewPhoneEmpInfo


	/**		     	
		This method is a helper for updating an existing employee Address info.
		
   		@param data an EmpData object to be updated
   		@param originalData an EmpData object of existing Employee
   		@throws SQLException if there is an error with some SQL command
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean setNewAddrEmpInfo(EmpData data, EmpData originalData) 
		throws SQLException
	{		
		boolean rvalue = true;
		String response = null;
		String name = null;
		int i;
													 
		System.out.println("\nYou can update up to 5 lines of address," +
			" starting with the new 1st line, etc." +			
			" Watch for Pop-up prompts.");
		
		i = 0;
		do
		{			
			name = originalData.getAddr(i);
			if (name != null)
			{
				System.out.println("Current Address at index " + i + " is " + name);

			}
			else
			{
				System.out.println("Current Address at index " + i + " is blank");
			}			
			
			name = JOptionPane.showInputDialog("New address line?" +
				" Enter nothing to leave as is." +
				" Enter 'Quit' to stop making address changes.");
				
			if (name.equals("Quit") || name.equals("quit") || name.equals("QUIT"))
			{
				do // clear the rest out
				{
					data.setEmail(null,i);
					rvalue = dbaccess.updateEmployeeAddress(data,i);	
				} while (++i < 5);
				return rvalue; 
			}
				
			if (name.length() > 0)
			{
				data.setAddr(name,i);
				rvalue = dbaccess.updateEmployeeAddress(data,i);	
			}

		} while (++i < 5);
			
		return rvalue;	
	} // End of setNewAddrEmpInfo


}
	
		
			
				
