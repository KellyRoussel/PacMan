import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class test {
	public static void main(String[] args) throws FileNotFoundException {
		int [][] maze = new int[33][30];
		FileReader fileReader = 
                new FileReader("maze.txt");

		Scanner sc = new Scanner(new File("maze.txt"));
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 30; j++) {
				maze[i][j] = sc.nextInt();
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();				            
        }
	}
}
