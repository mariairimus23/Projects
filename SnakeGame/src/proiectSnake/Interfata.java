package proiectSnake;

import java.awt.Color;

/**
 * Date: 7 Ianuarie 2020
 * @author User
 */

import javax.swing.JFrame;

public class Interfata {
	JFrame frame;
	FunctiiJoc joc = new FunctiiJoc();

	public static void main(String[] args) {
		new Interfata();
	}

	/**
	 * Constructor in care se creeaza fereastra(gradina), se fixeaza dimensiunea,
	 * titlul, fundalul, vizibilitatea ferestrei
	 */
	public Interfata() {
		frame = new JFrame();
		frame.setSize(900, 600);
		frame.setTitle("Snake Game");
		frame.getContentPane().setBackground(Color.black);
		frame.add(joc);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}