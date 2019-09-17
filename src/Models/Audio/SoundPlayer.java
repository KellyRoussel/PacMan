package Models.Audio;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;

public class SoundPlayer extends AudioPlayer {

	// Volume des sons du jeu
	private static float SoundVolume;

	// boolean qui est true si le son est en mute mode
	private static AtomicBoolean SoundMuted;

	public SoundPlayer(String filePath) {
		super(filePath);
		// TODO Auto-generated constructor stub
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
			this.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// valeur du volume de 0 a 1
	public synchronized void changeVolume() {
		if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			// determination du gain
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * SoundVolume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}

	public synchronized void muteOnOff() {
		BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		muteControl.setValue(!SoundMuted.get());
		SoundMuted.getAndSet(!SoundMuted.get());
	}

	public static float getSoundVolume() {
		return SoundVolume;
	}

	public static void setSoundVolume(float soundVolume) {
		SoundVolume = soundVolume;
	}

	public static AtomicBoolean getSoundMuted() {
		return SoundMuted;
	}

	// pour changer l etat SoundMuted on fait appel a muteOnOff et non le setter
	public static void setSoundMuted(AtomicBoolean soundMuted) {
		SoundMuted = soundMuted;
	}

}
