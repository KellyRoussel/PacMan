package Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.Timer;

import Controllers.GameController;
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


public class GamePanel extends JPanel{
	
	// la dimension des messages afficher selon etat du jeu
	private static final int TEXT_MESSAGE_SIZE = 50;
	private static final int TEXT_STATUS_SIZE = 15;
	
    private Graphics dbg;
    private Image dbImage;
    
    private static int debutX;
    private static int debutY;
    
    // ToSprite Instance qui creer les tiles des nombres et les chiffres
    private static ToSprite chiffre_lettre = new ToSprite(16,"pacmanTiles");

    public GamePanel() {
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
			 double ratio = (double) GameController.getSize()/ (double) GameController.getDefaultSize();
			 g.drawImage(dbImage, debutX, debutY, (int)((double)GameController.getDefaultSize()*GameController.getnColumn() * ratio), (int)((double)GameController.getDefaultSize() * 36 * ratio), null);
			 Toolkit.getDefaultToolkit().sync(); 
		}
        catch (Exception e){ 
        }
	}

    public void gameRender(PacMan pacMan, Maze maze, ArrayList<Food> foodList, ArrayList<Ghost> ghostList) {
    	
    	// Redimensionner la fenetre 
    	if (GameController.resize) {
    		MainGame.resize();
    		MainGame.updateMazeSize();
    		GameController.resize = false;
    	}
        dbImage = createImage(GameController.getDefaultSize()*GameController.getnColumn(), GameController.getDefaultSize()*(GameController.getnRow() + 3));
        if (dbImage == null) {
		   return; }
		else {
			dbg = dbImage.getGraphics();
		}
          // clear the background
        dbg.setColor(Color.black);
        dbg.fillRect (0, 0, GameController.getDefaultSize()*GameController.getnColumn(), GameController.getDefaultSize()*(GameController.getnRow() + 3));
        
        maze.draw(dbg);
        
        
        // dessiner les gums
        for(int i = 0; i < foodList.size(); i++) {
    		//int x = Resizer.resizeX(gumList.get(i).getY() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutX);
    		//int y = Resizer.resizeY(gumList.get(i).getX() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutY);
    		foodList.get(i).draw(dbg);
        }
        // redimensionner le PacMan
        //int pacX = Resizer.resizeX(pacMan.getX(), maze.getSize(), maze.getDefaultSize(), debutX);
        //int pacY = Resizer.resizeY(pacMan.getY(), maze.getSize(), maze.getDefaultSize(), debutY);
        
        pacMan.draw(dbg);
        
        if(!pacMan.isDead()) {
        for(int i = 0; i < ghostList.size(); i++)
        	ghostList.get(i).draw(dbg);
        }
        //*******************************************************************************************************************************
        //Status du jeu
        // Affichage du score
        chiffre_lettre.drawToSprite("score",25 ,GameController.getDefaultSize()*GameController.getnRow(),TEXT_STATUS_SIZE,TEXT_STATUS_SIZE,dbg);
        chiffre_lettre.drawToSprite(GameController.getScore(), 135, GameController.getDefaultSize()*GameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
        
        // Affichage des vies
        chiffre_lettre.drawToSprite("lives",235,GameController.getDefaultSize()*GameController.getnRow(),TEXT_STATUS_SIZE,TEXT_STATUS_SIZE,dbg);
        for(int i = 0 ; i < GameController.getLives() ; i ++ ) {
        	ImageIcon liveIcon = new ImageIcon("ressources" + File.separator + "Left_0.png");
        	dbg.drawImage(liveIcon.getImage(),335+i*(TEXT_STATUS_SIZE+5), GameController.getDefaultSize()*GameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, null);
        }
        
        // Affichage du niveau
        chiffre_lettre.drawToSprite("level",445,GameController.getDefaultSize()*GameController.getnRow(),TEXT_STATUS_SIZE,TEXT_STATUS_SIZE,dbg);
        chiffre_lettre.drawToSprite(GameController.getLevel(), 527, GameController.getDefaultSize()*GameController.getnRow(), TEXT_STATUS_SIZE, TEXT_STATUS_SIZE, dbg);
        //*******************************************************************************************************************************
        
        // dessiner le "Pause" au cas de pause
        if(GameController.pause && !pacMan.isResurrection()) {
        	String text = "pause";
        	chiffre_lettre.drawToSprite(text,(MainGame.DefaultWidth-text.length()*TEXT_MESSAGE_SIZE)/2,(MainGame.actualWindowHeight-150-TEXT_MESSAGE_SIZE)/2, TEXT_MESSAGE_SIZE, TEXT_MESSAGE_SIZE, dbg);
        }
        
        // dessiner le "Pause" au cas de pause
        if(GameController.resume) {
        	int text = GameController.RESUME;
        	chiffre_lettre.drawToSprite(text,(MainGame.DefaultWidth)/2,debutY+(MainGame.actualWindowHeight-150-TEXT_MESSAGE_SIZE)/2, TEXT_MESSAGE_SIZE, TEXT_MESSAGE_SIZE, dbg);
        }
        
        // dessiner le "GameOver" au cas d echec
        if(GameController.gameOver) {
        	String text = "gameover";
        	chiffre_lettre.drawToSprite(text,(MainGame.actualWindowWidth-text.length()*TEXT_MESSAGE_SIZE)/2, (MainGame.actualWindowHeight-150-TEXT_MESSAGE_SIZE)/2, TEXT_MESSAGE_SIZE, TEXT_MESSAGE_SIZE, dbg);
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