package com.github.hiroshi_cl.wakaba.v2010.sol.srm;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import java.math.BigInteger;
public class IncreasingSequence {
	StringBuilder sb;
	int n;
	int[] nz;
	int[][] lpc;
	BigInteger MOD = valueOf(1000000003);
	public int getProduct(String[] digits) {
		sb = new StringBuilder();
		for (String s : digits)
			sb.append(s);
		n = sb.length();
		nz = new int[n + 1];
		nz[n] = n;
		for (int i = n - 1; i >= 0; i--) {
			nz[i] = sb.charAt(i) != '0' ? i : nz[i + 1];
		}
		lpc = new int[n + 1][n + 1];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				lpc[i][j] = sb.charAt(i) == sb.charAt(j) ? lpc[i + 1][j + 1] + 1 : 0;
			}
		}
		int[] f = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= i; j++) {
				if (less(f[j - 1], j - 1, j, i)) f[i] = j;
			}
		}
		int[] g = new int[n];
		g[f[n - 1]] = n - 1;
		for (int i = f[n - 1] - 1; i >= 0; i--) {
			if (nz[i] == nz[f[n - 1]]) g[i] = n - 1;
			else for (int j = i; j <= f[n - 1] - 1; j++) {
				if (less(i, j, j + 1, g[j + 1])) g[i] = j;
			}
		}
		BigInteger res = ONE;
		for (int i = 0; i < n; i = g[i] + 1) {
			res = res.multiply(new BigInteger(sb.substring(nz[i], g[i] + 1))).mod(MOD);
		}
		return res.intValue();
	}
	boolean less(int a, int b, int c, int d) {
		a = nz[a];
		c = nz[c];
		if (b - a != d - c) return b - a < d - c;
		int l = lpc[a][c];
		if (l >= b - a + 1) return false;
		return sb.charAt(a + l) < sb.charAt(c + l);
	}
}
