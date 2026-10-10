public class Problem5 {
    public String multBrute(String A, String B) {
        String[] values = normalizeInputs(A, B);
        String a = values[0], b = values[1];
        boolean negative = values[2].equals("true");
        if (a.equals("0") || b.equals("0")) return "0";
        int[] result = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            int da = a.charAt(i) - '0';
            for (int j = b.length() - 1; j >= 0; j--) {
                int pos = i + j + 1;
                int value = da * (b.charAt(j) - '0') + result[pos];
                result[pos] = value % 10;
                result[pos - 1] += value / 10;
            }
        }
        String product = digitsToString(result);
        return negative ? "-" + product : product;
    }

    public String multSmart(String A, String B) {
        String[] values = normalizeInputs(A, B);
        String a = values[0], b = values[1];
        boolean negative = values[2].equals("true");
        if (a.equals("0") || b.equals("0")) return "0";
        String product = karatsuba(a, b);
        return negative ? "-" + product : product;
    }

    private String karatsuba(String x, String y) {
        x = trimZeros(x); y = trimZeros(y);
        if (x.equals("0") || y.equals("0")) return "0";
        int n = Math.max(x.length(), y.length());
        if (n <= 32) return multiplySchoolbook(x, y);

        int split = n / 2;
        String xPadded = leftPad(x, n), yPadded = leftPad(y, n);
        String xHigh = trimZeros(xPadded.substring(0, n - split));
        String xLow = trimZeros(xPadded.substring(n - split));
        String yHigh = trimZeros(yPadded.substring(0, n - split));
        String yLow = trimZeros(yPadded.substring(n - split));

        String z0 = karatsuba(xLow, yLow);
        String z2 = karatsuba(xHigh, yHigh);
        String z1 = subtract(subtract(karatsuba(add(xHigh, xLow), add(yHigh, yLow)), z2), z0);
        return trimZeros(add(add(appendZeros(z2, 2 * split), appendZeros(z1, split)), z0));
    }

    private String multiplySchoolbook(String a, String b) {
        int[] digits = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                int pos = i + j + 1;
                int v = (a.charAt(i) - '0') * (b.charAt(j) - '0') + digits[pos];
                digits[pos] = v % 10;
                digits[pos - 1] += v / 10;
            }
        }
        return digitsToString(digits);
    }

    private String[] normalizeInputs(String A, String B) {
        if (A == null || B == null || !A.matches("[+-]?\\d+") || !B.matches("[+-]?\\d+"))
            throw new IllegalArgumentException("Inputs must be signed decimal integer strings.");
        boolean negA = A.charAt(0) == '-';
        boolean negB = B.charAt(0) == '-';
        String a = trimZeros(stripSign(A)), b = trimZeros(stripSign(B));
        return new String[]{a, b, Boolean.toString(negA ^ negB)};
    }

    private String stripSign(String s) {
        return (s.startsWith("-") || s.startsWith("+")) ? s.substring(1) : s;
    }
    private String trimZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') i++;
        return s.substring(i);
    }
    private String leftPad(String s, int n) {
        if (s.length() >= n) return s;
        return "0".repeat(n - s.length()) + s;
    }
    private String appendZeros(String s, int count) {
        return s.equals("0") ? "0" : s + "0".repeat(count);
    }
    private String add(String a, String b) {
        StringBuilder out = new StringBuilder();
        int i = a.length()-1, j = b.length()-1, carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            int v = carry + (i >= 0 ? a.charAt(i--)-'0' : 0)
                    + (j >= 0 ? b.charAt(j--)-'0' : 0);
            out.append((char)('0' + v % 10)); carry = v / 10;
        }
        return out.reverse().toString();
    }
    // Preconditions: a >= b and both are nonnegative decimal strings.
    private String subtract(String a, String b) {
        StringBuilder out = new StringBuilder();
        int i = a.length()-1, j = b.length()-1, borrow = 0;
        while (i >= 0) {
            int v = a.charAt(i--)-'0' - borrow - (j >= 0 ? b.charAt(j--)-'0' : 0);
            if (v < 0) { v += 10; borrow = 1; } else borrow = 0;
            out.append((char)('0' + v));
        }
        return trimZeros(out.reverse().toString());
    }
    private String digitsToString(int[] digits) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < digits.length - 1 && digits[i] == 0) i++;
        for (; i < digits.length; i++) s.append((char)('0' + digits[i]));
        return trimZeros(s.toString());
    }

    public static void main(String[] args) {
        Problem5 p = new Problem5();
        String a = "12345678987654321", b = "98765432123456789";
        System.out.println(p.multBrute(a, b));
        System.out.println(p.multSmart(a, b));
    }
}
