package inDev;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
	
	int [][] maze;
	
	public Maze() throws FileNotFoundException {
		maze = new int[33][30];
		
		Scanner sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		
		for(int i = 0; i < 33; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < 30; j++) {
				maze[i][j] = Integer.parseInt(strings[j]);
			}
        }
	}
	
	
	
	public void load() {
		
	}
	public void display() {
		
	}
}
