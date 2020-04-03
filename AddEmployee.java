import java.util.Scanner; 
import javax.swing.JOptionPane; 
import java.sql.*;  	
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
		     	
public class AddEmployee
{
	
	protected Stage myStage;

				
	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void addEmployee(Stage stage) throws SQLException
	{	
		EmpData data = new EmpData();
		//CustInfoDB custinfoDB = new CustInfoDB();
		
		Label banner = new Label("Add Employee    ");
		Label fnamePrompt = new Label("Employee's first name:");
		Label mnamePrompt = new Label("Employee's middle name:");
		Label lnamePrompt = new Label("Employee's last name:");
		Label jobPrompt   = new Label("Employee's job:");
		Label salaryPrompt= new Label("Employee's salary:");
		Label commissionPrompt = new Label("Employee's commission:");
		Label phonePrompt = new Label("Employee's primary phone:");
		Label emailPrompt = new Label("Employee's primary email:");
		Label addr1Prompt = new Label("Employee's primary Address line 1:");
		Label addr2Prompt = new Label("Employee's primary Address line 2:");
		Label addr3Prompt = new Label("Employee's primary Address line 3:");
		Label addr4Prompt = new Label("Employee's primary Address line 4:");
		Label addr5Prompt = new Label("Employee's primary Address line 5:");
		Label successLabel = new Label();
		
		banner.setFont(Font.font("Ariel",24));
		fnamePrompt.setFont(Font.font("Ariel",18));
		mnamePrompt.setFont(Font.font("Ariel",18));
		lnamePrompt.setFont(Font.font("Ariel",18));
		jobPrompt.setFont(Font.font("Ariel",18));
		salaryPrompt.setFont(Font.font("Ariel",18));
		commissionPrompt.setFont(Font.font("Ariel",18));
		phonePrompt.setFont(Font.font("Ariel",18));
		emailPrompt.setFont(Font.font("Ariel",18));
		addr1Prompt.setFont(Font.font("Ariel",18));
		addr2Prompt.setFont(Font.font("Ariel",18));
		addr3Prompt.setFont(Font.font("Ariel",18));
		addr4Prompt.setFont(Font.font("Ariel",18));
		addr5Prompt.setFont(Font.font("Ariel",18));
		successLabel.setFont(Font.font("Ariel",18));
			
		TextField fnameText = new TextField();
		TextField mnameText = new TextField();
		TextField lnameText = new TextField();
		TextField jobText   = new TextField();
		TextField salaryText= new TextField();
		TextField commissionText = new TextField();
		TextField phoneText = new TextField();
		TextField emailText = new TextField();
		TextField addr1Text = new TextField();
		TextField addr2Text = new TextField();
		TextField addr3Text = new TextField();
		TextField addr4Text = new TextField();
		TextField addr5Text = new TextField();

		// Add button handling
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");
		
		submitButton.setFont(Font.font("Ariel",18));
		backButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
		
		submitButton.setOnAction(event ->
		{
			try
        	{               			
				data.setUserID(TransactionApp.userid);
				data.setFname(fnameText.getText());
				data.setMinit(mnameText.getText());	
				data.setLname(lnameText.getText());
				data.setJob(jobText.getText());
				data.setSalary(Double.parseDouble(salaryText.getText()));
				data.setCommission(Double.parseDouble(commissionText.getText()));
				data.setPhone(phoneText.getText(),0);
				data.setEmail(emailText.getText(),0);
				data.setAddr(addr1Text.getText(),0);
				data.setAddr(addr2Text.getText(),1);
				data.setAddr(addr3Text.getText(),2);
				data.setAddr(addr4Text.getText(),3);
				data.setAddr(addr5Text.getText(),4);
														
				if (TransactionApp.EmployeeDBaccess.addEmployee(data))
				{
					successLabel.setText(String.format("Successfully " +
						"added new Employee base info"));
					/*
					if (addressArray[0] != null)
					{
						if (TransactionApp.CustomerDBaccess.searchCustomerByFullName
						(data) == 1)
						{
							// data.getCustID() now filled in												
							if (custinfoDB.addCustInfoAddress
								(custinfoDB.SubTypePrimary,data))
							{
								successLabel.setText(String.format("Successfully " +
									"added new Customer all info"));
							}
						}	
					}
					*/	
				}
				else
				{
					successLabel.setText(String.format("Failed to add new Employee!"));						
				}		
            }
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
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
				
				
		HBox bannerHbox = new HBox(banner);
		
		HBox fnameHbox = new HBox(10, fnamePrompt, fnameText);
		HBox mnameHbox = new HBox(10, mnamePrompt, mnameText);
		HBox lnameHbox = new HBox(10, lnamePrompt, lnameText);
		HBox jobHbox = new HBox(10, jobPrompt, jobText);
		HBox salaryHbox = new HBox(10, salaryPrompt, salaryText);
		HBox commissionHbox = new HBox(10, commissionPrompt, commissionText);
		HBox phoneHbox = new HBox(10, phonePrompt, phoneText);
		HBox emailHbox = new HBox(10, emailPrompt, emailText);
		HBox addr1Hbox = new HBox(10, addr1Prompt, addr1Text);
		HBox addr2Hbox = new HBox(10, addr2Prompt, addr2Text);
		HBox addr3Hbox = new HBox(10, addr3Prompt, addr3Text);
		HBox addr4Hbox = new HBox(10, addr4Prompt, addr4Text);
		HBox addr5Hbox = new HBox(10, addr5Prompt, addr5Text);
		HBox submitHbox = new HBox(10, submitButton);
		HBox backHbox = new HBox(10, backButton);
		HBox exitHbox = new HBox(10, exitButton);
		
		bannerHbox.setAlignment(Pos.CENTER_LEFT);
		fnameHbox.setAlignment(Pos.CENTER_LEFT);
		mnameHbox.setAlignment(Pos.CENTER_LEFT);
		lnameHbox.setAlignment(Pos.CENTER_LEFT);
		jobHbox.setAlignment(Pos.CENTER_LEFT);
		salaryHbox.setAlignment(Pos.CENTER_LEFT);
		commissionHbox.setAlignment(Pos.CENTER_LEFT);
		phoneHbox.setAlignment(Pos.CENTER_LEFT);
		emailHbox.setAlignment(Pos.CENTER_LEFT);
		addr1Hbox.setAlignment(Pos.CENTER_LEFT);
		addr2Hbox.setAlignment(Pos.CENTER_LEFT);
		addr3Hbox.setAlignment(Pos.CENTER_LEFT);
		addr4Hbox.setAlignment(Pos.CENTER_LEFT);
		addr5Hbox.setAlignment(Pos.CENTER_LEFT);
		submitHbox.setAlignment(Pos.CENTER_LEFT);
		backHbox.setAlignment(Pos.CENTER_LEFT);
		exitHbox.setAlignment(Pos.CENTER_LEFT);
		
		GridPane grid = new GridPane();
		grid.add(bannerHbox, 0, 0, 2, 1);
		grid.add(fnameHbox, 0, 1);
		grid.add(mnameHbox, 0, 2);
		grid.add(lnameHbox, 0, 3);
		grid.add(jobHbox, 0, 4);
		grid.add(salaryHbox, 0, 5);
		grid.add(commissionHbox, 0, 6);
		grid.add(phoneHbox, 0, 7);
		grid.add(emailHbox, 0, 8);
		grid.add(addr1Hbox, 0, 9);
		grid.add(addr2Hbox, 0, 10);
		grid.add(addr3Hbox, 0, 11);
		grid.add(addr4Hbox, 0, 12);
		grid.add(addr5Hbox, 0, 13);
		grid.add(submitHbox, 0, 14);
		grid.add(backHbox, 0, 15);
		grid.add(exitHbox, 0, 16);
		grid.add(successLabel, 0, 17);
		
		Scene addcustomerScene = new Scene(grid,800,800);

		stage.setScene(addcustomerScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();
 								
	}


}

		
				
