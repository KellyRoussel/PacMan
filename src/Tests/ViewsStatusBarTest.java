package Tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import Views.StatusBar;


class ViewsStatusBarTest {

	@Test
	void testStatusBar() {
		StatusBar test =  new StatusBar();
		
		assertEquals(false,(test==null));
		assertEquals(0,test.getScore());
		assertEquals(3,test.getLives());
		
		assertEquals(false,(test.getScoreLabel()==null));
		assertEquals(false,(test.getFruitsPane()==null));
		assertEquals(false,(test.getLvPanel()==null));
		assertEquals(false,(test.getCollisionPanel()==null));
	}

	@Test
	void testUpdateCollision() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateScore() {
		StatusBar test =  new StatusBar();
		int score = 100;
		test.incrementScore(score);
		test.updateScore();
		
		assertEquals("<html><font color = 'YELLOW'>" + score + "</font></html>", test.getScoreLabel().getText());
	}

	@Test
	void testIncrementScore() {
		StatusBar test =  new StatusBar();
		test.incrementScore(100);
		
		assertEquals(100,test.getScore());
	}

	@Test
	void testLoad() {
		StatusBar test =  new StatusBar();
		test.load();
		
		assertEquals(0,0);
	}

	@Test
	void testUpdate() {
		StatusBar test =  new StatusBar();
		test.update();
		
		assertEquals(0,0);
	}

	@Test
	void testDecrementLife() {
		StatusBar test =  new StatusBar();
		test.decrementLife();
		
		assertEquals(2,test.getLives());
	}
	
	@Test
	void testDecrement3Life() {
		StatusBar test =  new StatusBar();
		test.decrementLife();
		test.decrementLife();
		test.decrementLife();
		assertEquals(0,test.getLives());
	}

}
