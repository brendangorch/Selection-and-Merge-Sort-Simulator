package se2203.assignment1;

public class MergeSort implements SortingStrategy {
    // define attributes
    private int[] list;
    private SortingHubController controller;

    // the constructor to set SortingHubController
    public MergeSort(SortingHubController controller) {
        this.controller = controller;
    }

    // the sort method
    public void sort(int[] arr, int left, int right) {
        if (left < right) {
            // define mid
            int mid = left + (right - left) / 2;
            // recursive calls
            sort(arr, left, mid); // sort the left half of the array
            sort(arr, mid + 1, right); // sort the right half of the array
            merge(arr, left, mid, right); // merge the left, middle, and right
        }
    }

    // the merge method that is called in sort
    void merge(int[] arr, int start, int middle, int end) {
        int secondStart = middle + 1;
        if (arr[middle] <= arr[secondStart]) { // case if merge is already sorted
            return;
        }
        while (start <= middle && secondStart <= end) {
            // if element 1 is sorted properly (in correct index)
            if (arr[start] <= arr[secondStart]) {
                // increment the start
                start++;
            } else {
                int value = arr[secondStart];
                int index = secondStart;
                // shift the values by 1
                while (index != start) {
                    arr[index] = arr[index - 1];
                    index--;
                    // code for the threading and to create merge animation
                    try {
                        // 5-millisecond delay
                        Thread.sleep(5);
                        // controller reference object updates the screen
                        controller.updateGraph(arr);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                arr[start] = value;
                // increment the start, middle, secondStart
                start++;
                middle++;
                secondStart++;
            }
        }
    }

    // the run method
    public void run() {
    }

}
