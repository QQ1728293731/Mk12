package Beta;

//排序算法练习
public class SortAlgorithm {

    ///冒泡排序
    public static void bubbleSort(int[] arr) {
        for (int j = 1; j < arr.length; j++) {
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
    }

    ///选择排序
    public static void selectionSort(int[] arr) {
        for (int j = 0; j < arr.length; j++) {
            for (int i = j + 1; i < arr.length; i++) {
                if (arr[j] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    ///插入排序
    public static void insertionSort(int[] arr) {
        int i = 1;
        for (i = 1; i < arr.length; i++) if (arr[i] < arr[i - 1]) break;
        for (; i < arr.length; i++) {
            int j = i;
            while (j > 0 && arr[j] < arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    ///快速排序
    public static void quickSort(int[] arr, int i, int j) {
        int start = i, end = j;
        if (start > end) return;
        int baseNumber = arr[i];
        while (start != end) {
            for (; ; end--) if (end <= start || arr[end] < baseNumber) break;
            for (; ; start++) if (end <= start || arr[start] > baseNumber) break;
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
        }
        int temp = arr[start];
        arr[start] = arr[i];
        arr[i] = temp;
        quickSort(arr, i, start - 1);
        quickSort(arr, start + 1, j);
    }
}
