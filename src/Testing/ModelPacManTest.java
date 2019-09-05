package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD
=======
import inDev.Models.Maze;
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
import inDev.Models.Characters.PacMan;

class ModelPacManTest {

	@Test
	void testPacMan() {
		fail("Not yet implemented");
	}

	@Test
	void testNextX() {
		fail("Not yet implemented");
	}

	@Test
	void testNextY() {
		fail("Not yet implemented");
	}

	@Test
<<<<<<< HEAD
	void testNextNextX() {
		fail("Not yet implemented");
	}

	@Test
	void testNextNextY() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNextDirection() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNextDirection() {
		
		//fail("Not yet implemented");
		
		//arrange
		
		PacMan pacman = new PacMan();
		
		//act
		pacman.setNextDirection("Up");
		
		//assert
		assertEquals(0, pacman.getNextDx());
		
	}

	@Test
=======
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	void testMove() {
		fail("Not yet implemented");
	}

	@Test
	void testGetX() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getX();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetY() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getX();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetWidth() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getWidth();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetHeight() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getHeight();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetImage() {
		fail("Not yet implemented");
	}

	@Test
	void testKeyPressed() {
		fail("Not yet implemented");
	}

	@Test
	void testShiftedX() {
		fail("Not yet implemented");
	}

	@Test
	void testDraw() {
		fail("Not yet implemented");
	}

	@Test
	void testTreatcollision() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNextX() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getNextX();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testSetNextX() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNextY() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		int output = test.getNextY();
		assertEquals(0, output);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testSetNextY() {
		fail("Not yet implemented");
	}

	@Test
	void testGetW() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		test.setW(15);
		
		assertEquals(15, test.getW());
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetH() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		test.setH(10);
		
		assertEquals(10, test.getH());
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testGetDirection() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		test.setDirection("Up");
		
		assertEquals("Up", test.getDirection());
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testUndefinedPosition() {
		fail("Not yet implemented");
	}

	@Test
	void testSetPosition() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		test.setPosition(10, 15);
		
		assertEquals(10, test.getX());
		assertEquals(15, test.getY());
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testSetInsideTile() {
<<<<<<< HEAD
		fail("Not yet implemented");
=======
		PacMan test = new PacMan();
		Maze testmaze = new Maze(5,6);
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
	}

	@Test
	void testUpdateDirection() {
		fail("Not yet implemented");
	}
<<<<<<< HEAD

=======
>>>>>>> 96bb19d020b9ba64909ba5a265b99bc7387c0c8d
}
