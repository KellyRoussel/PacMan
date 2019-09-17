package Models.Audio;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicPlayer extends AudioPlayer {

	// Volume de la musique du jeu
	private static float MusicVolume;
	
	// boolean qui est true si la musique est en mute mode
	private static AtomicBoolean MusicMuted;
	
	
	public MusicPlayer(String filePath) {
		super(filePath);
		
		// pour boucler l audio
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public synchronized void play() {
		// regler le volume 
		changeVolume();
		super.play();
	}
	
	public synchronized void resetAudioStream() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			this.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		// valeur du volume de 0 a 1
		public synchronized void changeVolume() {
			if(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				// determination du gain
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain = (range * MusicVolume) + gainControl.getMinimum();
				gainControl.setValue(gain);
			}
		}
		
		public synchronized void muteOnOff() {
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(!MusicMuted.get());
			MusicMuted.getAndSet(!MusicMuted.get());
		}

		public static float getMusicVolume() {
			return MusicVolume;
		}

		public static void setMusicVolume(float musicVolume) {
			MusicVolume = musicVolume;
		}

		private static AtomicBoolean getMusicMuted() {
			return MusicMuted;
		}

		// pour changer l etat MusicMuted on fait appel a muteOnOff et non le setter
		private static void setMusicMuted(AtomicBoolean musicMuted) {
			MusicMuted = musicMuted;
		}
		
		

}
