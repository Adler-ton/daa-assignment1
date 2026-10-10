import java.util.*;
public class AssignmentTests {
 public static void main(String[] args) {
  Random r=new Random(42);
  Problem1 p1=new Problem1(); Problem2 p2=new Problem2(); Problem3 p3=new Problem3();
  Problem4 p4=new Problem4(); Problem5 p5=new Problem5();
  for(int t=0;t<1000;t++){
   int n=r.nextInt(100); int[] a=new int[n]; for(int i=0;i<n;i++)a[i]=r.nextInt(21)-10; Arrays.sort(a);
   int key=r.nextInt(25)-12;
   if(p1.countFreqBrute(key,a)!=p1.countFreqSmart(key,a))throw new RuntimeException("p1");
   int m=1+r.nextInt(50); int[] b=new int[m]; for(int i=0;i<m;i++)b[i]=r.nextInt(21)-10; Arrays.sort(b);
   if(Math.abs(p2.getMedianBrute(a,b)-p2.getMedianSmart(a,b))>1e-9)throw new RuntimeException("p2");
   int[] c=new int[1+r.nextInt(60)]; for(int i=0;i<c.length;i++)c[i]=r.nextInt(201)-100;
   if(p3.maxSumBrute(c)!=p3.maxSumSmart(c))throw new RuntimeException("p3");
   double[][] pts=new double[2+r.nextInt(30)][2]; for(double[] q:pts){q[0]=r.nextInt(100);q[1]=r.nextInt(100);}
   if(Math.abs(p4.minDistBrute(pts)-p4.minDistSmart(pts))>1e-9)throw new RuntimeException("p4 "+p4.minDistBrute(pts)+" "+p4.minDistSmart(pts));
   String x=String.valueOf(Math.abs(r.nextLong())), y=String.valueOf(Math.abs(r.nextLong()));
   java.math.BigInteger expected=new java.math.BigInteger(x).multiply(new java.math.BigInteger(y));
   if(!p5.multBrute(x,y).equals(expected.toString())||!p5.multSmart(x,y).equals(expected.toString()))throw new RuntimeException("p5");
  }
  if(!p5.multSmart("-000123", "+045").equals("-5535"))throw new RuntimeException("sign");
  System.out.println("All 1000 randomized cross-checks passed.");
 }
}
