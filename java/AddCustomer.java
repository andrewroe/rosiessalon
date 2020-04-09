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
		     	
public class AddCustomer
{
	
	protected Stage myStage;

				
	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void addCustomer(Stage stage) throws SQLException
	{	
		CustData data = new CustData();
		CustInfoDB custinfoDB = new CustInfoDB();
		
		Label banner = new Label("Add a Customer    ");
		Label fnamePrompt = new Label("Customer's first name:");
		Label mnamePrompt = new Label("Customer's middle name:");
		Label lnamePrompt = new Label("Customer's last name:");
		Label phonePrompt = new Label("Customer's primary phone:");
		Label emailPrompt = new Label("Customer's primary email:");
		Label addr1Prompt = new Label("Customer's primary Address line 1:");
		Label addr2Prompt = new Label("Customer's primary Address line 2:");
		Label addr3Prompt = new Label("Customer's primary Address line 3:");
		Label addr4Prompt = new Label("Customer's primary Address line 4:");
		Label addr5Prompt = new Label("Customer's primary Address line 5:");
		Label successLabel = new Label();
		
		//Label findPrompt = new Label("Submit");
		//Label backPrompt = new Label("Back to Menu");
		//Label exitPrompt = new Label("Click this Button to exit");
			
		TextField fnameText = new TextField();
		TextField mnameText = new TextField();
		TextField lnameText = new TextField();
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
		
		submitButton.setOnAction(event ->
		{
			String[] addressArray = new String[5];
			try
        	{               			
				data.setUserID(TransactionApp.userid);
				data.setFname(fnameText.getText());
				data.setMinit(mnameText.getText());	
				data.setLname(lnameText.getText());
				data.setPrimaryPhone(phoneText.getText());
				data.setPrimaryEmail(emailText.getText());
				addressArray[0] = addr1Text.getText();
				addressArray[1] = addr2Text.getText();
				addressArray[2] = addr3Text.getText();
				addressArray[3] = addr4Text.getText();
				addressArray[4] = addr5Text.getText();
				data.setAddr(addressArray,0); // only the Primary
											
				if (TransactionApp.CustomerDBaccess.addNewCustomer(data))
				{
					successLabel.setText(String.format("Successfully " +
						"added new Customer base info"));
					if (addressArray[0] != null)
					{
						if (TransactionApp.
							CustomerDBaccess.searchCustomerByFullName
						(data) == 1)
						{
							// data.getCustID() now filled in												
							if (custinfoDB.addCustInfoAddress
								(custinfoDB.SubTypePrimary,data))
							{
								successLabel.setText(String.format
									("Successfully " +
									"added new Customer all info"));
							}
						}	
					}	
				}
				else
				{
					successLabel.setText(String.format
						("Failed to add new Customer!"));						
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
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
				
				
		HBox bannerHbox = new HBox(banner);
		
		HBox fnameHbox = new HBox(10, fnamePrompt, fnameText);
		HBox mnameHbox = new HBox(10, mnamePrompt, mnameText);
		HBox lnameHbox = new HBox(10, lnamePrompt, lnameText);
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
		grid.add(bannerHbox, 0, 0);
		grid.add(fnameHbox, 1, 0);
		grid.add(mnameHbox, 1, 1);
		grid.add(lnameHbox, 1, 2);
		grid.add(phoneHbox, 1, 3);
		grid.add(emailHbox, 1, 4);
		grid.add(addr1Hbox, 1, 5);
		grid.add(addr2Hbox, 1, 6);
		grid.add(addr3Hbox, 1, 7);
		grid.add(addr4Hbox, 1, 8);
		grid.add(addr5Hbox, 1, 9);
		grid.add(submitHbox, 1, 10);
		grid.add(backHbox, 1, 11);
		grid.add(exitHbox, 1, 12);
		grid.add(successLabel, 1, 13);
		
		Scene addcustomerScene = new Scene(grid,800,800);

		stage.setScene(addcustomerScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();
 								
	}

}

		
				
