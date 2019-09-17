package Views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

import Controllers.GameController;
import Models.Maze;

public class MainGame extends JFrame implements WindowListener{
	
	public static final int DefaultWidth = 600;
	public static final int DefaultHeight = 800;
	
	public static int actualWindowWidth;
	public static int actualWindowHeight;
	
	
	private static MainGame SINGLE_INSTANCE = new MainGame();
	private static GameController gameController;
	public static GameMenu menuPane;
	public AudioPanel audioPane;
	
	public static MainGame getInstance() {
      return SINGLE_INSTANCE;
    }
	
	public MainGame() {
		
		actualWindowHeight = DefaultHeight;
		actualWindowWidth = DefaultWidth;
		gameController = new GameController(new GamePanel(), this);
		menuPane = new GameMenu(gameController);
		audioPane = new AudioPanel(gameController);
		this.setContentPane(menuPane);
		addWindowListener(this);
	}
	
	public void displayMenu() {
		setContentPane(menuPane);
		menuPane.requestFocus();
		revalidate();
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
		GameController.setSize(Math.min((MainGame.actualWindowHeight - StatusBar.HEIGHT) / (GameController.getnRow() + 3), MainGame.actualWindowWidth / GameController.getnColumn()));
		
		if (!GameController.fullScreen) {
			GameController.setDefaultSize(Math.min((MainGame.actualWindowHeight - StatusBar.HEIGHT) / (GameController.getnRow() + 3), MainGame.actualWindowWidth / GameController.getnColumn()));
		}
		GamePanel.setDebutX((actualWindowWidth - GameController.getSize() * GameController.getnColumn()) / 2);
		//GamePanel.setDebutY((actualWindowHeight - GameController.getSize()  * GameController.getnRow() - 150) / 2);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(() -> {
			MainGame.getInstance();
			MainGame.resize();
        });
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		gameController.pause = true;
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		gameController.pause = true;
		gameController.resume = true;
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		gameController.pause();
		
	}
}