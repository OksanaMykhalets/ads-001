import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static int[][] experiencePyramid;
	public static int maximalExperience;

	public static void main(String[] args) {
		readDataFromFile();
		calculateMaximalExperience();
		writeResultToFile();
	}

	public static void readDataFromFile() {
		try {
			Scanner scanner = new Scanner(new File("career.in"));
			short numberOfLevels = scanner.nextShort();
			experiencePyramid = new int[numberOfLevels][numberOfLevels];
			for (int i = 0; i < numberOfLevels; i++) {
				for (int j = 0; j <= i; j++) {
					experiencePyramid[i][j] = scanner.nextShort();
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file wasn`t found.");
			System.exit(42);
		}
	}

	public static void calculateMaximalExperience() {
		for (int upperLevelNumber = experiencePyramid.length - 2; upperLevelNumber >= 0; upperLevelNumber--) {
			for (int upperLevelElement = 0; upperLevelElement <= upperLevelNumber; upperLevelElement++) {
				int lowerLevelFirstExperiencePoint = experiencePyramid[upperLevelNumber + 1][upperLevelElement];
				int lowerLevelSecondExperiencePoint = experiencePyramid[upperLevelNumber + 1][upperLevelElement + 1];
				int maximalPossibleExperience = Math.max(
						lowerLevelFirstExperiencePoint,
						lowerLevelSecondExperiencePoint);
				experiencePyramid[upperLevelNumber][upperLevelElement] += maximalPossibleExperience;
			}
		}
		maximalExperience = experiencePyramid[0][0];
	}

	public static void writeResultToFile() {
		File outputFile = new File("career.out");
		try {
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.write("" + maximalExperience);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Can't save result.");
			System.exit(42);
		}
	}

}
