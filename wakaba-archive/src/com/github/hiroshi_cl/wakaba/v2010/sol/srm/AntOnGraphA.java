package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import static java.util.Arrays.*;
import static java.lang.Math.*;

public class AntOnGraphA {
	int n;
	int[][] tmp1;
	long[][] costs;

	public String maximumBonus(String[] p0, String[] p1, String[] p2,
			int stepsPerSecond, int timeLimit) {
		n = p0.length;
		tmp1 = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int c = Integer.valueOf(p0[i].charAt(j) + "" + p1[i].charAt(j)
						+ "" + p2[i].charAt(j));
				tmp1[i][j] = c;
			}
		costs = new long[n][n];
		for (int i = 0; i < n; i++) {
			long[][] dp = new long[stepsPerSecond + 1][n];
			for (int j = 0; j <= stepsPerSecond; j++)
				fill(dp[j], -INF);
			dp[0][i] = 0;
			for (int j = 0; j < stepsPerSecond; j++) {
				for (int i2 = 0; i2 < n; i2++) {
					if (dp[j][i2] == -INF)
						continue;
					for (int j2 = 0; j2 < n; j2++) {
						if (tmp1[i2][j2] == 0)
							continue;
						dp[j + 1][j2] = max(dp[j + 1][j2], dp[j][i2]
								+ (tmp1[i2][j2] - 500));
					}
				}
			}
			for (int j = 0; j < n; j++)
				costs[i][j] = dp[stepsPerSecond][j];
		}
		long res = pow(costs, timeLimit)[0][1];
		return res < -INF / 2 ? "IMPOSSIBLE" : String.valueOf(res);
	}

	long INF = 1l << 60;

	long[][] pow(long[][] a, int b) {
		long[][] c = new long[n][n];
		for (int i = 0; i < n; i++)
			fill(c[i], -INF);
		while (b > 0) {
			if ((b & 1) == 1)
				c = mul(c, a);
			a = mul(a, a);
			b >>>= 1;
		}
		return c;
	}

	long[][] mul(long[][] mat1, long[][] mat2) {
		long[][] res = new long[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res[i][j] = max(mat1[i][j], mat2[i][j]);
				for (int k = 0; k < n; k++) {
					res[i][j] = max(res[i][j], mat1[i][k] + mat2[k][j]);
				}
			}
		}
		return res;
	}
}