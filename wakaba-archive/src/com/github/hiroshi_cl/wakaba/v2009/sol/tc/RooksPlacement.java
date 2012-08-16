package com.github.hiroshi_cl.wakaba.v2009.sol.tc;
import static java.util.Arrays.fill;
public class RooksPlacement {
	long[][][] dp;
	int MOD = 1000001;
	public int countPlacements(int X, int Y, int K) {
		dp = new long[X + 1][Y + 1][K + 1];
		for (int i = 0; i <= X; i++)
			for (int j = 0; j <= Y; j++) {
				fill(dp[i][j], -1);
				dp[i][j][0] = 1;
			}
		return (int) get(X, Y, K);
	}
	long get(int x, int y, int k) {
		if(x<0||y<0||k<0)return 0;
		if (dp[x][y][k] >= 0) return dp[x][y][k];
		return dp[x][y][k] = (
					  get(x - 1, y, k)
					+ y * get(x - 1, y - 1, k - 1) % MOD
					+ y	* (x - 1) * get(x - 2, y - 1, k - 2) % MOD
					+ y * (y - 1) / 2 * get(x - 1, y - 2, k - 2) % MOD
				) % MOD;
	}
}
