package proiectSnake;

/**
 * Date: 7 Ianuarie 2020
 * @author User
 */

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FunctiiJoc extends JPanel implements ActionListener, KeyListener {

	/**
	 * Se initializeaza entitatile Sarpe si Mar
	 */
	private Sarpe sarpe;
	private Mar mar = new Mar();
	/**
	 * Se initializeaza coordonatele sarpelui
	 */
	int x[] = new int[900];
	int y[] = new int[600];

	private Timer timp;
	/**
	 * Se initializeaza variabilele pentru directia de deplasare a sarpelui
	 */
	boolean sus = false;
	boolean jos = false;
	boolean stanga = false;
	boolean dreapta = false;
	/**
	 * Se initializeaza variabila care verifica daca sarpele este inca in viata(ne
	 * aflam inca in joc)
	 */
	boolean verifica = true;
	/**
	 * Se initializeaza variabila care verifica daca suntem la inceputul jocului
	 */
	boolean inceput = true;
	/**
	 * Se initializeaza numarul de mutari si scorul
	 */
	int mutari = 0;
	int scor = 0;
	JLabel etichetaScor = new JLabel();

	/**
	 * Se declara imaginile de start si game over
	 */
	ImageIcon img = new ImageIcon("img/snik.jpg");
	ImageIcon image = new ImageIcon("img/sniky.png");
	ImageIcon image2 = new ImageIcon("img/gameover.jpg");
	Image img2 = img.getImage();
	Image img3 = image.getImage();
	Image img4 = image2.getImage();

	/**
	 * Constructor in care se adauga sensibilitatea pentru taste, imaginea de fundal
	 * si se apeleaza functia de initializare
	 */
	public FunctiiJoc() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setBackground(new Color(34, 139, 34));
		initializeaza();
	}

	/**
	 * Getter pentru coordonata x a sarpelui
	 * 
	 * @param i parametrul care da pozitia de pe axa Ox din vectorul(corpul) sarpe
	 * @return returneaza coordonata de pe axa Ox la care se afla bucata din
	 *         vectorul sarpe la momentul apelat
	 */
	public int getX(int i) {
		return x[i];
	}

	/**
	 * Getter pentru coordonata y a sarpelui
	 * 
	 * @param i parametrul care da pozitia de pe axa Ox din vectorul(corpul) sarpe
	 * @return returneaza coordonata de pe axa Oy la care se afla bucata din
	 *         vectorul sarpe la momentul apelat
	 */
	public int getY(int i) {
		return y[i];
	}

	/**
	 * Getter pentru variabila intreaga ce numara scorul
	 * 
	 * @return returneaza scorul de tip intreg
	 */
	public int getScor() {
		return this.scor;
	}

	/**
	 * Getter pentru variabila verifica
	 * 
	 * @return returneaza variabila booleana verifica
	 */
	public boolean getVerifica() {
		return this.verifica;
	}

	/**
	 * Metoda prin care setez imaginile de inceput ale jocului si dimensiunile,
	 * culorile, textul
	 * 
	 * @param g parametrul pentru desenarea obiectelor in fereastra
	 */

	void inceputJoc(Graphics g) {
		g.drawImage(img2, 0, 0, 900, 600, null);
		g.drawImage(img3, 270, 100, 400, 200, null);
		Font font2 = new Font("VERDANA", Font.BOLD, 16);
		g.setColor(Color.black);
		g.setFont(font2);
		g.drawString("Press Space to Start", 390, 290);
	}

	/**
	 * Functie suprascrisa pentru desenarea si setarea pozitiei initiale atat a
	 * capului, cat si a corpului sarpelui, a marului, dar si a gradinii
	 */

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (inceput == false) {
			if (getVerifica()) {
				super.paint(g);
				if (mutari == 0) {
					x[2] = 100;
					x[1] = 150;
					x[0] = 200;

					y[2] = 200;
					y[1] = 200;
					y[0] = 200;
				}
				g.setColor(Color.red);
				g.fillRect(mar.getX(), mar.getY(), 30, 30);

				for (int i = 0; i < sarpe.getLungime(); i++) {
					if (i == 0) {
						g.setColor(Color.green);
						g.fillOval(x[i], y[i], 35, 35);
					} else {
						g.setColor(Color.green);
						g.fillOval(x[i], y[i], 30, 30);
					}
				}

				g.setFont(new Font("COMIC SANS MS", Font.BOLD, 20));
				g.setColor(Color.white);
				g.drawString("Scor: ", 100, 30);
				g.drawString(etichetaScor.getText(), 160, 30);

			} else {
				g.setColor(Color.black);
				g.fill3DRect(0, 0, 900, 600, true);
				sfarsit(g);
			}
		} else {
			inceputJoc(g);
		}

	}

	/**
	 * Metoda pentru initializarea si totodata resetarea elementelor jocului
	 * (sarpele, scorul, mutarile, timpul, conditia ce verifica daca sarpele este in
	 * viata)
	 */

	void initializeaza() {
		sarpe = new Sarpe();
		verifica = true;
		scor = 0;
		mutari = 0;
		timp = new Timer(150, this);
		timp.start();
	}

	/**
	 * Functie care modifica sensul in care se deplaseaza sarpele (sus, jos, stanga
	 * dreapta)
	 */

	public void modifica() {

		if (sus == true) {
			for (int i = sarpe.getLungime() - 1; i >= 0; i--) {
				x[i + 1] = x[i];
			}
			for (int i = sarpe.getLungime(); i >= 0; i--) {
				if (i == 0) {
					y[i] = y[i] - 40;
				} else {
					y[i] = y[i - 1];
				}
			}
		}

		if (jos == true) {
			for (int i = sarpe.getLungime() - 1; i >= 0; i--) {
				x[i + 1] = x[i];
			}
			for (int i = sarpe.getLungime(); i >= 0; i--) {
				if (i == 0) {
					y[i] = y[i] + 40;
				} else {
					y[i] = y[i - 1];
				}
			}
		}

		if (stanga == true) {
			for (int i = sarpe.getLungime() - 1; i >= 0; i--) {
				y[i + 1] = y[i];
			}
			for (int i = sarpe.getLungime(); i >= 0; i--) {
				if (i == 0) {
					x[i] = x[i] - 40;
				} else {
					x[i] = x[i - 1];
				}
			}
		}
		if (dreapta == true) {
			for (int i = sarpe.getLungime() - 1; i >= 0; i--) {
				y[i + 1] = y[i];
			}
			for (int i = sarpe.getLungime(); i >= 0; i--) {
				if (i == 0) {
					x[i] = x[i] + 40;
				} else {
					x[i] = x[i - 1];
				}
			}
		}
	}

	/**
	 * Functie ce apeleaza metodele de modificare, mancare, ciocnire, actualizare si
	 * redesenare a jocului dupa fiecare actiune realizata de jucator(click sau
	 * apasarea tastei Space)
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if (verifica == true) {
			modifica();
			mananca();
			ciocnire();
			actualizeazaScor();
		}
		repaint();
	}

	/**
	 * Functie pentru manevrarea sarpelui utilizand cele 4 sageti ale tastaturii
	 * (sus, jos, stanga, dreapta)
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		int tasta = e.getKeyCode();

		if (tasta == KeyEvent.VK_UP) {
			mutari++;
			sus = true;
			stanga = false;
			dreapta = false;
			if (!jos) {
				sus = true;
			} else {
				sus = false;
				jos = true;
			}
		}
		if (tasta == KeyEvent.VK_DOWN) {
			mutari++;
			jos = true;
			stanga = false;
			dreapta = false;
			if (!sus) {
				jos = true;
			} else {
				sus = true;
				jos = false;
			}
		}
		if (tasta == KeyEvent.VK_LEFT) {
			mutari++;
			sus = false;
			jos = false;
			stanga = true;
			if (!dreapta) {
				stanga = true;
			} else {
				stanga = false;
				dreapta = true;
			}

		}
		if (tasta == KeyEvent.VK_RIGHT) {
			mutari++;
			sus = false;
			jos = false;
			dreapta = true;
			if (!stanga) {
				dreapta = true;
			} else {
				stanga = true;
				dreapta = false;
			}
		}
		if (tasta == KeyEvent.VK_SPACE) {
			inceput = false;
			scor = 0;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * Functie care genereaza de fiecare data marul intr-o alta locatie, daca marul
	 * se afla in acelasi loc cu capul sarpelui
	 */
	public void creeazaMar() {
		if ((mar.getX() == getX(0)) && (mar.getY() == getY(0))) {
			mar.creeaza();
		}
	}

	/**
	 * Functie ce verifica daca distanta dintre sarpe si mar este de cel mult 10
	 * spatii(pixeli));
	 * 
	 * @param x      parametrul ce da coordonata sarpelui
	 * @param y      parametrul ce da coordonata marului
	 * @param spatiu parametrul ce da spatiu(distanta, nr de pixeli) dintre capul
	 *               sarpelui si mar
	 * @return returneaza true daca sarpele este suficient de aproape de mar, altfel
	 *         returneaza false
	 */

	public boolean verificaApropiere(int x, int y, int spatiu) {
		return Math.abs((long) x - y) <= spatiu;
	}

	/**
	 * Functie prin care sarpele mananca marul (daca cele doua entitati sunt
	 * suficient de aproape una de cealalta, dimensiunea sarpelui, scorul si numarul
	 * de mutari cresc si se genereaza un nou mar intr-o locatie diferita
	 */
	void mananca() {

		if ((verificaApropiere(x[0], mar.getX(), 10)) && (verificaApropiere(y[0], mar.getY(), 10))) {
			creeazaMar();
			mutari++;
			scor++;
			sarpe.setLungime(sarpe.getLungime() + 1);
		}
	}

	/**
	 * Functie ce verifica daca sarpele isi musca propria coada(lucru imposibil daca
	 * lungimea acestuia este mai mica de 5) sau se izbeste de marginile gradinii.
	 * In caz afirmativ, jocul se incheie, timpul se opreste, iar conditia ce
	 * verifica daca sarpele este in viata devine falsa
	 */

	void ciocnire() {

		for (int i = sarpe.getLungime(); i > 0; i--) {
			if ((i > 5) && (getX(0) == getX(i) && (getY(0) == getY(i)))) {
				verifica = false;
			}
		}

		if (getY(0) >= 600) {
			verifica = false;
		}

		if (getY(0) < 0) {
			verifica = false;
		}

		if (getX(0) >= 900) {
			verifica = false;
		}

		if (getX(0) < 0) {
			verifica = false;
		}

		if (!verifica) {
			timp.stop();
		}
	}

	/**
	 * Functie pentru afisarea scorului jucatorului ce arata in timp real cate
	 * puncte a acumulat acesta
	 */

	public void actualizeazaScor() {
		etichetaScor.setText(" " + getScor());
	}

	/**
	 * Functie ce afiseaza doua mesaje(cel care instiinteaza jucatorul ca a pierdut,
	 * “Game Over”, si cel care ii explica modul in care ar putea relua jocul de la
	 * inceput, si anume, apasand “Try again”) si un buton.
	 * 
	 * @param g parametrul pentru desenarea obiectelor in fereastra
	 */

	void sfarsit(Graphics g) {

		g.drawImage(img4, 140, 0, 600, 500, null);

		Font font2 = new Font("COMIC SANS MS", Font.BOLD, 10);
		g.setColor(Color.green);
		g.setFont(font2);
		g.drawString("For a new game move the mouse than press the button", 310, 400);

		JButton jocNou = new JButton("Try again");
		jocNou.setBackground(Color.green);
		jocNou.setForeground(Color.black);
		jocNou.setBounds(390, 430, 100, 50);
		jocNou.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				verifica = true;
				sus = false;
				jos = false;
				stanga = false;
				dreapta = false;
				jocNou.setVisible(false);
				initializeaza();
			}

		});
		this.add(jocNou);
		jocNou.setVisible(true);

	}
}