package inDev.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import inDev.Controllers.GameController;
import inDev.Models.Maze;

public class PacManGame extends JFrame{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	public static int actualWindowWidth = DefaultWidth;
	public static int actualWindowHeight = DefaultHeight;
	private static GamePanel gp = null;
	
	
	private static PacManGame SINGLE_INSTANCE = new PacManGame();
	
	public static PacManGame getInstance() {
      return SINGLE_INSTANCE;
    }
	
	public PacManGame() {
		//this.setSize(DefaultWidth, DefaultHeight);
		//this.setVisible(true);
		//this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gp = new GamePanel();
		new GameController(gp, this);
	}
	
	public static void resize() {
		int mazeSize;
		if (GameController.fullScreen) {
			SINGLE_INSTANCE.setExtendedState(JFrame.MAXIMIZED_BOTH);
			//SINGLE_INSTANCE.setUndecorated(true);
			//SINGLE_INSTANCE.setVisible(true);
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Dimension screenDimension = env.getMaximumWindowBounds().getSize();
			Insets insets = SINGLE_INSTANCE.getInsets();
			final int left = insets.left;
			final int right = insets.right;
			final int top = insets.top;
			final int bottom = insets.bottom;

			actualWindowWidth = screenDimension.width - left - right;
			actualWindowHeight = screenDimension.height - top - bottom;
			mazeSize = Math.min((actualWindowHeight - StatusBar.HEIGHT) / 33, actualWindowWidth / 30);
		}
		else {
			SINGLE_INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SINGLE_INSTANCE.setSize(DefaultWidth, DefaultHeight);
			//SINGLE_INSTANCE.setUndecorated(true);
			SINGLE_INSTANCE.setResizable(false);
			SINGLE_INSTANCE.setVisible(true);
			actualWindowWidth = DefaultWidth;
			actualWindowHeight = DefaultHeight;
			mazeSize = Math.min((actualWindowHeight - StatusBar.HEIGHT) / 33, actualWindowWidth / 30);
			Maze.setDefaultSize(mazeSize);
		}		
		 
		Maze.setSize(mazeSize);
		GamePanel.debutX = (actualWindowWidth - mazeSize * 30) / 2;
		GamePanel.debutY = (actualWindowHeight - mazeSize * 33 - StatusBar.HEIGHT) / 2;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			PacManGame.getInstance();
			PacManGame.resize();
        });
		
	}
}