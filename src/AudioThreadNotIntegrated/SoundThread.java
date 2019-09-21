package AudioThreadNotIntegrated;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoundThread extends AudioThreadTest {
	// list of available sounds
	private List<MySound> sounds = new ArrayList<MySound>();
	private String[] soundsFilePath = { 
			"Ressources" + File.separator + "pacman_chomp.wav",
			"Ressources" + File.separator + "pacman_death.wav", 
			"Ressources" + File.separator + "beginning.wav" };

	// variables that detect if there is change in the game
	private AtomicBoolean isGumEaten = new AtomicBoolean(false);
	private AtomicBoolean isPacManDead = new AtomicBoolean(false);
	private AtomicBoolean isGameStarted = new AtomicBoolean(false);
	private static AtomicBoolean MuteOnOff = new AtomicBoolean(false);
	private static boolean isPaused = true;
	private static float volume;

	// champs qui sont a true si le clip correspondant aux sons est lance pour la
	// premiere fois
	private static boolean firstTimeDead = true;
	private static boolean firstTimeEat = true;
	private static boolean firstTimeStart = true;

	public SoundThread() {
		super();
		initSounds();
	}

	public void run() {
		System.out.println("START - " + this.getName());
		while (isRunning) {
			// Dans le cas ou il n y a pas de pause
			if (!makeInPause.get()) {

				if (MuteOnOff.get()) {
					for (MySound s : sounds) {
						s.setMute(MuteOnOff(s.getClip(), s.isMute()));
					}
					MuteOnOff.compareAndExchange(true, false);
				}

				// Gestion de son
				for (MySound s : sounds) {
					if (s.getClipState() == eSoundState.PLAY) {
						play(s.getClip(), volume);
						s.setClipState(eSoundState.STOP);
					}
					if (s.getClipState() == eSoundState.RESUME) {
						resumeAudio(s.getClip(), s.getClipFilePath(), isPaused, volume);
						s.setClipState(eSoundState.STOP);
					}
				}

				// mode en pause du jeu
			} else {
				for (MySound s : sounds) {
					pause(s.getClip(), isPaused());
				}
				setPaused(!isPaused());
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
			for (MySound s : sounds) {
				stop(s.getClip());
			}
		}
		System.out.println("STOP - " + this.getName());
	}

	public void initSounds() {
		for (String s : soundsFilePath) {
			sounds.add(new MySound(s));
		}
	}

	public List<MySound> getSounds() {
		return sounds;
	}

	public void setSounds(List<MySound> sounds) {
		this.sounds = sounds;
	}

	public String[] getSoundsFilePath() {
		return soundsFilePath;
	}

	public void setSoundsFilePath(String[] soundsFilePath) {
		this.soundsFilePath = soundsFilePath;
	}

	public AtomicBoolean getIsGumEaten() {
		return isGumEaten;
	}

	public void setIsGumEaten(boolean isGumEaten) {
		if(isGumEaten) {
			if(firstTimeEat) {
				sounds.get(0).setClipState(eSoundState.PLAY);
				firstTimeEat = false;
			}
			else {
				if(!sounds.get(0).getClip().isRunning()) {
					sounds.get(0).setClipState(eSoundState.RESUME);
				}
			}
		}
	}

	public AtomicBoolean getIsPacManDead() {
		return isPacManDead;
	}

	public void setIsPacManDead(boolean isPacManDead) {
		this.isPacManDead = new AtomicBoolean(isPacManDead);
		if(isPacManDead) {
			if(firstTimeDead) {
				sounds.get(1).setClipState(eSoundState.PLAY);
				firstTimeDead = false;
			}
			else {
				if(!sounds.get(1).getClip().isRunning()) {
					sounds.get(1).setClipState(eSoundState.RESUME);
				}
			}
		}
	}

	public AtomicBoolean getIsGameStarted() {
		return isGameStarted;
	}

	public void setIsGameStarted(boolean isGameStarted) {
		if(isGameStarted) {
			if(firstTimeStart) {
				sounds.get(2).setClipState(eSoundState.PLAY);
				firstTimeStart = false;
			}
			else {
				if(!sounds.get(2).getClip().isRunning()) {
					sounds.get(2).setClipState(eSoundState.RESUME);
				}
			}
		}	
	}

	public static boolean getMuteOnOff() {
		return MuteOnOff.get();
	}

	public static void setMuteOnOff(boolean muteOnOff) {
		MuteOnOff = new AtomicBoolean(muteOnOff);
	}

	public static boolean isPaused() {
		return isPaused;
	}

	public static void setPaused(boolean isPaused) {
		SoundThread.isPaused = isPaused;
	}

	public static float getVolume() {
		return volume;
	}

	public static void setVolume(float volume) {
		SoundThread.volume = volume;
	}

}
