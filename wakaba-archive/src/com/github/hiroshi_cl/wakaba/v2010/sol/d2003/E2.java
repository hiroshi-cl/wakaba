package com.github.hiroshi_cl.wakaba.v2010.sol.d2003;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class E2 {
	public static void main(String[] args) {
		new E2().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int w = sc.nextInt(), h = sc.nextInt();
			if ((w | h) == 0)
				return;
			boolean[][] m = new boolean[h][w];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					m[i][j] = sc.nextInt() == 1;
				}
			}
			int res2 = Integer.MAX_VALUE;
			for (int o = 0; o < 4; o++) {
				// debug(m);
				int n = min(w, h);
				int[][] car = new int[h][w];
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						car[i][j] = (maxL(i, j, m, n));
					}
				}

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int n1 = car[i][j];
						for (int i2 = 0; i2 < n1; i2++) {
							for (int j2 = 0; j2 < n1; j2++) {
								if (i2 == 0 && j2 == 0)
									continue;
								int n2 = car[i + i2][j + j2];
								if (max(i2, j2) <= n1 - n2) {
									car[i + i2][j + j2] = 0;
								}
							}
						}
					}
				}
				int[][] num = new int[h][w];
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int nn = car[i][j];
						for (int i2 = 0; i2 < nn; i2++) {
							for (int j2 = 0; j2 < nn; j2++) {
								num[i + i2][j + j2]++;
							}
						}
					}
				}
				int res = 0;
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int nn = car[i][j];
						boolean ok = false;
						loop: for (int i2 = 0; i2 < nn; i2++) {
							for (int j2 = 0; j2 < nn; j2++) {
								if (num[i + i2][j + j2] == 1) {
									ok = true;
									res++;
									break loop;
								}
							}
						}
						if (!ok) {
							for (int i2 = 0; i2 < nn; i2++) {
								for (int j2 = 0; j2 < nn; j2++) {
									num[i + i2][j + j2]--;
								}
							}
						}
					}
				}
				res2 = min(res, res2);
				int t = h;
				h = w;
				w = t;
				boolean[][] m2 = new boolean[h][w];
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						m2[i][j] = m[j][h - i - 1];
					}
				}
				m = m2;
			}
			System.out.println(res2);
		}
	}

	int maxL(int x, int y, boolean[][] m, int n) {
		if (!m[x][y])
			return 0;
		for (int n2 = 1; n2 <= n; n2++) {
			for (int i = 0; i < n2; i++) {
				for (int j = 0; j < n2; j++) {
					if (x + i >= m.length || y + j >= m[0].length
							|| !m[x + i][y + j]) {
						return n2 - 1;
					}
				}
			}
		}
		return n;
	}
}