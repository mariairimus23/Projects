package model;

/**
 * Date Apr 15-2020 Aceasta clasa descrie obiectul Product, model de date al aplicatiei
 * 
 * @author Irimus Maria
 */

public class Product {

	private int id;
	private String name;
	private int quantity;
	private double price;

	public Product() {

	}

	/**
	 * Constructor
	 * 
	 * @param id       produs
	 * @param name     produs
	 * @param quantity produs
	 * @param price    produs
	 */
	public Product(int id, String name, int quantity, double price) {

		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Product(String name, int quantity, double price) {

		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}