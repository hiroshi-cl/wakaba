package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

/*
 * Dinic のアルゴリズムにより、最大流を求める。
 * bfsで、グラフを層状にし、それに従って、流せなくなるまでdfsでフローを流しまくる。
 * これを、bfsにおける終点までのパスがなくなるまで繰り返す。
 * 繰り返し毎に、終点までのパスが1以上長くなることが保証されるので、
 * O(V^2 E)
 *
 * verified : pku3469 (15391MS)
 */
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Collections.*;
import static java.util.Arrays.*;

class Dinic {
	static final int INF = Integer.MAX_VALUE;
	int n;
	V s, t;
	V[] vs;

	public Dinic(int n, int s, int t, int[] from, int[] to, int[] cap) {
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

	public Dinic(int n, int s, int t) {
		this.n = n;
		this.vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
		this.s = vs[s];
		this.t = vs[t];
	}

	public Dinic(int n) {
		this.n = n;
		this.vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
	}

	void make(int from, int to, int cap) {
		E e = new E(vs[to], cap);
		E r = new E(vs[from], 0);
		e.rev = r;
		r.rev = e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}

	int maxflow(int s, int t) {
		this.s = vs[s];
		this.t = vs[t];
		return maxflow();
	}

	int maxflow() {
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

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int level;
		int p; // bfs時の、チェック用
		int i; // esのid
	}

	class E {
		E rev;
		V to;
		int cap;

		E(V to, int cap) {
			this.to = to;
			this.cap = cap;
		}
	}
}