package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.math.*;
import java.util.*;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class PeopleYouMayKnowA {
	int n;
	boolean[][] f;

	public int maximalScore(String[] friends, int A, int B) {
		n = friends.length;
		f = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (friends[i].charAt(j) == 'Y')
					f[i][j] = true;
		}
		int res = 0;
		int nn = 0, m = 0;
		ArrayList<Integer> Alist = new ArrayList<Integer>(), Blist = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (f[i][A] && f[i][B]) {
				res++;
			} else {
				if (f[i][A]) {
					nn++;
					Alist.add(i);
				}
				if (f[i][B]) {
					m++;
					Blist.add(i);
				}
			}
		}
		if (nn == 0 || m == 0)
			return res;
		boolean[][] graph = new boolean[nn][m];
		for (int i = 0; i < nn; i++)
			for (int j = 0; j < m; j++) {
				if (f[Alist.get(i)][Blist.get(j)])
					graph[i][j] = true;
			}
		return new BipartiteMatching().bipartiteMatching(graph) + res;
	}

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

	void debug(Object... os) {
	}
}