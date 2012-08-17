package com.github.hiroshi_cl.wakaba.v2010.lib.search.graph;

import java.util.*;

// with PriorityQueue
// - O(E log E)
class DijkstraPQ {
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
		Queue<E> que = new PriorityQueue<E>();

		// startをsetする
		vs[0].cost = 0;
		que.offer(new E(vs[0], 0));

		// 探索する ad hoc な変更不要
		while (!que.isEmpty()) {
			V v = que.poll().to;
			if (v.isGoal) {
				ret = v.cost;
				break;
			}
			for (E e : v) {
				if (v.cost + e.cost < e.to.cost) {
					e.to.cost = v.cost + e.cost;
					que.offer(new E(e.to, e.to.cost));
				}
			}
		}

		// 出力する
		System.out.println(ret);
	}

	class V extends ArrayList<E> {
		double cost = INF;
		final boolean isGoal;

		public V(boolean isGoal) {
			this.isGoal = isGoal;
		}
	}

	class E implements Comparable<E> {
		final double cost;
		final V to;

		public E(V to, double cost) {
			this.cost = cost;
			this.to = to;
		}

		@Override
		public int compareTo(E o) {
			return Double.compare(cost, o.cost);
		}
	}
}

/*
その問題本当にグラフの多重化が必要ですか？
狭義のDPでは駄目なんですか？

変数が多くてメモリがぎりぎりになるときはProrityQueueバージョンが非常に遅くなる。
*/