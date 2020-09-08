package validators;

import model.Client;

public class ClientNameValidator implements Validator<Client>{

	public boolean validate(Client t) {
		String cname= t.getName();
		return cname.matches("[a-zA-Z]+"); 
	}

	
	
}
