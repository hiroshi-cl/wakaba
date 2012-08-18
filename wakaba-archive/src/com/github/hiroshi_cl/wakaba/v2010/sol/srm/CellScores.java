package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import static java.util.Arrays.*;

public class CellScores {
	int n;

	public long countScores(int N, int M, int K, int X0, int A, int B, int Y0,
			int C, int D) {
		n = N;
		boolean[][] f = new boolean[n][n];
		for (int i = 0; i < n; i++)
			fill(f[i], true);
		int x = X0 % n, y = Y0 % n;
		for (int i = 0; i < M; i++) {
			f[x][y] = false;
			x = (x * A + B) % n;
			y = (y * C + D) % n;
		}
		long[][] Score = calcScore(f);
		long res = 0;
		for (int i = 0; i < K; i++) {
			res += Score[x][y];
			x = (x * A + B) % n;
			y = (y * C + D) % n;
		}
		return res;
	}

	long[][] calcScore(boolean[][] f) {
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