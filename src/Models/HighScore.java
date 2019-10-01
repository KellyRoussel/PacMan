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

	String scoreFile = "ressources" + File.separator + "highestScores.txt";
	List<Integer> scoreList = new ArrayList<Integer>();
	List<String> scorerList = new ArrayList<String>();

	public HighScore() {
		try{
			InputStream ips = new FileInputStream(scoreFile); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			while ((line=br.readLine())!=null){
				scorerList.add(line.substring(0, 3));
				scoreList.add(Integer.parseInt(line.substring(4,line.length())));
			}
			br.close(); 
		}  
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void newScore(int score) {
		int k = scoreList.size()-1; //On part de la fin de la liste
		while(score >= scoreList.get(k) && k > 0) { //Si le score est plus grand que le plus petit score
			k-=1;
		}
		if(k < 4){ //Si la position du nouveau score dans la liste est < 4, il faut l'ajouter
			newHighScore(k);
		}
	}

	public void newHighScore(int k) {
		// Lancer le visuel d'enregistrement
		// Récupérer les valeurs String Scorer et score
		//scorerList.add(k, scorer); 
		//scoreList.add(k, score);
		if(scoreList.size()> 5) { //On ne garde que les 5 meilleurs
			scoreList.remove(-1);
			scorerList.remove(-1);
		}
		try { // On écrit dans le fichier
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
}
