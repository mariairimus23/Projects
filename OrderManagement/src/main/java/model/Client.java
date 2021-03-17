package model;

/**
 * Date Apr 15-2020 Aceasta clasa descrie obiectul Client, model de date al aplicatiei
 * 
 * @author Irimus Maria
 */

public class Client {

	private int id;
	private String name;
	private String address;

	public Client() {

	}

	/**
	 * Constructor
	 * 
	 * @param id      client
	 * @param name    client
	 * @param address client
	 */
	public Client(int id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public Client(String name, String address) {
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}