import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Movrat {

    public static int N;
    public static int[] estimates;
    public static int lowIgnoreCount;
    public static int highIgnoreCount;
    public static int filmRating;

    public static void main(String[] args) {
        readDataFromFile("movrat.in");
        Arrays.sort(estimates);
        discardFalseReviews();
        calculateFilmRating();
        writeResultToFile("movrat.out");
    }

    public static void readDataFromFile(String fileName) {
        File inputFile = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Input file wasn`t found.");
            System.exit(0);
        }
        N = scanner.nextInt();
        estimates = new int[N];
        for (int i = 0; i < N; i++) {
            estimates[i] = scanner.nextInt();
        }
        lowIgnoreCount = scanner.nextInt();
        highIgnoreCount = scanner.nextInt();
        scanner.close();
    }

    public static void discardFalseReviews() {
        int tmpArraySize = N - lowIgnoreCount - highIgnoreCount;
        int[] tmpArray = new int[tmpArraySize];
        int shift = lowIgnoreCount;
        for (int i = 0; i < tmpArraySize; i++) {
            tmpArray[i] = estimates[shift];
            shift++;
        }
        estimates = tmpArray;
    }

    public static void calculateFilmRating() {
        int sum = 0;
        for (int i : estimates) {
            sum += i;
        }
        filmRating = sum/estimates.length;
    }

    public static void writeResultToFile(String fileName) {
        File outputFile = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write("" + filmRating);
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("Can't save result.");
            System.exit(0);
        }
    }

}
