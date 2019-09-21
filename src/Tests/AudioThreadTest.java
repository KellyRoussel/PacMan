package Tests;

import Threads.AudioThread;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioThreadTest {
	private AudioThread tAudio;

	@BeforeEach
	void setUp() throws Exception {
		tAudio = new AudioThread();
		assertNotNull(tAudio, "AudioThread Not initialised");
		tAudio.setName("Audio");
		tAudio.start();
		assertTrue(tAudio.isAlive(), "AudioThread Not started");

	}

	@AfterEach
	void tearDown() throws Exception {
		tAudio.stopThread();
		Thread.sleep(200);
		assertFalse(tAudio.isAlive(), "AudioThread Not stopped");
	}

	@Test
	void testVolume() {
		float volume1 = tAudio.getSoundVolume();
		tAudio.setSoundVolume(volume1 + 0.005f);
		assertNotEquals(volume1, tAudio.getSoundVolume(), "Volume Not Changed");

		float volume2 = tAudio.getMusicVolume();
		tAudio.setMusicVolume(volume1 + 0.005f);
		assertNotEquals(volume1, tAudio.getMusicVolume(), "Volume Not Changed");
	}

	@Test
	void testMute() {
		try {
			tAudio.setMusicMuted(true);;
			tAudio.setMuteOnOffMusic(true);

			Thread.sleep(200);

			assertFalse(tAudio.isMusicMuted(), "Mute MusicMode Not Changed");

			tAudio.setSoundMuted(true);;
			tAudio.setMuteOnOffSound(true);

			Thread.sleep(200);

			assertFalse(tAudio.isSoundMuted(), "Mute SoundMode Not Changed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testPause() {
		try {

			tAudio.setMusicPaused(false);
			tAudio.setIsPause(true);
		

			Thread.sleep(500);

			assertFalse(tAudio.isMusicPaused(), "Pause Mode for Music Not Changed");

			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testIsDead() {
		try {

			tAudio.setIsDead(true);
			tAudio.setSoundMuted(false);


			Thread.sleep(500);
			
			assertFalse(tAudio.getIsDead(), "IsDead should be turned to false");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	void testIsStart() {
		try {

			tAudio.setIsStart(true);
			tAudio.setSoundMuted(false);


			Thread.sleep(500);
			
			assertFalse(tAudio.getIsStart(), "IsStart should be turned to false");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsEaten() {
		try {

			tAudio.setIsEaten(true);
			tAudio.setSoundMuted(false);

			Thread.sleep(500);
			
			assertFalse(tAudio.getIsEaten(), "IsEaten should be turned to false");
			
			Thread.sleep(1500);
			
			//so that the test pass from the restart method
			tAudio.setIsEaten(true);
			tAudio.setSoundMuted(false);


			Thread.sleep(500);
			
			assertFalse(tAudio.getIsEaten(), "IsEaten should be turned to false");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
