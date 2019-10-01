package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Characters.Strategies.Normal.PinkStrategy;

class ModelStrategyPinkTest {

	@Test
	void testMeet() {
		PinkStrategy test = new PinkStrategy();
		test.meet();
		assertEquals(0, 0);
	}

}
