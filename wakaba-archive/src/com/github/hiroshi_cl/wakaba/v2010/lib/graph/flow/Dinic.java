package com.github.hiroshi_cl.wakaba.v2010.lib.graph.flow;

import java.util.*;
import static java.lang.Math.*;

/*
 Dinic のアルゴリズムにより、最大流を求める。
 bfsで、グラフを層状にし、それに従って、流せなくなるまでdfsでフローを流しまくる。
 これを、bfsにおける終点までのパスがなくなるまで繰り返す。

 - make :
 - maxflow :

 - O(V^2 E) 繰り返し毎に終点までのパスが1以上長くなることが保証されるので
 */

public class Dinic {
	static final long INF = Integer.MAX_VALUE;
	int n;
	V s, t;
	V[] vs;

	public Dinic(int n) {
		this.n = n;
		this.vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
	}

	void make(int from, int to, long cap) {
		E e = new E(vs[to], cap);
		E r = new E(vs[from], 0); // 無向グラフの場合は、0 → cap とする。
		e.rev = r;
		r.rev = e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}

	long maxflow(int si, int ti) {
		init();// 一回だけなら必要ない.
		s = vs[si];
		t = vs[ti];
		long flow = 0;
		for (int p = 1;; p++) {
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
			for (long f; (f = dfs(s, INF)) > 0;)
				flow += f;
		}
	}

	long dfs(V v, long f) {
		if (v == t)
			return f;
		for (; v.i >= 0; v.i--) {
			E e = v.es.get(v.i);
			if (e.to.level > v.level && e.cap > 0) {
				long min = dfs(e.to, min(e.cap, f));
				if (min > 0) {
					e.cap -= min;
					e.rev.cap += min;
					return min;
				}
			}
		}
		return 0;
	}

	void init() {
		for (V v : vs) {
			v.level = 0;
			v.p = 0;
			v.i = 0;
			for (E e : v.es) {
				e.cap = e.CAP;
			}
		}
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
		long cap;
		final long CAP;

		E(V to, long cap) {
			this.to = to;
			this.cap = cap;
			CAP = cap;
		}
	}
}