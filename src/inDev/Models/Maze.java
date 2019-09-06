package inDev.Models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

import inDev.Views.GamePanel;

public class Maze {
	
	private int [][] maze;
	private static int size = 19; 
	private static int defaultSize = 19; 

	private Image [][] images;
	
	public Maze() throws FileNotFoundException {
		
		maze = new int[33][30];
		images = new Image[33][30];
		
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
				if(maze[i][j] < 30)
					images[i][j] = new ImageIcon("ressources" + File.separator + "maze" + getMaze()[i][j] + ".png").getImage();
			}
        }
		
		//AccessibleTiles(debutX, debutY);
	}
	
		
	private void AccessibleTiles(int debutX, int debutY) {
		if(debutX >= 0 && debutX < 33 && debutY >= 0 && debutY < 30 && (maze[debutX][debutY] == 0 ||maze[debutX][debutY] >= 30) && maze[debutX][debutY] != 120) {
			maze[debutX][debutY] = 120;
			AccessibleTiles(debutX + 1, debutY);
			AccessibleTiles(debutX - 1, debutY);	
			AccessibleTiles(debutX, debutY + 1);
			AccessibleTiles(debutX, debutY - 1);
		}
	}

	public void draw(Graphics g) {

		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				g.drawImage(images[i][j], GamePanel.debutX + j * size, GamePanel.debutY + i * size,size,size, null);
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
		return size;
	}

	public static void setSize(int mazeSize) {
		size = mazeSize;
	}

	public static int getDefaultSize() {
		return defaultSize;
	}
	public static void setDefaultSize(int mazeSize) {
		defaultSize = mazeSize;
	}
}
