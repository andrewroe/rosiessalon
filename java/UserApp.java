import java.util.Scanner;  
import java.sql.*;  	

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
	
	static EmpDBaccess dbaccess = new EmpDBaccess();
		
	public static void main(String[] args) throws SQLException
	{
    	  
        UsersChoice choice = UsersChoice.invalidChoice;	// The user's choice 
        int userid = 0;		// the user's EmpID
        int empid = 0;		// the employee's EmpID
        EmpData userData = new EmpData();
        EmpData employeeData = new EmpData();
        boolean validEmployeeData = false;
        
        dbaccess.ConnectToDB("rosiessalon","andrewroe","andysql");
        
        
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
		Scanner keyboard = new Scanner (System.in);	
		
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
   		@param data which is an EmpData object to be updated when user successfully
   		can be found in the Database.
   		@return UserID of user or 0, if not found.  		
	*/
		  
	public static int handlelogin(EmpData user) throws SQLException
	{
		int trycount = 0;
		String username = null;
		Scanner userinput = new Scanner (System.in);
		int empid = 0;
		String password = null;
		String userpassword = null;
		
		do
		{
			// add JOption later
			System.out.print("enter user name: ");
			username = userinput.nextLine();
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
					userpassword = userinput.nextLine();
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

		Scanner keyboard = new Scanner(System.in);
		
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
   		@return boolean of either success or failure.  		
	*/
		  
	public static boolean findEmployee(EmpData data) throws SQLException
	{
		int callingUser = data.getUserID();
		String name = null;
		int gotback = 0;
		
		Scanner keyboard = new Scanner(System.in);
		
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
			System.out.println("Successfully found employee.");

			System.out.println("Employee Job: " + data.getJob());
			System.out.println("Employee Salary: "+	data.getSalary());
			System.out.println("Employee Commission: " + data.getCommission());
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
		
		Scanner keyboard = new Scanner(System.in);
		
		data.clearAll();
		data.setUserID(callingUser);

		System.out.println("So you want to update an employee's information.");
		System.out.println("First, who are we talking about?");

		System.out.print("First name please: ");
		name = keyboard.nextLine();
		data.setFname(name);

		System.out.print("Middle initial or name: ");
		name = keyboard.nextLine();
		data.setMinit(name);
		
		System.out.print("Last name: ");
		name = keyboard.nextLine();
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
			if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
			{
				System.out.println("Change first name, which was " +
					originalData.getFname() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new First name? ");
					name = keyboard.nextLine();
					data.setFname(name);
				}
				
				System.out.println("Change Middle Initial or Name, which was " +
					originalData.getMinit() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new Middle name/initial? ");
					name = keyboard.nextLine();
					data.setMinit(name);
				}
			
				System.out.println("Change Last Name, which was " +
					originalData.getLname() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new Last name? ");
					name = keyboard.nextLine();
					data.setLname(name);
					rvalue = dbaccess.updateEmployeeLname(data);
				}
			} // End of name fields
			
			System.out.println("Did you want to update job, salary, or commission " +
				"(y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
			{
				System.out.println("Change job description, which was " +
					originalData.getJob() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new Job? ");
					name = keyboard.nextLine();
					data.setJob(name);
				}
				
				System.out.println("Change salary, which was " +
					originalData.getSalary() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new salary? ");
					doubleResponse = keyboard.nextDouble();
					data.setSalary(doubleResponse);
				}
				
				System.out.println("Change commission, which was " +
					originalData.getCommission() + " (y or n)?");
				userresponse = keyboard.nextLine();
				if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
				{
					System.out.print("new commission? ");
					doubleResponse = keyboard.nextDouble();
					data.setCommission(doubleResponse);
				}
				
			} // End of job, salary, or commission  fields
					
			name = originalData.getEmail(0);
			if (name != null)
			{
				System.out.println("Current Primary Email is " +
				originalData.getEmail(0));
			}	
			name = originalData.getEmail(1);
			if (name != null)
			{
				System.out.println("Current Secondary Email is " +
				originalData.getEmail(1));
			}	
			name = originalData.getEmail(2);
			if (name != null)
			{
				System.out.println("Current Tertiary Email is " +
				originalData.getEmail(2));
			}	
			name = originalData.getEmail(3);
			if (name != null)
			{
				System.out.println("Current Fourth Email is " +
				originalData.getEmail(3));
			}	
			name = originalData.getEmail(4);
			if (name != null)
			{
				System.out.println("Current Fifth Email is " +
				originalData.getEmail(4));
			}	
			
			System.out.println("Change any email addresses (y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
			{
				System.out.println(" You can update up to 5 Email addresses, " +
					"starting with the new primary email, etc.");
				rvalue = true;
				another = true;
				i = 0;
				do
				{
					System.out.print("Email address " + (i + 1) + " ? ");
					name = keyboard.nextLine();
					rvalue = data.setEmail(name,i++);
					if (rvalue)
					{
						System.out.println("another Email?");
						if ((userresponse.charAt(0) == 'Y') || 
							(userresponse.charAt(0) == 'y'))
						{
							another = true;
						}
					}
					else
					{
						System.out.println("Email address can not be added, full.");
						another = false;
					}
						
				} while (rvalue && another && (i < 5));
					 
			} // End of Email info

				
			System.out.println("Change phone numbers (y or n)?");
			userresponse = keyboard.nextLine();
			if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
			{
				System.out.println(" You can update up to 5 Phone numbers, " +
					"starting with the new primary phone, etc.");
				rvalue = true;
				another = true;
				i = 0;
				do
				{
					System.out.print("Another Phone number (y or n)? ");
					userresponse = keyboard.nextLine();
					if ((userresponse.charAt(0) == 'Y') || (userresponse.charAt(0) == 'y'))
					{
						System.out.print("Next Phone number? ");
						name = keyboard.nextLine();
						rvalue = data.setPhone(name,i);
					
						// deactivate any old phone for this slot
						if ((phone = originalData.getPhone(i)) != null)
						{
							System.out.println("old phone # to be deactivated " + phone);
							rvalue = dbaccess.deactivateEmpPhone(originalData, i);
							System.out.println("deactivate old phone # rvalue " + rvalue);
						}
					
						// then add new phone number for this slot
						if ((phone = data.getPhone(i)) != null)
						{
							System.out.println("new phone # to be added " + phone);
							rvalue = dbaccess.addEmpPhone(data, i);
							System.out.println("add new phone # rvalue " + rvalue);
						}
						
						i++;
						
						if (rvalue)
						{
							System.out.println("another Phone number?");
							if ((userresponse.charAt(0) == 'Y') || 
								(userresponse.charAt(0) == 'y'))
							{
							another = true;
							}
						}
						else
						{
							System.out.println("more Phone numbers can not be added, full.");
							another = false;
						}
					}
					else
					{
						another = false;
					}
						
				} while (rvalue && another && (i < 5));
					 
			} // End of Email info
			
			
			return true;
			
		} // End of found existing Employee record
			
		else
		{
			System.out.println("VERY confusing code return value " +
					"and failed to find employee.");
			return false;
		}
		
		
	}
	
	
}
	
		
			
				
