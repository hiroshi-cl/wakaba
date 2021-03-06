package com.github.hiroshi_cl.wakaba.v2010.lib.graph.flow;

import java.util.*;
import static java.lang.Math.*;

/*decreaseKeyを使ったDikstra使用版

- コンストラクタ : 頂点数を指定
- make(int from, int to, int cap, int cost) : from から to へ最大流量 cap で、コスト cost の辺を作る
- minCostFlow(int from,int to,int flow) : from から to へ 流量flow のフローを流すときの最小コスト。流せない場合 -1 を返す。

- O(V^2 U C).  U:容量の合計, C:コストの合計.
*/

public class PrimalDualC {
	static final int INF = 1 << 28;
	int n;
	V[] vs;
	V s, t;

	PrimalDualC(int n) {
		vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V(i);
	}

	void make(int from, int to, int cap, int cost) {
		E e = new E(vs[to], cap, cost);
		E r = new E(vs[from], 0, -cost);
		e.rev = r;
		r.rev = e;
		vs[from].es.add(e);
		vs[to].es.add(r);
	}

	int minCostFlow(int from, int to, int flow) {
		s = vs[from];
		t = vs[to];
		int cost = 0;
		while (flow > 0) {
			for (V v : vs)
				v.cost = INF;
			TreeSet<V> q = new TreeSet<V>();
			s.cost = 0;
			s.bef = null;
			t.bef = null;
			q.add(s);
			while (!q.isEmpty()) {
				V v = q.pollFirst();
				if (v == t)
					break;
				for (E e : v.es) {
					if (e.cap > 0 && e.to.cost > e.cost + v.cost) {
						q.remove(e.to);
						e.to.cost = e.cost + v.cost;
						e.to.bef = e;
						q.add(e.to);
					}
				}
			}
			if (t.bef == null)
				return -1;
			int min = flow;
			for (E e = t.bef; e != null; e = e.rev.to.bef) {
				min = min(min, e.cap);
			}
			for (E e = t.bef; e != null; e = e.rev.to.bef) {
				e.cap -= min;
				cost += e.cost * min;
				e.rev.cap += min;
			}
			flow -= min;
		}
		return cost;
	}

	class V implements Comparable<V> {
		int id;
		ArrayList<E> es = new ArrayList<E>();
		E bef;
		int cost;

		V(int id) {
			this.id = id;
		}

		public int compareTo(V o) {
			return o.cost == cost ? id - o.id : cost - o.cost;
		}
	}

	class E {
		E rev;
		V to;
		int cap;
		int cost;

		E(V to, int cap, int cost) {
			this.to = to;
			this.cap = cap;
			this.cost = cost;
		}
	}
}
