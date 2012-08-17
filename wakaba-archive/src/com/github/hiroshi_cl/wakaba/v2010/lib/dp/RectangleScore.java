package com.github.hiroshi_cl.wakaba.v2010.lib.dp;

/**
 * calcScore(boolean[][] f): 各セルに対し、そのセルを含む長方形の数を収めた配列をO(n^2)で計算する。
 * calcBR(boolean[][] f)で、各セルに対して、そのセルを右下とする長方形の数を収めた配列が、O(n^2)で計算できることがポイント。
 */
public class RectangleScore {
	long[][] calcScore(boolean[][] f) {
		int n = f.length;
		int[][] B = calcB(f);
		for (int i = 0; i + i < n; i++)
			for (int j = 0; j < n; j++) {
				boolean tmp = f[i][j];
				f[i][j] = f[n - 1 - i][j];
				f[n - 1 - i][j] = tmp;
			}
		int[][] U = calcB(f);
		for (int i = 0; i + i < n; i++)
			for (int j = 0; j < n; j++) {
				boolean tmp = f[i][j];
				f[i][j] = f[n - 1 - i][j];
				f[n - 1 - i][j] = tmp;
				int tmp2 = U[i][j];
				U[i][j] = U[n - 1 - i][j];
				U[n - 1 - i][j] = tmp2;
			}
		long[][] Score = new long[n][n];
		for (int j = 0; j < n; j++)
			for (int i = 0; i < n; i++) {
				Score[i][j] = U[i][j];
				if (i > 0)
					Score[i][j] += Score[i - 1][j] - B[i - 1][j];
			}
		return Score;
	}

	int[][] calcB(boolean[][] f) {
		int n = f.length;
		int[][] BR = calcBR(f);
		for (int i = 0; i < n; i++)
			for (int j = 0; j + j < n; j++) {
				boolean tmp = f[i][j];
				f[i][j] = f[i][n - 1 - j];
				f[i][n - 1 - j] = tmp;
			}
		int[][] B = calcBR(f);
		for (int i = 0; i < n; i++)
			for (int j = 0; j + j < n; j++) {
				boolean tmp = f[i][j];
				f[i][j] = f[i][n - 1 - j];
				f[i][n - 1 - j] = tmp;
				int tmp2 = B[i][j];
				B[i][j] = B[i][n - 1 - j];
				B[i][n - 1 - j] = tmp2;
			}
		for (int i = 0; i < n; i++)
			for (int j = 1; j < n; j++)
				B[i][j] += B[i][j - 1] - BR[i][j - 1];
		return B;
	}

	int[][] calcBR(boolean[][] f) {
		int n = f.length;
		int[] h = new int[n];
		int[][] BR = new int[n][n];
		for (int i = 0; i < n; i++) {
			int[] c = new int[n + 2];
			int k = 0;
			c[++k] = -1;
			int A = 0, B = 0;
			for (int j = 0; j < n; j++) {
				h[j] = f[i][j] ? h[j] + 1 : 0;
				while (0 < k && h[j] <= (c[k] >= 0 ? h[c[k]] : 0)) {
					if (k >= 2) {
						A -= h[c[k]] - (c[k - 1] >= 0 ? h[c[k - 1]] : 0);
						B -= (h[c[k]] - (c[k - 1] >= 0 ? h[c[k - 1]] : 0))
								* c[k - 1];
					}
					k--;
				}
				c[++k] = j;
				if (k >= 2) {
					A += h[c[k]] - (c[k - 1] >= 0 ? h[c[k - 1]] : 0);
					B += (h[c[k]] - (c[k - 1] >= 0 ? h[c[k - 1]] : 0))
							* c[k - 1];
				}
				BR[i][j] = A * j - B;
			}
		}
		return BR;
	}
}
