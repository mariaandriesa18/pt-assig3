package start;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import business_logic.ClientBLL;
import model.Client;
import presentation.Controller;
import presentation.MainView;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	
	public static void main(String[] args) throws SQLException {
			
		MainView view = new MainView();
	 	Controller control = new Controller(view);
		
		
		
		/*if (id > 0) {
		 	clientBll.findClientById(id);
		}*/
				
		// Generate error
		/*try {
			clientBll.findClientById(1);
	j	} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}*/
		
		
		//obtain field-value pairs for object through reflection
		//ReflectionExample.retrieveProperties(client);
	}
	
	

}
