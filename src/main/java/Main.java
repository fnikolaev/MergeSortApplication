import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] array = {10, 5, 1, 6, 2};

        //sortArrray(array);

        System.out.println(Arrays.toString(sortArrray(array)));
    }

    /**
     * Sort array using multithreading and mergesort.
     * If nearby elements from unsorted array could be grouped in pairs they will be processed by separate thread.
     *
     * @param array - Unsorted array.
     * @return Sorted array.
     * @throws InterruptedException
     */
    public static int[] sortArrray(int[] array) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int subArraysLength = 1;
        int pairLength = 2;
        boolean sorted = false;
        // elements which not in subarray with same length pair.
        int unincludedElements = (array.length % 2 == 0) ? 0 : 1;

        while (!sorted && unincludedElements != array.length) {

            CountDownLatch countDownLatch = new CountDownLatch((array.length - unincludedElements) / pairLength);
            // subarrays are paired up for merging
            int pair;

            for (pair = 0; pair < (array.length - unincludedElements) / pairLength; pair++) {

                int[] leftSubArray = new int[subArraysLength];
                int[] rightSubArray = new int[subArraysLength];

                System.arraycopy(array, pairLength * pair, leftSubArray, 0, subArraysLength);
                System.arraycopy(array, pairLength * pair + subArraysLength, rightSubArray, 0, subArraysLength);

                System.out.println(Arrays.toString(leftSubArray));
                System.out.println(Arrays.toString(rightSubArray));

                // this fields are only for lambda expression
                int finalPair = pair;
                int finalPairLength = pairLength;
                executorService.submit(() -> {
                    MyMergeSort.merge(array, leftSubArray, rightSubArray, finalPair * finalPairLength);
                    countDownLatch.countDown();
                });
            }
            // wait untill all pairs complete merging
            countDownLatch.await();

            // for example there was an array of 10 alements, after 1 circle there will be 4 pairs with 2 el and 2 without pair
            if (unincludedElements == 0) {
                unincludedElements = array.length - pairLength * pair;
            }

            // after all threads have alredy sorted pairs with merging we need to merge unincluded elements with last sorted pair(subpair)
            if (unincludedElements != 0) {

                if (pair == 0) {
                    pair++;
                    pairLength /= 2;
                    sorted = true;
                }

                int[] leftSubArray;
                int[] rightSubArray;

                if (array.length - pair * pairLength == unincludedElements) {
                    leftSubArray = new int[pairLength];
                    rightSubArray = new int[unincludedElements];

                    System.arraycopy(array, pairLength * (pair - 1), leftSubArray, 0, pairLength);
                    System.arraycopy(array, pairLength * (pair - 1) + pairLength, rightSubArray, 0, unincludedElements);

                    MyMergeSort.merge(array, leftSubArray, rightSubArray, (pair - 1) * pairLength);
                } else {
                    leftSubArray = new int[subArraysLength];
                    rightSubArray = new int[unincludedElements];

                    System.arraycopy(array, pairLength * pair, leftSubArray, 0, subArraysLength);
                    System.arraycopy(array, pairLength * pair + subArraysLength, rightSubArray, 0, unincludedElements);

                    MyMergeSort.merge(array, leftSubArray, rightSubArray, pair * pairLength);
                }

                unincludedElements = leftSubArray.length + rightSubArray.length;
            }
            System.out.println("Circle completed. Merging sorted pairs...");

            subArraysLength *= 2;
            pairLength *= 2;
        }
        executorService.shutdown();
        return array;
    }
}