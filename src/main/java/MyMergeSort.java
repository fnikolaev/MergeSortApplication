public class MyMergeSort {
    public static synchronized void merge(int[] array, int[] leftSubArray, int[] rightSubArray, int pairStartIndex) {
        int i = 0, j = 0, k = pairStartIndex;
        while (i < leftSubArray.length && j < rightSubArray.length) {
            if (leftSubArray[i] <= rightSubArray[j]) {
                array[k] = leftSubArray[i];
                i++;
            } else {
                array[k] = rightSubArray[j];
                j++;
            }
            k++;
        }
        while (i < leftSubArray.length) {
            array[k] = leftSubArray[i];
            i++;
            k++;
        }
        while (j < rightSubArray.length) {
            array[k] = rightSubArray[j];
            j++;
            k++;
        }
    }
}
