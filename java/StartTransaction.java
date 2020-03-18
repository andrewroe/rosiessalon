import java.util.Scanner; 
import javax.swing.JOptionPane; 
import java.sql.*;  	
import javafx.application.Application;
import javafx.stage.Stage;

/**		     	
	Then a main method that passes a class reference rather than allowing
	the launcher to reflectively find the application:
	
	There are no input parameters.
*/
public final class StartTransaction
{
	private StartTransaction()
	{
	
	}
	
	public static void main(final String[] args)
	{
		/*
		* Non-modular applications must specify the application class explicitly rather
		* than allowing the launcher to use reflection to try to instantiate and start
		* an application.
		*/

		Application.launch(TransactionApp.class, args);
	}
}		
			
				
