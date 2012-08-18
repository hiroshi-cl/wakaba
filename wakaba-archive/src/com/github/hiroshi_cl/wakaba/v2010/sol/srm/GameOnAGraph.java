package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

public class GameOnAGraph {
	static final int MOD = 1000 * 1000 * 1000 + 7;
	int M;

	public int[] getMarks(String[] adj, String colors, String marks, int N) {
		M = adj.length;
		char[] cs = colors.toCharArray();

		long[][] m1 = new long[M][M];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < M; j++)
				m1[i][j] = (adj[i].charAt(j) == '1' && cs[i] == 'B' ? 1 : 0);
		for (int i = 0; i < M; i++)
			m1[i][i] = (cs[i] == 'W' ? 1 : 0);

		long[][] m2 = new long[M][M];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < M; j++)
				m2[i][j] = (adj[i].charAt(j) == '1' && cs[i] == 'W' ? 1 : 0);
		for (int i = 0; i < M; i++)
			m2[i][i] = (cs[i] == 'B' ? 1 : 0);

		long[][] m = new long[M][M];
		for (int i = 0; i < M; i++)
			m[i][0] = marks.charAt(i) - '0';
		m = mul(pow(mul(m2, m1), N / 2), m);
		if (N % 2 == 1)
			m = mul(m1, m);

		int[] ret = new int[M];
		for (int i = 0; i < M; i++)
			ret[i] = (int) m[i][0];

		return ret;
	}

	long[][] E() {
		long[][] ret = new long[M][M];
		for (int i = 0; i < M; i++)
			ret[i][i] = 1;
		return ret;
	}

	long[][] pow(long[][] m, int n) {
		if (n == 0)
			return E();
		else if (n == 1)
			return m;
		long[][] m2 = pow(m, n / 2);
		m2 = mul(m2, m2);
		if (n % 2 == 1)
			m2 = mul(m2, m);
		return m2;
	}

	long[][] mul(long[][] m1, long[][] m2) {
		long[][] ret = new long[M][M];
		for (int i = 0; i < M; i++)
			for (int j = 0; j < M; j++)
				for (int k = 0; k < M; k++)
					ret[i][j] = (ret[i][j] + m1[i][k] * m2[k][j]) % MOD;
		return ret;
	}
}