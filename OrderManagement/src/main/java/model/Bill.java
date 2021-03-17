package model;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul Bill, model de date al aplicatiei
 * 
 * @author Irimus Maria
 */

public class Bill {

	private int id;
	private String clientName;
	private String productName;
	private int quantity;
	private double total;

	public Bill() {

	}

	/**
	 * Constructor
	 * 
	 * @param id          comanda
	 * @param clientName  cel care face comanda
	 * @param productName din comanda
	 * @param quantity    comanda
	 * @param total       comanda
	 */
	public Bill(int id, String clientName, String productName, int quantity, double total) {

		this.id = id;
		this.clientName = clientName;
		this.productName = productName;
		this.quantity = quantity;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}