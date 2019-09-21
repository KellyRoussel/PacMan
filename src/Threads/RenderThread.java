package Tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.channels.InterruptedByTimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameController;
import Threads.RenderThread;
import Views.MainGame;

public class RenderThreadTest {
		
		private static final long JOIN_TIMER = 500;
		private static RenderThread renderThread;

		@BeforeEach
		public void setUp() throws Exception 
		{
			GameController gController = new GameController(new MainGame());
			gController.startGame();
			renderThread = gController.gettRender();
			
			assertTrue("Thread should be started", renderThread.isAlive());
		}

		/**
		 * DESC: Method called AFTER each single test.
		 * @throws Exception
		 */
		@AfterEach
		public void tearDown() throws Exception 
		{
			// Stop the Counter thread		
			synchronized(renderThread)
			{
				try 
				{		
					renderThread.stopThread();
					renderThread.join(JOIN_TIMER);
					if (renderThread.isAlive())
					{
						renderThread.interrupt();
						throw new InterruptedByTimeoutException();
					}
					
				} 
				catch (InterruptedException | InterruptedByTimeoutException e) 
				{
					assertEquals("Throw exception occurred", null, e);
				}
				
				assertFalse("Thread should be stopped", renderThread.isAlive());
			}
		}
		
	
		
		@Test
		public void testUpdatedFPS() {
			synchronized (renderThread) {
				int fps = renderThread.updatedFPS(30, System.currentTimeMillis());
				
				assertTrue(fps <= 60 && fps >= 30);
			}
		}
		
		@Test
		public void testResume() {

			synchronized (renderThread) {
				synchronized (renderThread) {
					renderThread.setResume(true);
					assertEquals(renderThread.isResume(), true);
				}
			}
		}

}
