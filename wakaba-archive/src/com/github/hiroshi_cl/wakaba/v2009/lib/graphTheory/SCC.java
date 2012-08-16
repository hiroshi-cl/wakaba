package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.ArrayList;

/*
 * 強連結成分分解
 * build()は、vsをトポロジカル順序に（閉路は無視して）並べ替えたものsortedを作り、
 * エッジの向きを逆にしたグラフを、その順に深さ優先探索することで、
 * 強連結成分を見つける。同じ成分に属する v の comp には同じ値が割り振られる。
 * nには、強連結成分の数が格納される。
 * buildDag()は、強連結成分を点としたグラフ(dag)をつくる。
 *
 * O(V+E)
 *
 * verified: pku2553
 */
class SCC {
	int n;
	V[] vs;
	V[] sorted;
	V[] dag;

	SCC(int k) {
		vs = new V[k];
		for (int i = 0; i < k; i++)
			vs[i] = new V();
	}

	void make(int from, int to) {
		vs[from].es.add(vs[to]);
		vs[to].rev.add(vs[from]);
	}

	void buildDag() {
		build();
		dag = new V[n];
		for (int i = 0; i < n; i++)
			dag[i] = new V();
		for (V v : vs) {
			for (V u : v.es) {
				if (v.comp != u.comp) {
					dag[v.comp].es.add(dag[u.comp]);
					dag[u.comp].rev.add(dag[v.comp]);
				}
			}
		}
	}

	void build() {
		init();
		n = vs.length;
		sorted = new V[n];
		for (V v : vs)
			if (!v.visit)
				dfs(v);
		for (V v : vs)
			v.visit = false;
		for (V u : sorted)
			if (!u.visit)
				dfsrev(u, n++);
	}

	void dfs(V v) {
		v.visit = true;
		for (V u : v.es)
			if (!u.visit)
				dfs(u);
		sorted[--n] = v;
	}

	void dfsrev(V v, int k) {
		v.visit = true;
		for (V u : v.rev)
			if (!u.visit)
				dfsrev(u, k);
		v.comp = k;
	}

	void init() {
		for (V v : vs)
			v.init();
	}

	class V {
		ArrayList<V> es = new ArrayList<V>();
		ArrayList<V> rev = new ArrayList<V>();
		boolean visit;
		int comp;

		void init() {
			visit = false;
			comp = 0;
		}
	}
}