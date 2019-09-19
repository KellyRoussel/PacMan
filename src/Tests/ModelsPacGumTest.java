package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Foods.PacGum;

class ModelPacGumTest {

	@Test
	void testGetX() {
		//re-do
		PacGum test = new PacGum();
		int output = test.getX();
		assertEquals(0, output);
	}

	@Test
	void testGetY() {
		PacGum test = new PacGum();
		int output = test.getY();
		assertEquals(0, output);
	}

	@Test
	void testPacGum() {
		PacGum test = new PacGum();
		int outputX = test.getX();
		int outputY = test.getY();
		boolean outputIsEaten = test.isEaten();
		
		assertEquals(0, outputX);
		assertEquals(0, outputY);
		assertEquals(false, outputIsEaten);
	}
	
	@Test
	void testPacGumXY() {
		int X = 1;
		int Y = 1;
		
		PacGum test = new PacGum(X,Y);
		int outputX = test.getX();
		int outputY = test.getY();
		
		assertEquals(1, outputX);
		assertEquals(1, outputY);
	}

	@Test
	void testIsEaten() {
		PacGum test = new PacGum();
		boolean output = test.isEaten();
		assertEquals(false, output);
	}

	@Test
	void testSetEaten() {
		PacGum test = new PacGum();
		test.setEaten();
		boolean output = test.isEaten();
		assertEquals(true, output);
	}

}

