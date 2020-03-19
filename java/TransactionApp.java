import java.util.Scanner; 
import javax.swing.JOptionPane; 
import java.sql.*;  	
import javafx.application.Application;
import javafx.stage.Stage;
// import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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
	
	protected static EmpDBaccess EmployeeDBaccess = new EmpDBaccess();
	protected static Button signinButton = new Button("Sign-In");
	protected static Button exitButton = new Button("Exit");
	protected static TextField usernameText = null;
	protected static TextField passwordText = null;
	protected static int userid = 0;		// the user's EmpID
	protected static int logintries = 0;
	protected static boolean validEmployeeData = false;
	protected static Stage mainStage;

			
	@Override
	public void start(Stage primaryStage) throws SQLException
	{
		//UsersChoice choice = UsersChoice.invalidChoice;	// The user's choice      
        //int empid = 0;		// the employee's EmpID
        //EmpData userData = new EmpData();
        //EmpData employeeData = new EmpData();
     
     	mainStage = primaryStage;
        // Stage title
 		mainStage.setTitle("Rosie Salon Transaction GUI Application");  	
 		
 		if (!EmployeeDBaccess.ConnectToDB("rosiessalon","andrewroe","andysql"))
   		{
   			System.out.println("Can not connect to DB, Bye.");
   			return;
   		}     
        
        handlelogin(mainStage);
        /* this comes back immediately - before actual login 
        userData.setUserID(userid);
        System.out.println("Got the user logged in, now what?");
        */
        
        // if we are not happy about who is logged in, then go back to handlelogin()
        
	}  // End of start()
	

	
	/**		     	
		This method is for login handling of the user.
		
   		@param user which is an EmpData object to be updated when user successfully
   		can be found in the Database.
   		@throws SQLException if there is an error with some SQL command			
	*/
		  
	public static void handlelogin(Stage primaryStage) throws SQLException
	{
		int trycount = 0;
		String username = null;
		int empid = 0;
		String password = null;
		String userpassword = null;
		
		Label loginScreen = new Label("Login Screen - 3 tries max");
		Label userPrompt = new Label("enter user name");
		Label passwordPrompt = new Label("enter Password");
		Label signinPrompt = 
			new Label("After filling in Username and Password click Sign-in Button");
		Label exitPrompt = new Label("Click Button to exit");
			
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
		
		Image yogaDoor = new Image("file:yogadoor.jpg");
		ImageView imageDoor = new ImageView(yogaDoor);
		imageDoor.setFitWidth(500);
		imageDoor.setFitHeight(500);
		imageDoor.setPreserveRatio(true);
		
		usernameText = new TextField();
		passwordText = new TextField();

		// Add button handling
		signinButton.setOnAction(new ButtonClickHandler());
		exitButton.setOnAction(new ButtonClickHandler());
		
		HBox doorHbox = new HBox(imageDoor);				
		HBox loginHbox = new HBox(loginScreen);
		HBox userHbox = new HBox(40, userPrompt, usernameText);
		HBox pwdHbox = new HBox(40, passwordPrompt, passwordText);
		HBox signinHbox = new HBox(40, signinPrompt, signinButton);
		HBox exitHbox = new HBox(40, exitPrompt, exitButton);
		
		doorHbox.setAlignment(Pos.CENTER);
		loginHbox.setAlignment(Pos.CENTER);
		userHbox.setAlignment(Pos.CENTER);
		pwdHbox.setAlignment(Pos.CENTER);
		signinHbox.setAlignment(Pos.CENTER);
		exitHbox.setAlignment(Pos.CENTER);
		
		VBox loginVbox = 
			new VBox(20,doorHbox,loginHbox,userHbox,pwdHbox,signinHbox,exitHbox);
		loginVbox.setAlignment(Pos.CENTER);
				
		Scene loginScene = new Scene(loginVbox,800,800);
		// Scene loginScene = new Scene(doorHbox,500,400);
		// Scene loginScene = new Scene(doorVbox);
		// Scene loginScene = new Scene(doorHbox);
		// Scene exitScene = new Scene(exitBox,700,700);
		
		//primaryStage.setScene(loginScene);
		primaryStage.setScene(loginScene);
 		primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		primaryStage.show();
 								
		// return;
	}

	
	/*
	private static final Logger LOG =
	Logger.getLogger(ExampleApplication.class.getCanonicalName());
	*/	
	
}
	

class ButtonClickHandler implements EventHandler<ActionEvent>
//class ButtonClickHandler implements EventHandler<event>
{		
	private static EmpDBaccess EmployeeDBaccess = new EmpDBaccess();
	
	@Override
	public void handle(ActionEvent event)
	{		
		if (event.getSource() == TransactionApp.exitButton)
		{
			System.out.println("Good bye.");
			
			try
        	{               
				//TransactionApp.dbDisconnect();
				TransactionApp.EmployeeDBaccess.DisconnectFromDB();
            }
            catch (SQLException ex)
            {
            	System.out.println("Got a SQL exception!");
            }
                
        	System.exit(0);
        }
        
		else if (event.getSource() == TransactionApp.signinButton)
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
						System.out.println("found the user, now what?");
						TransactionApp.userid = userid;
						TransactionApp.logintries = 0;
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
            	System.out.println("Got a SQL exception!");
            }
					
        }
        else
        {
        }		        
        		
	}
}

		
				
