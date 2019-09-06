package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Foods.Fruit;

class ModelFruitTest {

	@Test
	void testGetX() {
		Fruit test = new Fruit();
		int output = test.getX();
		assertEquals(0, output);
	}

	@Test
	void testGetY() {
		Fruit test = new Fruit();
		int output = test.getY();
		assertEquals(0, output);
	}

	@Test
	void testFruit() {
		Fruit test = new Fruit();
		
		assertEquals(0, test.getX());
		assertEquals(0, test.getY());
		assertEquals(false, test.isEaten());;
	}
	
	@Test
	void testFruitXY() {
		int x = 1;
		int y = 1;
		
		Fruit test = new Fruit(x,y);
		
		assertEquals(1, test.getX());
		assertEquals(1, test.getY());
		assertEquals(false, test.isEaten());;
	}

	@Test
	void testIsEaten() {
		Fruit test = new Fruit();
		boolean output = test.isEaten();
		assertEquals(false, output);
	}

	@Test
	void testSetEaten() {
		Fruit test = new Fruit();
		test.setEaten();
		boolean output = test.isEaten();
		assertEquals(true, output);
	}

}
