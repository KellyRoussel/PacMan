package Models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;


public class Maze {
	
	private int [][] maze;
	private static int size; 
	private static int defaultSize;
	private static int nRaw, nColumn;	
	private Image [][] images;

	
	public Maze(int height, int width) throws FileNotFoundException {

		// Lire le fichier du maze.txt
		Scanner sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		
		String lineDimension = sc.nextLine();
		String[] dimensions = lineDimension.split(",");
		
		
		nColumn =  Integer.parseInt(dimensions[0]);
		nRaw =  Integer.parseInt(dimensions[1]);	
		
		// creer la matrice du labyrinthe
		maze = new int[nRaw][nColumn];
		
		// creer la matrice des images
		images = new Image[nRaw][nColumn];
		
		for(int i = 0; i < nRaw; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < nColumn; j++) {
				maze[i][j] = Integer.parseInt(strings[j]);
				if(maze[i][j] < 30)
					images[i][j] = new ImageIcon("ressources" + File.separator + "maze" + maze[i][j] + ".png").getImage();
			}
        }
		size = Math.min(height / nRaw, width / nColumn);
		defaultSize = size;
	}
	

	// dessin du labyrinthe a partir de la matrice des images
	public void draw(Graphics g, int debutX, int debutY) {
		for(int i = 0; i < nRaw; i++) {
			for(int j = 0; j < nColumn; j++) {
				g.drawImage(images[i][j], debutX + j * size, debutY + i * size,size,size, null);
			}
        }
	}

	public int [][] getMaze() {
		return maze;
	}
	
	public void updateSize() {
		
	}
	
	// Definir la position initiale du PacMan
	public Point getFirstPosition() {
		for(int i = 0; i < nRaw; i++) {
			for(int j = 0; j < nColumn; j++) {
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
	public static int getnRaw() {
		return nRaw;
	}

	public static int getnColumn() {
		return nColumn;
	}
}
