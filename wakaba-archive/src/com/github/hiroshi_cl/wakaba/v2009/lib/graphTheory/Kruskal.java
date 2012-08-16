package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.ArrayList;
import static java.util.Arrays.*;
import static java.util.Collections.*;

/*
 * mst()は、Kruskalのアルゴリズム（重みの小さい辺から貪欲に取る）により、
 * 無向グラフのMSTをvsにつくり、連結グラフが作れたかどうかを返す。実行後、クラスの、
 * edgeに、使った枝の本数
 * weightに、使った枝の重みの合計  が入る。
 *
 * verified : pku1287,pku3723
 */
class Kruskal {
	int n;
	V[] vs;
	ArrayList<E> es;
	int edge;
	int weight;

	Kruskal(int n) {
		this.n = n;
		vs = new V[n];
		for (int i = 0; i < n; i++)
			vs[i] = new V(i);
		es = new ArrayList<E>();
	}

	void make(int from, int to, int weight) {
		es.add(new E(vs[from], vs[to], weight));
	}

	boolean mst() {
		init();
		UnionFind uf = new UnionFind(n);
		for (E e : es) {
			if (!uf.find(e.from.id, e.to.id)) {
				uf.union(e.from.id, e.to.id);
				e.from.es.add(e);
				edge++;
				weight += e.weight;
			}
		}
		return edge == n - 1;
	}

	void init() {
		sort(es);
		for (V v : vs)
			v.init();
		edge = 0;
		weight = 0;
	}

	class UnionFind {
		int[] tree;

		UnionFind(int size) {
			tree = new int[size];
			fill(tree, -1);
		}

		void union(int x, int y) {
			x = root(x);
			y = root(y);
			if (x == y)
				return;
			if (tree[x] > tree[y]) {
				x ^= y;
				y ^= x;
				x ^= y;
			}
			tree[x] += tree[y];
			tree[y] = x;
		}

		boolean find(int x, int y) {
			return root(x) == root(y);
		}

		int root(int x) {
			if (tree[x] < 0)
				return x;
			else
				return tree[x] = root(tree[x]);
		}
	}

	class V {
		ArrayList<E> es = new ArrayList<E>();
		int id;
		int weight;

		V(int id) {
			this.id = id;
		}

		void init() {
			es = new ArrayList<E>();
			weight = 0;
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

	public static void main(String[] args) {// pku1287
		java.util.Scanner sc = new java.util.Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0)
				return;
			Kruskal kr = new Kruskal(n);
			for (int i = sc.nextInt(); i-- > 0;)
				kr.make(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt());
			kr.mst();
			System.out.println(kr.weight);
		}
	}
}