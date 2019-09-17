package Threads;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import Models.Audio.AudioPlayer;
import Models.Audio.MusicPlayer;
import Models.Audio.SoundPlayer;

public class AudioThread extends Thread{
	
	private ArrayList<SoundPlayer> soundList = new ArrayList<SoundPlayer>();
	
	private MusicPlayer gameMusic;
	private SoundPlayer gumEatenSound;
	private SoundPlayer startGameSound;
	private SoundPlayer deadPacmanSound;
	
	private static AtomicInteger musicVolume;   // valeur permise 0 10 .. 100
	private static AtomicInteger soundVolume;	// valeur permise 0 10 .. 100
	private static AtomicBoolean isSoundMute = new AtomicBoolean(false);
	private static AtomicBoolean isMusicMute = new AtomicBoolean(false);

	public void AudioThread() {
		//initialiser la musique du jeu
		gameMusic = new MusicPlayer("Ressources" + File.separator + "loop.wav");
		
		//initialiser les sons et les stocker dans la liste soundList
		gumEatenSound = new SoundPlayer("Ressources" + File.separator + "pacman_chomp.wav");
		soundList.add(gumEatenSound);
		startGameSound = new SoundPlayer("Ressources"+File.separator +"beginning.wav");
		soundList.add(startGameSound);
		deadPacmanSound = new SoundPlayer("Ressources"+File.separator +"pacman_death.wav");
		soundList.add(deadPacmanSound);
		
	}

	public void run() {
		
	}
	
	public ArrayList<SoundPlayer> getSoundList() {
		return soundList;
	}


	public void setSoundList(ArrayList<SoundPlayer> soundList) {
		this.soundList = soundList;
	}


	public MusicPlayer getGameMusic() {
		return gameMusic;
	}


	public void setGameMusic(MusicPlayer gameMusic) {
		this.gameMusic = gameMusic;
	}


	public static int getMusicVolume() {
		return musicVolume.get();
	}


	public static void setMusicVolume(int musicVolume) {
		AudioThread.musicVolume = new AtomicInteger(musicVolume);
	}


	public static int getSoundVolume() {
		return soundVolume.get();
	}


	public static void setSoundVolume(int soundVolume) {
		AudioThread.soundVolume = new AtomicInteger(soundVolume);
	}


	public static boolean getIsSoundMute() {
		return isSoundMute.get();
	}


	public static void setIsSoundMute(boolean isSoundMute) {
		AudioThread.isSoundMute = new AtomicBoolean(isSoundMute);
	}


	public static boolean getIsMusicMute() {
		return isMusicMute.get();
	}


	public static void setIsMusicMute(boolean isMusicMute) {
		AudioThread.isMusicMute = new AtomicBoolean(isMusicMute);
	}
	
}
