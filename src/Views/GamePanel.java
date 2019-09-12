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
	
	
    private Graphics dbg;
    private Image dbImage;
    
    private static int debutX;
    private static int debutY;
	
    public GamePanel() {
    	debutX = 0;
    	debutY = 0;
    	setBackground(Color.black);
        setFocusable(true);
        
    }
  
    public void paintScreen() {
        Graphics g;
        try {
			 g = this.getGraphics(); 
			 g.drawImage(dbImage, debutX, debutY, null);
			 Toolkit.getDefaultToolkit().sync(); 
		}
        catch (Exception e){ 
        }
	}

    public void gameRender(PacMan pacMan, Maze maze, ArrayList<Food> foodList) {
    	// Redimensionner la fenetre 
    	if (GameController.resize) {
    		MainGame.resize();
    		MainGame.updateMazeSize();
    		GameController.resize = false;
    	}
        dbImage = createImage(MainGame.actualWindowWidth, MainGame.actualWindowHeight);
        if (dbImage == null) {
		   return; }
		else {
			dbg = dbImage.getGraphics();
		}
          // clear the background
        dbg.setColor(Color.black);
        dbg.fillRect (0, 0, MainGame.actualWindowWidth, MainGame.actualWindowHeight);
        
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
        
        // dessiner le "Pause" au cas de pause
        /*if(GameController.pause) {
        	Image image = new ImageIcon("ressources" + File.separator + "pause.png").getImage(); 
			dbg.drawImage(image, GamePanel.debutX , GamePanel.debutY, maze.getSize() * maze.getnColumn(), maze.getSize() * maze.getnRaw(), null);
        }
        
        // dessiner le "GameOver" au cas d echec
        if(GameController.gameOver) {
        	Image image = new ImageIcon("ressources" + File.separator + "gameOver.png").getImage(); 
			dbg.drawImage(image, GamePanel.debutX , GamePanel.debutY, maze.getSize() * maze.getnColumn(), maze.getSize() * maze.getnRaw(), null);
        }*/
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