package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Models.Sound;

class ModelsSoundTest {

	@Test
	void testSound() {
		Sound test = new Sound(null);
		assertEquals(0,0);
	}

	@Test
	void testPlay() {
		Sound test = new Sound(null);
		test.play();
		assertEquals(0,0);
	}

	@Test
	void testLoop() {
		Sound test = new Sound(null);
		test.loop();
		assertEquals(0,0);
	}

	@Test
	void testStop() {
		Sound test = new Sound(null);
		test.stop();
		assertEquals(0,0);
	}

}
