package Models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

import Controllers.GameController;


public class Maze extends Sprite{
	

	
	public Maze(int width, int height, Image image, Point initialPosition) {
		super(width, height, image, initialPosition);
	}
	
}
