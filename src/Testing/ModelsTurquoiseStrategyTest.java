package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Characters.Strategies.Normal.TurquoiseStrategy;

class ModelTurquoiseStrategyTest {

	@Test
	void testMeet() {
		TurquoiseStrategy test = new TurquoiseStrategy();
		test.meet();
		assertEquals(0, 0);
	}

}
