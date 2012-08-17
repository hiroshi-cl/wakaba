package com.github.hiroshi_cl.wakaba.v2010.lib.math.enumeration;

import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import java.math.*;

/*
 -[[oeis:A000055]]

 - 入力 : ノード数
 - 出力 : 木の個数

 - O(n^3).
 */

public class Trees {
	BigInteger count(int n) {
		if (n == 0)
			return ONE;
		BigInteger[] dp = new BigInteger[n + 1];
		fill(dp, ZERO);
		dp[1] = ONE;
		for (int m = 1; m < n; m++) {
			for (int k = 1; k <= m; k++) {
				BigInteger tmp = ZERO;
				for (int d = 1; d <= k; d++)
					if (k % d == 0) {
						tmp = tmp.add(dp[d].multiply(valueOf(d)));
					}
				dp[m + 1] = dp[m + 1].add(tmp.multiply(dp[m - k + 1]));
			}
			dp[m + 1] = dp[m + 1].divide(valueOf(m));
		}
		BigInteger res = dp[n];
		BigInteger tmp = ZERO;
		for (int i = 0; i <= n; i++) {
			tmp = tmp.subtract(dp[i].multiply(dp[n - i]));
		}
		if (n % 2 == 0)
			tmp = tmp.add(dp[n / 2]);
		return res.add(tmp.divide(valueOf(2)));
	}
}
