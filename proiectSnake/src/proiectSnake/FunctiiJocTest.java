package proiectSnake;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FunctiiJocTest {

	FunctiiJoc f = new FunctiiJoc();

	@Test
	void testScor() {
		assertEquals(0, f.getScor());
	}

	@Test
	void testNrMutari() {
		assertEquals(0, f.mutari);
	}

	@Test
	void verifica() {
		assertEquals(false, f.verificaApropiere(190, 350, 10));
		assertEquals(true, f.verifica);
	}
}