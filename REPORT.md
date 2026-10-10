# DAA Assignment 1 — Report

## Environment and method

The implementations are in `src/Problem1.java` through `src/Problem5.java`. The public method signatures supplied in the starter project are unchanged. `src/Benchmark.java` contains the empirical comparison code. It uses `System.nanoTime()`, performs two warm-up runs, then reports the median of repeated measurements. Timings are machine- and JVM-dependent; they are evidence from one run, not universal performance guarantees.

## Problem 1 — Number of Occurrences

**Brute force.** Scan every element and increment a counter when it equals the key. This works because every element is checked, including all duplicates. The running time is \(\Theta(n)\) in the best, average, and worst cases; extra space is \(\Theta(1)\).

**Efficient solution.** Since the input is sorted, use binary search twice: `lowerBound` finds the first index whose value is at least the key, and `upperBound` finds the first index whose value is greater than the key. Their difference is the number of occurrences. If the lower-bound position is outside the array or does not contain the key, the answer is zero. Each search takes \(\Theta(\log n)\), so total time is \(\Theta(\log n)\) and extra space is \(\Theta(1)\).

## Problem 2 — Median of Two Sorted Arrays

**Brute force.** Merge the two sorted arrays into one sorted array, then select the middle element or average the two middle elements. The merge takes \(\Theta(n+m)\) time and \(\Theta(n+m)\) extra space.

**Efficient solution.** Binary-search for a partition in the shorter array. The partition is valid when every value on the left of both arrays is no greater than every value on the right. The maximum of the left partition and minimum of the right partition determine the median. The search range is halved each iteration, giving \(\Theta(\log(\min(n,m)+1))\) time and \(\Theta(1)\) extra space. The implementation rejects null arrays and two empty arrays because their median is undefined.

## Problem 3 — Largest Subrange (Maximum Subarray Sum)

**Brute force.** For each starting index, extend the ending index one position at a time and maintain the current sum. This checks every non-empty contiguous subarray in \(\Theta(n^2)\) time and uses \(\Theta(1)\) extra space.

**Divide and conquer.** Split the array into two halves. The maximum subarray is either entirely in the left half, entirely in the right half, or crosses the midpoint. For the crossing case, find the best suffix of the left half and best prefix of the right half, then add them. Thus \(T(n)=2T(n/2)+\Theta(n)\), which by the Master Theorem is \(\Theta(n\log n)\). The recursion uses \(\Theta(\log n)\) stack space. The algorithm returns the largest element for an all-negative array rather than incorrectly returning zero.

## Problem 4 — Closest Pair of Points

**Brute force.** Calculate the Euclidean distance for every pair of points and retain the minimum. There are \(n(n-1)/2\) pairs, so time is \(\Theta(n^2)\), with \(\Theta(1)\) auxiliary space.

**Divide and conquer.** Sort points by x-coordinate, split into two halves, and recursively find the closest pair in each half. Merge the halves in y-coordinate order and inspect points in the vertical strip around the split line. For each strip point, only following points whose y-distance is smaller than the current best distance need to be considered; the geometric packing argument bounds the number of relevant neighbors by a constant. Sorting costs \(\Theta(n\log n)\), and the recurrence is \(T(n)=2T(n/2)+\Theta(n)\), giving \(\Theta(n\log n)\) time and \(\Theta(n)\) auxiliary space. Points are copied into internal point objects so the caller's coordinate array is not reordered. For fewer than two points, both methods return `0.0`.

## Problem 5 — Integer Multiplication

**Brute force.** Multiply each digit of one number by each digit of the other, storing carries in a decimal digit array. For operand lengths \(n\) and \(m\), time is \(\Theta(nm)\) and the output storage is \(\Theta(n+m)\). No `BigInteger` is used by either implementation.

**Karatsuba divide and conquer.** Split each operand into high and low parts and compute three products:
\(z_2=ac\), \(z_0=bd\), and \(z_1=(a+b)(c+d)-z_2-z_0\).
Combine them as \(z_2 10^{2k}+z_1 10^k+z_0\). This reduces four half-size multiplications to three, giving \(T(n)=3T(n/2)+\Theta(n)=\Theta(n^{\log_2 3})\), approximately \(\Theta(n^{1.585})\), for similarly sized operands. Decimal string addition, subtraction, and shifting are implemented directly. A schoolbook multiplication cutoff avoids recursive overhead for small operands. Optional leading signs and leading zeros are normalized, and negative results are supported.

## Empirical comparison

The benchmark was run with the bundled `Benchmark` class using `System.nanoTime()`. Each result below is the median of repeated runs after two warm-up runs. Values are in nanoseconds.

| Problem | Input size | Brute-force / merge | Divide-and-conquer | Observation |
|---|---:|---:|---:|---|
| 1. Occurrences | 100,000 elements | 191,569 | 1,062 | Binary search avoids scanning the full array. |
| 2. Median | 200,000 total elements | 840,790 | 290 | The partition search examines only a logarithmic number of positions. |
| 3. Maximum subarray | 4,000 elements | 2,231,990 | 189,336 | The quadratic enumeration becomes more expensive as n grows. |
| 4. Closest pair | 1,800 points | 29,488,598 | 1,462,869 | Sorting and the strip check substantially reduce pair comparisons. |
| 5. Multiplication | Two 1,200-digit operands | 4,073,501 | 6,836,823 | At this size, Karatsuba's recursive string operations cost more than the simple method on this run. |

The measurements illustrate both asymptotic behavior and constant-factor effects. In particular, a better asymptotic bound does not guarantee a faster result for every input size: Karatsuba can lose to schoolbook multiplication until operands are large enough for its reduced multiplication count to outweigh recursion and string-operation overhead. Timing results can vary with hardware, JVM version, warm-up, and system load.

## Validation

The implementations were compiled with `javac` and checked with 1,000 randomized cross-checks. For each problem, the brute-force and efficient results were compared on the same inputs; multiplication was additionally checked against `BigInteger` in the test harness only. The provided examples are also available in the `main` methods of the problem classes. Run `java -cp src Benchmark` from the project directory to repeat the measurements.

