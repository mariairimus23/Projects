package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Bill;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul BillDAO si face legatura
 * dintre obiectul Java Product si tabela product din mySQL, implementeaza
 * accesul la baza de date
 * 
 * @author Irimus Maria
 */

public class BillDAO extends AbstractDAO<Bill> {

	protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

	/**
	 * Query pentru fiecare dintre operatiile implementate: INSERT, UPDATE, DELETE,
	 * FIND
	 */
	private static final String insertStatementString = "INSERT INTO bill(id,clientName,productName,quantity,total)"
			+ " VALUES (?,?,?,?,?)";
	private static final String updateStatementString = "UPDATE bill SET clientName = ?, productName = ?, quantity = ?, total = ? WHERE id =?";
	private static final String deleteStatementString = "DELETE FROM bill WHERE id =?";

	/**
	 * Inserarea unei comenzi in baza de date
	 * 
	 * @param bill care se insereaza
	 * @return id-ul comenzii inserate, in caz contrar -1
	 */
	public static int insert(Bill bill) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, bill.getId());

			insertStatement.setString(2, bill.getClientName());
			insertStatement.setString(3, bill.getProductName());
			insertStatement.setInt(4, bill.getQuantity());
			insertStatement.setDouble(5, bill.getTotal());

			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "BillDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Actualizarea informatiilor despre o anumita comanda
	 * 
	 * @param bill comanda cu datele actualizate
	 */
	public static void update(Bill bill) {

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);

			updateStatement.setString(1, bill.getClientName());
			updateStatement.setString(2, bill.getProductName());
			updateStatement.setInt(3, bill.getQuantity());
			updateStatement.setDouble(4, bill.getTotal());
			updateStatement.setInt(5, bill.getId());

			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "BillDAO:updateById " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Stergerea din tabela a unei comenzi folosind id-ul acesteia
	 * 
	 * @param bill   ce trebuie sters
	 * @param billId id-ul comenzii ce trebuie cautata pentru a fi stearsa
	 * @return id-ul comenzii sterse, in caz contrat -1
	 */
	public static int delete(Bill bill, int billId) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int deletedId = -1;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, bill.getId());

			deleteStatement.executeUpdate();

			ResultSet rs = deleteStatement.getGeneratedKeys();
			if (rs.next()) {
				deletedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "BillDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return deletedId;
	}
}