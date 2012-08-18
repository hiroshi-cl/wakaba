package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class F2 {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("F.in"));
			System.setOut(new PrintStream("F.out"));
		} catch (Exception e) {
		}
		new F2().run(sc);
	}

	static void debug(Object... os) {
		// System.err.println(deepToString(os));
	}

	static int INF = 1 << 27;
	static double EPS = 1e-9;

	static int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

	static boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	boolean nextPermutation(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j])
					;
				swap(is, i - 1, j);
				rev(is, i, n);
				return true;
			}
		}
		rev(is, 0, n);
		return false;
	}

	void swap(int[] is, int i, int j) {
		int t = is[i];
		is[i] = is[j];
		is[j] = t;
	}

	void rev(int[] is, int s, int t) {
		while (s < --t)
			swap(is, s++, t);
	}

	int[] dx = new int[] { 1, 0, -1, 0 };
	int[] dy = new int[] { 0, 1, 0, -1 };

	void run(Scanner sc) {
		for (;;) {
			int w = sc.nextInt(), h = sc.nextInt();
			if (w + h == 0)
				return;
			char[][] css = new char[h][w];
			int m = 1;
			for (int i = 0; i < h; i++) {
				css[i] = sc.next().toCharArray();
				for (int j = 0; j < w; j++)
					if (css[i][j] == '*')
						m++;
			}
			int[] xs = new int[m], ys = new int[m];
			for (int i = 0, k = 1; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (css[i][j] == '*') {
						xs[k] = i;
						ys[k++] = j;
					}
					if (css[i][j] == 'o') {
						xs[0] = i;
						ys[0] = j;
					}
				}
			}
			int[][][][] f = new int[h][w][h][w];
			for (int i = 0; i < h; i++)
				for (int j = 0; j < w; j++)
					for (int k = 0; k < h; k++)
						fill(f[i][j][k], INF);
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (css[i][j] != 'x')
						for (int d = 0; d < 4; d++) {
							int ni = i + dx[d];
							int nj = j + dy[d];
							if (0 <= ni && ni < h && 0 <= nj && nj < w) {
								if (css[ni][nj] != 'x') {
									f[i][j][ni][nj] = 1;
								}
							}
						}
				}
			}
			for (int k1 = 0; k1 < h; k1++) {
				for (int k2 = 0; k2 < w; k2++) {
					for (int i1 = 0; i1 < h; i1++) {
						for (int i2 = 0; i2 < w; i2++) {
							for (int j1 = 0; j1 < h; j1++) {
								for (int j2 = 0; j2 < w; j2++) {
									f[i1][i2][j1][j2] = min(f[i1][i2][j1][j2],
											f[i1][i2][k1][k2]
													+ f[k1][k2][j1][j2]);
								}
							}
						}
					}
				}
			}
			// debug(f);
			int[][] dist = new int[m][m];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					dist[i][j] = f[xs[i]][ys[i]][xs[j]][ys[j]];
				}
			}
			int[] perm = new int[m - 1];
			for (int i = 1; i < m; i++)
				perm[i - 1] = i;
			int res = INF;
			do {
				int tmp = 0;
				tmp += dist[0][perm[0]];
				for (int i = 1; i < m - 1; i++) {
					tmp += dist[perm[i - 1]][perm[i]];
				}
				res = min(res, tmp);
			} while (nextPermutation(perm));
			System.out.println(res == INF ? -1 : res);
		}
	}
}