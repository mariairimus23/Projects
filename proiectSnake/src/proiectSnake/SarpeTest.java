package proiectSnake;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SarpeTest {

	Sarpe s = new Sarpe();

	@Test
	void testGet() {
		assertEquals(3, s.getLungime());
	}

	@Test
	void testSet() {
		s.setLungime(5);
		assertEquals(5, s.getLungime());
	}
}