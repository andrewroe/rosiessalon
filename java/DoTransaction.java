import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JOptionPane; 
import java.sql.*;
import java.io.*;  	
import javafx.application.Application;
import javafx.stage.Stage;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
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
		     	
public class DoTransaction
{
	
	protected Stage myStage;
	protected CustData Cdata = new CustData();
	protected CustInfoDB custinfo = new CustInfoDB();
	protected Label banner = new Label("Transaction");
	
	protected ArrayList<ProductData> products = new ArrayList<>();
	protected String[] strProdItems;
	protected ListView<String> prodList = new ListView<>();
	protected Label productsLabel = new Label("Select a Product");
	
	protected ArrayList<ServiceData> services = new ArrayList<>();
	protected String[] strServItems;
	protected ListView<String> servList = new ListView<>();
	protected Label servicesLabel = new Label("Select a Service");
		
	protected ArrayList<TransDetailData> transDetails = new ArrayList<>();
	protected String[] strTransDetails;	
	protected ListView<String> itemList = new ListView<>();
	protected Label TransDetailLabel = new Label("Sub-Total items???");
	
	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public void beginTransaction(Stage stage) throws SQLException
	{
		myStage = stage;
		int i = 0;
		String tempStr;
		
		products = TransactionApp.TransactionDBaccess.fetchAllProductData();
		services = TransactionApp.TransactionDBaccess.fetchAllServiceData();
		
		i = 0;
		strProdItems = new String[products.size()];
		for (ProductData Pdata : products)
		{
			tempStr = new String();
			tempStr.format("%s $%.0f", Pdata.Pname, Pdata.Price);
			strProdItems[i++] = tempStr;
		}
		prodList.getItems().addAll(strProdItems);

		i = 0;
		strServItems = new String[services.size()];
		for (ServiceData Sdata : services)
		{
			tempStr = new String();
			tempStr.format("%s $%.0f", Sdata.Sname, Sdata.Price);
			strServItems[i++] = tempStr;
		}
		servList.getItems().addAll(strServItems);
		

		String fname = null;
		String mname = null;
		String lname = null;
			
		Label banner = new Label("New Transaction");
		Label fnamePrompt = new Label("Customer's first name:");
		Label mnamePrompt = new Label("Customer's middle name:");
		Label lnamePrompt = new Label("Customer's last name:");
		banner.setFont(Font.font("Ariel",24));
		fnamePrompt.setFont(Font.font("Ariel",18));
		mnamePrompt.setFont(Font.font("Ariel",18));
		lnamePrompt.setFont(Font.font("Ariel",18));
				
		Label custidLabel = new Label();
		custidLabel.setFont(Font.font("Ariel",18));		
		
		Label findPrompt = new Label("Submit");
		Label backPrompt = new Label("Back to Menu");
		Label exitPrompt = new Label("Click to exit Application");
		findPrompt.setFont(Font.font("Ariel",18));
		backPrompt.setFont(Font.font("Ariel",18));
		exitPrompt.setFont(Font.font("Ariel",18));		
			
		TextField fnameText = new TextField();
		TextField mnameText = new TextField();
		TextField lnameText = new TextField();	
		fnameText.setText("fill in");
		mnameText.setText("fill in");
		lnameText.setText("fill in");
		fnameText.setFont(Font.font("Ariel",18));
		mnameText.setFont(Font.font("Ariel",18));
		lnameText.setFont(Font.font("Ariel",18));
		
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back to Main");
		Button exitButton = new Button("Exit");	
		submitButton.setFont(Font.font("Ariel",18));
		backButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
			
		HBox bannerHbox = new HBox(banner);
		
		HBox fnameHbox = new HBox(10, fnamePrompt, fnameText);
		HBox mnameHbox = new HBox(10, mnamePrompt, mnameText);
		HBox lnameHbox = new HBox(10, lnamePrompt, lnameText);
		fnameHbox.setAlignment(Pos.CENTER_LEFT);
		mnameHbox.setAlignment(Pos.CENTER_LEFT);
		lnameHbox.setAlignment(Pos.CENTER_LEFT);
				
		HBox submitHbox = new HBox(10, submitButton);
		HBox backHbox = new HBox(10, backButton);
		HBox exitHbox = new HBox(10, exitButton);
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
				Cdata.setFname(fnameText.getText());
				Cdata.setMinit(mnameText.getText());
				Cdata.setLname(lnameText.getText());
				
				System.out.println("Submit - values = " +
					Cdata.getFname() + " " + Cdata.getMinit() + " " + Cdata.getLname());				
							
				if (TransactionApp.CustomerDBaccess.searchCustomerByFullName(Cdata) == 1)
				{
					custidLabel.setText(String.format("Customer ID = %d",
						Cdata.getCustID()));
				
					this.transactionScreen(myStage);	
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
		  
	public void transactionScreen(Stage stage) 
		throws SQLException, FileNotFoundException
	{
		int i = 0;
		String tempStr;
		
		System.out.println("transactionScreen() - enter");
	
		i = 0;
		
		if (transDetails.size() > 0)
		{
			strTransDetails = new String[transDetails.size()];
			for (TransDetailData TDdata : transDetails)
			{
				tempStr = new String();
				tempStr.format("%s $%.0f", TDdata.CharBig, TDdata.Nbr2Parm);
				strTransDetails[i++] = tempStr;
			}		
		}
		else
		{
			strTransDetails = new String[1];
			strTransDetails[i++] = "empty";
		}

		itemList.getItems().addAll(strTransDetails);
		        
		//ComboBox productsComboBox = new ComboBox();
        //productsComboBox.getItems().addAll(strTransDetails);        
 	
		prodList.setPrefSize(300,200);
		servList.setPrefSize(300,200);						
		itemList.setPrefSize(600,400); 
 	 	
		Label banner = new Label("Transaction");
		banner.setFont(Font.font("Ariel",24));
		HBox bannerHbox = new HBox(banner);
		
		Label selectServiceLabel = new Label("Select a Service");
		Button getServiceButton = new Button("Add Service to Transaction");
		getServiceButton.setOnAction(e ->
		{
			String lambdaStr;
			lambdaStr = servList.getSelectionModel().getSelectedItem();
			selectServiceLabel.setText(lambdaStr);
			itemList.getItems().addAll(lambdaStr);
			
			try
			{
				this.transactionScreen(myStage);
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
	
		Label selectProductLabel = new Label("Select a Product");
		Button getProductButton = new Button("Add Product to Transaction");
		getProductButton.setOnAction(e ->
		{
			String lambdaStr;
			lambdaStr = prodList.getSelectionModel().getSelectedItem();
			selectProductLabel.setText(lambdaStr);
			itemList.getItems().addAll(lambdaStr);
			
			try
			{
				this.transactionScreen(myStage);
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

		Label selectItemLabel = new Label("Select to Remove an Item");
		Button removeItemButton = new Button("Remove");
		removeItemButton.setOnAction(e ->
		{
			int index;	
			index = itemList.getSelectionModel().getSelectedIndex();
			if (index >= 0) {
    			itemList.getItems().remove(index);
			}
			
			try
			{
				this.transactionScreen(myStage);
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


		VBox servicesVbox = 
			new VBox(10, servList, selectServiceLabel, getServiceButton);
		servicesVbox.setPadding(new Insets(10));
		servicesVbox.setAlignment(Pos.CENTER);	
		
		VBox productsVbox = 
			new VBox(10, prodList, selectProductLabel, getProductButton);
		productsVbox.setPadding(new Insets(10));
		productsVbox.setAlignment(Pos.CENTER);	
		
		VBox itemsVbox = 
			new VBox(10, itemList, selectItemLabel, removeItemButton);
		itemsVbox.setPadding(new Insets(10));
		itemsVbox.setAlignment(Pos.CENTER);	
											
		Label backPrompt = new Label("Back to Menu");
		backPrompt.setFont(Font.font("Ariel",18));	
		Button backButton = new Button("Back to Main");
		backButton.setFont(Font.font("Ariel",18));
		HBox backButtonHbox = new HBox(10, backButton);
		backButtonHbox.setAlignment(Pos.CENTER_LEFT);
				
		Label exitPrompt = new Label("Click this Button to exit");
		exitPrompt.setFont(Font.font("Ariel",18));	
		Button exitButton = new Button("Exit");
		exitButton.setFont(Font.font("Ariel",18));
		HBox exitButtonHbox = new HBox(10, exitButton);
		exitButtonHbox.setAlignment(Pos.CENTER_LEFT);
		
		// Button Handling
		
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
				TransactionApp.TransactionDBaccess.DisconnectFromDB();
				TransactionApp.CustomerDBaccess.DisconnectFromDB();
				TransactionApp.EmployeeDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
																	
		GridPane grid = new GridPane();
		grid.add(bannerHbox, 1, 0, 2, 1);
		grid.add(servicesVbox, 0, 1, 2, 1);
		grid.add(productsVbox, 2, 1, 2, 1);
		grid.add(itemsVbox, 0, 2, 4, 1);
		grid.add(backButtonHbox, 0, 3);
		grid.add(exitButtonHbox, 0, 4);	
				
		Scene transactionScene = new Scene(grid,800,800);

		stage.setScene(transactionScene);
 		stage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		stage.show();						
	}

}

		
				