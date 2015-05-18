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
        /* please make sure, that your source file and input file lives in the same directory
           or specify an absolute path to your input file */
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
        if ((N < 1) || (N > 10000000)) {
            System.out.println("Incorrect number of estimates.");
            System.exit(0);
        }
        estimates = new int[N];
        for (int i = 0; i < N; i++) {
            estimates[i] = scanner.nextInt();
            if ((estimates[i] < 0) || (estimates[i] > 100)) {
                System.out.println("Incorrect estimate.");
                System.exit(0);
            }
        }
        lowIgnoreCount = scanner.nextInt();
        if ((lowIgnoreCount < 0) || (lowIgnoreCount > 99)) {
            System.out.println("Incorrect value of lowIgnoreCount.");
            System.exit(0);
        }
        highIgnoreCount = scanner.nextInt();
        if ((highIgnoreCount < 0) || (highIgnoreCount > 99)) {
            System.out.println("Incorrect value of highIgnoreCount.");
            System.exit(0);
        }
        if ((lowIgnoreCount + highIgnoreCount) >= N) {
            System.out.println("Sum of lowIgnoreCount and highIgnoreCount must be less than number of estimates.");
            System.exit(0);
        }
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
