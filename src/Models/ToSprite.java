package Models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ToSprite {

	// nombre de colonne du fichier contenant les tiles des lettres
	private int column_Number = 16; // par défaut pour les lettres et nombres
	private String fileName;

	public int getColumn_Number() {
		return column_Number;
	}

	public void setColumn_Number(int column_Number) {
		this.column_Number = column_Number;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// emplacement du premier tile des alphabets dans le fichier
	private static final int START_POINT_STRING_X = 6;
	private static final int START_POINT_STRING_Y = 2;

	// emplacement du premier tile des nombres dans le fichier
	private static final int START_POINT_INTEGER_X = 12;
	private static final int START_POINT_INTEGER_Y = 1;

	// dictionnaire stockant les images des tiles avec leur valeur correspondante
	private static Map<java.lang.Character, Image> stringImageMap = new HashMap<java.lang.Character, Image>();
	private static Map<Integer, Image> integerImageMap = new HashMap<Integer, Image>();

	public ToSprite(int column_Number, String fileName) {
		super();
		this.column_Number = column_Number;
		this.fileName = fileName;
	}

	public void fillMap() {

		// remplir le dictionnaire de mots_Images
		int AlphabetCounter = 0;
		int i = START_POINT_STRING_X;
		int j = START_POINT_STRING_Y;
		while (AlphabetCounter < 26) {
			stringImageMap.put((char) (65 + AlphabetCounter), extractImage(i, j, "string", AlphabetCounter));
			AlphabetCounter++;
			j = j + (i + 1) / column_Number;
			i = (i + 1) % column_Number;
		}

		// remplir le dictionnaire de chiffres_Images
		int NumberCounter = 0;
		i = START_POINT_INTEGER_X;
		j = START_POINT_INTEGER_Y;
		while (NumberCounter < 10) {
			integerImageMap.put(NumberCounter, extractImage(i, j, "number", NumberCounter));
			NumberCounter++;
			j = j + (i + 1) / column_Number;
			i = (i + 1) % column_Number;
		}
	}

	// extraire les sous images de l'image principale contenant tous les sprites
	public Image extractImage(int startPointX, int startPointY, String tileType, int tileNumber) {
		BufferedImage out = null;
		try {
			BufferedImage image = ImageIO.read(new File("ressources" + File.separator + fileName + ".png"));
			int size = image.getWidth() / column_Number;
			out = image.getSubimage(startPointX * size, startPointY * size, size, size);
			ImageIO.write(out, "png", new File("ressources" + File.separator + tileType + tileNumber + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	// dessiner sur le graphic en parametre une image correspondante au texte en
	// parametre
	public void drawToSprite(String text, int x, int y, int width, int height, Graphics g) {
		int i = 0;
		text = text.toUpperCase();
		for (char c : text.toCharArray()) {
			if(c == ' ') {}
			else {
				g.drawImage(stringImageMap.get(c), x + i * width, y, width, height, null);
			}
			i++;
		}
	}

	// dessiner sur le graphic en parametre une image correspondante au nombre en
	// parametre
	public void drawToSprite(int number, int x, int y, int width, int height, Graphics g) {
		String numberString = String.valueOf(number);
		int i = 0;
		for (char c : numberString.toCharArray()) {
			g.drawImage(integerImageMap.get(Integer.parseInt(Character.toString(c))), x + i * width, y, width, height,
					null);
			i++;
		}
	}
}
