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
			 g.drawImage(dbImage, 0, 0, null);
			 Toolkit.getDefaultToolkit().sync(); 
		}
        catch (Exception e){ 
        }
	}

    public void gameRender(PacMan pacMan, Maze maze, ArrayList<Gum> gumList, ArrayList<PacGum> pacGumList, ArrayList<Fruit> fruitList) {
    	if (GameController.resize) {
    		PacManGame.resize();
    		PacManGame.updateMazeSize();
    		GameController.resize = false;
    	}
        dbImage = createImage(PacManGame.actualWindowWidth, PacManGame.actualWindowHeight);
        if (dbImage == null) {
		   return; }
		else {
			dbg = dbImage.getGraphics();
		}
          // clear the background
        dbg.setColor(Color.black);
        dbg.fillRect (0, 0, PacManGame.actualWindowWidth, PacManGame.actualWindowHeight);
        
        maze.draw(dbg, debutX, debutY);
        
        if(pacMan.undefinedPosition()){
        	Point p = maze.getFirstPosition();
        	pacMan.setPosition(p.x, p.y);
        }
        
        for(int i = 0; i < gumList.size(); i++) {
        	if(!gumList.get(i).isEaten()) {
        		int x = Resizer.resizeX(gumList.get(i).getY() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutX);
        		int y = Resizer.resizeY(gumList.get(i).getX() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutY);
        		gumList.get(i).draw(dbg, x, y);
        	}
        }
        for(int i = 0; i < pacGumList.size(); i++) {
        	if(!pacGumList.get(i).isEaten()) {
        		int x = Resizer.resizeX(pacGumList.get(i).getY() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutX);
        		int y = Resizer.resizeY(pacGumList.get(i).getX() * maze.getDefaultSize() + 5, maze.getSize(), maze.getDefaultSize(), debutY);
        		pacGumList.get(i).draw(dbg, x, y);
        	}
        }
        for(int i = 0; i < fruitList.size(); i++) {
        	if(!fruitList.get(i).isEaten()) {
        		int x = Resizer.resizeX(fruitList.get(i).getY() * maze.getDefaultSize() + 2, maze.getSize(), maze.getDefaultSize(), debutX);
        		int y = Resizer.resizeY(fruitList.get(i).getX() * maze.getDefaultSize() + 2, maze.getSize(), maze.getDefaultSize(), debutY);
        		fruitList.get(i).draw(dbg, x, y);
        	}        
        }
        
        //g.drawImage(image, GamePanel.debutX + shiftedX(x, Maze.getSize()), GamePanel.debutY + shiftedX(y, Maze.getSize()), Maze.getSize(), Maze.getSize(), null);
        int pacX = Resizer.resizeX(pacMan.getX(), maze.getSize(), maze.getDefaultSize(), debutX);
        int pacY = Resizer.resizeX(pacMan.getY(), maze.getSize(), maze.getDefaultSize(), debutY);
        
        pacMan.draw(dbg, pacX, pacY, maze.getSize());
        
        if(GameController.pause) {
        	Image image = new ImageIcon("ressources" + File.separator + "pause.png").getImage(); 
			dbg.drawImage(image, GamePanel.debutX , GamePanel.debutY, maze.getSize() * maze.getnColumn(), maze.getSize() * maze.getnRaw(), null);
        }
        if(GameController.gameOver) {
        	Image image = new ImageIcon("ressources" + File.separator + "gameOver.png").getImage(); 
			dbg.drawImage(image, GamePanel.debutX , GamePanel.debutY, maze.getSize() * maze.getnColumn(), maze.getSize() * maze.getnRaw(), null);
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