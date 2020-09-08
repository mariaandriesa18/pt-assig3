 package data_access;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private final Class<T> type;

	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM `" + type.getSimpleName().toLowerCase()+ "`");
		sb.append(" WHERE " + field + " = ?");
		return sb.toString();
	}
	
	private String createInsertQuery( String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `");
		sb.append(type.getSimpleName().toLowerCase() + "` (");
		for (String fld : fields) {
			if(fld.contentEquals("id")) {
				continue;
			}
			sb.append(fld);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES (");
		for(int i = 0; i < fields.length - 1; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();	
	}
	
	private String createUpdateQuery( String[] fields) {
		StringBuilder sb= new StringBuilder();
		sb.append("UPDATE `");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append("` SET ");
		for(int i = 1; i < fields.length; i++) {
			sb.append(fields[i] + " = ?");
			if( i == (fields.length - 1)) {
				break;
			}
			sb.append(",");
		}
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	
	private String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName().toLowerCase());
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	private String createSelectAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM `" + type.getSimpleName().toLowerCase()+"`");
		return sb.toString();
	}
	public String[] findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			ResultSetMetaData rsMeta = resultSet.getMetaData();
			String[] nou = new String[rsMeta.getColumnCount()];
			for (int i = 0; i < rsMeta.getColumnCount(); i++) {
				nou[i] = resultSet.getObject(i + 1).toString();
			}
			System.out.println("ResultSet : ");
			for(int  i = 0 ; i< rsMeta.getColumnCount(); i++) {
				System.out.println(nou[i] + ";   " );
			}
			return  nou ;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	
	public Method[] getMethodsInSortedOrder() {
		Field[] fields = this.type.getDeclaredFields();
		Method[] methods = this.type.getDeclaredMethods();
		Method[] metName = new Method[methods.length];
		int i = 0;
		for(Field f: fields) {
			String fname = f.getName();
			for(Method m: methods) {
				if(m.getName().toLowerCase().contains(fname)) {
					metName[i] = m;
					i++;
					break;
				}
			}
		}
		
		return metName;
	}

	public T insert(T t) {
		Connection connection = null;
		PreparedStatement insertStatement = null;
		Class cls  = this.type;
		String classname = cls.getSimpleName().toLowerCase();
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = getMethodsInSortedOrder();
		Object[] args = null;
		String[] fNames = new String[fields.length];
		for(int i = 0 ; i < fields.length ; i++) {
			fNames[i] = fields[i].getName();
		}
		String query = createInsertQuery( fNames);
		
		try {
			connection = ConnectionFactory.getConnection();
			insertStatement = connection.prepareStatement(query);
			for(int i = 2; i <= methods.length; i++) {
				String res  = methods[i - 1].invoke(t, args).toString();
				insertStatement.setString(i - 1 , res);
			}
			System.out.println(insertStatement.toString());
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(connection);
		}	
		return t;
	}


	public T update(T t) {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		Field[] fields = this.type.getDeclaredFields();
		Method[] methods = getMethodsInSortedOrder();
		Object[] args = null;
		String[] fNames = new String[fields.length];
		try {
			Integer upid = (Integer)methods[0].invoke(t, args);
			String[] toBeUpdated = findById(upid);
		
			for(int i = 0 ; i < fields.length ; i++) {
				fNames[i] = fields[i].getName();
			}
			String query = createUpdateQuery(fNames);
			connection = ConnectionFactory.getConnection();
			updateStatement = connection.prepareStatement(query);
			int id  = (Integer)methods[0].invoke(t, args);
			updateStatement.setInt(fields.length, id);
			System.out.println("fields len " + fields.length);
			String res;
			 for(int i = 1; i < methods.length; i++) {
				if(methods[i].invoke(t, args) == null) {
					String replacement = toBeUpdated[i];
					System.out.println(replacement);
					updateStatement.setString(i, replacement);
					continue;
				}
				res  = methods[i].invoke(t, args).toString();
				System.out.println(res);
				updateStatement.setString(i, res);
			}
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	
	public T delete(T t) {
		Connection connection = null;
		PreparedStatement deleteStatement = null;
		Class cls  = this.type;
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = getMethodsInSortedOrder();
		Object[] args = null;
		String[] fNames = new String[fields.length];
		try {
			connection = ConnectionFactory.getConnection();
			String[] deleted = findById((Integer)methods[0].invoke(t, args));
			Integer upId = Integer.parseInt(deleted[0]);
			for(int i = 0 ; i < fields.length ; i++) {
				fNames[i] = fields[i].getName();
			}
			String query = createDeleteQuery();
			deleteStatement = connection.prepareStatement(query);
			Integer res  = (Integer) methods[0].invoke(t, args);
			deleteStatement.setInt(1, res);
			System.out.println("delete :" +deleteStatement);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[][] selectAll() {
		PreparedStatement findAllStatement = null;
		ResultSet resultSet = null;
		Class cls  = this.type;
		String query = createSelectAllQuery();
		String[][] rowData = new String [100][100];
		
		try {
			Connection dbConnection = ConnectionFactory.getConnection();
			findAllStatement = dbConnection.prepareStatement(query);
			resultSet = findAllStatement.executeQuery();
			ResultSetMetaData rsMeta = resultSet.getMetaData();
			
			int row = 0;
			
			if(resultSet.first()) {
				for (int i = 0; i < rsMeta.getColumnCount(); i++) {
					rowData[row][i] = resultSet.getObject(i + 1).toString();
				}
				while (resultSet.next()) {	
					row++;
					for (int i = 0; i < rsMeta.getColumnCount(); i++) {
						rowData[row][i] = resultSet.getObject(i + 1).toString();
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rowData;
	}
}

