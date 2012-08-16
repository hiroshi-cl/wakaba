package com.github.hiroshi_cl.wakaba.v2009.lib.linearCalculation;

public class Matrix {

	long[][] mul(long[][] a, long[][] b) {
		int n = a.length, m = b[0].length, s = a[0].length;
		long[][] c = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < s; k++) {
					c[i][j] = (c[i][j] + a[i][k] * b[k][j]); // %M;
				}
			}
		}
		return c;
	}

	long[][] pow(long[][] a, int b) {
		int n = a.length;
		long[][] c = new long[n][n];
		for (int i = 0; i < n; i++)
			c[i][i] = 1;
		while (b > 0) {
			if ((b & 1) == 1)
				c = mul(c, a);
			a = mul(a, a);
			b >>>= 1;
		}
		return c;
	}
}
