import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

	static long numberOfSheets;
	static long sheetWidth;
	static long sheetHeight;
	static long minimumBoardSize;

	public static void main(String[] args) {
		readDataFromFile();
		calculateMinimumBoardSize();
		writeResultToFile();
	}

	public static void readDataFromFile() {
		readDataFromFile("bugtrk.in");
	}

	public static void readDataFromFile(String fileName) {
		File inputFile = new File(fileName);
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
			numberOfSheets = scanner.nextLong();
			sheetWidth = scanner.nextLong();
			sheetHeight = scanner.nextLong();
			scanner.close();
			if ((numberOfSheets == 0) || (sheetHeight == 0)
					|| (sheetWidth == 0)) {
				System.out.println("Error. Input data have no sense.");
				System.exit(42);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void calculateMinimumBoardSize() {

		long boardWidth = sheetWidth;
		long boardHeight = sheetHeight;

		BigInteger minimumSquare = BigInteger.valueOf(sheetHeight * sheetWidth
				* numberOfSheets);
		BigInteger currentSquare = BigInteger.valueOf(boardWidth * boardHeight);

		while (minimumSquare.compareTo(currentSquare) == 1) {
			if ((boardWidth + sheetWidth) <= (boardHeight + sheetHeight)) {
				boardWidth += sheetWidth;
			} else {
				boardHeight += sheetHeight;
			}
			currentSquare = BigInteger.valueOf(boardWidth * boardHeight);
		}
		minimumBoardSize = Math.max(boardWidth, boardHeight);
	}

	public static void writeResultToFile() {
		writeResultToFile("bugtrk.out");
	}

	public static void writeResultToFile(String fileName) {
		File outputFile = new File(fileName);
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write("" + minimumBoardSize);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Can't save result.");
			System.exit(42);
		}
	}

}
