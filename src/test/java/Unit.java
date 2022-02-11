import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Unit {
    @Test
    public void positiveTestLess10Elements() throws InterruptedException {
        int[] actual = {5, 1, 6, 2, 3, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        Main.sortArrray(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void positiveTestMore10Elements() throws InterruptedException {
        int[] actual = {5, 1, 6, 2, 3, 4, 22, 43, 12, 100, 59, 40};
        int[] expected = {1, 2, 3, 4, 5, 6, 12, 22, 40, 43, 59, 100};
        Main.sortArrray(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void positiveTestMore22Elements() throws InterruptedException {
        int[] actual = {5, 1, 6, 2, 3, 4, 22, 43, 12, 100, 59, 40, 5, 1, 6, 2, 3, 4, 22, 43, 12, 100, 59, 40};
        int[] expected = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 12, 12, 22, 22, 40, 40, 43, 43, 59, 59, 100, 100};
        Main.sortArrray(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void positiveTestExactly21Elements() throws InterruptedException {
        int[] actual = {5, 1, 6, 2, 3, 4, 22, 43, 12, 100, 59, 40, 5, 1, 6, 2, 3, 4, 22, 43, 12};
        int[] expected = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 12, 12, 22, 22, 40, 43, 43, 59, 100};
        Main.sortArrray(actual);
        assertArrayEquals(expected, actual);
    }
}
