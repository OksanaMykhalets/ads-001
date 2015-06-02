import java.util.ArrayList;

public class CountingSorter {

    private static ArrayList<Integer> array;
    private static int[] counterArray;

    public static ArrayList<Integer> sort(ArrayList<Integer> arrayList) {
        array = arrayList;
        createCounterArray();
        createOutputArray();
        return array;
    }

    private static void createCounterArray() {
        counterArray = new int[1001];
        for (int i = 0; i < array.size(); i++) {
            counterArray[array.get(i)]++;
        }
    }

    private static void createOutputArray() {
        int counterIndex = 0;
        for (int i = 0; i < array.size(); i++) {
            if (counterArray[counterIndex] == 0) {
                counterIndex++;
            }
            array.set(i, counterIndex);
            counterArray[counterIndex]--;
        }
    }

}
