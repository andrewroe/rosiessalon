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
		     	
public class FindCustomer
{
	
	protected Stage myStage;

				
	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void findCustomer(Stage stage) throws SQLException
	{
		
		int custid = 0;
		CustData data = new CustData();
		
		Label banner = new Label("Find a Customer");
		Label fnamePrompt = new Label("Customer's first name:");
		Label mnamePrompt = new Label("Customer's middle name:");
		Label lnamePrompt = new Label("Customer's last name:");
		Label custidLabel = new Label();
		
		Label findPrompt = new Label("Submit");
		Label backPrompt = new Label("Back to Menu");
		Label exitPrompt = new Label("Click this Button to exit");
			
		TextField fnameText = new TextField();
		TextField mnameText = new TextField();
		TextField lnameText = new TextField();

		// Add button handling
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");
		
		submitButton.setOnAction(event ->
		{
			
			try
        	{               			
				String name = null;
	
				name = fnameText.getText();
				data.setFname(name);
				name = mnameText.getText();
				data.setMinit(name);
				name = lnameText.getText();
				data.setLname(name);
				
				System.out.println("Submit - values = " +
					data.getFname() + " " + data.getMinit() + " " + data.getLname());				
							
				if (TransactionApp.CustomerDBaccess.searchCustomerByFullName(data) == 1)
				{
					custidLabel.setText(String.format("Customer ID = %d",
						data.getCustID()));
				}
				else
				{
					custidLabel.setText(String.format("Did Not find that Customer"));						
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
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
				
				
		HBox banerHbox = new HBox(banner);
		
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
		grid.add(fnameHbox, 1, 0);
		grid.add(mnameHbox, 1, 1);
		grid.add(lnameHbox, 1, 2);
		grid.add(submitHbox, 1, 3);
		grid.add(backHbox, 1, 4);
		grid.add(exitHbox, 1, 5);
		grid.add(custidLabel, 1, 6);
						
		Scene findScene = new Scene(grid,800,800);

		stage.setScene(findScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();
 								
	}


}

		
				
