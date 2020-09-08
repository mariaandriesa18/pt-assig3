package start;

import java.lang.reflect.Field;

import model.Client;

public class Reflection{

	public static String[] retrieveProperties(Object object) {
		String[] fields = new String[object.getClass().getDeclaredFields().length];
		int i = 0;
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true); // set modifier to public
			Object value;
			try {
				value = field.get(object);
				fields[i++] = field.getName();
				System.out.println(field.getName() + "=" + value);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		
		return fields;
	}
	
	/*public static void main(String[] args)
	{
		Client client = new Client(1, "a", "b");
		retrieveProperties(client);
	}*/
	
}