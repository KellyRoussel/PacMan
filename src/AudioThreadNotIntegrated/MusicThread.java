package AudioThreadNotIntegrated;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.Clip;

public class MusicThread extends AudioThreadTest {
	private String musicFilePath = "Ressources" + File.separator + "loop.wav";
	private Clip musicClip;
	private boolean isMuted = false;
	private int volume;
	private boolean isPaused = true;
	private static AtomicBoolean MuteOnOff = new AtomicBoolean(false);

	public void run() {
		System.out.println("START - " + this.getName());
		play(musicClip, volume);
		while (isRunning) {
			// Dans le cas ou il n y a pas de pause
			if (!makeInPause.get()) {
				if (MuteOnOff.get()) {
					isMuted = MuteOnOff(musicClip, isMuted);
					MuteOnOff.compareAndExchange(true, false);
				}
				// resume la musique d arriere plan
				resumeAudio(musicClip, musicFilePath, isPaused, volume);

				// mode en pause du jeu
			} else {
				pause(musicClip, isPaused);
			}

			// mettre le thread en sleep
			try {
				Thread.sleep(SLEEP_TIMER);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Si le jeu est termine donc on stop et on ferme tous les clips
		if (!isRunning) {
			stop(musicClip);
		}
		System.out.println("STOP - " + this.getName());
	}

	public String getMusicFilePath() {
		return musicFilePath;
	}

	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath;
	}

	public Clip getMusicClip() {
		return musicClip;
	}

	public void setMusicClip(Clip musicClip) {
		this.musicClip = musicClip;
	}

	public boolean getIsMuted() {
		return isMuted;
	}

	public void setIsMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public boolean getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public static AtomicBoolean getMuteOnOff() {
		return MuteOnOff;
	}

	public static void setMuteOnOff(AtomicBoolean muteOnOff) {
		MuteOnOff = muteOnOff;
	}

}
