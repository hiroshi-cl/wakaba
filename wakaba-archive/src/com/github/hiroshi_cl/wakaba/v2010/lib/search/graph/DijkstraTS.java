package com.github.hiroshi_cl.wakaba.v2010.lib.search.graph;

import java.util.*;

// with TreeSet
// - O(E log V)
class DijkstraTS {

	static final double INF = 1e30;

	public void run() {
		int N = 100;

		// Vertexを作る goalならtrue
		V[] vs = new V[N];
		for (int i = 0; i < N; i++)
			vs[i] = new V(i == N - 1);

		// Edgeを張る
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (i != j)
					vs[i].add(new E(vs[j], 1));

		double ret = INF;
		NavigableSet<V> set = new TreeSet<V>();

		// startをsetする
		vs[0].cost = 0;
		set.add(vs[0]);

		// 探索する ad hoc な変更不要
		while (!set.isEmpty()) {
			V v = set.pollFirst();
			if (v.isGoal) {
				ret = v.cost;
				break;
			}
			for (E e : v) {
				if (v.cost + e.cost < e.to.cost) {
					// decreaseKey(e.to, v.cost + e.cost)
					set.remove(e.to);
					e.to.cost = v.cost + e.cost;
					set.add(e.to);
				}
			}
		}

		// 出力する
		System.out.println(ret);
	}

	int nid = 0; // 反対称律を満たすために重要!

	class V extends ArrayList<E> implements Comparable<V> {
		double cost = INF;
		final boolean isGoal;
		final int id = nid++;// 反対称律を満たすために重要!

		public V(boolean isGoal) {
			this.isGoal = isGoal;
		}

		@Override
		public int compareTo(V o) {
			return cost == o.cost ? id - o.id : Double.compare(cost, o.cost);
		}
	}

	class E {
		final double cost;
		final V to;

		public E(V to, double cost) {
			this.cost = cost;
			this.to = to;
		}
	}
}


/*
その問題本当にグラフの多重化が必要ですか？
狭義のDPでは駄目なんですか？

変数が多くてメモリがぎりぎりになるときはProrityQueueバージョンが非常に遅くなる。
*/