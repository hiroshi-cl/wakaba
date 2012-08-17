package com.github.hiroshi_cl.wakaba.v2010.lib.search.graph;

import java.util.ArrayList;

/*
 * shortestPath(from)は、負辺を含むグラフの、fromからの全点間最短距離を求める。
 * これを実行した後は、すべての頂点のweightは、始点からの最短距離に置き換わっている。
 * グラフ中に、負閉路がある場合は、falseを返す。
 *  
 *  O(E V)
 * 
 * verified : pku1364,pku3259
 */
class BellmanFord {
	static final int INF = 1 << 28;
	V[] vs;

	BellmanFord(int n) {
		vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V();
	}

	void make(int from, int to, int weight) {
		vs[from].es.add(new E(vs[to], weight));
	}

	boolean shortestPath(int from) {
		for (V v : vs) {
			v.weight = INF;
			v.bef = null;
		}
		vs[from].weight = 0;
		int n = vs.length;
		boolean negCyc = false;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (E e : vs[i].es) {
					if (e.to.weight > vs[i].weight + e.weight) {
						e.to.weight = vs[i].weight + e.weight;
						e.to.bef = vs[i];
						if (k == n - 1) {
							e.to.weight = Integer.MIN_VALUE;
							negCyc = true;
						}
					}
				}
			}
		}
		return !negCyc;
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int id;
		int weight;
		V bef;
	}

	class E {
		V to;
		int weight;

		E(V to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
}