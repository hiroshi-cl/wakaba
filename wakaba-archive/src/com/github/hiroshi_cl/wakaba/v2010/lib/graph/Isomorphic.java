package com.github.hiroshi_cl.wakaba.v2010.lib.graph;

import java.util.*;

/* グラフの同型性判定
 * 頂点を次数が小さい方から並べ、次数が同じものどうしに印をつける
 * それ以前につけた印との対応関係を見ながら、再帰的に調べていく
 */
class Isomorphic {
	boolean isomorphic(V[] hs, V[] gs) {
		Arrays.sort(hs);
		Arrays.sort(gs);
		return f(hs, gs, hs.length, 0);
	}

	boolean f(V[] hs, V[] gs, int n, int hi) {
		int gi;
		if (hi == n)
			return true;
		for (gi = 0; gs[gi].size() < hs[hi].size(); gi++)
			;
		L: for (; gs[gi].size() == hs[hi].size(); gi++) {
			if (gs[gi].id != -1)
				continue L;
			gs[gi].id = hs[hi].id = hi;
			for (V g : gs[gi].next)
				if (!hs[hi].containEdge(g)) {
					gs[gi].id = hs[hi].id = -1;
					continue L;
				}
			for (V h : hs[hi].next)
				if (!gs[gi].containEdge(h)) {
					gs[gi].id = hs[hi].id = -1;
					continue L;
				}
			boolean r = f(hs, gs, n, hi + 1);
			gs[gi].id = hs[hi].id = -1;
			if (r)
				return r;
		}
		return false;
	}

	class V implements Comparable<V> {
		public int compareTo(V o) {
			return size() - o.size();
		}

		int size() {
			return next.size();
		}

		boolean containEdge(V x) {
			for (V v : next)
				if (v.id == x.id)
					return true;
			return false;
		}

		ArrayList<V> next = new ArrayList<V>();
		int id = -1;
	}
}
