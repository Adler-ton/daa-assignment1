import java.util.Arrays;

public class Problem1 {
    public int countFreqBrute(int key, int[] A) {
        if (A == null || A.length == 0) return 0;
        int count = 0;
        for (int value : A) {
            if (value == key) count++;
        }
        return count;
    }

    public int countFreqSmart(int key, int[] A) {
        if (A == null || A.length == 0) return 0;
        int first = lowerBound(A, key);
        if (first == A.length || A[first] != key) return 0;
        int afterLast = upperBound(A, key);
        return afterLast - first;
    }

    private int lowerBound(int[] a, int key) {
        int left = 0, right = a.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] < key) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int upperBound(int[] a, int key) {
        int left = 0, right = a.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] <= key) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    public static void main(String[] args) {
        Problem1 p = new Problem1();
        int[] a = {1,1,1,2,2,2,2,2,2,4,4,4,5,5,5,5};
        System.out.println(p.countFreqBrute(4, a) + " " + p.countFreqSmart(4, a));
    }
}
