### Problem 3 - Largest Subrange (20 points)

+ Given an integer array $A$ of size $n$, find the index pair $i$ and $j$ that maximizes $S = \sum_{k=i}^{j}A[k]$, return $S$ as the answer.
+ **5pts.** Implement a brute-force solution as `maxSumBrute()` method in [src/Problem3.java](src/Problem3.java).
+ **5pts.** Implement a divide-and-conquer efficient solution as `maxSumSmart()` method in [src/Problem3.java](src/Problem3.java).
+ **5pts.** Describe in written form your solution ideas in the **Report** section below.
+ **5pts.** Defend your solutions to your instructor in live during practice sessions.
+ Your code will be tested against various inputs using `JUnit6` testing framework.
+ **Don't change API** of the solutions in [src/Problem3.java](src/Problem3.java).

#### Example
+ Input: `A={-17, 5, 3, -10, 6, 1, 4, -3, 8, 1, -13, 4}`
+ Output: `17`

#### Constraints
+ $1 \leq n \leq 10^5$
+ $-10^4 \leq A[i] \leq 10^4$

#### References
1. Chapter 5.6, The Algorithm Design Manual, 3rd Edition, 2020 - Steven H. Skiena
2. [maximum-subarray](https://leetcode.com/problems/maximum-subarray/description/)

### Report

**Approach.** `maxSumBrute` enumerates all non-empty subarrays in Θ(n²) time and Θ(1) extra space. `maxSumSmart` compares the best left, right, and crossing subarrays; its recurrence is T(n)=2T(n/2)+Θ(n), so it takes Θ(n log n) time and Θ(log n) recursion space. The measured comparison and full analysis are in [REPORT.md](REPORT.md).

### Defense Interview
+ Be ready to justify running times, for example using **Master method** for divide-and-conquer algorithms.
+ Be ready to answer questions on algorithms and your code.
+ Be ready to run your code and test it on various inputs.

Good Luck!