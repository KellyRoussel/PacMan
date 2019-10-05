package Controllers;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Models.HighScore;
import Models.Maze;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Characters.Strategies.StrBlue;
import Models.Characters.Strategies.Normal.OrangeStrategy;
import Models.Characters.Strategies.Normal.PinkStrategy;
import Models.Characters.Strategies.Normal.RedStrategy;
import Models.Characters.Strategies.Normal.TurquoiseStrategy;
import Models.Foods.Food;
import Models.Foods.Fruit;
import Models.Foods.Gum;
import Models.Foods.PacGum;
import Threads.AudioThread;
import Threads.PhysicsThread;
import Threads.RenderThread;
import Views.GamePanel;
import Views.MainGame;
import Views.StatusBar;

public class GameController implements Runnable {

	private MainGame frame;

	private JPanel mainPane;
	private GamePanel gamePanel;
	private StatusBar statusBar;
	private HighScore highScore;

	private boolean isNewScore;

	private static PacMan pacMan;
	private Maze maze;
	private List<Food> foodList;

	// private Sound music;
	private static int size, defaultSize;
	private int level;
	private static int score;
	private int lives;

	private static final int PM_INITIAL_POSITION = 60;
	private static final long JOIN_TIMER = 10;
	private static final int PINK_INITIAL_POSITION = 26;
	private static final int ORANGE_INITIAL_POSITION = 27;
	private static final int RED_INITIAL_POSITION = 28;
	private static final int TURQUOISE_INITIAL_POSITION = 29;
	private static int eatenGhosts;
	private Image[][] images;
	private BufferedImage output;
	private Graphics g;
	private ArrayList<Ghost> ghostList;
	private boolean running;
	private int ghostOutside;
	private boolean pause;
	private static AtomicInteger RESUME;
	private boolean resume;
	private boolean fullScreen;
	private boolean resize;
	private boolean gameOver;
	private boolean isScoreSaved;
	private static int[][] grille;
	private int nRow;
	private int nColumn;
	private int firstGhostToQuit;
	private final int SLEEP_TIMER = 1;
	private int ghostCounter = 0;

	private static long timeUpdate = 10;
	private static int FPS = 30;
	private static boolean isInvincible;
	private Thread gameThread;
	private RenderThread tRender;
	private static boolean isAudioThreadStarted = false;
	private static AudioThread tAudio = new AudioThread();

	private static int INVINCIBLE;

	private static long startInvicible;
	private ArrayList<Point> listTunnelLeft;
	private ArrayList<Point> listTunnelRight;

	private PhysicsThread tPhysics;

	private int updateCounter;

	private static AtomicInteger invincibleCounter = new AtomicInteger();

	public GameController(MainGame frame) {

		this.gamePanel = new GamePanel(this);

		init();

		this.setFrame(frame);

		statusBar = new StatusBar();
		highScore = new HighScore();

		// Creation du Border Layout pour contenir le gamePanel et le StatusBar
		BorderLayout bl = new BorderLayout();
		mainPane = new JPanel(bl);
		getMainPane().add(getGamePanel(), BorderLayout.CENTER);
		getMainPane().add(getStatusBar(), BorderLayout.SOUTH);

	}

	private void init() {
		setRESUME(0);
		setResume(false);
		grille = null;
		setRunning(false);
		setFullScreen(false);
		setResize(false);
		setInvincible(false);
		setGameOver(false);
		score = 0;
		setLives(3);
		level = 1;

		Scanner sc = null;
		try {
			sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lineDimension = sc.nextLine();
		String[] dimensions = lineDimension.split(",");

		nColumn = Integer.parseInt(dimensions[0]);
		nRow = Integer.parseInt(dimensions[1]);
		int height = MainGame.getActualWindowHeight() - StatusBar.HEIGHT;
		int width = MainGame.getActualWindowWidth();
		size = Math.min(height / nRow, width / nColumn);
		defaultSize = size;

		// creer la matrice du labyrinthe
		grille = new int[nRow][nColumn];

		images = new Image[nRow][nColumn];
		output = new BufferedImage(defaultSize * nColumn, defaultSize * nRow, BufferedImage.TYPE_INT_ARGB);
		g = getOutput().getGraphics();

		for (int i = 0; i < nRow; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for (int j = 0; j < nColumn; j++) {
				grille[i][j] = Integer.parseInt(strings[j]);
				if (grille[i][j] < 30) {
					getImages()[i][j] = loadImage("maze" + grille[i][j] + ".png");
					getG().drawImage(getImages()[i][j], j * defaultSize, i * defaultSize, defaultSize, defaultSize,
							null);
				}
			}
		}

		setListTunnelLeft(new ArrayList<Point>());
		setListTunnelRight(new ArrayList<Point>());

		for (int i = 0; i < nRow; i++) {
			if (grille[i][0] >= 30) {
				int j = 0;
				while (grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j++;
				getListTunnelLeft().add(new Point(i, j));
			}
		}

		for (int i = 0; i < nRow; i++) {
			if (grille[i][nColumn - 1] >= 30) {
				int j = nColumn - 1;
				while (grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j--;
				getListTunnelRight().add(new Point(i, j));
			}
		}

		// Creer une instance de la labyrinthe
		maze = new Maze(defaultSize * nColumn, defaultSize * nRow, getOutput(), new Point(0, 0));

		// Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
		MainGame.updateMazeSize();

		// Creer les Gums et PacGums

		foodList = Collections.synchronizedList(new ArrayList<Food>());
		fillFoodList();

		// Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
		MainGame.updateMazeSize();

		// Creer le PacMan
		ImageIcon ii = new ImageIcon("ressources" + File.separator + "Left_0.png");

		pacMan = new PacMan(defaultSize, defaultSize, ii.getImage(), definePosition(PM_INITIAL_POSITION), defaultSize,
				grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow);

		// Creer les fantomes
		ghostList = new ArrayList<Ghost>();

		getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostorange.png"),
				definePosition(ORANGE_INITIAL_POSITION), "orange", defaultSize, grille, getListTunnelLeft(),
				getListTunnelRight(), nColumn, nRow, new OrangeStrategy()));

		getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostred.png"),
				definePosition(RED_INITIAL_POSITION), "red", defaultSize, grille, getListTunnelLeft(),
				getListTunnelRight(), nColumn, nRow, new RedStrategy()));

		getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostturquoise.png"),
				definePosition(TURQUOISE_INITIAL_POSITION), "turquoise", defaultSize, grille, getListTunnelLeft(),
				getListTunnelRight(), nColumn, nRow, new TurquoiseStrategy()));
		getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostpink.png"),
				definePosition(PINK_INITIAL_POSITION), "pink", defaultSize, grille, getListTunnelLeft(),
				getListTunnelRight(), nColumn, nRow, new PinkStrategy()));

		setGhostOutside(0);

		setFirstGhostToQuit((int) (Math.random() * ghostList.size()));

		// Creer les Gums et PacGums
		synchronized (foodList) {
			foodList = Collections.synchronizedList(new ArrayList<Food>());
			fillFoodList();
		}
	}

	private synchronized void fillFoodList() {
		for (int i = 0; i < nRow; i++)
			for (int j = 0; j < nColumn; j++) {
				if (grille[i][j] == 30)
					getFoodList().add(new Gum((defaultSize * 3) / 4, (defaultSize * 3) / 4, loadImage("gum.png"),
							new Point(j * defaultSize, i * defaultSize)));
				if (grille[i][j] == 40)
					getFoodList().add(new PacGum(defaultSize, defaultSize, loadImage("pacGum.png"),
							new Point(j * defaultSize, i * defaultSize)));
				if (grille[i][j] == 50)
					getFoodList().add(new Fruit(defaultSize, defaultSize, loadImage("fruit.png"),
							new Point(j * defaultSize, i * defaultSize)));
			}
	}

	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}

	private void addListeners() {
		getMainPane().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();
				if (!isGameOver() && !pacMan.isDead() && !pacMan.isResurrection()) {
					// Mettre le jeu en pause
					if (key == KeyEvent.VK_P && !isPause()) {
						pause();
						setRESUME(4);
					}
					if (key == KeyEvent.VK_R && isPause()) {
						setResume(true);
					}

					if (key == KeyEvent.VK_M) {
						// Mettre la musique en muet
						if (!isResume()) {
							gettAudio().setMuteOnOffMusic(true);

						}
					}

					if (key == KeyEvent.VK_S) {
						// Mettre le son en muet
						if (!isResume()) {
							gettAudio().setMuteOnOffSound(true);

						}
					}

					// Redimensionner le jeu
					if (key == KeyEvent.VK_F) {
						setResize(true);
						setFullScreen(!isFullScreen());
					}
				}
				// Key Event pour gerer le score lors d un gameOver
				else {
					if (key == KeyEvent.VK_LEFT) {
						highScore.setLetterPosition((highScore.getLetterPosition() - 1) % 3);
						if (highScore.getLetterPosition() < 0) {
							highScore.setLetterPosition(highScore.getLetterPosition() + 3);
						}
					}
					if (key == KeyEvent.VK_RIGHT) {
						highScore.setLetterPosition((highScore.getLetterPosition() + 1) % 3);
					}
					if (key == KeyEvent.VK_UP) {
						highScore.getLetterIndex()[highScore.getLetterPosition()]--;
						if (highScore.getLetterIndex()[highScore.getLetterPosition()] < 0) {
							highScore.getLetterIndex()[highScore.getLetterPosition()] += 26;
						}
						highScore.getLetterIndex()[highScore.getLetterPosition()] %= 26;
					}
					if (key == KeyEvent.VK_DOWN) {
						highScore.getLetterIndex()[highScore
								.getLetterPosition()] = (highScore.getLetterIndex()[highScore.getLetterPosition()] + 1)
										% 26;
					}
					if (key == KeyEvent.VK_ENTER) {
						setScoreSaved(true);
						// highScore.getActualScorer();
						highScore.newHighScore();
						setNewScore(false);
					}
				}

				if (key == KeyEvent.VK_ESCAPE) {
					setPause(true);
					if (isGameOver()) {
						setResume(true);
						getFrame().displayNewMenu();
					} else
						getFrame().displayMenu();
				}

				// Changer la direction du PacMan selon la fleche choisie.
				if (!isPause()) {
					if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP
							|| key == KeyEvent.VK_DOWN) {
						getPacMan().setNextDirection(key);
					}
				}

			}
		});
	}

	// Boucle du jeu
	@Override
	public void run() {
		setRunning(true);
		System.out.println("START - " + gameThread.getName());
		setPause(true);
		updateCounter = 0;
		while (isRunning()) {
			long pastTime = System.currentTimeMillis();
			if (isGameOver()) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex) {
				}
			} else {
				if (!isPause()) {
					updateCounter++;
					updateCounter %= 3;
					if (updateCounter == 0)
						gameUpdate();
				} else
					pause();
				if (isResume()) {
					resume();
				} else if (isInvincible() && !isPause() && !isResume()) {
					switchToInvicibleMode();
				}
			}
			try {
				Thread.sleep((1000L / getFPS()) / 2);
			} catch (InterruptedException ex) {
			}
		}
		System.out.println("STOP - " + gameThread.getName());
	}

	private void switchToInvicibleMode() {
		if (pacMan.getPas() == 4) {
			while (gettRender() == null) {
			}
			gettAudio().setIsPacGumEaten(true);
			gettRender().setIsInvincible(true);

			for (int i = 0; i < ghostList.size(); i++) {
				ghostList.get(i).setStrategy(new StrBlue());
				ghostList.get(i).loadImage();
			}
			pacMan.setPas(5);
		}
		long date = System.currentTimeMillis();
		setInvincibleCounter((int) ((date - startInvicible) / 1000));
		if (date - startInvicible > 8000) {
			stopInvincibleMode();
		}

	}

	private void stopInvincibleMode() {
		// TODO Auto-generated method stub
		while (gettRender() == null) {
		}
		gettAudio().setIsInivincible(false);
		gettRender().setIsInvincible(false);
		setInvincible(false);
		for (int i = 0; i < ghostList.size(); i++) {
			ghostList.get(i).setNormalStrategy();
			ghostList.get(i).setEaten(false);
		}
		pacMan.setPas(4);
		setEatenGhosts(0);
	}

	private void setEatenGhosts(int i) {
		// TODO Auto-generated method stub
		eatenGhosts = i;
	}

	public static boolean isInvincible() {
		// TODO Auto-generated method stub
		return isInvincible;
	}

	public void gameUpdate() {

		int raw = 0;
		int column = 0;

		if (defaultSize != 0) {
			if (!isResume()) {
				if (getGhostOutside() < ghostList.size()) {
					if (ghostCounter == 0) {
						getGhostList().get(getFirstGhostToQuit()).updatePosition();
						raw = getGhostList().get(getFirstGhostToQuit()).getY() / defaultSize;
						column = getGhostList().get(getFirstGhostToQuit()).getX() / defaultSize;
						if (grille[raw][column] == 15 || grille[raw][column] == 2) {
							setGhostOutside(getGhostOutside() + 1);
							getGhostList().get(getFirstGhostToQuit()).setOutside(true);
							setFirstGhostToQuit((getFirstGhostToQuit() + 1) % ghostList.size());
						}
					}
					ghostCounter++;
					ghostCounter = ghostCounter % 1;
				}

				for (int i = 0; i < getGhostList().size(); i++) {
					if (getGhostList().get(i).isOutside()) {
						getGhostList().get(i).updatePosition();
					}
				}
			}
			// Pour savoir le tile suivant ou le pacman va se placer

			int tile = -1;
			if (!getPacMan().isInsideTunnel()) {
				getPacMan().nextNextX();
				getPacMan().nextNextY();
				raw = (int) Math.floor(
						(getPacMan().getNextY() + getPacMan().getPacManFront().get(getPacMan().getNextDirection()).x)
								/ defaultSize)
						% nRow;
				column = (int) Math.floor(
						(getPacMan().getNextX() + getPacMan().getPacManFront().get(getPacMan().getNextDirection()).y)
								/ defaultSize)
						% nColumn;
				tile = grille[raw][column];
			}
			if (tile == 0 || tile >= 30) {
				updatePositions(raw, column, true);
			} else {
				getPacMan().nextX();
				getPacMan().nextY();
				raw = (int) Math
						.floor((getPacMan().getNextY() + getPacMan().getPacManFront().get(getPacMan().getDirection()).x)
								/ defaultSize)
						% nRow;
				column = (int) Math
						.floor((getPacMan().getNextX() + getPacMan().getPacManFront().get(getPacMan().getDirection()).y)
								/ defaultSize)
						% nColumn;
				tile = grille[raw][column];

				if (tile == 0 || tile >= 30) {
					updatePositions(raw, column, false);
				}
			}
		}

		if (getPacMan().isDead() && pacMan.getDeadAnimationCounter() == 0) {
			gettAudio().setIsDead(true);
			getPacMan().deadAnimate();
			// getPacMan().setIsDead(false);
		}
		if (getPacMan().isResurrection()) {
			startNewLife();
		}

	}

	public synchronized void updatePositions(int raw, int column, boolean flag) {
		synchronized (foodList) {
			Iterator iter = getFoodList().iterator();
			while (iter.hasNext()) {
				Food f = (Food) iter.next();
				if (f.getEaten()) {
					gettAudio().setIsEaten(true);
					// Tile contenant une Gum
					int scoreBefore = score;
					score += f.getGain();
					if (getScore() >= 10000 && scoreBefore < 10000) {
						setLives(lives + 1);
					}
					iter.remove();
					// statusBar.updateScore();
				}
			}
		}

		synchronized (foodList) {
			if (getFoodList().size() == 0) {
				level++;
				// statusBar.updateLevel();
				getPacMan().returnInitialPosition();
				for (int i = 0; i < getGhostList().size(); i++) {
					getGhostList().get(i).returnInitialPosition();
				}
				getPacMan().initPM();
				getPacMan().setNextDirection(KeyEvent.VK_LEFT);
				getPacMan().loadImage();
				fillFoodList();
				setPause(true);
				resume = true;
			}
		}

		if (!isResume()) {
			getPacMan().move();

			if (flag)
				getPacMan().updateDirection();
			getPacMan().setInsideTile(raw, column);
		}
	}

	public void startNewLife() {
		setLives(lives - 1);
		getPacMan().setIsDead(false);
		if (lives == 0) {
			setGameOver(true);
			highScore.setScore(score);

			setNewScore(getHighScore().newScore(score));
		} else {
			getPacMan().returnInitialPosition();
			for (int i = 0; i < ghostList.size(); i++) {
				getGhostList().get(i).returnInitialPosition();
			}
			getPacMan().initPM();
			getPacMan().setNextDirection(KeyEvent.VK_LEFT);
			getPacMan().loadImage();

			setPause(true);
			setResume(true);

		}
	}

	public void startGame() {
		getFrame().setContentPane(getMainPane());
		getMainPane().requestFocus();
		getFrame().revalidate();

		if (getGameThread() == null || !getGameThread().isAlive()) {

			setGameThread(new Thread(this));
			getGameThread().setName("GameController");

			init();
			getGameThread().start();

			settPhysics(new PhysicsThread(getPacMan(), getGhostList(), getFoodList()));
			gettPhysics().setName("Physics");

			tRender = new RenderThread(getPacMan(), getGamePanel(), getMaze(), getFoodList(), getGhostList(),
					getStatusBar());
			gettRender().setName("Render");

			// lancer l'audio thread
			if (!isAudioThreadStarted) {
				// Lancer un listener sur le clavier
				addListeners();
				isAudioThreadStarted = true;
			} else {
				tAudio = new AudioThread();
			}

			gettAudio().setName("Audio");
			gettAudio().start();
			gettPhysics().start();
			gettRender().start();

			getFrame().getMenuPane().gameRunning();
			pause = false;
			resume = true;
			isScoreSaved = false;

		}
//		else if(gameOver) {
//			System.out.println("ici gameOver");
//			init();
//			getPacMan().returnInitialPosition();
//			for (int i = 0; i < getGhostList().size(); i++) {
//				getGhostList().get(i).returnInitialPosition();
//			}
//			getPacMan().initPM();
//			getPacMan().setNextDirection(KeyEvent.VK_LEFT);
//			getPacMan().loadImage();
//			fillFoodList();
//			pause = false;
//			resume = true;
//			
//		}
		setResume(true);
	}

	public void stop() {
		setRunning(false);
		gettRender().setRunning(false);
		tAudio.stopThread();
		try {

			getGameThread().join(500);
			if (getGameThread().isAlive()) {
				getGameThread().interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getFrame().getMenuPane().noMoreRunning();
		getFrame().displayMenu();
	}

	public void pause() {
		while (gettRender() == null) {
		}
		gettRender().setPause(true);
		gettAudio().setIsPause(true);
		setPause(true);
	}

	public void resume() {
		setPause(false);
		while (gettRender() == null) {
		}
		gettAudio().setIsPause(false);
		gettRender().Resume();

		setResume(false);
		if (getPacMan().isResurrection()) {
			getPacMan().setResurrection(false);
		}
		startInvicible += System.currentTimeMillis() - startInvicible;
	}

	public Point definePosition(int initialPositionValue) {
		Point p = new Point();
		for (int i = 0; i < nRow; i++)
			for (int j = 0; j < nColumn; j++) {
				if (grille[i][j] == initialPositionValue) {
					p.x = j * defaultSize;
					p.y = i * defaultSize;
				}
			}
		return p;
	}

	public void changeVolume() {
		frame.getAudioPane().muteMusicButton.setSelected(gettAudio().isMusicMuted());
		frame.getAudioPane().muteSoundButton.setSelected(gettAudio().isSoundMuted());
		getFrame().setContentPane(getFrame().getAudioPane());
		getFrame().requestFocus();
		getFrame().revalidate();
	}

	public static int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {

		this.grille = grille;
	}

	public int getnRow() {
		return nRow;
	}

	public void setnRow(int nRow) {
		this.nRow = nRow;
	}

	public int getnColumn() {
		return nColumn;
	}

	public void setnColumn(int nColumn) {
		this.nColumn = nColumn;
	}

	/**
	 * @return the maze
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * @return the pacMan
	 */
	public PacMan getPacMan() {
		return pacMan;
	}

	public static Point getPacManPosition() {
		return pacMan.getPosition();
	}

	/**
	 * @return the foodList
	 */
	public synchronized List<Food> getFoodList() {
		return foodList;
	}

	/**
	 * @return the ghostList
	 */
	public ArrayList<Ghost> getGhostList() {
		return ghostList;
	}

	/**
	 * @return the firstGhostToQuit
	 */
	public int getFirstGhostToQuit() {
		return firstGhostToQuit;
	}

	/**
	 * @return the mainPane
	 */
	public JPanel getMainPane() {
		return mainPane;
	}

	public static long getJoinTimer() {
		return JOIN_TIMER;
	}

	public MainGame getFrame() {
		return frame;
	}

	public void setFrame(MainGame frame) {
		this.frame = frame;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public static int getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	public int getLives() {
		// TODO Auto-generated method stub
		return lives;
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public static int getRESUME() {
		return RESUME.get();
	}

	public static void setRESUME(int rESUME) {
		RESUME = new AtomicInteger(rESUME);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static int getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(int defaultSize) {
		this.defaultSize = defaultSize;
	}

	public static long getTimeUpdate() {
		return timeUpdate;
	}

	public static void setTimeUpdate(long timeUpdate) {
		GameController.timeUpdate = timeUpdate;
	}

	public static int getFPS() {
		return FPS;
	}

	public static void setFPS(int fPS) {
		FPS = fPS;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public PhysicsThread gettPhysics() {
		return tPhysics;
	}

	public void settPhysics(PhysicsThread tPhysics) {
		this.tPhysics = tPhysics;
	}

	/**
	 * @return the gamePanel
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * @return the tAudio
	 */
	public AudioThread gettAudio() {
		return tAudio;
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @return the statusBar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @return the images
	 */
	public Image[][] getImages() {
		return images;
	}

	/**
	 * @return the output
	 */
	public BufferedImage getOutput() {
		return output;
	}

	/**
	 * @return the g
	 */
	public Graphics getG() {
		return g;
	}

	/**
	 * @return the listTunnelLeft
	 */
	public ArrayList<Point> getListTunnelLeft() {
		return listTunnelLeft;
	}

	/**
	 * @return the listTunnelRight
	 */
	public ArrayList<Point> getListTunnelRight() {
		return listTunnelRight;
	}

	/**
	 * @param listTunnelRight the listTunnelRight to set
	 */
	public void setListTunnelRight(ArrayList<Point> listTunnelRight) {
		this.listTunnelRight = listTunnelRight;
	}

	/**
	 * @param listTunnelLeft the listTunnelLeft to set
	 */
	public void setListTunnelLeft(ArrayList<Point> listTunnelLeft) {
		this.listTunnelLeft = listTunnelLeft;
	}

	/**
	 * @return the tRender
	 */
	public RenderThread gettRender() {
		return tRender;
	}

	public void settRender(RenderThread tRender) {
		this.tRender = tRender;
	}

	/**
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;

	}

	public static void setInvincible(boolean isInvincble) {

		isInvincible = isInvincble;
	}

	/**
	 * @return the ghostOutside
	 */
	public int getGhostOutside() {
		return ghostOutside;
	}

	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * @param firstGhostToQuit the firstGhostToQuit to set
	 */
	public void setFirstGhostToQuit(int firstGhostToQuit) {
		this.firstGhostToQuit = firstGhostToQuit;
	}

	/**
	 * @param ghostOutside the ghostOutside to set
	 */
	public void setGhostOutside(int ghostOutside) {
		this.ghostOutside = ghostOutside;
	}

	public void closeWindow() {
		System.out.println("Closing window \nStopping threads");
		if (getGameThread() != null && getGameThread().isAlive()) {
			stop();
		}
		if (gettRender() != null && gettRender().isAlive()) {
			gettRender().stopThread();
		}
		if (gettAudio() != null && gettAudio().isAlive()) {
			gettAudio().stopThread();
		}
		if (gettPhysics() != null && gettPhysics().isAlive()) {
			gettPhysics().stopThread();
		}

		if (gettRender() != null && gettRender().isAlive()) {
			gettRender().stopThread();
		}

		getFrame().dispose();
	}

	/**
	 * @return the highScore
	 */
	public HighScore getHighScore() {
		return highScore;
	}

	public static int getPacManDirection() {
		// TODO Auto-generated method stub
		return pacMan.getDirection();
	}

	public boolean isScoreSaved() {
		return isScoreSaved;
	}

	public void setScoreSaved(boolean isScoreSaved) {
		this.isScoreSaved = isScoreSaved;
	}

	/**
	 * @return the isNewScore
	 */
	public boolean isNewScore() {
		return isNewScore;
	}

	/**
	 * @param isNewScore the isNewScore to set
	 */
	public void setNewScore(boolean isNewScore) {
		this.isNewScore = isNewScore;
	}

	public void setHighScore(HighScore highScore) {
		this.highScore = highScore;
	}

	public static void setINVINCIBLE(int i) {
		// TODO Auto-generated method stub
		INVINCIBLE = i;
	}

	public static int getINVINCIBLE() {
		// TODO Auto-generated method stub
		return INVINCIBLE;
	}

	public static void setStartInvincible(long currentTimeMillis) {
		// TODO Auto-generated method stub
		startInvicible = currentTimeMillis;
	}

	public static void setScore(int score) {
		GameController.score = score;
	}

	public static void incEatenGhosts() {
		// TODO Auto-generated method stub
		eatenGhosts++;
	}

	public static int getEatenGhosts() {
		// TODO Auto-generated method stub
		return eatenGhosts;
	}

	public static void initEatenGhosts() {
		// TODO Auto-generated method stub
		eatenGhosts = 0;
	}

	public static int getInvincibleCounter() {
		return invincibleCounter.get();
	}

	public static void setInvincibleCounter(int invincibleCounter) {
		GameController.invincibleCounter = new AtomicInteger(invincibleCounter);
	}

}