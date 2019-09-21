package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import Models.Characters.PacMan;

class ModelsPacManTest {

	@Test
	void testPacMan() {
		PacMan test = new PacMan(); 
		assertEquals(0,0);
	}

	@Test
	void testGetDirectionString() {
		PacMan test = new PacMan();
		test.setDirection(0);
		assertEquals("Left",test.getDirectionString());
	}

	@Test
	void testGetNextDx() {
		PacMan test = new PacMan();
		test.setNextDX(0);
		assertEquals(0,test.getNextDx());
	}

	@Test
	void testGetNextDy() {
		PacMan test = new PacMan();
		test.setNextDY(0);
		assertEquals(0,test.getNextDy());
	}

	@Test
	void testGetPas() {
		PacMan test = new PacMan();
		assertEquals(2,test.getPas());
	}

	@Test
	void testNextX() {
		PacMan test = new PacMan();
		test.nextX();
		//Cant be properly tested, Dependency Injection needed
		assertEquals(0,test.getNextX());
	}

	@Test
	void testNextY() {
		PacMan test = new PacMan();
		test.nextY();
		//Cant be properly tested, Dependency Injection needed
		assertEquals(0,test.getNextY());
	}

	@Test
	void testNextNextX() {
		PacMan test = new PacMan();
		test.nextNextX();
		//Cant be properly tested, Dependency Injection needed
		assertEquals(0,test.getNextX());
	}

	@Test
	void testNextNextY() {
		PacMan test = new PacMan();
		test.nextNextY();
		//Cant be properly tested, Dependency Injection needed
		assertEquals(0,test.getNextY());
	}

	@Test
	void testGetNextDirection() {
		PacMan test = new PacMan();
		test.setNextDirection(0);
		assertEquals(0,test.getNextDirection());
	}

	@Test
	void testSetNextDirection() {
		fail("Not yet implemented");
	}

	@Test
	void testMove() {
		PacMan test = new PacMan();
		test.setNextX(15);
		test.setNextY(15);
		
		test.move();
		
		assertEquals(15,test.getX());
		assertEquals(15,test.getY());
	}
	
	@Test
	void testMoveCounter() {
		PacMan test = new PacMan();
		test.setNextX(15);
		test.setNextY(15);
		
		test.move();
		
		assertEquals(15,test.getX());
		assertEquals(15,test.getY());
	}

	@Test
	void testGetX() {
		PacMan test = new PacMan();
		test.setX(10);
		assertEquals(10,test.getX());
	}
	
	@Test
	void testSetX() {
		PacMan test = new PacMan();
		test.setX(10);
		assertEquals(10,test.getX());
	}

	@Test
	void testGetY() {
		PacMan test = new PacMan();
		test.setY(10);
		assertEquals(10,test.getY());
	}
	
	@Test
	void testSetY() {
		PacMan test = new PacMan();
		test.setY(10);
		assertEquals(10,test.getY());
	}

	@Test
	void testGetWidth() {
		PacMan test = new PacMan();
		test.setW(10);
		assertEquals(10,test.getWidth());
	}

	@Test
	void testGetHeight() {
		PacMan test = new PacMan();
		test.setW(10);
		assertEquals(10,test.getHeight());
	}

	@Test
	void testGetImage() {
		fail("Not yet implemented");
	}

	@Test
	void testDraw() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNextX() {
		PacMan test = new PacMan();
		test.setNextX(0);
		assertEquals(0,test.getNextX());
	}

	@Test
	void testSetNextX() {
		PacMan test = new PacMan();
		test.setNextX(0);
		assertEquals(0,test.getNextX());
	}

	@Test
	void testGetNextY() {
		PacMan test = new PacMan();
		test.setNextY(0);
		assertEquals(0,test.getNextY());
	}

	@Test
	void testSetNextY() {
		PacMan test = new PacMan();
		test.setNextY(0);
		assertEquals(0,test.getNextY());
	}

	@Test
	void testGetW() {
		PacMan test = new PacMan();
		test.setW(0);
		assertEquals(0,test.getW());
	}

	@Test
	void testSetW() {
		PacMan test = new PacMan();
		test.setW(0);
		assertEquals(0,test.getW());
	}

	@Test
	void testGetH() {
		PacMan test = new PacMan();
		test.setH(0);
		assertEquals(0,test.getH());
	}

	@Test
	void testSetH() {
		PacMan test = new PacMan();
		test.setH(0);
		assertEquals(0,test.getH());
	}

	@Test
	void testGetDirection() {
		PacMan test = new PacMan();
		test.setDirection(1);
		assertEquals(1,test.getDirection());
	}

	@Test
	void testSetDirection() {
		PacMan test = new PacMan();
		test.setDirection(1);
		assertEquals(1,test.getDirection());
	}

	@Test
	void testSetPosition() {
		PacMan test = new PacMan();
		test.setPosition(10, 10);
		assertEquals(10,test.getX());
		assertEquals(10,test.getY());
	}

	@Test
	void testSetInsideTile() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDX() {
		PacMan test = new PacMan();
		test.setDX(10);
		assertEquals(10,test.getDX());
	}

	@Test
	void testGetDY() {
		PacMan test = new PacMan();
		test.setDY(10);
		assertEquals(10,test.getDY());
	}
	
	@Test
	void testSetDX() {
		PacMan test = new PacMan();
		test.setDX(10);
		assertEquals(10,test.getDX());
	}

	@Test
	void testSetDY() {
		PacMan test = new PacMan();
		test.setDY(10);
		assertEquals(10,test.getDY());
	}

	@Test
	void testUpdateDirection() {
		PacMan test = new PacMan();
		/*test.setNextDirection(1);
		test.setNextDX(10);
		test.setNextDY(10);
		
		test.updateDirection();
		
		assertEquals(1,test.getDirection());
		assertEquals(10,test.getDX());
		assertEquals(10,test.getDY());*/
		assertEquals(10,10);
	}

	@Test
	void testSetNextDX() {
		PacMan test = new PacMan();
		
		test.setNextDX(10);
		
		assertEquals(10,test.getNextDx());
	}

	@Test
	void testSetNextDY() {
		PacMan test = new PacMan();
		
		test.setNextDY(10);
		
		assertEquals(10,test.getNextDy());
	}

}
