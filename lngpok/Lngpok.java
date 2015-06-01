import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Lngpok {

    private static ArrayList<Integer> cardsValue;
    private static int numberOfJokers;
    private static int lengthOfTheLongestSequence;

    public static void main(String[] args) {
        readDataFromFile();
        Collections.sort(cardsValue);
        calculateTheLongestSequence();
        writeResultToFile();
    }

    public static void readDataFromFile() {
        readDataFromFile("lngpok.in");
    }

    public static void readDataFromFile(String fileName) {
        File inputFile = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile);
            cardsValue = new ArrayList<>();
            int currentNumber;
            while (scanner.hasNextInt()) {
                currentNumber = scanner.nextInt();
                if ((!cardsValue.contains(currentNumber)) && (currentNumber != 0)) {
                    cardsValue.add(currentNumber);
                }
                if (currentNumber == 0) {
                    numberOfJokers++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file wasn`t found.");
            System.exit(0);
        }
    }

    public static void calculateTheLongestSequence() {
        for (int i = 0; i < cardsValue.size() - 1; i++) {
            int tmpLengthOfTheLongestSequence = 1;
            int tmpNumberOfJokers = numberOfJokers;
            int stepOfSequence = 1;
            int currentIndex = i;
            while (currentIndex < cardsValue.size() - 1) {
                if (cardsValue.get(currentIndex + 1) == cardsValue.get(currentIndex) + stepOfSequence) {
                    tmpLengthOfTheLongestSequence += 1;
                    stepOfSequence = 1;
                    currentIndex++;
                } else if (tmpNumberOfJokers > 0) {
                    tmpLengthOfTheLongestSequence += 1;
                    tmpNumberOfJokers--;
                    stepOfSequence++;
                } else {
                    break;
                }
            }
            tmpLengthOfTheLongestSequence += tmpNumberOfJokers;
            if (tmpLengthOfTheLongestSequence > lengthOfTheLongestSequence) {
                lengthOfTheLongestSequence = tmpLengthOfTheLongestSequence;
            }
        }
    }

    public static void writeResultToFile() {
        writeResultToFile("lngpok.out");
    }

    public static void writeResultToFile(String fileName) {
        File outputFile = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write("" + lengthOfTheLongestSequence);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Can't save result.");
            System.exit(0);
        }
    }

}
