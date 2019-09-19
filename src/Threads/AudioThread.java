package Threads;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

	public class AudioThread extends Thread {

		// champs pour la musique du jeu
		private static AtomicBoolean isMusicPaused = new AtomicBoolean(true);
		private static float MusicVolume = 0.7f;
		private static AtomicBoolean MusicMuted = new AtomicBoolean(false);

		// champs pour les sons du jeu
		private static float SoundVolume = 0.8f;
		private static AtomicBoolean SoundMuted = new AtomicBoolean(false);
		
		// champs qui sont a true si le clip correspondant aux sons est lance pour la premiere fois 
		private static boolean firstTimeDead = true;
		private static boolean firstTimeEat = true;
		private static boolean firstTimeStart = true;

		// les clips du jeu et leurs emplacements
		private Clip musicBackgroundClip;
		private final static String MusicfilePath = "Ressources" + File.separator + "loop.wav";
		private Clip deadPacmanSoundClip;
		private final static String deadPacmanSoundfilePath = "Ressources" + File.separator + "pacman_death.wav";
		private Clip eatedGumSoundClip;
		private final static String eatGumSoundfilePath = "Ressources" + File.separator + "pacman_chomp.wav";
		private Clip startGameSoundClip;
		private final static String startGameSoundfilePath = "Ressources" + File.separator + "beginning.wav";
		
		private static final long SLEEP_TIMER = 50; //ms

		// variable qui detecte le changement du mode mute 
		private static AtomicBoolean MuteOnOffMusic = new AtomicBoolean(false);
		private static AtomicBoolean MuteOnOffSound = new AtomicBoolean(false);
		
		// Contexte du thread
		private AtomicBoolean isRunning = new AtomicBoolean(true);
		private AtomicBoolean isEaten 	= new AtomicBoolean(false);
		private AtomicBoolean isDead 	= new AtomicBoolean(false);
		private AtomicBoolean isStart 	= new AtomicBoolean(false);
		private AtomicBoolean isPause 	= new AtomicBoolean(false);

		
		
		public AudioThread() {
			// initialiser tous les clips
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(MusicfilePath).getAbsoluteFile());
				musicBackgroundClip = AudioSystem.getClip();
				musicBackgroundClip.open(audioInputStream);

				AudioInputStream audioInputStream1  = AudioSystem.getAudioInputStream(new File(deadPacmanSoundfilePath).getAbsoluteFile());
				deadPacmanSoundClip = AudioSystem.getClip();
				deadPacmanSoundClip.open(audioInputStream1);

				AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File(eatGumSoundfilePath).getAbsoluteFile());
				eatedGumSoundClip = AudioSystem.getClip();
				eatedGumSoundClip.open(audioInputStream2);

				AudioInputStream audioInputStream3 = AudioSystem.getAudioInputStream(new File(startGameSoundfilePath).getAbsoluteFile());
				startGameSoundClip = AudioSystem.getClip();
				startGameSoundClip.open(audioInputStream3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			System.out.println("Start AUDIO Thread");
			play(musicBackgroundClip, true);
			while (isRunning.get()) {
				// Dans le cas ou il n y a pas de pause
				if (!isPause.get()) {
					if (MuteOnOffMusic.get()) {
						MuteOnOff(musicBackgroundClip, true);
						MuteOnOffMusic.compareAndExchange(true, false);
					}
					// resume la musique d arriere plan
					resumeAudio(musicBackgroundClip, MusicfilePath, true);
					
					if (MuteOnOffSound.get()) {
						MuteOnOff(eatedGumSoundClip, false);
						MuteOnOff(startGameSoundClip, false);
						MuteOnOff(deadPacmanSoundClip, false);
						MuteOnOffSound.compareAndExchange(true, false);
					}
					// si le pacman est mort 
					if (isDead.get()) {
						isDead.compareAndExchange(true, false);
						// et le son n est pas en mode mute
						if(!SoundMuted.get()) {
							// si le clip deadPacmanSoundClip est deja lance une fois au moins
							if(!firstTimeDead && !deadPacmanSoundClip.isRunning()) {
								restart(deadPacmanSoundClip ,deadPacmanSoundfilePath ,false);
							}
							// la premiere fois qu on lance le clip deadPacmanSoundClip
							else {
								play(deadPacmanSoundClip, false);
								firstTimeDead = false;
							}
						}	
					}
					
					// si le pacman mange une gomme
					if (isEaten.get()) {
						// et le son n est pas en mode mute
						if(!SoundMuted.get()) {
							// si le clip eatedGumSoundClip est deja lance une fois au moins
							if(!firstTimeEat && !eatedGumSoundClip.isRunning()) {
								restart(eatedGumSoundClip ,eatGumSoundfilePath ,false);
							}
							// la premiere fois qu on lance le clip eatedGumSoundClip
							else {
								play(eatedGumSoundClip, false);
								firstTimeEat = false;
							}
						}
						isEaten.compareAndExchange(true, false);
					}
					
					//  lancement d un nouveau niveau
					if (isStart.get()) {
						// et le son n est pas en mode mute
						if(!SoundMuted.get()) {
							// si le clip eatedGumSoundClip est deja lance une fois au moins
							if(!firstTimeStart) {
								restart(startGameSoundClip ,startGameSoundfilePath ,false);
							}
							// la premiere lance du clip eatedGumSoundClip
							else {
								play(startGameSoundClip, false);
								firstTimeStart = false;
							}
						}
						isStart.compareAndExchange(true, false);
					}
					
				// mode en pause du jeu
				} else {
					pause(musicBackgroundClip);
					pause(eatedGumSoundClip);
					pause(startGameSoundClip);
					pause(deadPacmanSoundClip);
				}
				
				// mettre le thread en sleep 
				try {
					Thread.sleep(SLEEP_TIMER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//  Si le jeu est termine donc on stop et on ferme tous les clips
			if (!isRunning.get()) {
				stop(musicBackgroundClip);
				stop(eatedGumSoundClip);
				stop(startGameSoundClip);
				stop(deadPacmanSoundClip);
			}
			System.out.println("Stop AUDIO Thread");
		}
		
		// mettre le thread en stop
		public void stopThread() {
			isRunning.compareAndExchange(true, false);	
			try {
				this.join(500);
				if (this.isAlive()){
					this.interrupt();
				}
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}

		// methode pour lancer l audio 
		public synchronized void play(Clip clip, boolean isMusic) {
			// regler le volume
			changeVolume(clip, isMusic);
			// start the clip
			clip.start();
			// changer le status en played
			if(isMusic) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				isMusicPaused.compareAndSet(true, false);
			}
		}

		// methode pour mettre l audio en pause
		public synchronized void pause(Clip clip) {
			if (isMusicPaused.get()) {
				// audio deja en pause
				return;
			}
			clip.stop();
			isMusicPaused.compareAndExchange(false, true);
		}

		// methode pour relancer l audio si en pause
		public synchronized void resumeAudio(Clip clip, String filePath, boolean isMusic) {
			if (!isMusicPaused.get()) {
				// audio deja lance
				return;
			}
			clip.close();
			resetAudioStream(clip, filePath, isMusic);
			this.play(clip, isMusic);
		}

		
		 // methode pour relancer l audio depuis le début 
		 public synchronized void restart(Clip clip, String filePath ,boolean isMusic){	
			 stop(clip);
			 resetAudioStream(clip ,filePath ,isMusic); 
			 play(clip,isMusic); 
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
		public synchronized void resetAudioStream(Clip clip, String filePath, boolean isMusic) {
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
				clip.open(audioInputStream);
				if (isMusic) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				this.play(clip, isMusic);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// changer le volume selon les champs de volume de musique et de sons
		public synchronized void changeVolume(Clip clip, boolean isMusic) {
			if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				// determination du gain
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain;
				if (isMusic) {
					gain = (range * MusicVolume) + gainControl.getMinimum();
				} else {
					gain = (range * SoundVolume) + gainControl.getMinimum();
				}
				gainControl.setValue(gain);
			}
		}

		// gestion de mode mute 
		public synchronized void MuteOnOff(Clip clip, boolean isMusic) {
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			if (isMusic) {
				muteControl.setValue(!MusicMuted.get());
				MusicMuted.getAndSet(!MusicMuted.get());
			} else {
				muteControl.setValue(!SoundMuted.get());
				SoundMuted.getAndSet(!SoundMuted.get());
			}
		}

		
		// Getters et Setters
		public static boolean isMusicPaused() {
			return isMusicPaused.get();
		}

		public static void setMusicPaused(boolean isMusicPaused) {
			AudioThread.isMusicPaused = new AtomicBoolean(isMusicPaused);
		}

		public static float getMusicVolume() {
			return MusicVolume;
		}

		public static void setMusicVolume(float musicVolume) {
			MusicVolume = musicVolume;
		}

		public static boolean isMusicMuted() {
			return MusicMuted.get();
		}

		public static void setMusicMuted(boolean musicMuted) {
			MusicMuted = new AtomicBoolean(musicMuted);
		}

		public static float getSoundVolume() {
			return SoundVolume;
		}

		public static void setSoundVolume(float soundVolume) {
			SoundVolume = soundVolume;
		}

		public static boolean isSoundMuted() {
			return SoundMuted.get();
		}

		public static void setSoundMuted(boolean soundMuted) {
			SoundMuted = new AtomicBoolean(soundMuted);
		}

		public boolean getIsRunning() {
			return isRunning.get();
		}

		public void setIsRunning(boolean isRunning) {
			this.isRunning = new AtomicBoolean(isRunning);
		}

		public boolean getIsEaten() {
			return isEaten.get();
		}

		public void setIsEaten(boolean isEaten) {
			this.isEaten = new AtomicBoolean(isEaten);
		}

		public boolean getIsDead() {
			return isDead.get();
		}

		public void setIsDead(boolean isDead) {
			this.isDead = new AtomicBoolean(isDead);
		}

		public boolean getIsStart() {
			return isStart.get();
		}

		public void setIsStart(boolean isStart) {
			this.isStart = new AtomicBoolean(isStart);
		}

		public boolean getIsPause() {
			return isPause.get();
		}

		public void setIsPause(boolean isPause) {
			this.isPause = new AtomicBoolean(isPause);
		}

		public static boolean getMuteOnOffMusic() {
			return MuteOnOffMusic.get();
		}

		public static void setMuteOnOffMusic(boolean MuteOnOff) {
			MuteOnOffMusic = new AtomicBoolean(MuteOnOff);
		}
		
		public static boolean getMuteOnOffSound() {
			return MuteOnOffSound.get();
		}

		public static void setMuteOnOffSound(boolean MuteOnOff) {
			MuteOnOffSound = new AtomicBoolean(MuteOnOff);
		}
		
		
}
