package com.github.hiroshi_cl.wakaba.v2009.lib.dp;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.util.Random;
/*
 * Longest Increasing Sequenceの長さを求める。ここでは、広義に増加する列を考えている。
 * 普通にDPだとO(n^2).
 * バイナリサーチを使って、O(n log(n)).
 *
 * verified : 一応、自前でverified.
 */
public class LIS {
	static final int INF = Integer.MAX_VALUE;

	int lis(int[] as) {
		int n = as.length;
		int[] dp = new int[n];
		int res = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (as[j] <= as[i]) {
					dp[i] = max(dp[i], dp[j] + 1);
				}
			}
			res = max(res, dp[i]);
		}
		return res + 1;
	}

	int lis_bin(int[] as) {
		int n = as.length;
		int[] mins = new int[n];// 長さjを実現する最後の数のうち最小のもの。
		fill(mins, INF);
		int[] dp = new int[n];
		int res = 0;
		for (int i = 0; i < n; i++) {
			int l = -1, r = n, mid = n / 2;
			do {
				mid = (l + r) / 2;
				if (mins[mid] <= as[i]) {
					l = mid;
				} else {
					r = mid;
				}
			} while (r - l > 1);
			dp[i] = r;
			mins[r] = min(mins[r], as[i]);
			res = max(res, r);
		}
		return res + 1;
	}
}
