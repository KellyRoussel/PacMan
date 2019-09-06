package Views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

import Controllers.GameController;
import Models.Maze;

public class MainGame extends JFrame{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	
	public static int actualWindowWidth;
	public static int actualWindowHeight;
	
	
	private static MainGame SINGLE_INSTANCE = new MainGame();
	
	public static MainGame getInstance() {
      return SINGLE_INSTANCE;
    }
	
	public MainGame() {
		
		actualWindowHeight = DefaultHeight;
		actualWindowWidth = DefaultWidth;
		new GameController(new GamePanel(), this);
	}
	
	public static void resize() {
		int mazeSize;
		if (GameController.fullScreen) {
			SINGLE_INSTANCE.setExtendedState(JFrame.MAXIMIZED_BOTH);
			
			//La taille de l'ecran
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Dimension screenDimension = env.getMaximumWindowBounds().getSize();

			//La taille des extremites de la fenetre
			Insets insets = SINGLE_INSTANCE.getInsets();
			final int left = insets.left;
			final int right = insets.right;
			final int top = insets.top;
			final int bottom = insets.bottom;
			
			//La taille du Panel alors sera la taille de l'ecran - celle de la fenetre
			actualWindowWidth = screenDimension.width - left - right;
			actualWindowHeight = screenDimension.height - top - bottom;
			
			
		}
		else {
			SINGLE_INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SINGLE_INSTANCE.setSize(DefaultWidth, DefaultHeight);
			SINGLE_INSTANCE.setResizable(false);
			SINGLE_INSTANCE.setVisible(true);
			actualWindowWidth = DefaultWidth;
			actualWindowHeight = DefaultHeight;
		}		
		
	}
	
	// redimensionner le labyrinthe
	public static void updateMazeSize() {
		Maze.setSize(Math.min((MainGame.actualWindowHeight - StatusBar.HEIGHT) / Maze.getnRaw(), MainGame.actualWindowWidth / Maze.getnColumn()));
		
		if (!GameController.fullScreen) {
			Maze.setDefaultSize(Math.min((MainGame.actualWindowHeight - StatusBar.HEIGHT) / Maze.getnRaw(), MainGame.actualWindowWidth / Maze.getnColumn()));
		}
		GamePanel.setDebutX((actualWindowWidth - Maze.getSize() * Maze.getnColumn()) / 2);
		GamePanel.setDebutY((actualWindowHeight - Maze.getSize()  * Maze.getnRaw() - StatusBar.HEIGHT) / 2);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			MainGame.getInstance();
			MainGame.resize();
        });
		
	}
}