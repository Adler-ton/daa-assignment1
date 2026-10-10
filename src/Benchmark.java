import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static volatile long sink;
    private static long time(Runnable task, int repetitions) {
        for (int i = 0; i < 2; i++) task.run(); // warm-up
        long[] samples = new long[repetitions];
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            task.run();
            samples[i] = System.nanoTime() - start;
        }
        Arrays.sort(samples);
        return samples[repetitions / 2];
    }
    public static void main(String[] args) {
        Random random = new Random(2026);
        Problem1 p1 = new Problem1();
        int[] sorted = new int[100_000];
        for (int i=0;i<sorted.length;i++) sorted[i]=i/10;
        System.out.printf("P1 n=100000 brute=%d ns smart=%d ns%n",
            time(() -> sink += p1.countFreqBrute(5000, sorted), 7),
            time(() -> sink += p1.countFreqSmart(5000, sorted), 7));

        Problem2 p2 = new Problem2();
        int[] a = new int[100_000], b = new int[100_000];
        for(int i=0;i<a.length;i++){a[i]=2*i;b[i]=2*i+1;}
        System.out.printf("P2 total=200000 merge=%d ns smart=%d ns%n",
            time(() -> sink += (long)p2.getMedianBrute(a,b), 7),
            time(() -> sink += (long)p2.getMedianSmart(a,b), 7));

        Problem3 p3 = new Problem3();
        int[] sub = new int[4000]; for(int i=0;i<sub.length;i++)sub[i]=random.nextInt(201)-100;
        System.out.printf("P3 n=4000 brute=%d ns smart=%d ns%n",
            time(() -> sink += p3.maxSumBrute(sub), 5),
            time(() -> sink += p3.maxSumSmart(sub), 7));

        Problem4 p4 = new Problem4();
        double[][] points = new double[1800][2];
        for(double[] q:points){q[0]=random.nextDouble()*10000;q[1]=random.nextDouble()*10000;}
        System.out.printf("P4 n=1800 brute=%d ns smart=%d ns%n",
            time(() -> sink += (long)p4.minDistBrute(points), 5),
            time(() -> sink += (long)p4.minDistSmart(points), 7));

        Problem5 p5 = new Problem5();
        String x=randomDigits(random, 1200), y=randomDigits(random, 1200);
        System.out.printf("P5 1200-digit operands schoolbook=%d ns Karatsuba=%d ns%n",
            time(() -> sink += p5.multBrute(x,y).length(), 5),
            time(() -> sink += p5.multSmart(x,y).length(), 7));
        System.out.println("Benchmark completed; median of repeated System.nanoTime measurements. sink="+sink);
    }
    private static String randomDigits(Random r, int n) {
        StringBuilder s = new StringBuilder(n);
        s.append((char)('1'+r.nextInt(9)));
        for(int i=1;i<n;i++)s.append((char)('0'+r.nextInt(10)));
        return s.toString();
    }
}
