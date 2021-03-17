package proiectSnake;

/**
 * Date: 7 Ianuarie 2020
 * 
 * @author User
 */

public class Mar {

	private int x;
	private int y;
	private int random = 15;

	/**
	 * Getter pentru intregul random
	 * 
	 * @return returneaza intregul random
	 */

	public int getRandom() {
		return random;
	}

	/**
	 * Setter pentru intregul random
	 * 
	 * @param random parametrul cu ajutorul caruia se calculeaza locatia exacta a
	 *               marului urmator
	 */

	public void setRandom(int random) {
		this.random = random;
	}

	/**
	 * Getter pentru coordonata y
	 * 
	 * @return returneaza coordonata
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter pentru coordonata y
	 * 
	 * @return returneaza coordonata
	 */

	public int getY() {
		return y;
	}

	/**
	 * Setter pentru x
	 * 
	 * @param x coordonata de pe axa Ox a marului
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter pentru y
	 * 
	 * @param y coordonata de pe axa Oy a marului
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Functie pentru creearea marului intr-o locatie oarecare de pe ecran
	 */

	void creeaza() {
		int locatie;

		locatie = (int) (Math.random() * random);
		x = (locatie * 40);

		locatie = (int) (Math.random() * random);
		y = (locatie * 40);
	}
}