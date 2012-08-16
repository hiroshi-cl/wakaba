package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import static java.util.Arrays.fill;

/*
 *	bipartiteMathing(graph)は、この形であらわされた二部グラフの最大マッチングを求める。
 *	実行後、matchには、右側の節点に対応する、左側の節点の番号が格納される。
 *  対応する節点が存在しない場合は、-1が入る。
 *
 *	O(m n maxflow).
 *
 *  verified: TopCoder SRM358 Hard.
 */
public class BipartiteMatching {
	int n, m;
	boolean[][] graph;
	boolean[] visited;
	int[] match;

	int bipartiteMatching(boolean[][] graph) {
		n = graph.length;
		m = graph[0].length;
		this.graph = graph;
		match = new int[m];
		fill(match, -1);
		int res = 0;
		for (int i = 0; i < n; i++) {
			visited = new boolean[m];
			if (go(i))
				res++;
		}
		return res;
	}

	boolean go(int v) {
		for (int i = 0; i < m; i++)
			if (!visited[i] && graph[v][i] && match[i] == -1) {
				visited[i] = true;
				match[i] = v;
				return true;
			}
		for (int i = 0; i < m; i++)
			if (!visited[i] && graph[v][i]) {
				visited[i] = true;
				if (go(match[i])) {
					match[i] = v;
					return true;
				}
			}
		return false;
	}
}