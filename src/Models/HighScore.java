package Models;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HighScore {

	private String scoreFile = "ressources" + File.separator + "highestScores.txt";
	private List<Integer> scoreList = new ArrayList<Integer>();
	private List<String> scorerList = new ArrayList<String>();

	// champs de scorer
	private static int letterPosition; // Position du curseur sur l'une des trois lettres du scorer
	private static int[] letterIndex = { 0, 0, 0 }; // Index des lettres actuelles selon les fl�ches haut ou bas

	// champ du score
	private static int score;
	
	//Position du nouveau score dans la liste
	private int position;

	// champ pour �crire les lettres et chiffres en images
	private ToSprite toSprite = new ToSprite(16, "pacmanTiles");

	// le size du texte
	private static final int TEXT_MESSAGE_SIZE = 20;
	private static final int TEXT_MESSAGE_SIZE_EMPHASIZED = 25;

	public HighScore() {
		try {
			InputStream ips = new FileInputStream(scoreFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				getScorerList().add(line.substring(0, 3));
				getScoreList().add(Integer.parseInt(line.substring(4, line.length())));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public boolean newScore(int score) {
		boolean isNewScore = false;
		int k = getScoreList().size() - 1;// On part de la fin de la liste
		if(k == -1) {
			position = 0;
			isNewScore = true;
			return isNewScore;
		}
		while (k >= 0 && score >= getScoreList().get(k)) { // Si le score est plus grand que le plus petit score
			k -= 1;
		}
		k++;
		if (k < 4) { // Si la position du nouveau score dans la liste est < 4, il faut l'ajouter
			position = k;
			isNewScore = true;
		}
		return isNewScore;
	}

	public void newHighScore() {
		// Lancer le visuel d'enregistrement

		// R�cup�rer les valeurs String Scorer et score
		scorerList.add(position, getActualScorer());
		scoreList.add(position, getScore());
		if (getScoreList().size() > 5) { // On ne garde que les 5 meilleurs
			getScoreList().remove(getScoreList().size()-1);
			getScorerList().remove(getScoreList().size()-1);
		}
		try { // On �crit dans le fichier
			FileWriter fw = new FileWriter(scoreFile);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			for(int i = 0; i< scorerList.size(); i++) {
				writer.println(scorerList.get(i) + ";" + scoreList.get(i));
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void visualHighScore(Graphics g, int width) {
		String text = "new high score";
		// toSprite.drawToSprite(text, (width-text.length())/2, 20,
		// TEXT_MESSAGE_SIZE,TEXT_MESSAGE_SIZE, g);
		toSprite.drawToSprite(text, 12, 40, TEXT_MESSAGE_SIZE, TEXT_MESSAGE_SIZE, g);
		text = String.valueOf(score);
		// toSprite.drawToSprite(score,(width-text.length())/2, 70,
		// TEXT_MESSAGE_SIZE,TEXT_MESSAGE_SIZE, g);
		toSprite.drawToSprite(score, 12 + (int) ((14 - text.length()) / 2 * TEXT_MESSAGE_SIZE), 90, TEXT_MESSAGE_SIZE,
				TEXT_MESSAGE_SIZE, g);
		for (int i = 0; i < 3; i++) {
			// toSprite.drawToSprite(String.valueOf((char) (65 + letterIndex[i] % 26)),
			// (width-3 +i *2 *TEXT_MESSAGE_SIZE)/2, 120,
			// TEXT_MESSAGE_SIZE,TEXT_MESSAGE_SIZE, g);
			int textMessage = TEXT_MESSAGE_SIZE;
			int shift = 0;
			// on agrandit le size si il s agit de la lettre selectionne par l utilisateur
			if (letterPosition == i) {
				textMessage = TEXT_MESSAGE_SIZE_EMPHASIZED;
				shift = TEXT_MESSAGE_SIZE_EMPHASIZED - TEXT_MESSAGE_SIZE;
			}
			toSprite.drawToSprite(String.valueOf((char) (65 + letterIndex[i])),
					(int) (12 + (int) (4.5 * TEXT_MESSAGE_SIZE) + i * 1.5 * TEXT_MESSAGE_SIZE), 140 - shift,
					textMessage, textMessage, g);
		}
	}

	public String getActualScorer() {
		// R�cup�rer les valeurs String Scorer 
		String scorer;
		char[] scorerCharArray = {'A','A','A'};
		for (int i = 0; i < 3; i++) {
			scorerCharArray[i] = (char) (65 + letterIndex[i]);
			
		}
		scorer = new String(scorerCharArray);
		return scorer;
	}

	/**
	 * @return the scoreList
	 */
	public List<Integer> getScoreList() {
		return scoreList;
	}

	/**
	 * @return the scorerList
	 */
	public List<String> getScorerList() {
		return scorerList;
	}

	public static int getLetterPosition() {
		return letterPosition;
	}

	public static void setLetterPosition(int letterPosition) {
		HighScore.letterPosition = letterPosition;
	}

	public static int[] getLetterIndex() {
		return letterIndex;
	}

	public static void setLetterIndex(int[] letterIndex) {
		HighScore.letterIndex = letterIndex;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		HighScore.score = score;
	}

}
