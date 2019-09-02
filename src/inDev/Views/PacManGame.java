package inDev.Views;

import java.awt.BorderLayout;
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

import inDev.Controllers.GameController;

public class PacManGame extends JFrame{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	public static int actualWindowWidth = DefaultWidth;
	public static int actualWindowHeight = DefaultHeight;

	
	private static PacManGame SINGLE_INSTANCE = new PacManGame();
	  
	public static PacManGame getInstance() {
      return SINGLE_INSTANCE;
    }
	
	public PacManGame() {
		//this.setSize(DefaultWidth, DefaultHeight);
		//this.setVisible(true);
		//this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gp = new GamePanel();
		new GameController(new GamePanel(), this);
	}
	
	public static void resize() {
		if (GameController.fullScreen) {
			SINGLE_INSTANCE.setExtendedState(JFrame.MAXIMIZED_BOTH);
			//SINGLE_INSTANCE.setUndecorated(true);
			//SINGLE_INSTANCE.setVisible(true);
			actualWindowWidth = (int) SINGLE_INSTANCE.getSize().getWidth();
			actualWindowHeight = (int) SINGLE_INSTANCE.getSize().getHeight();
		}
		else {
			SINGLE_INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SINGLE_INSTANCE.setSize(DefaultWidth, DefaultHeight);
			//SINGLE_INSTANCE.setUndecorated(true);
			SINGLE_INSTANCE.setResizable(false);
			SINGLE_INSTANCE.setVisible(true);
			actualWindowWidth = DefaultWidth;
			actualWindowHeight = DefaultHeight;
		}		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			PacManGame.getInstance();
			PacManGame.resize();
        });
		
	}
}