package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

import Models.HighScore;

class HighScoreTest {

	private HighScore highScore = new HighScore();

	@Test
	void EmptyFileNewScore() {
		// On vide le fichier de test et les listes
		try {
			FileWriter fw = new FileWriter(highScore.getScoreFile());
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			writer.println("");
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();
		// On cr�er un nouveau score
		highScore.setScore(5000);
		//Scorer AAA
		int [] scorerAAA = {0,0,0};
		highScore.setLetterIndex(scorerAAA);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
		//On l'enregistre
		highScore.newHighScore();

		//On vide les listes
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();

		//On lit le fichier
		try {
			InputStream ips = new FileInputStream(highScore.getScoreFile());
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				highScore.getScorerList().add(line.substring(0, 3));
				highScore.getScoreList().add(Integer.parseInt(line.substring(4, line.length())));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		//On v�rfie que le score a bien �t� ajout�
		assertEquals(5000, highScore.getScoreList().get(0),  "Le score 5000 aurait du �tre ajout�");
		assertEquals("AAA", highScore.getScorerList().get(0),  "Le scorer AAA aurait du �tre ajout�");
	}

	@Test
	void NewHighestScore() {
		// On vide le fichier de test et les listes
		try {
			FileWriter fw = new FileWriter(highScore.getScoreFile());
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			writer.println("");
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();
		// On cr�er un nouveau score
		highScore.setScore(5000);
		//Scorer AAA
		int [] scorerAAA = {0,0,0};
		highScore.setLetterIndex(scorerAAA);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
		//On l'enregistre
		highScore.newHighScore();

		//On cr�er un plus grand score
		highScore.setScore(6000);
		//Scorer BBB
		int [] scorerBBB = {1,1,1};
		highScore.setLetterIndex(scorerBBB);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
		//On l'enregistre
		highScore.newHighScore();

		//On vide les listes
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();

		//On lit le fichier
		try {
			InputStream ips = new FileInputStream(highScore.getScoreFile());
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				highScore.getScorerList().add(line.substring(0, 3));
				highScore.getScoreList().add(Integer.parseInt(line.substring(4, line.length())));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		//On v�rifie que le score a bien �t� ajout� en premi�re place
		assertEquals( 6000, highScore.getScoreList().get(0), "Le score 6000 aurait du �tre ajout� en premi�re place");
		assertEquals("BBB",highScore.getScorerList().get(0),  "Le scorer BBB aurait du �tre ajout� en premi�re place");
	}

	@Test
	void NewEqualScore() {
		// On vide le fichier de test et les listes
		try {
			FileWriter fw = new FileWriter(highScore.getScoreFile());
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			writer.println("");
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();
		// On cr�er un nouveau score
		highScore.setScore(5000);
		//Scorer AAA
		int [] scorerAAA = {0,0,0};
		highScore.setLetterIndex(scorerAAA);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
		//On l'enregistre
		highScore.newHighScore();

		//On cr�er un score �gal
		highScore.setScore(5000);
		//Scorer BBB
		int [] scorerBBB = {1,1,1};
		highScore.setLetterIndex(scorerBBB);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
		//On l'enregistre
		highScore.newHighScore();

		//On vide les listes
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();

		//On lit le fichier
		try {
			InputStream ips = new FileInputStream(highScore.getScoreFile());
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				highScore.getScorerList().add(line.substring(0, 3));
				highScore.getScoreList().add(Integer.parseInt(line.substring(4, line.length())));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		//On v�rfie que le score a bien �t� ajout�
		assertEquals( 5000, highScore.getScoreList().get(0), "Le score 5000 aurait du �tre ajout� en premi�re place");
		assertEquals( "BBB", highScore.getScorerList().get(0), "Le scorer BBB aurait du �tre ajout� en premi�re place");
		assertEquals( 5000, highScore.getScoreList().get(1),"Le score 5000 aurait du �tre ajout� en deuxi�me place");
		assertEquals( "AAA", highScore.getScorerList().get(1), "Le scorer AAA aurait du �tre ajout� en deuxi�me place");
	}

	@Test
	void FullListNewScore() {
		// On vide le fichier de test et les listes
		try {
			FileWriter fw = new FileWriter(highScore.getScoreFile());
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			writer.println("");
			writer.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();
		// On cr�er 5 nouveaux scores
		for(int i = 0; i<5; i++) {
			highScore.setScore(5000);
			//Scorer AAA
			int [] scorerAAA = {0,0,0};
			highScore.setLetterIndex(scorerAAA);
			assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");
			//On l'enregistre
			highScore.newHighScore();
		}

		//On cr�er un score 6000
		highScore.setScore(6000);
		//Scorer BBB
		int [] scorerBBB = {1,1,1};
		highScore.setLetterIndex(scorerBBB);
		assertTrue(highScore.newScore(highScore.getScore()),"Le score devrait �tre un nouveau HighScore");

		//On l'enregistre
		highScore.newHighScore();

		//On vide les listes
		highScore.getScoreList().clear();
		highScore.getScorerList().clear();

		//On lit le fichier
		try {
			InputStream ips = new FileInputStream(highScore.getScoreFile());
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) {
				highScore.getScorerList().add(line.substring(0, 3));
				highScore.getScoreList().add(Integer.parseInt(line.substring(4, line.length())));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}


		//On v�rfie que le score a bien �t� ajout�
		assertEquals(6000, highScore.getScoreList().get(0),"Le score 6000 aurait du �tre ajout� en premi�re place");
		assertEquals("BBB", highScore.getScorerList().get(0), "Le scorer BBB aurait du �tre ajout� en premi�re place");
		assertEquals(5, highScore.getScoreList().size(), "La liste de score devrait avoir une longueur de 5");
		assertEquals(5,highScore.getScorerList().size(), "La liste de scorer devrait avoir une longueur de 5");
	}


}
