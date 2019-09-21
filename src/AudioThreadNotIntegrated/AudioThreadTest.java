package AudioThreadNotIntegrated;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioThreadTest extends Thread {
	protected static final long SLEEP_TIMER = 50; // ms

	// Contexte du thread
	protected boolean isRunning = true;

	protected AtomicBoolean makeInPause = new AtomicBoolean(false);
	
	public AudioThreadTest() {
		super();
	}

	// mettre le thread en stop
	public void stopThread() {
		isRunning = false;
		try {
			this.join(500);
			if (this.isAlive()) {
				this.interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// methode pour lancer l audio
	public synchronized void play(Clip clip,float volume) {
		// regler le volume
		changeVolume(clip,volume);
		// start the clip
		clip.start();
	}

	// methode pour mettre l audio en pause
	public synchronized boolean pause(Clip clip, boolean isPaused) {
		if (isPaused) {
			// audio deja en pause
			return isPaused;
		}
		clip.stop();
		return !isPaused;
	}

	// methode pour relancer l audio si en pause
	public synchronized void resumeAudio(Clip clip, String filePath, boolean isPaused , float volume) {
		if (!isPaused) {
			// audio deja lance
			return;
		}
		clip.close();
		resetAudioStream(clip, filePath);
		this.play(clip,volume);
	}

	// methode pour relancer l audio depuis le début
	public synchronized void restart(Clip clip, String filePath, float volume) {
		stop(clip);
		resetAudioStream(clip, filePath);
		play(clip, volume);
	}

	// Methode pour stopper l audio
	public synchronized void stop(Clip clip) {
		try {
			clip.stop();
			clip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// réinitialiser l audio stream
	public synchronized void resetAudioStream(Clip clip, String filePath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			clip.open(audioInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// changer le volume selon les champs de volume de musique et de sons
	public synchronized void changeVolume(Clip clip, float volume) {
		if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			// determination du gain
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}
	

	// gestion de mode mute
	public synchronized boolean MuteOnOff(Clip clip, boolean muted) {
		BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		muteControl.setValue(!muted);
		return !muted;
	}

	public boolean getMakeInPause() {
		return makeInPause.get();
	}

	public void setMakeInPause(boolean makeInPause) {
		this.makeInPause = new AtomicBoolean(makeInPause);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	

}
