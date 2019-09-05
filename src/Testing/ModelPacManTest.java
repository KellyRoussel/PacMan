package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inDev.Models.Maze;
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
	void testGetX() {

		PacMan test = new PacMan();
		int output = test.getX();
		assertEquals(0, output);
	}

	@Test
	void testGetY() {

		PacMan test = new PacMan();
		int output = test.getX();
		assertEquals(0, output);
	}

	@Test
	void testGetWidth() {

		PacMan test = new PacMan();
		int output = test.getWidth();
		assertEquals(0, output);
	}

	@Test
	void testGetHeight() {

		PacMan test = new PacMan();
		int output = test.getHeight();
		assertEquals(0, output);
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

		PacMan test = new PacMan();
		int output = test.getNextX();
		assertEquals(0, output);
	}

	@Test
	void testSetNextX() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNextY() {

		PacMan test = new PacMan();
		int output = test.getNextY();
		assertEquals(0, output);
	}

	@Test
	void testSetNextY() {
		fail("Not yet implemented");
	}

	@Test
	void testGetW() {

		PacMan test = new PacMan();
		test.setW(15);
		
		assertEquals(15, test.getW());
	}

	@Test
	void testGetH() {

		PacMan test = new PacMan();
		test.setH(10);
		
		assertEquals(10, test.getH());
	}

	@Test
	void testGetDirection() {

		PacMan test = new PacMan();
		test.setDirection("Up");
		
		assertEquals("Up", test.getDirection());
	}
	
	@Test
	void testSetUndefinedPosition() {
		PacMan test = new PacMan();
		test.setUndefinedPosition(true);
		
		assertEquals(true, test.undefinedPosition());
	}

	@Test
	void testUndefinedPositionTrue() {
		PacMan test = new PacMan();
		test.setUndefinedPosition(true);
		
		assertEquals(true, test.undefinedPosition());
	}
	
	@Test
	void testUndefinedPositionFalse() {
		PacMan test = new PacMan();
		test.setUndefinedPosition(false);
		
		assertEquals(false, test.undefinedPosition());
	}

	@Test
	void testSetPosition() {
		PacMan test = new PacMan();
		test.setPosition(10, 15);
		
		assertEquals(10, test.getX());
		assertEquals(15, test.getY());
	}

	@Test
	void testSetInsideTileLeft() {
		PacMan test = new PacMan();
		Maze testmaze = new Maze(5,6);
		
		test.setDirection("Left");
		int sz = testmaze.getDefaultSize();
		test.setInsideTile(10, 15);
		
		assertEquals(10*sz,test.getY());
	}
	
	@Test
	void testSetInsideTileRight() {
		PacMan test = new PacMan();
		Maze testmaze = new Maze(5,6);
		
		test.setDirection("Right");
		int sz = testmaze.getDefaultSize();
		test.setInsideTile(10, 15);
		
		assertEquals(10*sz,test.getY());
	}
	
	@Test
	void testSetInsideTileUp() {
		PacMan test = new PacMan();
		Maze testmaze = new Maze(5,6);
		
		test.setDirection("Up");
		int sz = testmaze.getDefaultSize();
		test.setInsideTile(10, 15);
		
		assertEquals(15*sz,test.getX());
	}
	
	@Test
	void testSetInsideTileDown() {
		PacMan test = new PacMan();
		Maze testmaze = new Maze(5,6);
		
		test.setDirection("Down");
		int sz = testmaze.getDefaultSize();
		test.setInsideTile(10, 15);
		
		assertEquals(15*sz,test.getX());
	}

	@Test
	void testUpdateDirection() {
		PacMan test = new PacMan();
		
		test.setNextDX(10);
		test.setNextDY(10);
		test.setNextDirection("Down");
		
		test.updateDirection();
		
		assertEquals("Down",test.getDirection());
		assertEquals(10,test.getDX());
		assertEquals(10,test.getDY());
	}
}
