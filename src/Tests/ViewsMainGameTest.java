package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Views.MainGame;

class ViewsMainGameTest {

	@Test
	void testGetInstance() {
		MainGame test = new MainGame();
		MainGame Instance;
		
		Instance = test.getInstance();
		
		assertEquals(false,(Instance==null));
	}

	@Test
	void testMainGame() {
		fail("Not yet implemented");
	}

	@Test
	void testResize() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateMazeSize() {
		fail("Not yet implemented");
	}

	@Test
	void testMain() {
		fail("Not yet implemented");
	}

}
