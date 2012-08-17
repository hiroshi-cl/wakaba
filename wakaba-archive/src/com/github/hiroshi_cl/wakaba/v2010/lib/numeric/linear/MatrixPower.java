package com.github.hiroshi_cl.wakaba.v2010.lib.numeric.linear;

/*
 - 入力 : 行列A,long p,int mod.
 - 出力 : A^p を 法mod で.

 - O( n^3 log p ).
 */

public class MatrixPower {
	// O(n^3 log p).
	long[][] pow(long[][] A, long p, int mod) {
		if (p == 0)
			return identity(A.length);
		return p % 2 == 0 ? pow(mul(A, A, mod), p / 2, mod) : mul(A,
				pow(A, p - 1, mod), mod);
	}

	long[][] mul(long[][] A, long[][] B, int mod) {
		int n = A.length, m = B[0].length, s = B.length;
		long[][] res = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				for (int k = 0; k < s; k++) {
					res[i][j] = (res[i][j] + A[i][k] * B[k][j]) % mod;
				}
			}
		}
		return res;
	}

	long[][] identity(int n) {
		long[][] res = new long[n][n];
		for (int i = 0; i < n; i++)
			res[i][i] = 1;
		return res;
	}
}
