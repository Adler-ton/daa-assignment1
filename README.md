# DAA Assignment 1 — Divide and Conquer

This project implements five algorithm problems with both brute-force and divide-and-conquer approaches.

## Contents
- `src/Problem1.java` — number of occurrences in a sorted array
- `src/Problem2.java` — median of two sorted arrays
- `src/Problem3.java` — maximum contiguous subarray sum
- `src/Problem4.java` — closest pair of 2D points
- `src/Problem5.java` — decimal integer multiplication (schoolbook and Karatsuba)
- `src/Benchmark.java` — empirical runtime comparison using `System.nanoTime()`
- `REPORT.md` — solution descriptions, complexity analysis, measurements, and defense notes
- `Problem1.md`–`Problem5.md` — original assignment statements

## Requirements
JDK 11 or newer is recommended. No external libraries are needed.

## Compile and run
Open a terminal in this directory:

```bash
javac src/*.java
java -cp src Problem1
java -cp src Problem2
java -cp src Problem3
java -cp src Problem4
java -cp src Problem5
java -cp src Benchmark
```

The benchmark results depend on the computer and Java runtime. See `REPORT.md` for the recorded run and methodology.

## Notes
- The original public method names and parameter types have been preserved.
- The implementations do not use `BigInteger`; it is suitable only as an independent test oracle.
- `Problem3` expects a non-empty array. `Problem2` rejects two empty arrays because a median is undefined.

## Run the randomized validation checks

```bash
javac src/*.java
java -cp src AssignmentTests
```

The test class compares both implementations for each problem on randomized inputs; multiplication is also checked against Java `BigInteger` as a test oracle only.
