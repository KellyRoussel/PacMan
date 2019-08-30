package inDev;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {
	
	AudioClip clip;
	public Sound(URL url) {
		clip = Applet.newAudioClip(url);
	}
	
	public void play() {
		clip.play();
	}
	
	public void loop() {
		clip.loop();
	}
	
	public void stop(){
		clip.stop();
	}
 
}
