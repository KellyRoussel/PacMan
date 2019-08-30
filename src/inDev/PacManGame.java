package inDev;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;

public class PacManGame extends JFrame{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	
	public PacManGame() {
		this.setSize(DefaultWidth, DefaultHeight);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gp = new GamePanel();
		this.setResizable(false);
		new GameController(new GamePanel(), this);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			new PacManGame();
        });
	}
}
