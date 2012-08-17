package com.github.hiroshi_cl.wakaba.v2010.lib.string.search;

import java.util.*;

/*
 * Aho-Corasick
 * 複数の文字列を、テキストから検索する。RabinKarpと異なり、文字列の長さが違ってもよい。
 * オートマトンを作る際、failure関数は、一回のbfsで構築できる。
 * 構築:O(m) 応答:O(n)
 * ただし、mは文字列長の総和、nはテキスト長。
 *
 * verified: pku3690
 */
class AhoCorasick {
	long[][] ass;
	ArrayList<V> vs;
	V root;

	AhoCorasick(long[][] ass) {
		this.ass = ass;
		vs = new ArrayList<V>();
		build();
	}

	void build() {// O(m)
		vs.add(root = new V(-1));
		for (int id = 0; id < ass.length; id++) {
			V v = root;
			for (long a : ass[id]) {
				if (v.g.containsKey(a))
					v = v.g.get(a);
				else {
					V nv = new V(a);
					v.g.put(a, nv);
					vs.add(v = nv);
				}
			}
			v.out.add(id);
		}
		bfs();
	}

	void bfs() {
		Queue<V> q = new LinkedList<V>();
		for (V v : root.g.values()) {
			v.f = root;
			q.offer(v);
		}
		while (!q.isEmpty()) {
			V r = q.poll();
			loop: for (V u : r.g.values())
				if (u != null) {
					q.offer(u);
					V v = r.f;
					while (!v.g.containsKey(u.a)) {
						v = v.f;
						if (v == null) {
							u.f = root;
							continue loop;
						}
					}
					u.f = v == null ? root : v.g.get(u.a);
					u.out.addAll(u.f.out);
				}
		}
	}

	int searchFrom(long[] ts) {// O(n+m)
		V q = root;
		int res = 0;
		for (int i = 0; i < ts.length; i++) {
			long t = ts[i];
			while (!q.g.containsKey(t) && q != root)
				q = q.f;
			if (q == root && !q.g.containsKey(t))
				q = root;
			else
				q = q.g.get(t);
			if (q.out.size() > 0) {
				res += q.out.size();
			}
		}
		return res;
	}

	class V {
		long a;
		V f;
		HashMap<Long, V> g = new HashMap<Long, V>();
		TreeSet<Integer> out = new TreeSet<Integer>();

		V(long a) {
			this.a = a;
		}
	}
}