package Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Controllers.GameController;
import Models.HighScore;
import Models.Maze;
import Models.ToSprite;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;

public class GamePanel extends JPanel {

	// la dimension des messages afficher selon etat du jeu
	private static final int TEXT_MESSAGE_SIZE = 50;
	private static final int TEXT_STATUS_SIZE = 15;
	private static final int TEXT_MESSAGE_SIZE_EMPHASIZED = 25;
	private static final int TEXT_SCORE_SIZE = 20;

	private Graphics dbg;
	private Image dbImage;
	private Image scoreImage;
	private GameController gameController;

	private static int debutX;
	private static int debutY;

	// ToSprite Instance qui creer les tiles des nombres et les chiffres
	private static ToSprite chiffre_lettre = new ToSprite(16, "pacmanTiles");
	private ToSprite toSpriteNumbers = new ToSprite(16, "pacmanTiles2");

	public GamePanel(GameController gameController) {
		this.gameController = gameController;
		debutX = 0;
		debutY = 0;
		setBackground(Color.black);
		setFocusable(true);

		// remplir les dictionnaires de mots_Images et chiffres_Images
		chiffre_lettre.fillMap();
		toSpriteNumbers.fillMap();

	}

	public void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			double ratio = (double) gameController.getSize() / (double) gameController.getDefaultSize();
			g.drawImage(dbImage, debutX, debutY,
					(int) ((double) gameController.getDefaultSize() * gameController.getnColumn() * ratio),
					(int) ((double) gameController.getDefaultSize() * 36 * ratio), null);
			Toolkit.getDefaultToolkit().sync();
		} catch (Exception e) {
		}
	}

	public void gameRender(PacMan pacMan, Maze maze, List<Food> foodList, ArrayList<Ghost> ghostList) {

		// Redimensionner la fenetre
		if (gameController.isResize()) {
			MainGame.resize();
			MainGame.updateMazeSize();
			gameController.setResize(false);
		}
		dbImage = createImage(gameController.getDefaultSize() * gameController.getnColumn(),
				gameController.getDefaultSize() * (gameController.getnRow() + 3));
		if (dbImage == null) {
			return;
		} else {
			dbg = dbImage.getGraphics();
		}
		// clear the background
		dbg.setColor(Color.black);
		dbg.fillRect(0, 0, gameController.getDefaultSize() * gameController.getnColumn(),
				gameController.getDefaultSize() * (gameController.getnRow() + 3));

		maze.draw(dbg);

		// dessiner les gums
		for (int i = 0; i < foodList.size(); i++) {
			// int x = Resizer.resizeX(gumList.get(i).getY() * maze.getDefaultSize() + 5,
			// maze.getSize(), maze.getDefaultSize(), debutX);
			// int y = Resizer.resizeY(gumList.get(i).getX() * maze.getDefaultSize() + 5,
			// maze.getSize(), maze.getDefaultSize(), debutY);
			foodList.get(i).draw(dbg);
		}
		// redimensionner le PacMan
		// int pacX = Resizer.resizeX(pacMan.getX(), maze.getSize(),
		// maze.getDefaultSize(), debutX);
		// int pacY = Resizer.resizeY(pacMan.getY(), maze.getSize(),
		// maze.getDefaultSize(), debutY);

		pacMan.draw(dbg);

		if (!pacMan.isDead()) {
			for (int i = 0; i < ghostList.size(); i++)
				ghostList.get(i).draw(dbg);
		}
		// *******************************************************************************************************************************
		// Status du jeu
		// Affichage du score
		chiffre_lettre.drawToSprite("score", 25, gameController.getDefaultSize() * gameController.getnRow(),
				TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
		chiffre_lettre.drawToSprite(gameController.getScore(), 135,
				gameController.getDefaultSize() * gameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);

		// Affichage des vies
		chiffre_lettre.drawToSprite("lives", 235, gameController.getDefaultSize() * gameController.getnRow(),
				TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
		for (int i = 0; i < gameController.getLives(); i++) {
			ImageIcon liveIcon = new ImageIcon("ressources" + File.separator + "Left_0.png");
			dbg.drawImage(liveIcon.getImage(), 335 + i * (TEXT_STATUS_SIZE + 5),
					gameController.getDefaultSize() * gameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE,
					null);
		}

		// Affichage du niveau
		chiffre_lettre.drawToSprite("level", 445, gameController.getDefaultSize() * gameController.getnRow(),
				TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
		chiffre_lettre.drawToSprite(gameController.getLevel(), 527,
				gameController.getDefaultSize() * gameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
		// *******************************************************************************************************************************

		// dessiner le "Pause" au cas de pause
		if (gameController.isPause() && !pacMan.isResurrection()) {
			String text = "pause";
			chiffre_lettre.drawToSprite(text, (MainGame.getDefaultwidth() - text.length() * TEXT_MESSAGE_SIZE) / 2,
					(MainGame.getActualWindowHeight() - 150 - TEXT_MESSAGE_SIZE) / 2, TEXT_MESSAGE_SIZE,
					TEXT_MESSAGE_SIZE, dbg);
		}

		// dessiner le d�compte Resume
		if (gameController.isResume() && gameController.getRESUME() <= 3) {
			int text = gameController.getRESUME();
			chiffre_lettre.drawToSprite(text, (MainGame.getDefaultwidth() - TEXT_MESSAGE_SIZE) / 2,
					debutY + (MainGame.getActualWindowHeight() - 150 - TEXT_MESSAGE_SIZE) / 2, TEXT_MESSAGE_SIZE,
					TEXT_MESSAGE_SIZE, dbg);
		}

		// dessiner le "GameOver" au cas d echec
		if (gameController.isGameOver()) {
			String text = "gameover";
			chiffre_lettre.drawToSprite(text, (MainGame.getDefaultwidth() - text.length() * TEXT_MESSAGE_SIZE) / 2,
					(MainGame.getActualWindowHeight() - 150 - TEXT_MESSAGE_SIZE) / 2, TEXT_MESSAGE_SIZE,
					TEXT_MESSAGE_SIZE, dbg);
			if (!gameController.isScoreSaved() && gameController.isNewScore()) {
				// l'image du score
				scoreImage = createImage(gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3));
				Graphics scorebg = scoreImage.getGraphics();
				scorebg.setColor(Color.black);
				scorebg.fillRect(0, 0, gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3));
				visualHighScore(scorebg);
				dbg.drawImage(scoreImage, gameController.getDefaultSize() * (gameController.getnColumn() / 4),
						(int) ((MainGame.getActualWindowHeight() - 150 - TEXT_MESSAGE_SIZE) * (3.0 / 5)),
						gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3), null);
			}
		}
		// Affichage du score quand PC mange un fantome
		if (gameController.getEatenGhosts() > 0) {
			int score = 100 * (int) (Math.pow(2, GameController.getEatenGhosts()));
			Point pacman = gameController.getDeathLocation(); // pacman = pznege
			if (!gameController.isPause() && !gameController.isResume()) {
				if (System.currentTimeMillis() - GameController.deathTime < 3000) {
					chiffre_lettre.drawToSprite(score, pacman.x, pacman.y - 20, 20, 20, dbg);
				}
			} else {
				chiffre_lettre.drawToSprite(score, pacman.x, pacman.y - 20, 20, 20, dbg);
			}

		}
	}
	
	public void visualHighScore(Graphics g) {
		String text = "new high score";
		chiffre_lettre.drawToSprite(text, 12, 40, TEXT_SCORE_SIZE, TEXT_SCORE_SIZE, g);
		text = String.valueOf(HighScore.getScore());
		chiffre_lettre.drawToSprite(HighScore.getScore(), 12 + (int) ((14 - text.length()) / 2 * TEXT_SCORE_SIZE), 90, TEXT_SCORE_SIZE,
				TEXT_SCORE_SIZE, g);
		for (int i = 0; i < 3; i++) {
			int textMessage = TEXT_SCORE_SIZE;
			int shift = 0;
			// on agrandit le size si il s agit de la lettre selectionne par l utilisateur
			if (HighScore.getLetterPosition() == i) {
				textMessage = TEXT_MESSAGE_SIZE_EMPHASIZED;
				shift = TEXT_MESSAGE_SIZE_EMPHASIZED - TEXT_SCORE_SIZE;
			}
			if(HighScore.getLetterIndex()[i]>25) {
				toSpriteNumbers.drawToSprite(HighScore.getLetterIndex()[i]-26,
						(int) (12 + (int) (4.5 * TEXT_SCORE_SIZE) + i * 1.5 * TEXT_SCORE_SIZE), 140 - shift,
						textMessage, textMessage, g);
			}
			else {
				chiffre_lettre.drawToSprite(String.valueOf((char) (65 + HighScore.getLetterIndex()[i])),
						(int) (12 + (int) (4.5 * TEXT_SCORE_SIZE) + i * 1.5 * TEXT_SCORE_SIZE), 140 - shift,
						textMessage, textMessage, g);
			}
		}
	}

	public static int getDebutX() {
		return debutX;
	}

	public static void setDebutX(int debutX) {
		GamePanel.debutX = debutX;
	}

	public static int getDebutY() {
		return debutY;
	}

	public static void setDebutY(int debutY) {
		GamePanel.debutY = debutY;
	}

}