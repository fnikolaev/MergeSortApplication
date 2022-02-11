import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] array = {10, 5, 1, 6, 2, 3, 4, 10, 5, 1, 22, 523, 21, 13, 43, 22};

        sortArrray(array);

        System.out.println(Arrays.toString(array));
    }

    /**
     * Sorting using merge sort with threads. Thread couldn't sort more than 10 elements.
     * If there are more than 10 elemets in array, it will devided into 2 parts, which will be processed by different
     * threads.
     *
     * @param array - Main array before sorting
     * @return sorted array
     * @throws InterruptedException
     */
    public static void sortArrray(int[] array) throws InterruptedException {
        if (array.length <= 10) {
            MyMergeSort.mergeSort(array, array.length);
        } else {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            MyMergeSort.mergeSort(array, array.length, executorService);
            executorService.shutdown();
        }
    }
}
