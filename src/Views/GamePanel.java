package Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import Controllers.GameController;
import Models.HighScore;
import Models.Maze;
import Models.ToSprite;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;
import Models.Foods.Fruit;
import Models.Foods.Gum;
import Models.Foods.PacGum;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GamePanel extends JPanel {

	// la dimension des messages afficher selon etat du jeu
	private static final int TEXT_MESSAGE_SIZE = 50;
	private static final int TEXT_STATUS_SIZE = 15;

	private Graphics dbg;
	private Image dbImage;
	private Image scoreImage;
	private GameController gameController;

	private static int debutX;
	private static int debutY;

	// ToSprite Instance qui creer les tiles des nombres et les chiffres
	private static ToSprite chiffre_lettre = new ToSprite(16, "pacmanTiles");
	

	public GamePanel(GameController gameController) {
		this.gameController = gameController;
		debutX = 0;
		debutY = 0;
		setBackground(Color.black);
		setFocusable(true);

		// remplir les dictionnaires de mots_Images et chiffres_Images
		chiffre_lettre.fillMap();

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

		// dessiner le décompte Resume
		if (gameController.isResume() && gameController.getRESUME()<=3) {
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
			if(!gameController.isScoreSaved() && gameController.isNewScore()) {
				// l'image du score
				scoreImage = createImage(gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3));
				Graphics scorebg = scoreImage.getGraphics();
				scorebg.setColor(Color.black);
				scorebg.fillRect(0, 0, gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3));
				gameController.getHighScore().visualHighScore(scorebg, gameController.getDefaultSize() * (gameController.getnColumn() / 3));
				dbg.drawImage(scoreImage, gameController.getDefaultSize() * (gameController.getnColumn() / 4),
						(int) ((MainGame.getActualWindowHeight() - 150 - TEXT_MESSAGE_SIZE) * (3.0 / 5)),
						gameController.getDefaultSize() * (gameController.getnColumn() / 2),
						gameController.getDefaultSize() * ((gameController.getnRow() / 4) + 3), null);
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