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
	
	private static final int DefaultWidth = 600;
	private static final int DefaultHeight = 800;
	
	private static int actualWindowWidth;
	private static int actualWindowHeight;
	
	
	private static MainGame SINGLE_INSTANCE = new MainGame();
	private static GameController gameController;
	private static GameMenu menuPane;
	private AudioPanel audioPane;
	private HelpPanel helpPane;
	
	public static MainGame getInstance() {
      return SINGLE_INSTANCE;
    }
	
	public MainGame() {
		
<<<<<<< HEAD
		setActualWindowHeight(DefaultHeight);
		setActualWindowWidth(getDefaultwidth());
		gameController = new GameController(this);
		setMenuPane(new GameMenu(gameController));
		setAudioPane(new AudioPanel(gameController));
		setHelpPane(new HelpPanel(gameController));
		this.setContentPane(getMenuPane());
=======
		actualWindowHeight = DefaultHeight;
		actualWindowWidth = DefaultWidth;
		gameController = new GameController(new GamePanel(), this);
		menuPane = new GameMenu(gameController);
		audioPane = new AudioPanel(gameController);
		helpPane = new HelpPanel(gameController);
		this.setContentPane(menuPane);
		this.addKeyListener(menuPane.cursor);
>>>>>>> f87069a4a07123ecb4bc54032692e768bf6a380f
		addWindowListener(this);
		this.setFocusable(true);
	}
	
	public void displayMenu() {
<<<<<<< HEAD
		setContentPane(getMenuPane());
		getMenuPane().requestFocus();
=======
		setContentPane(menuPane);
		this.requestFocus();
>>>>>>> f87069a4a07123ecb4bc54032692e768bf6a380f
		revalidate();
	}
	
	public void displayNewMenu() {
		gameController = new GameController(this);
		setMenuPane(new GameMenu(gameController));
		displayMenu();
	}
	
	public static void resize() {
		int mazeSize;
		if (gameController.isFullScreen()) {
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
			setActualWindowWidth(screenDimension.width - left - right);
			setActualWindowHeight(screenDimension.height - top - bottom);
			
			
		}
		else {
			SINGLE_INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SINGLE_INSTANCE.setSize(getDefaultwidth(), DefaultHeight);
			SINGLE_INSTANCE.setResizable(false);
			SINGLE_INSTANCE.setVisible(true);
			setActualWindowWidth(getDefaultwidth());
			setActualWindowHeight(DefaultHeight);
		}		
		
	}
	
	// redimensionner le labyrinthe
	public static void updateMazeSize() {
		if(gameController != null) {
			gameController.setSize(Math.min((MainGame.getActualWindowHeight() - StatusBar.HEIGHT) / (gameController.getnRow() + 3), MainGame.getActualWindowWidth() / gameController.getnColumn()));
			
			if (!gameController.isFullScreen()) {
				gameController.setDefaultSize(Math.min((MainGame.getActualWindowHeight() - StatusBar.HEIGHT) / (gameController.getnRow() + 3), MainGame.getActualWindowWidth() / gameController.getnColumn()));
			}
			GamePanel.setDebutX((getActualWindowWidth() - gameController.getSize() * gameController.getnColumn()) / 2);
		}
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
		gameController.setPause(true);
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		gameController.setPause(true);
		gameController.setResume(true);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		gameController.setPause(true);
		
	}

	public static GameMenu getMenuPane() {
		return menuPane;
	}

	public static void setMenuPane(GameMenu menuPane) {
		MainGame.menuPane = menuPane;
	}

	public AudioPanel getAudioPane() {
		return audioPane;
	}

	public void setAudioPane(AudioPanel audioPane) {
		this.audioPane = audioPane;
	}

	public static int getActualWindowHeight() {
		return actualWindowHeight;
	}

	public static void setActualWindowHeight(int actualWindowHeight) {
		MainGame.actualWindowHeight = actualWindowHeight;
	}

	public static int getActualWindowWidth() {
		return actualWindowWidth;
	}

	public static void setActualWindowWidth(int actualWindowWidth) {
		MainGame.actualWindowWidth = actualWindowWidth;
	}

	public HelpPanel getHelpPane() {
		return helpPane;
	}

	public void setHelpPane(HelpPanel helpPane) {
		this.helpPane = helpPane;
	}

	public static int getDefaultwidth() {
		return DefaultWidth;
	}
}