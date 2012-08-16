package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

/*
 * 最大流をEdmond-Karpのアルゴリズムによって求める。
 * 流せなくなるまで、ひたすらbfsで見つけたルートに流しまくる。
 * O(V E^2)
 *
 * verified : pku1273
 */

import java.util.*;
import static java.util.Collections.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;

class EdmondsKarp {
	static final int INF = Integer.MAX_VALUE;
	V s, t;
	int n;
	V[] vs;

	public EdmondsKarp(int n, int s, int t, int[] from, int[] to, int[] cap) {
		this.n = n;
		this.vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
		this.s = vs[s];
		this.t = vs[t];
		for (int i = 0; i < from.length; i++) {
			make(from[i], to[i], cap[i]);
		}
	}

	EdmondsKarp(int n, int s, int t) {
		this.n = n;
		this.vs = new V[n];
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
			Queue<V> q = new LinkedList<V>();
			s.p = p;
			s.bef = null;
			q.offer(s);
			loop: while (!q.isEmpty()) {
				V v = q.poll();
				for (E e : v.es) {
					if (e.to.p < p && e.cap > 0) {
						e.to.p = p;
						e.to.bef = e;
						if (e.to == t)
							break loop;
						q.offer(e.to);
					}
				}
			}
			if (t.p < p)
				return flow;
			int min = INF;
			for (V v = t; v.bef != null; v = v.bef.from)
				min = min(min, v.bef.cap);
			for (V v = t; v.bef != null; v = v.bef.from) {
				v.bef.cap -= min;
				v.bef.rev.cap += min;
			}
			flow += min;
		}
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		E bef;
		int id;
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