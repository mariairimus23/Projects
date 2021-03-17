package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul ProductDAO si face legatura
 * dintre obiectul Java Bill si tabela bill din mySQL, implementeaza accesul la
 * baza de date
 * 
 * @author Irimus Maria
 */

public class ProductDAO extends AbstractDAO<Product> {

	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

	/**
	 * Query pentru fiecare dintre operatiile implementate: INSERT, UPDATE, DELETE,
	 * FIND
	 */
	private static final String insertStatementString = "INSERT INTO product(id,name,quantity,price)"
			+ " VALUES (?,?,?,?)";
	private static final String updateStatementString = "UPDATE product SET name = ?, quantity = ?, price = ? WHERE name =?";
	private static final String deleteStatementString = "DELETE FROM product WHERE name =?";
	private final static String findNameStatementString = "SELECT * FROM product WHERE name = ?";

	/**
	 * Gasirea unui produs din baza de date dupa numele acestuia
	 * 
	 * @param productName produsul cautat
	 * @return produsul gasit, in caz contrar se returneaza null
	 */
	public static Product findByName(String productName) {
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findNameStatementString);
			findStatement.setString(1, productName);
			rs = findStatement.executeQuery();
			rs.next();

			int quantity = rs.getInt("quantity");
			Double price = rs.getDouble("price");
			toReturn = new Product(productName, quantity, price);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findByName " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * Inserarea unui produs in baza de date
	 * 
	 * @param product care se insereaza
	 * @return id-ul produsului inserat, in caz contrat -1
	 */
	public static int insert(Product product) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, product.getId());
			insertStatement.setString(2, product.getName());
			insertStatement.setInt(3, product.getQuantity());
			insertStatement.setDouble(4, product.getPrice());

			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Actualizarea informatiilor despre un anumit produs folosind numele acestuia
	 * 
	 * @param productNou produsul cu datele actualizate
	 */
	public static void update(Product productNou) {

		Product productVechi = findByName(productNou.getName());

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);

			updateStatement.setString(1, productNou.getName());
			updateStatement.setInt(2, productNou.getQuantity());
			updateStatement.setDouble(3, productNou.getPrice());
			updateStatement.setString(4, productVechi.getName());

			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:updateById " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Stergerea din tabela a unui produs folosind numele acestuia
	 * 
	 * @param product     ce trebuie sters
	 * @param productName numele produsului ce trebuie cautat pentru a fi sters
	 * @return String ""
	 */
	public static String delete(Product product, String productName) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		String toReturn = "";
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, product.getName());

			deleteStatement.executeUpdate();

			@SuppressWarnings("unused")
			ResultSet rs = deleteStatement.getGeneratedKeys();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
}