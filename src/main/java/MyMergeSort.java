import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class MyMergeSort {
    public static void mergeSort(int[] array, int length) {

        if (length == 1) {
            return;
        }
        int middle = length / 2;
        int[] leftSubArray = new int[middle];
        int[] rightSubArray = new int[length - middle];

        System.arraycopy(array, 0, leftSubArray, 0, middle);

        System.arraycopy(array, middle, rightSubArray, 0, length - middle);

        mergeSort(leftSubArray, middle);
        mergeSort(rightSubArray, length - middle);

        merge(array, leftSubArray, rightSubArray, middle, length - middle);
        System.out.println("merged");
    }

    public static void mergeSort(int[] array, int length, ExecutorService executorService) throws InterruptedException {
        int middle = length / 2;
        int[] leftSubArray = new int[middle];
        int[] rightSubArray = new int[length - middle];

        if (length > 10) {
            CountDownLatch countDownLatch = new CountDownLatch(2);

            executorService.submit(() -> {
                System.arraycopy(array, 0, leftSubArray, 0, middle);
                try {
                    mergeSort(leftSubArray, middle, executorService);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

            executorService.submit(() -> {
                System.arraycopy(array, middle, rightSubArray, 0, length - middle);
                try {
                    mergeSort(rightSubArray, length - middle, executorService);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

            countDownLatch.await();
        } else {
            System.arraycopy(array, 0, leftSubArray, 0, middle);
            mergeSort(leftSubArray, middle);
            System.arraycopy(array, middle, rightSubArray, 0, length - middle);
            mergeSort(rightSubArray, length - middle);
        }

        merge(array, leftSubArray, rightSubArray, middle, length - middle);
        System.out.println("merged: " + Thread.currentThread().getName());
    }

    public static void merge(int[] array, int[] leftSubArray, int[] rightSubArray, int leftIndex, int rightIndex) {

        int i = 0, j = 0, k = 0;
        while (i < leftIndex && j < rightIndex) {
            if (leftSubArray[i] <= rightSubArray[j]) {
                array[k++] = leftSubArray[i++];
            } else {
                array[k++] = rightSubArray[j++];
            }
        }
        while (i < leftIndex) {
            array[k++] = leftSubArray[i++];
        }
        while (j < rightIndex) {
            array[k++] = rightSubArray[j++];
        }
    }
}
