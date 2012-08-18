package com.github.hiroshi_cl.wakaba.v2010.sol.d2005;

import java.util.*;
import java.io.*;
import java.math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class D {
	public static void main(String[] args) throws Exception {
		try {
			System.setIn(new FileInputStream(new File("D.in")));
			System.setOut(new PrintStream("D.out"));
		} catch (Exception e) {
		}
		new D().run();
	}

	static void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	int INF = 1 << 20;
	double EPS = 1e-9;

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt(), m = sc.nextInt(), p = sc.nextInt(), a = sc
					.nextInt() - 1, b = sc.nextInt() - 1;
			if (n + m + p == 0)
				break;
			double[][] f = new double[m][m];
			for (int i = 0; i < m; i++)
				fill(f[i], INF);
			double[] ts = new double[n];
			for (int i = 0; i < n; i++) {
				ts[i] = sc.nextInt();
			}
			sort(ts);
			for (int i = 0; i < p; i++) {
				int x = sc.nextInt() - 1, y = sc.nextInt() - 1;
				double z = sc.nextDouble();
				f[x][y] = z;
				f[y][x] = z;
			}
			// for(int i=0;i<m;i++)
			// debug(f[i]);
			double[][][] dp = new double[n + 1][m][1 << n];
			for (int i = 0; i < n + 1; i++)
				for (int j = 0; j < m; j++)
					fill(dp[i][j], INF);
			dp[0][a][0] = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					for (int k = 0; k < (1 << n); k++) {
						if (dp[i][j][k] < INF) {
							for (int j2 = 0; j2 < m; j2++) {
								if (j == j2)
									continue;
								if (f[j][j2] < INF) {
									for (int l = 0; l < n; l++) {
										if ((k & (1 << l)) == 0) {
											dp[i + 1][j2][k | (1 << l)] = min(
													dp[i + 1][j2][k | (1 << l)],
													dp[i][j][k] + f[j][j2]
															/ ts[l]);
										}
									}
								}
							}
						}
					}
				}
			}
			double res = INF;
			for (int i = 0; i < n + 1; i++) {
				for (int k = 0; k < (1 << n); k++) {
					res = min(res, dp[i][b][k]);
				}
				// for(int j=0;j<m;j++)
				// debug(i,j,dp[i][j]);
			}
			System.out.println(res == INF ? "Impossible" : res);
		}
	}

	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}
