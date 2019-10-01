package AudioThreadNotIntegrated;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MySound {
	private Clip clip;
	private String clipFilePath;
	private eSoundState clipState = eSoundState.RESUME;
	private boolean isMute = false;

	public MySound(String clipFilePath) {
		super();
		this.clipFilePath = clipFilePath;
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(this.clipFilePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public String getClipFilePath() {
		return clipFilePath;
	}

	public void setClipFilePath(String clipFilePath) {
		this.clipFilePath = clipFilePath;
	}

	public eSoundState getClipState() {
		return clipState;
	}

	public void setClipState(eSoundState clipState) {
		this.clipState = clipState;
	}

	public boolean isMute() {
		return isMute;
	}

	public void setMute(boolean isMute) {
		this.isMute =isMute;
	}

}
