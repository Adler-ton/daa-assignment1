public class Problem2 {
    public double getMedianBrute(int[] A, int[] B) {
        validate(A, B);
        int[] merged = new int[A.length + B.length];
        int i = 0, j = 0, k = 0;
        while (i < A.length && j < B.length)
            merged[k++] = A[i] <= B[j] ? A[i++] : B[j++];
        while (i < A.length) merged[k++] = A[i++];
        while (j < B.length) merged[k++] = B[j++];
        int n = merged.length;
        return n % 2 == 1 ? merged[n / 2]
                : ((long) merged[n / 2 - 1] + merged[n / 2]) / 2.0;
    }

    public double getMedianSmart(int[] A, int[] B) {
        validate(A, B);
        if (A.length > B.length) return getMedianSmart(B, A);
        int m = A.length, n = B.length;
        int leftSize = (m + n + 1) / 2;
        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = lo + (hi - lo) / 2;
            int j = leftSize - i;
            if (i < m && j > 0 && B[j - 1] > A[i]) {
                lo = i + 1;
            } else if (i > 0 && j < n && A[i - 1] > B[j]) {
                hi = i - 1;
            } else {
                int leftMax;
                if (i == 0) leftMax = B[j - 1];
                else if (j == 0) leftMax = A[i - 1];
                else leftMax = Math.max(A[i - 1], B[j - 1]);
                if ((m + n) % 2 == 1) return leftMax;
                int rightMin;
                if (i == m) rightMin = B[j];
                else if (j == n) rightMin = A[i];
                else rightMin = Math.min(A[i], B[j]);
                return ((long) leftMax + rightMin) / 2.0;
            }
        }
        throw new IllegalArgumentException("Input arrays must be sorted.");
    }

    private void validate(int[] A, int[] B) {
        if (A == null || B == null || A.length + B.length == 0)
            throw new IllegalArgumentException("Arrays must be non-null and not both empty.");
    }

    public static void main(String[] args) {
        Problem2 p = new Problem2();
        System.out.println(p.getMedianBrute(new int[]{2, 4}, new int[]{3, 5}));
        System.out.println(p.getMedianSmart(new int[]{2, 4}, new int[]{3, 5}));
    }
}
