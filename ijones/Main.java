import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	public static int passageWidth;
	public static int passageHeight;
	public static char[][] tiles;
	public static BigInteger numberOfPossiblePaths;
	public static HashMap<Character, LinkedList<int[]>> charactersCoordinates;

	public static void main(String[] args) {
		readDataFromFile();
		calculateNumberOfPossiblePaths();
		writeResultToFile();
	}

	public static void readDataFromFile() {
		charactersCoordinates = new HashMap<>();
		for (int i = 97; i <= 122; i++) {
			charactersCoordinates.put((char) i, new LinkedList<int[]>());
		}
		try {
			Scanner scanner = new Scanner(new File("ijones.in"));
			passageWidth = scanner.nextInt();
			passageHeight = scanner.nextInt();
			tiles = new char[passageHeight][passageWidth];
			for (int i = 0; i < passageHeight; i++) {
				String row = scanner.next();
				for (int j = 0; j < passageWidth; j++) {
					tiles[i][j] = row.charAt(j);
					int[] charCoordinates = new int[2];
					charCoordinates[0] = i;
					charCoordinates[1] = j;
					charactersCoordinates.get(tiles[i][j]).add(charCoordinates);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void calculateNumberOfPossiblePaths() {
		BigInteger[][] distances = new BigInteger[passageHeight][passageWidth];
		for (int i = 0; i < passageHeight; i++) {
			for (int j = 0; j < passageWidth; j++) {
				if (j == 0) {
					distances[i][j] = BigInteger.ONE;
				} else {
					distances[i][j] = BigInteger.ZERO;
				}
			}
		}
		for (int column = 1; column < passageWidth; column++) {
			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				if (currentTile != tiles[row][column - 1]) {
					distances[row][column] = distances[row][column - 1];
				}
				LinkedList<int[]> allTilesWithSomeLetter = charactersCoordinates
						.get(currentTile);
				for (int[] coordinates : allTilesWithSomeLetter) {
					int tileRow = coordinates[0];
					int tileColumn = coordinates[1];
					if (tileColumn < column) {
						distances[row][column] = distances[row][column]
								.add(distances[tileRow][tileColumn]);
					}
				}
			}
		}
		numberOfPossiblePaths = distances[0][passageWidth - 1];
		if (distances.length > 1) {
			numberOfPossiblePaths = numberOfPossiblePaths
					.add(distances[passageHeight - 1][passageWidth - 1]);
		}

	}

	public static void writeResultToFile() {
		File outputFile = new File("ijones.out");
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write("" + numberOfPossiblePaths);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Can't save result.");
			System.exit(42);
		}
	}

}