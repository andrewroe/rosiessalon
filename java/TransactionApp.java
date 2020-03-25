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
		     	
public class TransactionApp extends Application 
{
	public enum UsersChoice
	{
		exit,
		createTrans,
		findTrans,
		updateTrans,
		voidTrans,
		invalidChoice
	}
	
	// protected static String CredentialsFile = "/Users/andrewroe/temp/credentials.txt";
	protected static String CredentialsFile = null;
	protected static EmpDBaccess EmployeeDBaccess = new EmpDBaccess();
	protected static CustDBaccess CustomerDBaccess = new CustDBaccess();
	protected static CustInfoDB custinfoDB = new CustInfoDB();
	protected static Button dbconnectButton = new Button("DB connect");
	protected static Button dbdisconnectButton = new Button("DB disconnect");
	protected static Button signinButton = new Button("Submit");
	protected static Button exitButton = new Button("Exit");
	protected static TextField dbcredfileText = new TextField();
	protected static TextField usernameText = null;
	protected static TextField passwordText = null;
	protected static String dbcredentialsfile = "NotConnected";
	protected static boolean dbconnected = false; 
	protected static Label userPrompt = new Label("enter user name");
	protected static Label passwordPrompt = new Label("enter Password");
	protected static int userid = 0;		// the user's EmpID
	protected static int logintries = 0;
	//protected static boolean validEmployeeData = false;
	protected static Stage mainStage;

			
	@Override
	public void start(Stage primaryStage) 
			throws SQLException, FileNotFoundException
	{
		//UsersChoice choice = UsersChoice.invalidChoice;	// The user's choice      
        //int empid = 0;		// the employee's EmpID
        //EmpData userData = new EmpData();
        //EmpData employeeData = new EmpData();
     
     	mainStage = primaryStage;
        // Stage title
 		mainStage.setTitle("Rosie Salon Transaction GUI Application");  	
	    
	    promptForConnectToDB(mainStage);   
        // handlelogin(mainStage);
 
	}  // End of start()
	

	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void promptForConnectToDB(Stage primaryStage) 
		throws SQLException, FileNotFoundException
	{
		Label dbScreen = new Label("Database Connect"); 
		Label dbcredPrompt = new Label("DB credentials filename");
		Label dbconnectPrompt = new Label("DB connect");
		Label exitPrompt = new Label("Click Button to exit");
		
		dbScreen.setFont(Font.font("Ariel",24));
		dbcredPrompt.setFont(Font.font("Ariel",18));
		dbconnectPrompt.setFont(Font.font("Ariel",18));
		exitPrompt.setFont(Font.font("Ariel",18));
		
		Image yogaDoor = new Image("file:yogadoor.jpg");
		ImageView imageDoor = new ImageView(yogaDoor);
		imageDoor.setFitWidth(400);
		imageDoor.setFitHeight(400);
		imageDoor.setPreserveRatio(true);
		
		dbcredentialsfile = "NotConnected";
		dbcredfileText.setText(dbcredentialsfile);
		dbcredfileText.setFont(Font.font("Ariel",18));
		
		dbconnectButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
		
		HBox doorHbox = new HBox(imageDoor);
		HBox dbscreenHbox = new HBox(dbScreen);				
		HBox credHbox = new HBox(10, dbcredPrompt, dbcredfileText);
		HBox connectHbox = new HBox(10, dbconnectPrompt, dbconnectButton);
		HBox exitHbox = new HBox(10, exitPrompt, exitButton);
		
		doorHbox.setAlignment(Pos.CENTER);
		dbscreenHbox.setAlignment(Pos.CENTER);
		credHbox.setAlignment(Pos.CENTER);
		connectHbox.setAlignment(Pos.CENTER);
		exitHbox.setAlignment(Pos.CENTER);
		
		VBox dbconnectVbox = 
			new VBox(20,doorHbox, dbscreenHbox, credHbox, connectHbox, exitHbox);
		dbconnectVbox.setAlignment(Pos.CENTER);
				
		dbconnectButton.setOnAction(event ->
		{
			TransactionApp.dbcredentialsfile = TransactionApp.dbcredfileText.getText();

			try
			{	
   				connectToDB();	// local method
   				CredentialsFile = TransactionApp.dbcredentialsfile;				
			}
        	catch (SQLException ex)
        	{
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            	//TransactionApp.dbcredentialsfile="NotConnected";
            	dbcredentialsfile = "NotConnected";
        	}			
        	catch (FileNotFoundException ex)
        	{
            	System.out.println(ex.getMessage());
            	System.out.println("Got a File Not Found exception!");
            	//TransactionApp.dbcredentialsfile="NotConnected";
            	dbcredentialsfile = "NotConnected";
        	}
        	   					
		});
		
		exitButton.setOnAction(event ->
		{
			System.out.println("Good bye.");
        	System.exit(0);		
		});
		
		Scene dbconnectScene = new Scene(dbconnectVbox,500,800);
		primaryStage.setScene(dbconnectScene);
 		primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		primaryStage.show();
 	
	}

	
	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void handlelogin(Stage primaryStage) 
		throws SQLException, FileNotFoundException
	{
		Label loginScreen = new Label("Login Screen - 3 tries max"); // ??
		Label userPrompt = new Label("User name");
		Label passwordPrompt = new Label("Password");
		Label signinPrompt = 
			new Label("Click Button for User Sign-in");
		Label disconnectPrompt = new Label("DB disconnect");	
		Label exitPrompt = new Label("Click Button to exit");
			
		disconnectPrompt.setFont(Font.font("Ariel",18));
		userPrompt.setFont(Font.font("Ariel",18));
		passwordPrompt.setFont(Font.font("Ariel",18));
		exitPrompt.setFont(Font.font("Ariel",18));
			
		switch (logintries)
		{
			case 0:
				loginScreen = new Label("Login Screen");
				break;
			case 1:
				loginScreen = new Label("Login Screen - 2nd attempt");
				break;
			case 2:
				loginScreen = new Label("Login Screen - 3rd attempt");
				break;
			default:
				System.out.println("Too many login attempts! Bye.");
				System.exit(0);
				break;
		}
		
		loginScreen.setFont(Font.font("Ariel",24));
		
		Image yogaDoor = new Image("file:yogadoor.jpg");
		ImageView imageDoor = new ImageView(yogaDoor);
		imageDoor.setFitWidth(400);
		imageDoor.setFitHeight(400);
		imageDoor.setPreserveRatio(true);
		
		usernameText = new TextField();
		passwordText = new TextField();
		
		usernameText.setFont(Font.font("Ariel",18));
		passwordText.setFont(Font.font("Ariel",18));

		HBox doorHbox = new HBox(imageDoor);				
		HBox loginHbox = new HBox(loginScreen);
		HBox userHbox = new HBox(10, userPrompt, usernameText);
		HBox pwdHbox = new HBox(10, passwordPrompt, passwordText);
		HBox signinHbox = new HBox(10, signinButton);
		HBox disconnectHbox = new HBox(10, dbdisconnectButton);
		HBox exitHbox = new HBox(10, exitPrompt, exitButton);
		
		doorHbox.setAlignment(Pos.CENTER);
		loginHbox.setAlignment(Pos.CENTER);
		userHbox.setAlignment(Pos.CENTER);
		pwdHbox.setAlignment(Pos.CENTER);
		signinHbox.setAlignment(Pos.CENTER);
		disconnectHbox.setAlignment(Pos.CENTER);
		exitHbox.setAlignment(Pos.CENTER);
		
		VBox loginVbox = 
			new VBox(20,doorHbox,loginHbox,userHbox,
			pwdHbox,signinHbox,disconnectHbox,exitHbox);
			
		loginVbox.setAlignment(Pos.CENTER);
		
		dbdisconnectButton.setFont(Font.font("Ariel",18));
		signinButton.setFont(Font.font("Ariel",18));
		exitButton.setFont(Font.font("Ariel",18));
			
		dbdisconnectButton.setOnAction(event ->
		{
			try
			{	
   				TransactionApp.EmployeeDBaccess.DisconnectFromDB();
   				TransactionApp.CustomerDBaccess.DisconnectFromDB();	
   				TransactionApp.dbconnected = false;
   				promptForConnectToDB(TransactionApp.mainStage);		
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
		
		signinButton.setOnAction(new ButtonClickHandler());
		
		exitButton.setOnAction(event ->
		{
			System.out.println("Good bye.");
			try
        	{               			
				EmployeeDBaccess.DisconnectFromDB();
				CustomerDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
		
		Scene loginScene = new Scene(loginVbox,500,800);

		primaryStage.setScene(loginScene);
 		primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		primaryStage.show();							
	}

	
	/**		     	
		This method is for presenting the main action choices to the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void menu(Stage primaryStage) throws SQLException
	{
		//int trycount = 0;
		int empid = 0;
		String username = null;				
		username = usernameText.getText();
		FindCustomer findcustomer = new FindCustomer();
		AddCustomer addcustomer = new AddCustomer();		
		
		Label topbanner = new Label("Rosie's Salon");
		Label bottombanner = new Label("UserName: " + username);
		Label leftbanner = new Label("Select Action Area");
		Label centerbanner = new Label("Center Border Area");
		Label rightbanner = new Label("Select Sub-Action Area");
		
		topbanner.setFont(Font.font("Ariel",48));
		bottombanner.setFont(Font.font("Ariel",24));
				
		// giving up on toggle group
		Label mainPrompt = new Label("           Main Menu");
		Button bMain = new Button("Go");
		bMain.setOnAction(event ->
		{
			try
			{
				menu(mainStage);	
			}
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }							
		});		
		
		HBox mainHbox = new HBox(10, mainPrompt, bMain);
		
		Label findCustPrompt = new Label("        Find Customer");
		Button bFindCust = new Button("Go");
		
		bFindCust.setOnAction(event ->
		{
			try
			{
				findcustomer.findCustomer(mainStage);	
			}
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }							
		});		
		
		HBox findCustHbox = new HBox(10, findCustPrompt, bFindCust);
		
		Label addCustPrompt = new Label("         Add Customer");
		Button bAddCust = new Button("Go");
		bAddCust.setOnAction(event ->
		{
			try
			{
				addcustomer.addCustomer(mainStage);	
			}
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }							
		});				
		
		HBox addCustHbox = new HBox(10, addCustPrompt, bAddCust);
		
		Label updateCustPrompt = new Label("      Update Customer");
		Button bUpdateCust = new Button("Go");
		bUpdateCust.setOnAction(new ButtonClickHandler());
		HBox updateCustHbox = new HBox(10, updateCustPrompt, bUpdateCust);
		
		Label newTransPrompt = new Label("     New Transaction");
		Button bNewTrans = new Button("Go");
		bNewTrans.setOnAction(new ButtonClickHandler());
		HBox newTransHbox = new HBox(10, newTransPrompt, bNewTrans);
		
		Label findTransPrompt = new Label("    Find Transaction");
		Button bFindTrans = new Button("Go");
		bFindTrans.setOnAction(new ButtonClickHandler());
		HBox findTransHbox = new HBox(10, findTransPrompt, bFindTrans);
		
		Label updateTransPrompt = new Label("  Update Transaction");
		Button bUpdateTrans = new Button("Go");
		bUpdateTrans.setOnAction(new ButtonClickHandler());
		HBox updateTransHbox = new HBox(10, updateTransPrompt, bUpdateTrans);
		
		Label signOutPrompt = new Label("            Sign Out");
		Button bSignOut = new Button("Go");
		bSignOut.setOnAction(event ->
		{
			userid = 0;
			logintries = 0;
			try
			{
				handlelogin(mainStage);	
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
					
		HBox signOutHbox = new HBox(10, signOutPrompt, bSignOut);
				
		Label exitPrompt = new Label("        Exit Program");
		Button bExit = new Button("Go");
		
		// a Lambda
		bExit.setOnAction(event ->
		{
			System.out.println("Good bye.");
			try
        	{               			
				EmployeeDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println(ex.getMessage());
            	System.out.println("Got a SQL exception!");
            }    
        	System.exit(0);		
		});
		
		HBox exitHbox = new HBox(10, exitPrompt, bExit);
		
 				
		VBox actionVbox = 
			new VBox(20,mainHbox,findCustHbox,addCustHbox,updateCustHbox,
			newTransHbox,findTransHbox,updateTransHbox,signOutHbox,exitHbox);
					
		ImageView imageview = new ImageView();
		Image yogaDoor = new Image("file:yogadoor.jpg");
		ImageView imageDoor = new ImageView(yogaDoor);
		imageDoor.setFitWidth(600);
		imageDoor.setFitHeight(600);
		imageDoor.setPreserveRatio(true);
		imageview.setImage(yogaDoor);
		
		HBox bannerHbox = new HBox(topbanner);
		HBox footerHbox = new HBox(bottombanner);
		HBox centerHbox = new HBox(centerbanner);
		
		VBox rightVbox = new VBox(rightbanner);		
		
		bannerHbox.setAlignment(Pos.CENTER);
		footerHbox.setAlignment(Pos.CENTER);
						
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(bannerHbox);
		borderPane.setBottom(footerHbox);
		//borderPane.setLeft(leftVbox);
		borderPane.setLeft(actionVbox);
		borderPane.setCenter(imageview);	
		borderPane.setRight(rightVbox);
				
		Scene menuScene = new Scene(borderPane,800,800);
		menuScene.getStylesheets().add("Transaction.css");
		primaryStage.setScene(menuScene);
 		primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		primaryStage.show();
 				
	}
	
	/**		     	
		This method is for 
		
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void connectToDB() 
		throws SQLException, FileNotFoundException
	{
		try
		{
 			if (!EmployeeDBaccess.ConnectToDB(dbcredentialsfile))
   			{
   				System.out.println("Can not connect to Employee I/F to DB.");
   				dbcredentialsfile = "NotConnected";
				dbconnected = false; 
   			}  	
 			if (!CustomerDBaccess.ConnectToDB(dbcredentialsfile))
   			{
   				System.out.println("Can not connect to Customer I/F to DB.");
   				dbcredentialsfile = "NotConnected";
				dbconnected = false; 
   			} 
 			if (!custinfoDB.ConnectToDB(dbcredentialsfile))
   			{
   				System.out.println("Can not connect to Customer I/F to DB.");
   				dbcredentialsfile = "NotConnected";
				dbconnected = false; 
   			} 
   			
   			dbconnected = true;  
   			handlelogin(mainStage);		
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
 		
 		return;					
	}

	/**		     	
		This method is for login handling of the user.
		
   		@param primaryStage which is 
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void disconnectFromDB(Stage primaryStage) 
		throws SQLException, FileNotFoundException
	{
		EmployeeDBaccess.DisconnectFromDB();
		CustomerDBaccess.DisconnectFromDB();
		promptForConnectToDB(primaryStage);
		return;
	}
						
	
	/*
	private static final Logger LOG =
	Logger.getLogger(ExampleApplication.class.getCanonicalName());
	*/	
	
}
	

class ButtonClickHandler implements EventHandler<ActionEvent>
{		
	private static EmpDBaccess EmployeeDBaccess = new EmpDBaccess();
	
	@Override
	public void handle(ActionEvent event)
	{		
        // sign-in?
		if (event.getSource() == TransactionApp.signinButton)
		{
			int userid = 0;
			String username = null;
			String userpassword = null;
			String passwordInDB = null;
				
			username = TransactionApp.usernameText.getText();
			userpassword = TransactionApp.passwordText.getText();
			
			System.out.println("User login: " + "username = " + username + 
				" password = " + userpassword);

			try
        	{
                userid = TransactionApp.EmployeeDBaccess.fetchUser(username);
                if (userid != 0)
                {
                    passwordInDB = TransactionApp.EmployeeDBaccess.fetchPassword(userid);

                    if (userpassword.compareTo(passwordInDB) == 0)
                    {
                        System.out.println("found the user, display menu.");
                        TransactionApp.userid = userid;
                        TransactionApp.logintries = 0;
                        TransactionApp.menu(TransactionApp.mainStage);
                    }
                    else
                    {
                        System.out.println("user password wrong, try again!");
                        TransactionApp.userid = 0;
                        TransactionApp.logintries++;
                        TransactionApp.handlelogin(TransactionApp.mainStage);
                    }                                       
                }   
                else
                {
                    System.out.println("username wrong, try again!");
                    TransactionApp.userid = 0;
                    TransactionApp.logintries++;
                    TransactionApp.handlelogin(TransactionApp.mainStage);
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
        } // End of sign-in
		        
        // none of the above
        else
        {
        }		        
        		
	}
	
}

					
		
				
