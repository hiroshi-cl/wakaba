package com.github.hiroshi_cl.wakaba.v2009.lib.graphTheory;

import java.util.*;

/*
 * トポロジカル順序： v[i]　から v[j] に辺があるならば　i < j
 * Tsort は、有向閉路が存在する場合はnull を返し、存在しない場合は、
 * トポロジカル順序によってソートした結果を返す。
 * 深さ優先探索して、帰りがけに順序を割り振ればよい。
 * 
 * O(E)
 * 
 * verified : UVa10305(Ordering Tasks)
 */

class TSort {
	V[] sorted;
	int n;

	V[] sort(V[] vs) {
		n = vs.length;
		sorted = new V[n];
		for (V v : vs) {
			if (v.state == 0 && !dfs(v))
				return null;
		}
		return sorted;
	}

	// 0:まだ訪れてない,1:もう訪れたけど、最後まで達していない,2:順序を割り振った
	boolean dfs(V v) {
		v.state = 1;
		for (V u : v.es) {
			if (u.state == 1 || u.state == 0 && !dfs(u))
				return false;
		}
		sorted[--n] = v;
		v.state = 2;
		return true;
	}

	class V {
		ArrayList<V> es = new ArrayList<V>();
		int state = 0;
	}
}