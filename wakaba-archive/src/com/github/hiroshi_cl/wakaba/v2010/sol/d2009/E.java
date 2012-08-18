package com.github.hiroshi_cl.wakaba.v2010.sol.d2009;

import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class E {
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("E"));
			System.setOut(new PrintStream("E.out"));
		} catch (Exception e) {
		}
		for (;;) {
			int m = sc.nextInt(), n = sc.nextInt();
			if (m + n == 0)
				return;
			int[] bs = new int[m];
			int[] rs = new int[n];
			for (int i = 0; i < m; i++)
				bs[i] = sc.nextInt();
			for (int i = 0; i < n; i++)
				rs[i] = sc.nextInt();
			boolean[][] graph = new boolean[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (gcd(bs[i], rs[j]) > 1)
						graph[i][j] = true;
				}
			}
			System.out.println(new B().matching(graph));
		}
	}

	class B {
		int n, m;
		boolean[] visited;
		boolean[][] graph;
		int[] match;

		int matching(boolean[][] graph) {
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

	int gcd(int x, int y) {
		return y == 0 ? x : gcd(y, x % y);
	}

	public static void main(String[] args) {
		new E().run();
	}
}
