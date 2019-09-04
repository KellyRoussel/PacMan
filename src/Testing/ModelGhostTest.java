package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;

import org.junit.jupiter.api.Test;

import inDev.Models.Characters.Ghost;

class ModelGhostTest {

	@Test
	void testDraw() {
		Ghost test = new Ghost();
		Graphics g = null;
		test.draw(g);
		assertEquals(0, 0);
	}

	@Test
	void testMove() {
		Ghost test = new Ghost();
		test.move();
		assertEquals(0, 0);
	}

	@Test
	void testTreatcollision() {
		Ghost test = new Ghost();
		test.treatcollision();
		assertEquals(0, 0);
	}

}
