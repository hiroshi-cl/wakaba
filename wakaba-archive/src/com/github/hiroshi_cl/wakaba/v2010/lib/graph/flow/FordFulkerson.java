package com.github.hiroshi_cl.wakaba.v2010.lib.graph.flow;

import java.util.*;
import static java.lang.Math.*;

/*
 FordFulkerson のalgorithmによって、最大流を求める。
 流せなくなるまで、ひたすらdfsで見つけたrouteに流しまくる。
 O(E maxflow)

 2部マッチングなど、流量が小さいものは、これを使うのが適切。
 一方にsource、もう一方にsinkをおいて、最大流を求めればよい。

 verified : pku1273,pku1274
 */

public class FordFulkerson {
	static final int INF = Integer.MAX_VALUE;
	int n;
	V s, t;
	V[] vs;

	FordFulkerson(int n, int s, int t, int[] from, int[] to, int[] cap) {
		this.n = n;
		vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
		this.s = vs[s];
		this.t = vs[t];
		for (int i = 0; i < from.length; i++) {
			make(from[i], to[i], cap[i]);
		}
	}

	FordFulkerson(int n, int s, int t) {
		this.n = n;
		vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
		this.s = vs[s];
		this.t = vs[t];
	}

	void make(int from, int to, int cap) {
		E e = new E(vs[from], vs[to], cap);
		E r = new E(vs[to], vs[from], 0);
		e.rev = r;
		r.rev = e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}

	int maxflow() {
		for (int p = 1, flow = 0;; p++) {
			s.p = p;
			int f = dfs(s, INF, p);
			if (f == 0)
				return flow;
			flow += f;
		}
	}

	int dfs(V s, int f, int p) {
		if (s == t)
			return f;
		for (E e : s.es) {
			if (e.to.p < p && e.cap > 0) {
				e.to.p = p;
				int min = dfs(e.to, min(e.cap, f), p);
				if (min > 0) {
					e.cap -= min;
					e.rev.cap += min;
					return min;
				}
			}
		}
		return 0;
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		E bef;
		int p;
	}

	class E {
		E rev;
		V from, to;
		int cap;

		E(V from, V to, int cap) {
			this.from = from;
			this.to = to;
			this.cap = cap;
		}
	}
}