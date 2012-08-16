package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.PriorityQueue;

/*
 * メソッドMST()はPrimのアルゴリズムによって、グラフのMSTを返す。
 * グラフが連結でない場合は、nullを返す。
 * 同時に、sumに、枝の重みの合計を格納する。
 * 
 * verified : pku2560
 */
public class Prim {
	V[] vs;
	int n;
	int sum;

	Prim(V[] vs) {
		this.vs = vs;
		n = vs.length;
	}

	E[] MST() {
		sum = 0;
		E[] res = new E[n - 1];
		boolean[] check = new boolean[n];
		PriorityQueue<E> q = new PriorityQueue<E>();
		q.offer(new E(null, vs[0], 0));
		check[0] = true;
		int k = 0;
		while (!q.isEmpty()) {
			E e = q.poll();
			if (!check[e.to.id]) {
				check[e.to.id] = true;
				sum += e.weight;
				res[k++] = e;
			}
			for (E ne : e.to) {
				if (!check[ne.to.id])
					q.offer(ne);
			}
		}
		return k == n - 1 ? res : null;
	}
}
