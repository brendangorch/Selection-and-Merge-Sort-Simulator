package se2203.assignment1;

public class SelectionSort implements SortingStrategy {
    // define attributes
    private int[] list;
    private SortingHubController controller;

    // the constructor to set SortingHubController
    public SelectionSort(SortingHubController controller) {
        this.controller = controller;
    }

    // sort method
    public void sort(int [] numbers, int l, int r) {
        // for loop to find the index of the minimum value in the list
        for (int i = 0; i < numbers.length - 1; i++) {
            int indexOfMin = i;
            // inner for loop will compare the next integer in the list to the current
            for (int j = i + 1; j < numbers.length; j++) {
                // if the next integer is smaller than the current, set the indexOfMin to the next integer's index
                if (numbers[j] < numbers[indexOfMin]) {
                    indexOfMin = j;
                    // code for the threading and to make selection animation
                    try {
                        // 20-millisecond delay
                        Thread.sleep(20);
                        // controller reference object updates the screen
                        controller.updateGraph(numbers);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            int temp = numbers[indexOfMin]; // define a temporary integer as the value of the smaller integer
            // swap the two integers in the list so the smaller one is before
            numbers[indexOfMin] = numbers[i];
            numbers[i] = temp;
        }
    }

    // the run method
    public void run() {

    }
}
