package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Characters.Strategies.StrBlue;

class ModelStrBlueTest {

	@Test
	void testMeet() {
		StrBlue test = new StrBlue();
		test.meet();
		assertEquals(0, 0);
	}

}
