import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class Discnt {

    private static ArrayList<Integer> pricesOfGoods;
    private static double discount;
    private static double minimumPurchasePrice;

    public static void main(String[] args) {
        readDataFromFile();
        sortArray();
        calculatePrice();
        writeResultToFile();
    }

    public static void readDataFromFile() {
        readDataFromFile("discnt.in");
    }

    public static void readDataFromFile(String fileName) {
        File inputFile = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile);
            pricesOfGoods = new ArrayList<>();
            while (scanner.hasNextInt()) {
                pricesOfGoods.add(scanner.nextInt());
            }
            int lastElementOfArray = pricesOfGoods.size() - 1;
            discount = pricesOfGoods.get(lastElementOfArray);
            pricesOfGoods.remove(lastElementOfArray);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file wasn`t found.");
            System.exit(0);
        }
    }

    public static void sortArray() {
        // N = 160 - the intersection point of functions: y = N*log2(N) and y = N + K, (при K = 1000)
        if (pricesOfGoods.size() < 160) {
            Collections.sort(pricesOfGoods);
        } else {
            pricesOfGoods = CountingSorter.sort(pricesOfGoods);
        }
    }

    public static void calculatePrice() {
        int numberOfPromotionalGoods = pricesOfGoods.size() / 3;
        minimumPurchasePrice = 0;
        for (int i = 0; i < pricesOfGoods.size(); i++) {
            if (i >= (pricesOfGoods.size() - numberOfPromotionalGoods)) {
                minimumPurchasePrice += pricesOfGoods.get(i) * (1 - discount / 100);
            } else minimumPurchasePrice += pricesOfGoods.get(i);
        }
    }

    public static void writeResultToFile() {
        writeResultToFile("discnt.out");
    }

    public static void writeResultToFile(String fileName) {
        File outputFile = new File(fileName);
        try {
            PrintWriter fileWriter = new PrintWriter(outputFile);
            fileWriter.printf(Locale.ENGLISH, "%.2f", minimumPurchasePrice);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Can't save result.");
            System.exit(0);
        }
    }

}
