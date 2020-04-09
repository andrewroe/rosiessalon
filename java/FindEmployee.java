import java.util.Scanner; 
import javax.swing.JOptionPane; 
import java.sql.*;
import java.io.*;  	
import javafx.application.Application;
import javafx.stage.Stage;
// import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

//import javafx.event.*;


/**		     	
   This program asks user to log in
   Then the program presents a menu:
	
   There are no input parameters.
*/
		     	
public class FindEmployee
{
	
	protected Stage myStage;
	protected EmpData data = new EmpData();
	protected EmpData originaldata = new EmpData();
	//protected EmpInfoDB empinfo = new EmpInfoDB();
	protected Label banner = new Label("Find/Update Employee");

	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void findEmployee(Stage stage) throws SQLException
	{
		myStage = stage;
		
		int empid = 0;
		
		String fname = null;
		String mname = null;
		String lname = null;
			
		Label banner = new Label("Find/Update Employee");
		Label fnamePrompt = new Label("Employee's first name:");
		Label mnamePrompt = new Label("Employee's middle name:");
		Label lnamePrompt = new Label("Employee's last name:");
		Label empidLabel = new Label();
		
		Label findPrompt = new Label("Submit");
		Label backPrompt = new Label("Back to Menu");
		Label exitPrompt = new Label("Click this Button to exit");
			
		TextField fnameText = new TextField();
		TextField mnameText = new TextField();
		TextField lnameText = new TextField();
		TextField pPhoneText = new TextField();
		TextField pEmailText = new TextField();
		
		TextField newFnameText = new TextField();
		TextField newMnameText = new TextField();
		TextField newLnameText = new TextField();
		TextField newpPhoneText = new TextField();
		TextField newpEmailText = new TextField();
		TextField newAddr00Text = new TextField();
		TextField newAddr01Text = new TextField();
		TextField newAddr02Text = new TextField();
		TextField newAddr03Text = new TextField();
		TextField newAddr04Text = new TextField();

		fnameText.setText("fill in");
		mnameText.setText("fill in");
		lnameText.setText("fill in");

		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");
		
		banner.setFont(Font.font("Ariel",24));
		fnamePrompt.setFont(Font.font("Ariel",18));
		mnamePrompt.setFont(Font.font("Ariel",18));
		lnamePrompt.setFont(Font.font("Ariel",18));
		findPrompt.setFont(Font.font("Ariel",18));
		backPrompt.setFont(Font.font("Ariel",18));
		exitPrompt.setFont(Font.font("Ariel",18));
		empidLabel.setFont(Font.font("Ariel",18));
		
		fnameText.setFont(Font.font("Ariel",18));
		mnameText.setFont(Font.font("Ariel",18));
		lnameText.setFont(Font.font("Ariel",18));
		
		submitButton.setFont(Font.font("Ariel",18));
		backButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
			
		HBox bannerHbox = new HBox(banner);
		
		HBox fnameHbox = new HBox(10, fnamePrompt, fnameText);
		HBox mnameHbox = new HBox(10, mnamePrompt, mnameText);
		HBox lnameHbox = new HBox(10, lnamePrompt, lnameText);
		
		HBox submitHbox = new HBox(10, submitButton);
		HBox backHbox = new HBox(10, backButton);
		HBox exitHbox = new HBox(10, exitButton);
		
		fnameHbox.setAlignment(Pos.CENTER_LEFT);
		mnameHbox.setAlignment(Pos.CENTER_LEFT);
		lnameHbox.setAlignment(Pos.CENTER_LEFT);
		submitHbox.setAlignment(Pos.CENTER_LEFT);
		backHbox.setAlignment(Pos.CENTER_LEFT);
		exitHbox.setAlignment(Pos.CENTER_LEFT);
		
		GridPane grid = new GridPane();
		grid.add(bannerHbox, 0, 0);
		grid.add(fnameHbox, 0, 1);
		grid.add(mnameHbox, 0, 2);
		grid.add(lnameHbox, 0, 3);
		grid.add(submitHbox, 0, 4);
		grid.add(backHbox, 0, 5);
		grid.add(exitHbox, 0, 6);
		grid.add(empidLabel, 0, 7);
						
		// Button Handling
		submitButton.setOnAction(event ->
		{
			try
        	{               			
				data.setFname(fnameText.getText());
				data.setMinit(mnameText.getText());
				data.setLname(lnameText.getText());
				
				System.out.println("Submit - values = " +
					data.getFname() + " " + data.getMinit() + " " + data.getLname());				
							
				if (TransactionApp.EmployeeDBaccess.searchEmployeeByFullName(data) == 1)
				{
					empidLabel.setText(String.format("Employee ID = %d",
						data.getEmpID()));
				
					this.displayEmployee(myStage);	
				}
				else
				{
					empidLabel.setText(String.format("Did Not find that Employee"));						
				}
				
            }
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }
        	catch (FileNotFoundException ex)
        	{
            	System.out.println(ex.getMessage());
            	System.out.println("Got a File Not Found exception!");
        	}	                
        		
		});	
		
		backButton.setOnAction(event ->
		{
			try
			{
				TransactionApp.menu(TransactionApp.mainStage);
			}
			catch (SQLException ex)
			{
				System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
			}
		});
		
		exitButton.setOnAction(event ->
		{
			System.out.println("Good bye.");
			try
        	{               			
				TransactionApp.EmployeeDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println("Got a SQL exception!");
            } 
       							 		            
        	System.exit(0);			
		});
				
		Scene findScene = new Scene(grid,800,800);

		stage.setScene(findScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();
 								
	}

				
	/**		     	
		This method is for 
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void displayEmployee(Stage stage) 
		throws SQLException, FileNotFoundException
	{
		int empid = data.getEmpID();
		
		System.out.println("displayEmployee() - enter");
		
		if (!TransactionApp.EmployeeDBaccess.fetchAllEmployeeInfo(data))
		{
			System.out.println("can not do fetchAllEmployeeInfo");
			return;
		}

		originaldata = data.Replicate();
		
		//String[] address = null;
		//String addrline = null;
		int i = 0;
		
		String fname = null;
		String mname = null;
		String lname = null;
		String job = null;
		String salary = null;
		String commission = null;
		String pPhone = null;
		String pEmail = null;
		String Addr00 = null;
		String Addr01 = null;
		String Addr02 = null;
		String Addr03 = null;
		String Addr04 = null;
		String Dob = null;
		String BalanceDue = null;
		
		String newFname = null;
		String newMname = null;
		String newLname = null;
		String newJob = null;
		String newSalary = null;
		String newCommission = null;
		String newpPhone = null;
		String newpEmail = null;
		String newAddr00 = null;
		String newAddr01 = null;
		String newAddr02 = null;
		String newAddr03 = null;
		String newAddr04 = null;
		String newDob = null;
				
		Label currentInfo  = new  Label("Current Employee");
		Label updateInfo   = new   Label("Update Employee");
		
		Label fnamePrompt  = new  Label("  Employee's first name:");
		Label mnamePrompt  = new  Label("  Employee's middle name:");
		Label lnamePrompt  = new  Label("    Employee's Last name:");
		Label jobPrompt  = new  Label("            Employee's job:");
		Label salaryPrompt  = new  Label("      Employee's salary:");
		Label commissionPrompt  = new  Label("   Employee's commission:");
		Label phonePrompt  = new  Label("Employee's Primary Phone:");
		Label emailPrompt  = new  Label("Employee's Primary Email:");
		Label addr00Prompt = new  Label(" Employee's Primary Addr:");
		Label addr01Prompt = new  Label(" Employee's P. A. line 2:");
		Label addr02Prompt = new  Label(" Employee's P. A. line 3:");
		Label addr03Prompt = new  Label(" Employee's P. A. line 4:");
		Label addr04Prompt = new  Label(" Employee's P. A. line 5:");
		Label dobPrompt    = new  Label("Employee's Date of Birth:");
		
		Label empidPrompt = new  Label("Employee's Id:");
			
		Label updatePrompt = new Label("Update");
		Label backPrompt = new Label("Back to Menu");
		Label exitPrompt = new Label("Click this Button to exit");
			
		TextField fnameText  = new TextField();
		TextField mnameText  = new TextField();
		TextField lnameText  = new TextField();
		TextField jobText    = new TextField();
		TextField salaryText = new TextField();
		TextField commissionText = new TextField();
		TextField pPhoneText = new TextField();
		TextField pEmailText = new TextField();
		TextField Addr00Text = new TextField();
		TextField Addr01Text = new TextField();
		TextField Addr02Text = new TextField();
		TextField Addr03Text = new TextField();
		TextField Addr04Text = new TextField();
		TextField dobText    = new TextField();
		TextField empidText = new TextField();
		
		TextField newFnameText  = new TextField();
		TextField newMnameText  = new TextField();
		TextField newLnameText  = new TextField();
		TextField newJobText    = new TextField();
		TextField newSalaryText = new TextField();
		TextField newCommissionText = new TextField();
		TextField newpPhoneText = new TextField();
		TextField newpEmailText = new TextField();
		TextField newAddr00Text = new TextField();
		TextField newAddr01Text = new TextField();
		TextField newAddr02Text = new TextField();
		TextField newAddr03Text = new TextField();
		TextField newAddr04Text = new TextField();
		TextField newDobText    = new TextField();
		
		fnameText.setText(data.getFname());
		mnameText.setText(data.getMinit());
		lnameText.setText(data.getLname());
		jobText.setText(data.getJob());
		salaryText.setText(String.format("%.02f",data.getSalary()));
		commissionText.setText(String.format("%.02f",data.getCommission()));
		pPhoneText.setText(data.getPhone(0));
		pEmailText.setText(data.getEmail(0));
			
		Addr00Text.setText(data.AddrData[0]);
		Addr01Text.setText(data.AddrData[1]);
		Addr02Text.setText(data.AddrData[2]);
		Addr03Text.setText(data.AddrData[3]);
		Addr04Text.setText(data.AddrData[4]);
		
		dobText.setText(data.getDob());
		
		empidText.setText( String.format("%d", empid) );

		Button updateButton = new Button("Update");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");
		
		banner.setFont(Font.font("Ariel",24)); // probably redundant
		
		currentInfo.setFont(Font.font("Ariel",18));
		updateInfo.setFont(Font.font("Ariel",18));
		
		fnamePrompt.setFont(Font.font("Ariel",18));
		mnamePrompt.setFont(Font.font("Ariel",18));
		lnamePrompt.setFont(Font.font("Ariel",18));
		jobPrompt.setFont(Font.font("Ariel",18));
		salaryPrompt.setFont(Font.font("Ariel",18));
		commissionPrompt.setFont(Font.font("Ariel",18));
		phonePrompt.setFont(Font.font("Ariel",18)); 
		emailPrompt.setFont(Font.font("Ariel",18));
		addr00Prompt.setFont(Font.font("Ariel",18)); 
		addr01Prompt.setFont(Font.font("Ariel",18));
		addr02Prompt.setFont(Font.font("Ariel",18));
		addr03Prompt.setFont(Font.font("Ariel",18));
		addr04Prompt.setFont(Font.font("Ariel",18));
		dobPrompt.setFont(Font.font("Ariel",18));
		empidPrompt.setFont(Font.font("Ariel",18));
		
		fnameText.setFont(Font.font("Ariel",18));
		mnameText.setFont(Font.font("Ariel",18));
		lnameText.setFont(Font.font("Ariel",18));
		jobText.setFont(Font.font("Ariel",18));
		salaryText.setFont(Font.font("Ariel",18));
		commissionText.setFont(Font.font("Ariel",18));
		pPhoneText.setFont(Font.font("Ariel",18)); 
		pEmailText.setFont(Font.font("Ariel",18));
		Addr00Text.setFont(Font.font("Ariel",18)); 
		Addr01Text.setFont(Font.font("Ariel",18));
		Addr02Text.setFont(Font.font("Ariel",18));
		Addr03Text.setFont(Font.font("Ariel",18));
		Addr04Text.setFont(Font.font("Ariel",18));
		dobText.setFont(Font.font("Ariel",18));

		newFnameText.setFont(Font.font("Ariel",18));
		newMnameText.setFont(Font.font("Ariel",18));
		newLnameText.setFont(Font.font("Ariel",18));
		newJobText.setFont(Font.font("Ariel",18));
		newSalaryText.setFont(Font.font("Ariel",18));
		newCommissionText.setFont(Font.font("Ariel",18));
		newpPhoneText.setFont(Font.font("Ariel",18)); 
		newpEmailText.setFont(Font.font("Ariel",18));
		newAddr00Text.setFont(Font.font("Ariel",18)); 
		newAddr01Text.setFont(Font.font("Ariel",18));
		newAddr02Text.setFont(Font.font("Ariel",18));
		newAddr03Text.setFont(Font.font("Ariel",18));
		newAddr04Text.setFont(Font.font("Ariel",18));
		newDobText.setFont(Font.font("Ariel",18));
		
		updatePrompt.setFont(Font.font("Ariel",18));
		backPrompt.setFont(Font.font("Ariel",18));
		exitPrompt.setFont(Font.font("Ariel",18));				
		
		updateButton.setFont(Font.font("Ariel",18));
		backButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
				
		HBox bannerHbox = new HBox(banner);
		HBox currentHbox = new HBox(currentInfo);
		HBox updateHbox = new HBox(updateInfo);
		
		HBox fnameHbox  = new HBox(10, fnamePrompt, fnameText);
		HBox mnameHbox  = new HBox(10, mnamePrompt, mnameText);
		HBox lnameHbox  = new HBox(10, lnamePrompt, lnameText);
		HBox jobHbox  = new HBox(10, jobPrompt, jobText);
		HBox salaryHbox  = new HBox(10, salaryPrompt, salaryText);
		HBox commissionHbox  = new HBox(10, commissionPrompt, commissionText);
		HBox phoneHbox  = new HBox(10, phonePrompt, pPhoneText);
		HBox emailHbox  = new HBox(10, emailPrompt, pEmailText);
		HBox addr00Hbox = new HBox(10, addr00Prompt, Addr00Text);
		HBox addr01Hbox = new HBox(10, addr01Prompt, Addr01Text);
		HBox addr02Hbox = new HBox(10, addr02Prompt, Addr02Text);
		HBox addr03Hbox = new HBox(10, addr03Prompt, Addr03Text);
		HBox addr04Hbox = new HBox(10, addr04Prompt, Addr04Text);
		HBox dobHbox    = new HBox(10, dobPrompt, dobText);
		
		HBox empidHbox = new HBox(10, empidPrompt, empidText);
		
		HBox newFnameHbox  = new HBox(10, newFnameText);
		HBox newMnameHbox  = new HBox(10, newMnameText);
		HBox newLnameHbox  = new HBox(10, newLnameText);
		HBox newJobHbox  = new HBox(10, newJobText);
		HBox newSalaryHbox  = new HBox(10, newSalaryText);
		HBox newCommissionHbox  = new HBox(10, newCommissionText);
		HBox newPhoneHbox  = new HBox(10, newpPhoneText);
		HBox newEmailHbox  = new HBox(10, newpEmailText);
		HBox newAddr00Hbox = new HBox(10, newAddr00Text);
		HBox newAddr01Hbox = new HBox(10, newAddr01Text);
		HBox newAddr02Hbox = new HBox(10, newAddr02Text);
		HBox newAddr03Hbox = new HBox(10, newAddr03Text);
		HBox newAddr04Hbox = new HBox(10, newAddr04Text);
		HBox newDobHbox    = new HBox(10, newDobText);
				
		HBox updateButtonHbox = new HBox(10, updateButton);
		HBox backButtonHbox = new HBox(10, backButton);
		HBox exitButtonHbox = new HBox(10, exitButton);
		
		fnameHbox.setAlignment(Pos.CENTER_LEFT);
		mnameHbox.setAlignment(Pos.CENTER_LEFT);
		lnameHbox.setAlignment(Pos.CENTER_LEFT);
		jobHbox.setAlignment(Pos.CENTER_LEFT);
		salaryHbox.setAlignment(Pos.CENTER_LEFT);
		commissionHbox.setAlignment(Pos.CENTER_LEFT);
		phoneHbox.setAlignment(Pos.CENTER_LEFT);
		emailHbox.setAlignment(Pos.CENTER_LEFT);
		addr00Hbox.setAlignment(Pos.CENTER_LEFT);
		addr01Hbox.setAlignment(Pos.CENTER_LEFT);
		addr02Hbox.setAlignment(Pos.CENTER_LEFT);
		addr03Hbox.setAlignment(Pos.CENTER_LEFT);
		addr04Hbox.setAlignment(Pos.CENTER_LEFT);
		dobHbox.setAlignment(Pos.CENTER_LEFT);
		
		empidHbox.setAlignment(Pos.CENTER_LEFT);
						
		updateButtonHbox.setAlignment(Pos.CENTER_LEFT);
		backButtonHbox.setAlignment(Pos.CENTER_LEFT);
		exitButtonHbox.setAlignment(Pos.CENTER_LEFT);
			
		newFnameHbox.setAlignment(Pos.CENTER_LEFT);
		newMnameHbox.setAlignment(Pos.CENTER_LEFT);
		newLnameHbox.setAlignment(Pos.CENTER_LEFT);
		newPhoneHbox.setAlignment(Pos.CENTER_LEFT);
		newJobHbox.setAlignment(Pos.CENTER_LEFT);
		newSalaryHbox.setAlignment(Pos.CENTER_LEFT);
		newCommissionHbox.setAlignment(Pos.CENTER_LEFT);
		newEmailHbox.setAlignment(Pos.CENTER_LEFT);
		newAddr00Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr01Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr02Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr03Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr04Hbox.setAlignment(Pos.CENTER_LEFT);
		newDobHbox.setAlignment(Pos.CENTER_LEFT);
						
		GridPane grid = new GridPane();
		grid.add(bannerHbox, 0, 0);
		grid.add(currentHbox, 0, 1);
		grid.add(updateHbox, 1, 1);
		
		grid.add(fnameHbox, 0, 2);
		grid.add(newFnameHbox, 1, 2);
		
		grid.add(mnameHbox, 0, 3);
		grid.add(newMnameHbox, 1, 3);
		
		grid.add(lnameHbox, 0, 4);
		grid.add(newLnameHbox, 1, 4);
		
		grid.add(jobHbox, 0, 5);
		grid.add(newJobHbox, 1, 5);
		
		grid.add(salaryHbox, 0, 6);
		grid.add(newSalaryHbox, 1, 6);
		
		grid.add(commissionHbox, 0, 7);
		grid.add(newCommissionHbox, 1, 7);		
						
		grid.add(phoneHbox, 0, 8);
		grid.add(newPhoneHbox, 1, 8);
		
		grid.add(emailHbox, 0, 9);
		grid.add(newEmailHbox, 1, 9);
		
		grid.add(addr00Hbox, 0, 10);
		grid.add(newAddr00Hbox, 1, 10);
		
		grid.add(addr01Hbox, 0, 11);
		grid.add(newAddr01Hbox, 1, 11);
		
		grid.add(addr02Hbox, 0, 12);
		grid.add(newAddr02Hbox, 1, 12);
		
		grid.add(addr03Hbox, 0, 13);
		grid.add(newAddr03Hbox, 1, 13);
		
		grid.add(addr04Hbox, 0, 14);
		grid.add(newAddr04Hbox, 1, 14);
		
		grid.add(dobHbox, 0, 15);
		grid.add(newDobHbox, 1, 15);
				
		grid.add(empidHbox, 0, 16);
					
		grid.add(updateButtonHbox, 0, 17);
		grid.add(backButtonHbox, 0, 18);
		grid.add(exitButtonHbox, 0, 19);
	
		// Button Handling
		updateButton.setOnAction(event ->
		{              			
			if ( (newFnameText.getText() != null) &&
				(newFnameText.getText().length() != 0) )
				data.setFname(newFnameText.getText());
			if ( (newMnameText.getText() != null) &&
				(newMnameText.getText().length() != 0) )
				data.setMinit(newMnameText.getText());
			if ( (newLnameText.getText() != null) &&
				(newLnameText.getText().length() != 0) )
				data.setLname(newLnameText.getText());
			if ( (newJobText.getText() != null) &&
				(newJobText.getText().length() != 0) )
				data.setJob(newJobText.getText());	
			if ( (newSalaryText.getText() != null) &&
				(newSalaryText.getText().length() != 0) )
				data.setSalary(Double.parseDouble(newSalaryText.getText()));	
			if ( (newCommissionText.getText() != null) &&
				(newCommissionText.getText().length() != 0) )
				data.setCommission(Double.parseDouble(newCommissionText.getText()));	
			if ( (newpPhoneText.getText() != null) &&
				(newpPhoneText.getText().length() != 0) )
				data.setPhone(newpPhoneText.getText(),0);
			if ( (newpEmailText.getText() != null) &&
				(newpEmailText.getText().length() != 0) )
				data.setEmail(newpEmailText.getText(),0);
					
			String[] newaddress = {null, null, null, null, null};
			if ( (newAddr00Text.getText() != null) &&
				(newAddr00Text.getText().length() != 0) )
				newaddress[0] = newAddr00Text.getText();
			else 
				newaddress[0] = data.AddrData[0];			
			if ( (newAddr01Text.getText() != null) &&
				(newAddr01Text.getText().length() != 0) )
				newaddress[1] = newAddr01Text.getText();
			else 
				newaddress[1] = data.AddrData[1];	
			if ( (newAddr02Text.getText() != null) &&
				(newAddr02Text.getText().length() != 0) )
				newaddress[2] = newAddr02Text.getText();
			else 
				newaddress[2] = data.AddrData[2];
			if ( (newAddr03Text.getText() != null) &&
				(newAddr03Text.getText().length() != 0) )
				newaddress[3] = newAddr03Text.getText();
			else 
				newaddress[3] = data.AddrData[3];
			if ( (newAddr04Text.getText() != null) &&
				(newAddr04Text.getText().length() != 0) )
				newaddress[4] = newAddr04Text.getText();
			else 
				newaddress[4] = data.AddrData[4];	
			data.setAddr(newaddress[0], 0);
			data.setAddr(newaddress[1], 1);
			data.setAddr(newaddress[2], 2);
			data.setAddr(newaddress[3], 3);
			data.setAddr(newaddress[4], 4);
			
			if ( (newDobText.getText() != null) &&
				(newDobText.getText().length() != 0) )
				data.setDob(newDobText.getText());
						
			try
        	{               			
				this.updateEmployee(myStage);
            }  			
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }
        	catch (FileNotFoundException ex)
        	{
            	System.out.println(ex.getMessage());
            	System.out.println("Got a File Not Found exception!");
        	}	       							
        					
		});	
		
		backButton.setOnAction(event ->
		{
			try
			{
				TransactionApp.menu(TransactionApp.mainStage);
			}
			catch (SQLException ex)
			{
				System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
			}
		});
		
		exitButton.setOnAction(event ->
		{
			System.out.println("Good bye.");
			try
        	{               			
				TransactionApp.EmployeeDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
				
		Scene findScene = new Scene(grid,800,800);

		stage.setScene(findScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();
 								
	}

				
	/**		     	
		This method is for 
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void updateEmployee(Stage stage) 
		throws SQLException, FileNotFoundException
	{
		int i = 0;
		boolean bValue = false;
		
		System.out.println("updateEmployee() - enter");

		if ( !data.equalsFname(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeFname(data);
			
		if ( !data.equalsMinit(originaldata)) 
			TransactionApp.EmployeeDBaccess.updateEmployeeMinit(data);
		
		if ( !data.equalsLname(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeLname(data);
		
		if ( !data.equalsJob(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeJob(data);
		
		if ( !data.equalsSalary(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeSalary(data);
		
		if ( !data.equalsCommission(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeCommission(data);
						
		if ( !data.equalsPrimaryPhone(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeePhone
				(data,0);
		
		if ( !data.equalsPrimaryEmail(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeEmail
				(data,0);
		 
		if ( !data.equalsDob(originaldata))
			TransactionApp.EmployeeDBaccess.updateEmployeeDob(data);
			
		if ( !data.equalsAddr(originaldata,0))
			TransactionApp.EmployeeDBaccess.updateEmployeeAddress(data,0);
		
		System.out.println("displayEmployee() -  call displayEmployee");
		displayEmployee(myStage);		
	} // End of updateCustomer()

}

		
				
