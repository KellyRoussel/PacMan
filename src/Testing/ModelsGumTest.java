package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Foods.Gum;

class ModelGumTest {

	@Test
	void testGetX() {
		Gum test = new Gum();
		int output = test.getY();
		assertEquals(0, output);
	}

	@Test
	void testGetY() {
		Gum test = new Gum();
		int output = test.getY();
		assertEquals(0, output);
	}

	@Test
	void testGum() {
		Gum test = new Gum();
		int outputX = test.getX();
		int outputY = test.getY();
		boolean outputIsEaten = test.isEaten();
		
		assertEquals(0, outputX);
		assertEquals(0, outputY);
		assertEquals(false, outputIsEaten);
	}

	@Test
	void testIsEaten() {
		Gum test = new Gum();
		boolean output = test.isEaten();
		assertEquals(false, output);
	}

	@Test
	void testSetEaten() {
		Gum test = new Gum();
		test.setEaten();
		boolean output = test.isEaten();
		assertEquals(true, output);
	}
	
	@Test
	void testGumXY() {
		int X = 1;
		int Y = 1;
		
		Gum test = new Gum(X,Y);
		int outputX = test.getX();
		int outputY = test.getY();
		
		assertEquals(1, outputX);
		assertEquals(1, outputY);
	}

}
