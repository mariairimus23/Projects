package proiectSnake;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MarTest {
	Mar m = new Mar();

	@Test
	void testGetRandom() {
		assertEquals(15, m.getRandom());
	}

	@Test
	void testSetRandom() {
		m.setRandom(30);
		assertEquals(30, m.getRandom());
	}

	@Test
	void testSetX() {
		m.setX(250);
		assertEquals(250, m.getX());
	}

	@Test
	void testSetY() {
		m.setY(150);
		assertEquals(150, m.getY());
	}

}