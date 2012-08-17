package com.github.hiroshi_cl.wakaba.v2010.lib.math.sequence;

// - 分割数をメモ化再帰で計算する。memoにははじめ、すべて-1を割り当てておくこと。
// - n以下の分割数をすべて計算するのに、O(n^1.5) と思われる。
public class PartitionNumber {
	int partitionNumber(int n, int[] memo, int mod) {
		if (n == 0)
			return memo[n] = 1;
		if (memo[n] >= 0)
			return memo[n];
		memo[n] = 0;
		for (int k = 1; n - k * (3 * k - 1) / 2 >= 0; k++)
			memo[n] = (memo[n] + (k % 2 == 0 ? -1 : 1)
					* partitionNumber(n - k * (3 * k - 1) / 2, memo, mod))
					% mod;
		for (int k = 1; n - k * (3 * k + 1) / 2 >= 0; k++)
			memo[n] = (memo[n] + (k % 2 == 0 ? -1 : 1)
					* partitionNumber(n - k * (3 * k + 1) / 2, memo, mod))
					% mod;
		return memo[n] = (memo[n] + mod) % mod;
	}
}
