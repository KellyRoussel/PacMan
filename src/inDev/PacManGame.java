package inDev;

import javax.swing.JFrame;

public class PacManGame extends JFrame{
	public PacManGame() {
		// TODO Auto-generated constructor stub
	}
	public static Frame createWindow() {
		JFrame frame = new JFrame("Pacman");
		frame.setSize(DefaultWidth, DefaultHeight);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		return frame;
	}
	
	
	public static void main(String[] args) {
		Frame frame = createWindow();	
	}
}
