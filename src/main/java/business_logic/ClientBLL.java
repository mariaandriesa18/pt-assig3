package business_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import data_access.AbstractDAO;
/*import business_logic.validators.EmailValidator;
import business_logic.validators.ClientAgeValidator;
import business_logic.validators.Validator;*/
import data_access.ClientDAO;
import model.Client;
import validators.ClientNameValidator;
import validators.Validator;

public class ClientBLL {

	public String[] findById(int id) {
		ClientDAO client = new ClientDAO();
		return client.findById(id);
	}
	
	public int insertClient(Client client) {
		ClientDAO ins = new ClientDAO();
		ClientNameValidator v = new ClientNameValidator();
		
		 if(!v.validate(client)) {
			 return 0;
		 }
		return ins.insert(client).getId();
	}

	public Client editClient(Client client) {
		ClientDAO up = new ClientDAO();
		return up.update(client);
	}

	public Client deleteClient(Client client) {
		ClientDAO del = new ClientDAO();
		return del.delete(client);
	}


	public String[][] selectAll(){
		ClientDAO s = new ClientDAO();
		return s.selectAll();
	}
}
