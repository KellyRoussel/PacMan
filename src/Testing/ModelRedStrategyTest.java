package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Characters.Strategies.Normal.RedStrategy;

class ModelRedStrategyTest {

	@Test
	void testMeet() {
		RedStrategy test = new RedStrategy();
		test.meet();
		assertEquals(0, 0);
	}

}
