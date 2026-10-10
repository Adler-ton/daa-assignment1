import java.util.Arrays;
import java.util.Comparator;

public class Problem4 {
    private static final class Point {
        final double x, y;
        int rank;
        Point(double x, double y) { this.x = x; this.y = y; }
    }
    private static final class Result {
        final double distance;
        final Point[] byY;
        Result(double distance, Point[] byY) { this.distance = distance; this.byY = byY; }
    }

    public double minDistBrute(double[][] p) {
        validate(p);
        if (p.length < 2) return 0.0;
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < p.length; i++)
            for (int j = i + 1; j < p.length; j++)
                best = Math.min(best, distance(p[i][0], p[i][1], p[j][0], p[j][1]));
        return best;
    }

    public double minDistSmart(double[][] p) {
        validate(p);
        if (p.length < 2) return 0.0;
        Point[] points = new Point[p.length];
        for (int i = 0; i < p.length; i++) points[i] = new Point(p[i][0], p[i][1]);
        Arrays.sort(points, Comparator.comparingDouble((Point q) -> q.x).thenComparingDouble(q -> q.y));
        for (int i = 0; i < points.length; i++) points[i].rank = i;
        return closest(points, 0, points.length).distance;
    }

    private Result closest(Point[] byX, int lo, int hi) {
        int n = hi - lo;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++)
                for (int j = i + 1; j < hi; j++)
                    best = Math.min(best, distance(byX[i].x, byX[i].y, byX[j].x, byX[j].y));
            Point[] byY = Arrays.copyOfRange(byX, lo, hi);
            Arrays.sort(byY, Comparator.comparingDouble((Point q) -> q.y).thenComparingDouble(q -> q.x));
            return new Result(best, byY);
        }

        int mid = lo + n / 2;
        double midX = byX[mid].x;
        Result left = closest(byX, lo, mid);
        Result right = closest(byX, mid, hi);
        double best = Math.min(left.distance, right.distance);

        Point[] byY = new Point[n];
        int i = 0, j = 0, k = 0;
        while (i < left.byY.length && j < right.byY.length)
            byY[k++] = left.byY[i].y <= right.byY[j].y ? left.byY[i++] : right.byY[j++];
        while (i < left.byY.length) byY[k++] = left.byY[i++];
        while (j < right.byY.length) byY[k++] = right.byY[j++];

        Point[] strip = new Point[n];
        int count = 0;
        for (Point point : byY)
            if (Math.abs(point.x - midX) < best) strip[count++] = point;
        for (i = 0; i < count; i++) {
            for (j = i + 1; j < count && strip[j].y - strip[i].y < best; j++)
                best = Math.min(best, distance(strip[i].x, strip[i].y, strip[j].x, strip[j].y));
        }
        return new Result(best, byY);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x1 - x2, y1 - y2);
    }

    private void validate(double[][] p) {
        if (p == null) throw new IllegalArgumentException("Point array must not be null.");
        for (double[] point : p)
            if (point == null || point.length < 2)
                throw new IllegalArgumentException("Each point must contain x and y coordinates.");
    }

    public static void main(String[] args) {
        double[][] p = {{0,0},{3,4},{-5,-3}};
        Problem4 solver = new Problem4();
        System.out.println(solver.minDistBrute(p) + " " + solver.minDistSmart(p));
    }
}
