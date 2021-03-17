package dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Date Apr 15-2020
 * Aceasta clasa realizeaza conexiunea cu baza de date, implementeaza
 * accesul la baza de date
 * 
 * @author Irimus Maria
 */

public class ConnectionFactory {

	/**
	 *Constante pentru realizarea conexiunii dintre Eclipse si mySQL
	 */
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/ordermanagement";
	private static final String USER = "root";
	private static final String PASS = "root";

	private static ConnectionFactory singleInstance = new ConnectionFactory();

	/**
	 * Metoda care face legatura cu driverul
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda care creeaza conexiunea
	 * @return conexiunea, in caz contrar returneaza null
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Metoda care primeste conexiunea
	 * @return getter conexiune
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**Metoda care inchide conexiunea
	 * @param connection conexiunea primita ce trebuie inchisa
	 */
	static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
		}
	}

	/**Metoda care inchide statementul
	 * @param statement stetementul primit ce trebuie inchis
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	/**Metoda care inchide resultSet
	 * @param resultSet primit ce trebuie inchis
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
}