package dataAccessLayer;

import java.beans.IntrospectionException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date Apr 15-2020 
 * Aceasta clasa implementeaza 2 metode cu caracter
 * general(pentru orice tip de obiect) folosind Reflection, implementeaza
 * accesul la baza de date
 * 
 * @author Irimus Maria
 */

public class AbstractDAO<T> {

	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	/**
	 * Metoda care gaseste toate campurile dintr-o tabela
	 * 
	 * @return returneaza toate campurile dintr-o tabela specificata, in caz contrar
	 *         null
	 */
	public List<T> findAll() {

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		String findStatementString = "SELECT * FROM " + type.getSimpleName();

		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			rs = findStatement.executeQuery();
			return createObjects(rs);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll" + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return null;
	}

	/**
	 * Metoda care creaza o lista de obiecte de tipul T
	 * 
	 * @param resultSet contine informatiile despre o tabela specificata
	 * @return returneaza o lista de obiecte T
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				@SuppressWarnings("deprecation")
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}
}