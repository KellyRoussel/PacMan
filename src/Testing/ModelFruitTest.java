package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Foods.Fruit;

class ModelFruitTest {

	@Test
	void testIsEaten() {
		Fruit test = new Fruit();
		boolean output = test.isEaten();
		assertEquals(false, output);
	}

}
