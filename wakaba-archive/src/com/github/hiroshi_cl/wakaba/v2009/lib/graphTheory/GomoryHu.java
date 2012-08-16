package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.*;
import static java.lang.Math.*;

/*
 * GomoryHu木 は、与えられたグラフに対し求まる、頂点集合を同じくする木で、
 * 任意の２点間の最小カットが、元のグラフと等しいようなもの。
 * 	weightに２点間の最小カットが格納される。
 * 構築:O(n maxflow) 応答:O(1)
 * 
 * ※ build()を忘れないこと。
 * 
 * verified: 自前
 */
public class GomoryHu {
	static final int INF = Integer.MAX_VALUE;
	int n;
	V[] vs;
	V s, t;
	int[] parent;
	int[][] weight;

	public GomoryHu(int n) {
		this.n = n;
		vs = new V[n];
		parent = new int[n];
		weight = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				weight[i][j] = INF;
		for (int i = 0; i < n; i++)
			vs[i] = new V();
	}

	void build() {
		for (int i = 1; i < n; i++) {
			int flow = maxflow(i, parent[i]);
			cut(s);
			for (int j = i + 1; j < n; j++)
				if (vs[j].b && parent[i] == parent[j])
					parent[j] = i;
			weight[i][parent[i]] = weight[parent[i]][i] = flow;
			for (int j = 0; j < i; j++)
				weight[i][j] = weight[j][i] = min(flow, weight[parent[i]][j]);
		}
	}

	void make(int from, int to, int cap) {
		E e = new E(vs[to], cap);
		E r = new E(vs[from], 0);
		e.rev = r;
		r.rev = e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}

	int maxflow(int source, int sink) {
		s = vs[source];
		t = vs[sink];
		if (s == t)
			return INF;
		init();
		for (int p = 1, flow = 0;; p++) {
			Queue<V> q = new LinkedList<V>();
			s.p = p;
			s.level = 0;
			q.offer(s);
			while (!q.isEmpty()) {
				V v = q.poll();
				v.i = v.es.size() - 1;
				for (E e : v.es)
					if (e.to.p < p && e.cap > 0) {
						e.to.level = v.level + 1;
						e.to.p = p;
						q.offer(e.to);
					}
			}
			if (t.p < p)
				return flow;
			for (int f; (f = dfs(s, INF)) > 0;)
				flow += f;
		}
	}

	int dfs(V v, int f) {
		if (v == t)
			return f;
		for (; v.i >= 0; v.i--) {
			E e = v.es.get(v.i);
			if (e.to.level > v.level && e.cap > 0) {
				int min = dfs(e.to, min(e.cap, f));
				if (min > 0) {
					e.cap -= min;
					e.rev.cap += min;
					return min;
				}
			}
		}
		return 0;
	}

	void cut(V s) {
		if (!s.b) {
			s.b = true;
			for (E e : s.es)
				if (e.cap > 0)
					cut(e.to);
		}
	}

	void init() {
		for (V v : vs) {
			v.init();
			for (E e : v.es)
				e.cap = e.CAP;
		}
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int level;
		int p; // bfs時の、チェック用
		int i; // esのid
		boolean b = false; // sと連結かどうか

		void init() {
			level = p = i = 0;
			b = false;
		}
	}

	class E {
		E rev;
		V to;
		final int CAP;
		int cap;

		E(V to, int cap) {
			this.to = to;
			CAP = cap;
		}
	}

	static void debug(Object... o) {
		System.err.println(Arrays.deepToString(o));
	}

	static long st;

	static void showTime() {
		System.err.println(System.currentTimeMillis() - st);
		st = System.currentTimeMillis();
	}

	public static void main(String[] args) {
		debug("start!");
		st = System.currentTimeMillis();
		int n = 100;
		int max = 100000;
		int[][] wss = new int[n][n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				// if (r.nextBoolean()) {
				int w = r.nextInt(max) + 1;
				wss[i][j] = wss[j][i] = w;
				// }
			}
		}
		GomoryHu gh = new GomoryHu(n);
		showTime();
		debug("making");
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (wss[i][j] > 0)
					gh.make(i, j, wss[i][j]);
		showTime();
		debug("building");
		long ct = System.currentTimeMillis();
		gh.build();
		System.out.println(System.currentTimeMillis() - ct);
		Dinic din = new Dinic(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (wss[i][j] > 0) {
					din.make(i, j, wss[i][j]);
				}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j) {
					int flow = din.maxflow(i, j);
					if (gh.weight[i][j] != flow) {
						System.out.println("failed:" + i + "," + j);
						System.out.println(gh.weight[i][j]);
						System.out.println(flow);
					}
				}
			}
		}
	}
}
