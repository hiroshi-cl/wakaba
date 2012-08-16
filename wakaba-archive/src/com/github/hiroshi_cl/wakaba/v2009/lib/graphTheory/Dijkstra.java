package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.ArrayList;
import java.util.PriorityQueue;

/*
 * dijkstra(from,to)は、各vertex に、fromからの最短距離の重みを割り当てる。
 * また、最短路木における、自分の前のvertexをbefに割り当てる。
 * to の重みを返す。
 * 
 * ※正しく　O(E log V) になっていないと思われる。
 * 厳しい条件でチェックした方がいい。
 * 
 * verified : uva341
 */

public class Dijkstra {
	V[] vs;

	Dijkstra(V[] vs) {
		this.vs = vs;
		for (V v : vs) {
			v.weight = Integer.MAX_VALUE;
			v.bef = null;
		}
	}

	int dijkstra(V from, V to) {
		PriorityQueue<E> q = new PriorityQueue<E>();
		from.weight = 0;
		q.offer(new E(null, from, 0));
		while (!q.isEmpty()) {
			V v = q.poll().to;
			for (E e : v) {
				if (e.to.weight > v.weight + e.weight) {
					e.to.weight = v.weight + e.weight;
					e.to.bef = v;
					q.offer(e);
				}
			}
		}
		return to.weight;
	}
}

class V extends ArrayList<E> {
	int id;
	int weight;
	V bef;

	V(int id) {
		this.id = id;
	}
}

class E implements Comparable<E> {
	V from, to;
	int weight;

	E(V from, V to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int compareTo(E o) {
		return weight - o.weight;
	}
}