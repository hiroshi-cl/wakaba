package com.github.hiroshi_cl.wakaba.v2010.lib.graph.flow;

import static java.util.Arrays.fill;

/*
- 入力 : boolean[][] graph
- 出力 : 最大マッチング (matchに右側の節点に対応する左側の節点の番号が代入される)

- O(V(V+E)).
*/

public class BipartiteMatchingA {
	int bipartiteMatching(boolean[][] graph) {
		int n = graph.length;
		if (n == 0)
			return 0;
		int m = graph[0].length;
		int[] match = new int[m];
		fill(match, -1);
		int res = 0;
		for (int i = 0; i < n; i++) {
			boolean[] visited = new boolean[m];
			if (go(i, graph, visited, match))
				res++;
		}
		// matchには、右側の節点に対応する、左側の節点の番号が格納される。
		return res;
	}

	boolean go(int v, boolean[][] graph, boolean[] visited, int[] match) {
		int m = graph[0].length;
		for (int i = 0; i < m; i++)
			if (!visited[i] && graph[v][i] && match[i] == -1) {
				visited[i] = true;
				match[i] = v;
				return true;
			}
		for (int i = 0; i < m; i++)
			if (!visited[i] && graph[v][i]) {
				visited[i] = true;
				if (go(match[i], graph, visited, match)) {
					match[i] = v;
					return true;
				}
			}
		return false;
	}
}
