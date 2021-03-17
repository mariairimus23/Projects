package proiectSnake;

import javax.swing.JPanel;

/**
 * Date: 7 Ianuarie 2020
 * 
 * @author User
 */
@SuppressWarnings("serial")
public class Sarpe extends JPanel {

	int lungime;

	/**
	 * Constructor pentru entitatea sarpe
	 */
	public Sarpe() {
		lungime = 3;
	}

	/**
	 * Getter pentru lungimea sarpelui
	 * 
	 * @return returneaza lungimea sarpelui
	 */

	public int getLungime() {
		return lungime;
	}

	/**
	 * Setter pentru entitatii sarpe
	 * 
	 * @param lungime parametrul care da lungimea sarpelui
	 */
	public void setLungime(int lungime) {
		this.lungime = lungime;
	}
}