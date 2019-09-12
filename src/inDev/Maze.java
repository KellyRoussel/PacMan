package inDev;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Maze {
	
	private int [][] maze;
	//private Image [][] images;
	private int sizeImages = 0;
	public static int size = 0; 
	private Image [][] currents;
	
	public Maze() throws FileNotFoundException {
		
		maze = new int[33][30];
		//images = new Image[33][30];
		currents = new Image[33][30];
		
		Scanner sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		
		for(int i = 0; i < 33; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < 30; j++) {
				getMaze()[i][j] = Integer.parseInt(strings[j]);
				currents[i][j] = new ImageIcon("ressources" + File.separator + "maze" + getMaze()[i][j] + ".png").getImage();
			}
        }
		
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
		
	public void draw(Graphics g, int size) {
		this.size = size;
		/*if(sizeImages != size) {
			loadImages(size);
			sizeImages = size;
		}*/
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				g.drawImage(currents[i][j], j * size, i * size, size, size, null);
			}
        }
	}
	
	public void display() {
		
	}

	public int [][] getMaze() {
		return maze;
	}
}
