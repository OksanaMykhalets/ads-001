import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static final int LOWERCASE_A = 97;
	public static final int LOWERCASE_Z = 122;
	public static int passageWidth;
	public static int passageHeight;
	public static char[][] tiles;
	public static BigInteger numberOfPossiblePaths;
	public static HashMap<Character, BigInteger> allPossiblePathsForPreviousColumns;

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
		allPossiblePathsForPreviousColumns = new HashMap<Character, BigInteger>();
		for (int i = LOWERCASE_A; i <= LOWERCASE_Z; i++) {
			allPossiblePathsForPreviousColumns.put((char) i,
					new BigInteger("0"));
		}
		BigInteger[][] distances = new BigInteger[passageHeight][passageWidth];
		for (int i = 0; i < passageHeight; i++) {
			for (int j = 0; j < passageWidth; j++) {
				if (j == 0) {
					distances[i][j] = new BigInteger("1");
					BigInteger newPath = allPossiblePathsForPreviousColumns
							.get(tiles[i][j]).add(BigInteger.ONE);
					allPossiblePathsForPreviousColumns
							.put(tiles[i][j], newPath);
				} else {
					distances[i][j] = new BigInteger("0");
				}
			}
		}

		for (int column = 1; column < passageWidth; column++) {
			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				if (currentTile != tiles[row][column - 1]) {
					distances[row][column] = distances[row][column - 1];
				}
				distances[row][column] = distances[row][column]
						.add(allPossiblePathsForPreviousColumns
								.get(currentTile));
			}
			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				BigInteger newPath = allPossiblePathsForPreviousColumns.get(
						currentTile).add(distances[row][column]);
				allPossiblePathsForPreviousColumns.put(currentTile, newPath);
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