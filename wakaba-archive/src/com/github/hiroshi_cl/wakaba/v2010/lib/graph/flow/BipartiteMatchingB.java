package com.github.hiroshi_cl.wakaba.v2010.lib.graph.flow;

// 実装例(短いバージョン)

public class BipartiteMatchingB {
	public int match(boolean[][] n, int src, int dst) {
		int ret = 0;
		while (match_sub(n, src, dst, new boolean[n[0].length]))
			ret++;
		return ret;
	}

	boolean match_sub(boolean[][] n, int i, int dst, boolean[] visited) {
		if (i == dst)
			return true;

		visited[i] = true;

		for (int j = 0; j < n[0].length; j++)
			if (n[i][j] && !visited[j]) {
				n[i][j] = false;
				n[j][i] = true;
				if (match_sub(n, j, dst, visited))
					return true;
				n[i][j] = true;
				n[j][i] = false;
			}

		return false;
	}
}
