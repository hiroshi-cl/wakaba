package com.github.hiroshi_cl.wakaba.v2010.lib.dp;
import static java.lang.Math.*;
/*
 * Longest Common Subsequenceの、長さを求める。
 * verified : pku1458,pku1159
 */
public class LCS {
	char[] as, bs;

	int calc() {
		int m = as.length, n = bs.length;
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int match = as[i - 1] == bs[j - 1] ? 1 : 0;
				dp[i][j] = max(dp[i - 1][j - 1] + match,
						max(dp[i - 1][j], dp[i][j - 1]));
			}
		}
		return dp[m][n];
	}
}
