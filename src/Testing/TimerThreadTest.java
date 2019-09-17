package Testing;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.channels.InterruptedByTimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Threads.TimerThread;

class TimerThreadTest {
	
	private static TimerThread timer;
	int iTimeInSec = 30;
	
	@BeforeEach
	public void setUp() throws Exception{
		
		//Nouvelle instance
		timer = new TimerThread(iTimeInSec);
		assertNotNull(timer, "Thread init failed");
		
		//Demarrage du Thread
		timer.start();
		assertTrue(timer.isAlive(), "Thread should be started");
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		synchronized(timer) {
			try {
				timer.stopThread();
				timer.join(500);
				if(timer.isAlive()) {
					timer.interrupt();
					throw new InterruptedByTimeoutException();
				}
				
			}catch(InterruptedException | InterruptedByTimeoutException e) {
				assertEquals(null, e, "Throw exception occurred");
			}
			
			assertFalse(timer.isAlive(), "Thread should be stopped");
		}
	}
	
	@Test
	public void timerCountTest() {
		assertTimeout(ofMillis(400), () -> {
			
			long value1 = timer.getTimerCount();
			
			try {
				Thread.sleep(200);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			long value2 = timer.getTimerCount(); 
			
			assertNotEquals(value1, value2, "Value 2 should be bigger then value 1");
			
		});
	}

}
