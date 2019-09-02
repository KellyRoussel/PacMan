package inDev;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Maze {
	
	private int [][] maze;
	//private Image [][] images;
	private int sizeImages = 0;
	private static int size = 0; 
	private Image [][] currents;
	
	public Maze() throws FileNotFoundException {
		
		maze = new int[33][30];
		//images = new Image[33][30];
		currents = new Image[33][30];
		
		Scanner sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		
		int debutX = 0;
		int debutY = 0;
		
		for(int i = 0; i < 33; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < 30; j++) {
				maze[i][j] = Integer.parseInt(strings[j]);
				if(maze[i][j] == 60) {
					debutX = i;
					debutY = j;
				}
				currents[i][j] = new ImageIcon("ressources" + File.separator + "maze" + getMaze()[i][j]%60 + ".png").getImage();
			}
        }
		recursiveAction(debutX + 1, debutY);
		recursiveAction(debutX - 1, debutY);	
		recursiveAction(debutX, debutY + 1);
		recursiveAction(debutX, debutY - 1);
	}
	
	/*public void loadImages(int size) {
		this.size = size;
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				ImageIcon ii = new ImageIcon(images[i][j].getScaledInstance(size, size, Image.SCALE_DEFAULT));
				currents[i][j] = ii.getImage();
			}
        }
	}*/
		
	private void recursiveAction(int debutX, int debutY) {
		// TODO Auto-generated method stub
		if(debutX >= 0 && debutX < 33 && debutY >= 0 && debutY < 30 && maze[debutX][debutY] == 0) {
			maze[debutX][debutY] = 120;
			recursiveAction(debutX + 1, debutY);
			recursiveAction(debutX - 1, debutY);	
			recursiveAction(debutX, debutY + 1);
			recursiveAction(debutX, debutY - 1);
		}
	}

	public void draw(Graphics g, int size) {
		this.size = size;
		/*if(sizeImages != size) {
			loadImages(size);
			sizeImages = size;
		}*/
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				g.drawImage(currents[i][j], j * size, i * size,size,size, null);
			}
        }
	}
	
	public void display() {
		
	}

	public int [][] getMaze() {
		return maze;
	}

	public Point getFirstPosition() {
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				if(maze[i][j] == 60) {
					return new Point(j * size, i * size + 2);
				}
			}
		}
		return new Point(0, 0);
	}

	public static int getSize() {
		// TODO Auto-generated method stub
		return size;
	}
}
