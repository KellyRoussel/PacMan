package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Characters.Strategies.Normal.StrategyPink;

class ModelStrategyPinkTest {

	@Test
	void testMeet() {
		StrategyPink test = new StrategyPink();
		test.meet();
		assertEquals(0, 0);
	}

}
