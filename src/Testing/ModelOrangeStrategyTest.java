package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Characters.Strategies.Normal.OrangeStrategy;

class ModelOrangeStrategyTest {

	@Test
	void testMeet() {
		OrangeStrategy test = new OrangeStrategy();
		test.meet();
		assertEquals(0, 0);
	}

}
