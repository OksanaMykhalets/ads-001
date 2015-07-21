import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static int passageWidth;
	public static int passageHeight;
	public static char[][] tiles;
	public static long numberOfPossiblePaths;

	public static void main(String[] args) {
		readDataFromFile();
		calculateNumberOfPossiblePaths();
		writeResultToFile();
	}

	public static void readDataFromFile() {
		try {
			Scanner scanner = new Scanner(new File("ijones.in"));
			passageWidth = scanner.nextInt();
			passageHeight = scanner.nextInt();
			tiles = new char[passageHeight][passageWidth];
			for (int i = 0; i < passageHeight; i++) {
				String row = scanner.next();
				for (int j = 0; j < passageWidth; j++) {
					tiles[i][j] = row.charAt(j);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void calculateNumberOfPossiblePaths() {
		long[][] distances = new long[passageHeight][passageWidth];
		for (int i = 0; i < passageHeight; i++) {
			distances[i][0] = 1;
		}
		for (int column = 1; column < passageWidth; column++) {
			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				if (currentTile != tiles[row][column - 1]) {
					distances[row][column]++;
				}
				for (int i = 0; i < passageHeight; i++) {
					for (int j = 0; j < column; j++) {
						if (tiles[i][j] == currentTile) {
							distances[row][column] += distances[i][j];
						}
					}
				}
			}
		}
		numberOfPossiblePaths = distances[0][passageWidth - 1]
				+ distances[passageHeight - 1][passageWidth - 1];
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
