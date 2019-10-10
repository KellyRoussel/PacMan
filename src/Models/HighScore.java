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
	
	private String scorer;
	
	//Position du nouveau score dans la liste
	private int position;


	public HighScore() {
		try {
			InputStream ips = new FileInputStream(getScoreFile());
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
			FileWriter fw = new FileWriter(getScoreFile());
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

	

	public String getActualScorer() {
		// R�cup�rer les valeurs String Scorer 
		char[] scorerCharArray = {'A','A','A'};
		for (int i = 0; i < 3; i++) {
			if(letterIndex[i]<26) {
				scorerCharArray[i] = (char) (65 + letterIndex[i]);
			}
			else {
				scorerCharArray[i] = String.valueOf(letterIndex[i]-26).charAt(0);
			}
			
			
		}
		setScorer(new String(scorerCharArray));
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public static void setScore(int score) {
		HighScore.score = score;
	}

	/**
	 * @return the scoreFile
	 */
	public String getScoreFile() {
		return scoreFile;
	}

	/**
	 * @param scorer the scorer to set
	 */
	public void setScorer(String scorer) {
		this.scorer = scorer;
	}

}
