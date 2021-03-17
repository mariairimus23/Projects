package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul ClientDAO si face legatura
 * dintre obiectul Java Client si tabela client din mySQL,, implementeaza
 * accesul la baza de date
 * 
 * @author Irimus Maria
 */

public class ClientDAO extends AbstractDAO<Client> {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

	/**
	 * Query pentru fiecare dintre operatiile implementate: INSERT, UPDATE, DELETE,
	 * FIND
	 */
	private static final String insertStatementString = "INSERT INTO client(id,name,address)" + " VALUES (?,?,?)";
	private static final String updateStatementString = "UPDATE client SET name = ?, address = ? WHERE name =?";
	private static final String deleteStatementString = "DELETE FROM client WHERE name =?";
	private final static String findStatementString = "SELECT * FROM client WHERE id = ?";
	private final static String findNameStatementString = "SELECT * FROM client WHERE name = ?";

	/**
	 * Gasirea unui client din baza de date dupa id-ul acestuia
	 * 
	 * @param clientId clientul cautat
	 * @return clientul gasit, in caz contrar se returneaza null
	 */
	public static Client findById(int clientId) {
		Client toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, clientId);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			toReturn = new Client(clientId, name, address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Gasirea unui client din baza de date dupa numele acestuia
	 * 
	 * @param clientName clientul cautat
	 * @return clientul gasit, in caz contrar se returneaza null
	 */
	public static Client findByName(String clientName) {
		Client toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findNameStatementString);
			findStatement.setString(1, clientName);
			rs = findStatement.executeQuery();
			rs.next();

			String address = rs.getString("address");
			toReturn = new Client(clientName, address);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findByName " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Inserarea unui client in baza de date
	 * 
	 * @param client care se insereaza
	 * @return id-ul clientului inserat, in caz contrar -1
	 */
	public static int insert(Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, client.getId());
			insertStatement.setString(2, client.getName());
			insertStatement.setString(3, client.getAddress());

			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Actualizarea informatiilor despre un anumit client folosind numele acestuia
	 * 
	 * @param clientNou clientul cu datele actualizate
	 */
	public static void update(Client clientNou) {

		Client clientVechi = findById(clientNou.getId());

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);

			updateStatement.setString(1, clientNou.getName());
			updateStatement.setString(2, clientNou.getAddress());
			updateStatement.setString(3, clientVechi.getName());
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:updateById " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Stergerea din tabela a unui client folosind numele acestuia
	 * 
	 * @param client     clientul ce trebuie sters
	 * @param clientName numele clientului ce trebuie cautat pentru a fi sters
	 * @return String ""
	 */
	public static String delete(Client client, String clientName) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		String toReturn = "";
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, client.getName());

			deleteStatement.executeUpdate();

			@SuppressWarnings("unused")
			ResultSet rs = deleteStatement.getGeneratedKeys();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
}