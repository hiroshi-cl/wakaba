package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class B {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("B.in")));
			System.setOut(new PrintStream("B.out"));
		} catch (Exception e) {
		}
		new B().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 29;
	double EPS = 1e-9;

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			int[][] xs = new int[n + 1][];
			int[][] ys = new int[n + 1][];
			int M = sc.nextInt();
			xs[0] = new int[M];
			ys[0] = new int[M];
			for (int j = 0; j < M; j++) {
				xs[0][j] = sc.nextInt();
				ys[0][j] = sc.nextInt();
			}
			for (int i = 1; i <= n; i++) {
				int m = sc.nextInt();
				xs[i] = new int[m];
				ys[i] = new int[m];
				for (int j = 0; j < m; j++) {
					// debug(i,j,m);
					xs[i][j] = sc.nextInt();
					ys[i][j] = sc.nextInt();
				}
			}
			int[][] ls = new int[2][M - 1];
			int[][] gs = new int[2][M - 2];
			for (int j = 0; j < M - 1; j++) {
				ls[0][j] = abs(xs[0][j] - xs[0][j + 1])
						+ abs(ys[0][j] - ys[0][j + 1]);
				ls[1][M - j - 2] = ls[0][j];
			}
			for (int j = 0; j < M - 2; j++) {
				int x1 = xs[0][j + 1] - xs[0][j], y1 = ys[0][j + 1] - ys[0][j], x2 = xs[0][j + 2]
						- xs[0][j + 1], y2 = ys[0][j + 2] - ys[0][j + 1];
				gs[0][j] = signum(x1 * y2 - x2 * y1);
				gs[1][M - j - 3] = gs[0][j] * -1;
			}
			// debug(ls,gs);
			for (int i = 1; i <= n; i++) {
				int m = xs[i].length;
				int[] nls = new int[m - 1], ngs = new int[m - 2];
				if (m == M) {
					for (int j = 0; j < m - 1; j++) {
						nls[j] = abs(xs[i][j] - xs[i][j + 1])
								+ abs(ys[i][j] - ys[i][j + 1]);
					}
					for (int j = 0; j < m - 2; j++) {
						int x1 = xs[i][j + 1] - xs[i][j], y1 = ys[i][j + 1]
								- ys[i][j], x2 = xs[i][j + 2] - xs[i][j + 1], y2 = ys[i][j + 2]
								- ys[i][j + 1];
						ngs[j] = signum(x1 * y2 - x2 * y1);
					}
					// debug(nls,ngs);
					for (int j = 0; j < 2; j++) {
						boolean ok = true;
						for (int k = 0; k < m - 1; k++) {
							if (nls[k] != ls[j][k])
								ok = false;
						}
						for (int k = 0; k < m - 2; k++) {
							if (ngs[k] != gs[j][k])
								ok = false;
						}
						if (ok) {
							System.out.println(i);
							break;
						}
					}
				}

			}
			System.out.println("+++++");
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}
