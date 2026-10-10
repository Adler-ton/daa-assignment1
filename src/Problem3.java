public class Problem3 {
    public int maxSumBrute(int[] A) {
        if (A == null || A.length == 0) throw new IllegalArgumentException("Array must not be empty.");
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            int sum = 0;
            for (int j = i; j < A.length; j++) {
                sum += A[j];
                if (sum > best) best = sum;
            }
        }
        return best;
    }

    public int maxSumSmart(int[] A) {
        if (A == null || A.length == 0) throw new IllegalArgumentException("Array must not be empty.");
        return divide(A, 0, A.length - 1);
    }

    private int divide(int[] a, int left, int right) {
        if (left == right) return a[left];
        int mid = left + (right - left) / 2;
        int bestLeft = divide(a, left, mid);
        int bestRight = divide(a, mid + 1, right);

        int sum = 0, leftCross = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += a[i];
            leftCross = Math.max(leftCross, sum);
        }
        sum = 0;
        int rightCross = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            sum += a[i];
            rightCross = Math.max(rightCross, sum);
        }
        return Math.max(Math.max(bestLeft, bestRight), leftCross + rightCross);
    }

    public static void main(String[] args) {
        int[] a = {-17, 5, 3, -10, 6, 1, 4, -3, 8, 1, -13, 4};
        Problem3 p = new Problem3();
        System.out.println(p.maxSumBrute(a) + " " + p.maxSumSmart(a));
    }
}
