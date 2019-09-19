package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Views.Resizer;

class ViewsResizerTest {

	@Test
	void testResizeX() {
		Resizer test = new Resizer();
		int x = 10;
		int size = 15;
		int defaultSize = 30;
		int debutX = 0;
		
		int results = test.resizeX(x, size, defaultSize, debutX);
		
		assertEquals(((((x % defaultSize) * (size / defaultSize)) + ((x / defaultSize) * size)) + debutX),results);	
	}

	@Test
	void testResizeY() {
		Resizer test = new Resizer();
		int y = 10;
		int size = 15;
		int defaultSize = 30;
		int debutY = 0;
		
		int results = test.resizeY(y, size, defaultSize, debutY);
		
		assertEquals(((((y % defaultSize) * (size / defaultSize)) + ((y / defaultSize) * size)) + debutY),results);
	}

};
