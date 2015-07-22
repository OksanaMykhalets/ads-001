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
			for (int row = 0; row < passageHeight; row++) {
				String stringRow = scanner.next();
				for (int column = 0; column < passageWidth; column++) {
					tiles[row][column] = stringRow.charAt(column);
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
			allPossiblePathsForPreviousColumns.put((char) i, BigInteger.ZERO);
		}
		for (int row = 0; row < passageHeight; row++) {
			BigInteger newPath = allPossiblePathsForPreviousColumns.get(
					tiles[row][0]).add(BigInteger.ONE);
			allPossiblePathsForPreviousColumns.put(tiles[row][0], newPath);
		}
		
		BigInteger[] previousDistances = new BigInteger[passageHeight];
		BigInteger[] nextDistances = new BigInteger[passageHeight];
		for (int row = 0; row < passageHeight; row++) {
			previousDistances[row] = BigInteger.ONE;
			nextDistances[row] = BigInteger.ZERO;
		}

		for (int column = 1; column < passageWidth; column++) {
			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				nextDistances[row] = allPossiblePathsForPreviousColumns
						.get(currentTile);
				if (currentTile != tiles[row][column - 1]) {
					nextDistances[row] = nextDistances[row]
							.add(previousDistances[row]);
				}
			}

			for (int row = 0; row < passageHeight; row++) {
				char currentTile = tiles[row][column];
				BigInteger newPath = allPossiblePathsForPreviousColumns.get(
						currentTile).add(nextDistances[row]);
				allPossiblePathsForPreviousColumns.put(currentTile, newPath);
			}

			BigInteger[] tmpArray = previousDistances;
			previousDistances = nextDistances;
			nextDistances = tmpArray;
		}

		numberOfPossiblePaths = previousDistances[0];
		if (previousDistances.length > 1) {
			numberOfPossiblePaths = numberOfPossiblePaths
					.add(previousDistances[passageHeight - 1]);
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