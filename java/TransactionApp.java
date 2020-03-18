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
	
	private static EmpDBaccess EmployeeDBaccess = new EmpDBaccess();
	//private Button executeButton = new Button("Execute");
	private static Button exitButton = new Button("Exit");

	
	@Override
	public void start(Stage primaryStage) throws SQLException
	{
		UsersChoice choice = UsersChoice.invalidChoice;	// The user's choice 
        int userid = 0;		// the user's EmpID
        int empid = 0;		// the employee's EmpID
        EmpData userData = new EmpData();
        EmpData employeeData = new EmpData();
        boolean validEmployeeData = false;
        
         // Stage title
 		primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	
 		
        EmployeeDBaccess.ConnectToDB("rosiessalon","andrewroe","andysql");
        
        userid = handlelogin(primaryStage,userData);
        if (userid == 0)
        {
        	System.out.println("Could not log you in - good bye!");
        	EmployeeDBaccess.DisconnectFromDB();
        	return;
        }	
        
        userData.setUserID(userid);
		
 		// Stage title
 		//primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 		// Show the window
 		//primaryStage.show();
	}  // End of start()
	

	
	/**		     	
		This method is for login handling of the user.
		
   		@param user which is an EmpData object to be updated when user successfully
   		can be found in the Database.
   		@throws SQLException if there is an error with some SQL command
   		@return UserID of user or 0, if not found.  		
	*/
		  
	public static int handlelogin(Stage primaryStage,EmpData user) throws SQLException
	{
		int trycount = 0;
		String username = null;
		int empid = 0;
		String password = null;
		String userpassword = null;
		
		Label loginScreen = new Label("Login Screen");
		Label userPrompt = new Label("enter user name");
		
		Image yogaDoor = new Image("file:yogadoor.jpg");
		ImageView imageDoor = new ImageView(yogaDoor);
		imageDoor.setFitWidth(800);
		imageDoor.setFitHeight(800);
		imageDoor.setPreserveRatio(true);
		
		HBox loginHbox = new HBox(loginScreen);
		//loginHbox.setAlignment(Pos.CENTER);
		loginHbox.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox loginVbox = new VBox(loginScreen);
		loginVbox.setAlignment(Pos.BOTTOM_CENTER);
		
		
		HBox doorHbox = new HBox(imageDoor);
		doorHbox.setAlignment(Pos.TOP_CENTER);
		
		VBox doorVbox = new VBox(20,imageDoor);
		doorVbox.setAlignment(Pos.TOP_CENTER);
		
		// Add a button
		Label exitPrompt = new Label("Click Button to exit");
		
		exitButton.setOnAction(new ButtonClickHandler());
		
		VBox exitBox = new VBox(20,exitPrompt,exitButton);
		exitBox.setAlignment(Pos.CENTER);
		
		Scene loginScene = new Scene(loginHbox,500,400);
		// Scene loginScene = new Scene(doorHbox,500,400);
		// Scene loginScene = new Scene(doorVbox);
		// Scene loginScene = new Scene(doorHbox);
		Scene exitScene = new Scene(exitBox,700,700);
		
		//do
		//{
			//primaryStage.setScene(loginScene);
			primaryStage.setScene(exitScene);
 	
 			primaryStage.setTitle("Rosie Salon Transaction GUI Application");  	  
 	
 			primaryStage.show();
 		
			username = ("admin");

			empid = EmployeeDBaccess.fetchUser(username);
			if (empid != 0) 
			{ 
				password = EmployeeDBaccess.fetchPassword(empid);
				/*
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
				*/
			}
			
		//} while (++trycount < 3);
		
		return empid;
	}

	/**		     	
		This method is 
			
   		@return boolean 		
	*/
		  
	public static boolean ISexitButton(Object button)
	{
		if (button == exitButton)
			return true;
		else
			return false;
	}	
	
	/**		     	
		This method is 
		
   		@throws SQLException if there is an error with some SQL command
   		@return void  		
	*/
		  
	public static void dbDisconnect() throws SQLException
	{
		EmployeeDBaccess.DisconnectFromDB();
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
		// if (event.getSource() == executeButton)
		if (TransactionApp.ISexitButton(event.getSource())) 
		{
			System.out.println("Good bye.");
			
			try
        	{               
				TransactionApp.dbDisconnect();
            }
            catch (SQLException ex)
            {
            	System.out.println("Got a SQL exception!");
            }
                
        	System.exit(0);
        }		
	}
}

		
				
