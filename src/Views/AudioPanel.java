package Views;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class AudioPanel extends JPanel {
	
	private GameController gameController;

	private JLabel musicLabel;
	private JLabel soundLabel;
	private JLabel musicValue;
	private JLabel soundValue;
	private JButton musicPlus;
	private JButton musicMinus;
	private JButton soundPlus;
	private JButton soundMinus;
	private Font defaultFont = new Font("Joystix", Font.BOLD, 15);
	
	public AudioPanel(GameController gameController) {
		
		setLayout(new GridLayout(2, 4));
		
		musicLabel = new JLabel();
		musicLabel.setFont(defaultFont);
		musicLabel.setText("<html><font color = 'BLACK'> MUSIC </font></html>");
		musicLabel.setAlignmentX(CENTER_ALIGNMENT);
		musicLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		soundLabel = new JLabel();
		soundLabel.setFont(defaultFont);
		soundLabel.setText("<html><font color = 'BLACK'> SOUND </font></html>");
		soundLabel.setAlignmentX(CENTER_ALIGNMENT);
		soundLabel.setAlignmentY(CENTER_ALIGNMENT);
		
		musicValue = new JLabel();
		musicValue.setFont(defaultFont);
		musicValue.setText("<html><font color = 'BLACK'> 50 </font></html>");
		musicValue.setAlignmentX(CENTER_ALIGNMENT);
		musicValue.setAlignmentY(CENTER_ALIGNMENT);
		
		soundValue = new JLabel();
		soundValue.setFont(defaultFont);
		soundValue.setText("<html><font color = 'BLACK'> 50 </font></html>");
		soundValue.setAlignmentX(CENTER_ALIGNMENT);
		soundValue.setAlignmentY(CENTER_ALIGNMENT);
		
		musicPlus = new JButton("+");
		musicPlus.setFont(defaultFont);
		musicPlus.setAlignmentX(CENTER_ALIGNMENT);
		musicPlus.setAlignmentY(CENTER_ALIGNMENT);
		
		musicMinus = new JButton("-");
		musicMinus.setFont(defaultFont);
		musicMinus.setAlignmentX(CENTER_ALIGNMENT);
		musicMinus.setAlignmentY(CENTER_ALIGNMENT);
		
		soundPlus = new JButton("+");
		soundPlus.setFont(defaultFont);
		soundPlus.setAlignmentX(CENTER_ALIGNMENT);
		soundPlus.setAlignmentY(CENTER_ALIGNMENT);
		
		soundMinus = new JButton("-");
		soundMinus.setFont(defaultFont);
		soundMinus.setAlignmentX(CENTER_ALIGNMENT);
		musicMinus.setAlignmentY(CENTER_ALIGNMENT);
		
		add(musicLabel);
		add(musicPlus);
		add(musicValue);
		add(musicMinus);
		add(soundLabel);
		add(soundPlus);
		add(soundValue);
		add(soundMinus);
			
		
	}
}
