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
		     	
public class FindCustomer
{
	
	protected Stage myStage;
	protected CustData data = new CustData();
	protected CustData originaldata = new CustData();
	protected Label banner = new Label("Find/Update Customer");


	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void findCustomer(Stage stage) throws SQLException
	{
		myStage = stage;
		
		int custid = 0;
		
		String fname = null;
		String mname = null;
		String lname = null;
			
		Label banner = new Label("Find/Update Customer");
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
		custidLabel.setFont(Font.font("Ariel",18));
		
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
		grid.add(custidLabel, 0, 7);
						
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
							
				if (TransactionApp.CustomerDBaccess.searchCustomerByFullName(data) == 1)
				{
					custidLabel.setText(String.format("Customer ID = %d",
						data.getCustID()));
					//FindCustomer.displayNupdate(myStage);
					this.displayCustomer(myStage);	
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
		  
	public void displayCustomer(Stage stage) 
		throws SQLException, FileNotFoundException
	{
		int custid = data.getCustID();
		//double balancedue;
		
		System.out.println("displayCustomer() - enter");
		
		if (!TransactionApp.CustomerDBaccess.fetchAllCustomerInfo(data))
		{
			System.out.println("can not do fetchAllCustomerInfo");
			return;
		}

		originaldata = data.Replicate();
		
		String[] address = null;
		int i = 0;
		
		String fname = null;
		String mname = null;
		String lname = null;
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
		String newpPhone = null;
		String newpEmail = null;
		String newAddr00 = null;
		String newAddr01 = null;
		String newAddr02 = null;
		String newAddr03 = null;
		String newAddr04 = null;
		String newDob = null;
		String newBalanceDue = null;		
		
		Label currentInfo  = new  Label("Current Customer");
		Label updateInfo   = new   Label("Update Customer");
		Label fnamePrompt  = new  Label("   Customer's first name:");
		Label mnamePrompt  = new  Label("  Customer's middle name:");
		Label lnamePrompt  = new  Label("    Customer's Last name:");
		Label phonePrompt  = new  Label("Customer's Primary Phone:");
		Label emailPrompt  = new  Label("Customer's Primary Email:");
		Label addr00Prompt = new Label(" Customer's Primary Addr:");
		Label addr01Prompt = new Label(" Customer's P. A. line 2:");
		Label addr02Prompt = new Label(" Customer's P. A. line 3:");
		Label addr03Prompt = new Label(" Customer's P. A. line 4:");
		Label addr04Prompt = new Label(" Customer's P. A. line 5:");
		Label dobPrompt    = new Label("Customer's Date of Birth:");
		Label balduePrompt = new Label("  Customer's Balance Due:");
		Label custidPrompt = new Label("  Customer's Id:");
			
		Label updatePrompt = new Label("Update");
		Label backPrompt = new Label("Back to Menu");
		Label exitPrompt = new Label("Click this Button to exit");
			
		TextField fnameText  = new TextField();
		TextField mnameText  = new TextField();
		TextField lnameText  = new TextField();
		TextField pPhoneText = new TextField();
		TextField pEmailText = new TextField();
		TextField Addr00Text = new TextField();
		TextField Addr01Text = new TextField();
		TextField Addr02Text = new TextField();
		TextField Addr03Text = new TextField();
		TextField Addr04Text = new TextField();
		TextField dobText    = new TextField();
		TextField baldueText = new TextField();
		TextField custidText = new TextField();
		
		TextField newFnameText  = new TextField();
		TextField newMnameText  = new TextField();
		TextField newLnameText  = new TextField();
		TextField newpPhoneText = new TextField();
		TextField newpEmailText = new TextField();
		TextField newAddr00Text = new TextField();
		TextField newAddr01Text = new TextField();
		TextField newAddr02Text = new TextField();
		TextField newAddr03Text = new TextField();
		TextField newAddr04Text = new TextField();
		TextField newDobText    = new TextField();
		TextField newBalDueText = new TextField();

		fnameText.setText(data.getFname());
		mnameText.setText(data.getMinit());
		lnameText.setText(data.getLname());
		pPhoneText.setText(data.getPrimaryPhone());
		pEmailText.setText(data.getPrimaryEmail());	
		address = data.getAddr(0);
		Addr00Text.setText(address[0]);
		Addr01Text.setText(address[1]);
		Addr02Text.setText(address[2]);
		Addr03Text.setText(address[3]);
		Addr04Text.setText(address[4]);
		dobText.setText(data.getDob());
		baldueText.setText( String.format("%.02f",data.getBalanceDue()) );
		custidText.setText( String.format("%d", custid) );

		Button updateButton = new Button("Update");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");
		
		banner.setFont(Font.font("Ariel",24)); // probably redundant
		
		currentInfo.setFont(Font.font("Ariel",18));
		updateInfo.setFont(Font.font("Ariel",18));
		
		fnamePrompt.setFont(Font.font("Ariel",18));
		mnamePrompt.setFont(Font.font("Ariel",18));
		lnamePrompt.setFont(Font.font("Ariel",18));
		phonePrompt.setFont(Font.font("Ariel",18)); 
		emailPrompt.setFont(Font.font("Ariel",18));
		addr00Prompt.setFont(Font.font("Ariel",18)); 
		addr01Prompt.setFont(Font.font("Ariel",18));
		addr02Prompt.setFont(Font.font("Ariel",18));
		addr03Prompt.setFont(Font.font("Ariel",18));
		addr04Prompt.setFont(Font.font("Ariel",18));
		dobPrompt.setFont(Font.font("Ariel",18));
		balduePrompt.setFont(Font.font("Ariel",18));
		custidPrompt.setFont(Font.font("Ariel",18));
		
		fnameText.setFont(Font.font("Ariel",18));
		mnameText.setFont(Font.font("Ariel",18));
		lnameText.setFont(Font.font("Ariel",18));
		pPhoneText.setFont(Font.font("Ariel",18)); 
		pEmailText.setFont(Font.font("Ariel",18));
		Addr00Text.setFont(Font.font("Ariel",18)); 
		Addr01Text.setFont(Font.font("Ariel",18));
		Addr02Text.setFont(Font.font("Ariel",18));
		Addr03Text.setFont(Font.font("Ariel",18));
		Addr04Text.setFont(Font.font("Ariel",18));
		dobText.setFont(Font.font("Ariel",18));
		baldueText.setFont(Font.font("Ariel",18));
		
		newFnameText.setFont(Font.font("Ariel",18));
		newMnameText.setFont(Font.font("Ariel",18));
		newLnameText.setFont(Font.font("Ariel",18));
		newpPhoneText.setFont(Font.font("Ariel",18)); 
		newpEmailText.setFont(Font.font("Ariel",18));
		newAddr00Text.setFont(Font.font("Ariel",18)); 
		newAddr01Text.setFont(Font.font("Ariel",18));
		newAddr02Text.setFont(Font.font("Ariel",18));
		newAddr03Text.setFont(Font.font("Ariel",18));
		newAddr04Text.setFont(Font.font("Ariel",18));
		newDobText.setFont(Font.font("Ariel",18));
		newBalDueText.setFont(Font.font("Ariel",18));
	
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
		HBox phoneHbox  = new HBox(10, phonePrompt, pPhoneText);
		HBox emailHbox  = new HBox(10, emailPrompt, pEmailText);
		HBox addr00Hbox = new HBox(10, addr00Prompt, Addr00Text);
		HBox addr01Hbox = new HBox(10, addr01Prompt, Addr01Text);
		HBox addr02Hbox = new HBox(10, addr02Prompt, Addr02Text);
		HBox addr03Hbox = new HBox(10, addr03Prompt, Addr03Text);
		HBox addr04Hbox = new HBox(10, addr04Prompt, Addr04Text);
		HBox dobHbox    = new HBox(10, dobPrompt, dobText);
		HBox baldueHbox = new HBox(10, balduePrompt, baldueText);
		HBox custidHbox = new HBox(10, custidPrompt, custidText);
		
		HBox newFnameHbox  = new HBox(10, newFnameText);
		HBox newMnameHbox  = new HBox(10, newMnameText);
		HBox newLnameHbox  = new HBox(10, newLnameText);
		HBox newPhoneHbox  = new HBox(10, newpPhoneText);
		HBox newEmailHbox  = new HBox(10, newpEmailText);
		HBox newAddr00Hbox = new HBox(10, newAddr00Text);
		HBox newAddr01Hbox = new HBox(10, newAddr01Text);
		HBox newAddr02Hbox = new HBox(10, newAddr02Text);
		HBox newAddr03Hbox = new HBox(10, newAddr03Text);
		HBox newAddr04Hbox = new HBox(10, newAddr04Text);
		HBox newDobHbox    = new HBox(10, newDobText);
		HBox newBalDueHbox = new HBox(10, newBalDueText);
				
		HBox updateButtonHbox = new HBox(10, updateButton);
		HBox backButtonHbox = new HBox(10, backButton);
		HBox exitButtonHbox = new HBox(10, exitButton);
		
		fnameHbox.setAlignment(Pos.CENTER_LEFT);
		mnameHbox.setAlignment(Pos.CENTER_LEFT);
		lnameHbox.setAlignment(Pos.CENTER_LEFT);
		phoneHbox.setAlignment(Pos.CENTER_LEFT);
		emailHbox.setAlignment(Pos.CENTER_LEFT);
		addr00Hbox.setAlignment(Pos.CENTER_LEFT);
		addr01Hbox.setAlignment(Pos.CENTER_LEFT);
		addr02Hbox.setAlignment(Pos.CENTER_LEFT);
		addr03Hbox.setAlignment(Pos.CENTER_LEFT);
		addr04Hbox.setAlignment(Pos.CENTER_LEFT);
		dobHbox.setAlignment(Pos.CENTER_LEFT);
		baldueHbox.setAlignment(Pos.CENTER_LEFT);
		custidHbox.setAlignment(Pos.CENTER_LEFT);
						
		updateButtonHbox.setAlignment(Pos.CENTER_LEFT);
		backButtonHbox.setAlignment(Pos.CENTER_LEFT);
		exitButtonHbox.setAlignment(Pos.CENTER_LEFT);
			
		newFnameHbox.setAlignment(Pos.CENTER_LEFT);
		newMnameHbox.setAlignment(Pos.CENTER_LEFT);
		newLnameHbox.setAlignment(Pos.CENTER_LEFT);
		newPhoneHbox.setAlignment(Pos.CENTER_LEFT);
		newEmailHbox.setAlignment(Pos.CENTER_LEFT);
		newAddr00Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr01Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr02Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr03Hbox.setAlignment(Pos.CENTER_LEFT);
		newAddr04Hbox.setAlignment(Pos.CENTER_LEFT);
		newDobHbox.setAlignment(Pos.CENTER_LEFT);
		newBalDueHbox.setAlignment(Pos.CENTER_LEFT);
						
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
		
		grid.add(phoneHbox, 0, 5);
		grid.add(newPhoneHbox, 1, 5);
		
		grid.add(emailHbox, 0, 6);
		grid.add(newEmailHbox, 1, 6);
		
		grid.add(addr00Hbox, 0, 7);
		grid.add(newAddr00Hbox, 1, 7);
		
		grid.add(addr01Hbox, 0, 8);
		grid.add(newAddr01Hbox, 1, 8);
		
		grid.add(addr02Hbox, 0, 9);
		grid.add(newAddr02Hbox, 1, 9);
		
		grid.add(addr03Hbox, 0, 10);
		grid.add(newAddr03Hbox, 1, 10);
		
		grid.add(addr04Hbox, 0, 11);
		grid.add(newAddr04Hbox, 1, 11);
		
		grid.add(dobHbox, 0, 12);
		grid.add(newDobHbox, 1, 12);
		
		grid.add(baldueHbox, 0, 13);
		grid.add(newBalDueHbox, 1, 13);
		
		grid.add(custidHbox, 0, 14);
					
		grid.add(updateButtonHbox, 0, 15);
		grid.add(backButtonHbox, 0, 16);
		grid.add(exitButtonHbox, 0, 17);
	
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
			if ( (newpPhoneText.getText() != null) &&
				(newpPhoneText.getText().length() != 0) )
				data.setPrimaryPhone(newpPhoneText.getText());
			if ( (newpEmailText.getText() != null) &&
				(newpEmailText.getText().length() != 0) )
				data.setPrimaryEmail(newpEmailText.getText());
					
			String[] newaddress = {null, null, null, null, null};
			if ( (newAddr00Text.getText() != null) &&
				(newAddr00Text.getText().length() != 0) )
				newaddress[0] = newAddr00Text.getText();
			if ( (newAddr01Text.getText() != null) &&
				(newAddr01Text.getText().length() != 0) )
				newaddress[1] = newAddr01Text.getText();
			if ( (newAddr02Text.getText() != null) &&
				(newAddr02Text.getText().length() != 0) )
				newaddress[2] = newAddr02Text.getText();
			if ( (newAddr03Text.getText() != null) &&
				(newAddr03Text.getText().length() != 0) )
				newaddress[3] = newAddr03Text.getText();
			if ( (newAddr04Text.getText() != null) &&
				(newAddr04Text.getText().length() != 0) )
				newaddress[4] = newAddr04Text.getText();	
			data.setAddr(newaddress, 0);
			
			if ( (newDobText.getText() != null) &&
				(newDobText.getText().length() != 0) )
				data.setDob(newDobText.getText());
			if ( (newBalDueText.getText() != null) &&
				(newBalDueText.getText().length() != 0) )
				// data.setBalanceDue(newBalDueText.getText());
				data.setBalanceDue(Double.parseDouble(newBalDueText.getText()));
						
			try
        	{               			
				this.updateCustomer(myStage);
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
		  
	public void updateCustomer(Stage stage) 
		throws SQLException, FileNotFoundException
	{
		int i = 0;
		boolean bValue = false;
		
		System.out.println("updateCustomer() - enter");

		if ( !data.getFname().equals(originaldata.getFname()) )
		{
			TransactionApp.CustomerDBaccess.updateCustomerFname(data);
		} 
		if ( !data.getMinit().equals(originaldata.getMinit()) )
		{
			TransactionApp.CustomerDBaccess.updateCustomerMinit(data);
		} 
		if ( !data.getLname().equals(originaldata.getLname()) )
		{
			TransactionApp.CustomerDBaccess.updateCustomerLname(data);
		}
		if ( data.getPrimaryPhone() != null && originaldata.getPrimaryPhone() != null 
			&& !data.getPrimaryPhone().equals(originaldata.getPrimaryPhone()) )
		{
			TransactionApp.CustomerDBaccess.updateCustomerPrimaryPhone(data);
		}
		if ( data.getPrimaryEmail() != null && originaldata.getPrimaryEmail() != null 
			&& !data.getPrimaryEmail().equals(originaldata.getPrimaryEmail()) )		 
		{
			TransactionApp.CustomerDBaccess.updateCustomerPrimaryEmail(data);
		} 
		if ( data.getDob() != null)
		{
			if ( originaldata.getDob() == null
				|| !data.getDob().equals(originaldata.getDob()) )
			{
				TransactionApp.CustomerDBaccess.updateCustomerDob(data);
			} 
		}
		if ( data.getBalanceDue() != originaldata.getBalanceDue() )
		{
			TransactionApp.CustomerDBaccess.updateCustomerBalanceDue(data);
		} 
		
		String [] oldaddress;
		String [] newaddress;
		oldaddress = originaldata.getAddr(0);
		newaddress = data.getAddr(0);
		bValue = false;
		for (i = 0; i < 5; i++)
		{
			if ( newaddress[i] != null )
			{
				if ( oldaddress[i] != null)
				{
					if ( !newaddress[i].equals(oldaddress[i]) )
						bValue = true;
				}
				else
				{
					bValue = true;
				}
			}				
		}
		if ( bValue )
		{
			TransactionApp.CustomerDBaccess.updateCustomerAddress(data,0);
		} 
		
		System.out.println("displayCustomer() -  call displayCustomer");
		displayCustomer(myStage);		
	} // End of updateCustomer()

}

		
				
