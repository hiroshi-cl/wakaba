package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
import static java.lang.Math.min;
public class Hotel_new {
	public int marketCost(int minCustomers, int[] customers, int[] cost) {
		int[] dp = new int[minCustomers + 100];
		int len = customers.length;
		for (int i = 100; i < minCustomers + 100; i++) {
			dp[i] = INF;
			for (int j = 0; j < len; j++) {
				dp[i] = min(dp[i], dp[i - customers[j]] + cost[j]);
			}
		}
		return dp[minCustomers + 100 - 1];
	}
	int INF = 1 << 29;
}
// Powered by FileEdit
// Powered by CodeProcessor
