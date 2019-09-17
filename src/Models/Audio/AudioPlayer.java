package Models.Audio;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class AudioPlayer {
	protected Clip clip;

	// current status of clip
	private AtomicReference<String> status = new AtomicReference<String>("paused");

	protected String filePath;

	// constructeur pour initialiser les streams et le clip
	public AudioPlayer(String filePath) {
		try {
			this.filePath = filePath;
			
			// create AudioInputStream object
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

			// creer une reference du clip
			clip = AudioSystem.getClip();

			// ouvrir audioInputStream dans le clip
			clip.open(audioInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to play the audio
	public synchronized void play() {
		// start the clip
		clip.start();
		// changer le status en played
		status.compareAndExchange("paused", "played");
	}

	// methode to mettre l audio en pause
	public synchronized void pause() {
		if (status.equals("paused")) {
			// audio deja en pause
			return;
		}
		clip.stop();
		status.compareAndExchange("played", "paused");
	}

	// methode pour relancer l audio si en pause
	public synchronized void resumeAudio() {
		if (status.equals("played")) {
			// audio deja lance
			return;
		}
		clip.close();
		resetAudioStream();
		this.play();
	}

	// methode pour relancer l audio depuis le début
	public synchronized void restart() {
		this.stop();
		resetAudioStream();
		this.play();
	}

	// Methode pour stopper l audio
	public synchronized void stop() {
		try {
			clip.stop();
			clip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// réinitialiser l audio stream
	public abstract void resetAudioStream(); 

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public AtomicReference<String> getStatus() {
		return status;
	}

	public void setStatus(AtomicReference<String> status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
