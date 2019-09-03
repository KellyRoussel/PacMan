package inDev.Views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.Timer;

import inDev.Controllers.GameController;
import inDev.Models.Maze;
import inDev.Models.Characters.PacMan;
import inDev.Models.Foods.Gum;
import inDev.Models.Foods.PacGum;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GamePanel extends JPanel{
	
	
    private Graphics dbg;
    private Image dbImage = null;
    public static int debutX = 0;
	public static int debutY = 0;
	
    public GamePanel() {
    	
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
        	System.out.println("Graphics context error: " + e);  
        }
	}

    public void gameRender(PacMan pacMan, Maze maze, ArrayList<Gum> gumList, ArrayList<PacGum> pacGumList) {
    	if (GameController.resize) {
    		PacManGame.resize();
    		GameController.resize = false;
    	}
        dbImage = createImage(PacManGame.actualWindowWidth, PacManGame.actualWindowHeight);
        if (dbImage == null) {
          System.out.println("dbImage is null");
		   return; }
		else {
			dbg = dbImage.getGraphics();
		}
          // clear the background
        dbg.setColor(Color.black);
        dbg.fillRect (0, 0, PacManGame.actualWindowWidth, PacManGame.actualWindowHeight);
        
        maze.draw(dbg);
        
        if(pacMan.undefinedPosition()){
        	Point p = maze.getFirstPosition();
        	pacMan.setPosition(p.x, p.y);
        }
        
        for(int i = 0; i < gumList.size(); i++) {
        	if(!gumList.get(i).isEaten())
        		gumList.get(i).draw(dbg, gumList.get(i).getY() * maze.getDefaultSize() + 5, gumList.get(i).getX() * maze.getDefaultSize() + 5);
        }
        for(int i = 0; i < pacGumList.size(); i++) {
        	if(!pacGumList.get(i).isEaten())
        		pacGumList.get(i).draw(dbg, pacGumList.get(i).getY() * maze.getDefaultSize() + 5, pacGumList.get(i).getX() * maze.getDefaultSize() + 5);
        }
        
        ;
        pacMan.draw(dbg);	

    }  
}