package inDev;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class PacManGame extends JFrame{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	
	public PacManGame() {
		this.setSize(DefaultWidth, DefaultHeight);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gp = new GamePanel();
<<<<<<< HEAD
=======
		this.setResizable(true);
>>>>>>> 8b2791e3d2925bb7b0e9b018d4cb65af471e1afc
		new GameController(new GamePanel(), this);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			new PacManGame();
        });
		
	}
}
