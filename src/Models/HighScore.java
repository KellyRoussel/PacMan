package Models;

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

	public HighScore() {
		try{
			InputStream ips = new FileInputStream(scoreFile); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			while ((line=br.readLine())!=null){
				getScorerList().add(line.substring(0, 3));
				getScoreList().add(Integer.parseInt(line.substring(4,line.length())));
			}
			br.close(); 
		}  
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void newScore(int score) {
		int k = getScoreList().size()-1; //On part de la fin de la liste
		while(score >= getScoreList().get(k) && k > 0) { //Si le score est plus grand que le plus petit score
			k-=1;
		}
		if(k < 4){ //Si la position du nouveau score dans la liste est < 4, il faut l'ajouter
			newHighScore(k);
		}
	}

	public void newHighScore(int k) {
		// Lancer le visuel d'enregistrement
		// R�cup�rer les valeurs String Scorer et score
		//scorerList.add(k, scorer); 
		//scoreList.add(k, score);
		if(getScoreList().size()> 5) { //On ne garde que les 5 meilleurs
			getScoreList().remove(-1);
			getScorerList().remove(-1);
		}
		try { // On �crit dans le fichier
			FileWriter fw = new FileWriter (scoreFile);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter writer = new PrintWriter (bw); 
			//writer.println(scorer + ";" + score); 
			writer.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}

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
}