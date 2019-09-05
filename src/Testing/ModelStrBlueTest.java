package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Characters.Strategies.StrBlue;

class ModelStrBlueTest {

	@Test
	void testMeet() {
		StrBlue test = new StrBlue();
		test.meet();
		assertEquals(0, 0);
	}

}
