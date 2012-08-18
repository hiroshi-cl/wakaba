package com.github.hiroshi_cl.wakaba.v2010.sol.srm;
import java.util.*;
import java.math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class WebsiteRank {
	public long countVotes(String[] votes, String website) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		ArrayList<Integer>[] lists = new ArrayList[5000];
		for (int i = 0; i < 5000; i++) {
			lists[i] = new ArrayList<Integer>();
		}
		int n = 0;
		for (String vote : votes) {
			String[] ss = vote.split(" ");
			if (!map.containsKey(ss[0])) map.put(ss[0], n++);
			for (String s : ss) {
				if (!map.containsKey(s)) {
					map.put(s, n++);
				}
				lists[map.get(ss[0])].add(map.get(s));
			}
		}
		SCC scc = new SCC(n);
		for (int i = 0; i < n; i++) {
			for (int k : lists[i]) {
				scc.make(k, i);
			}
		}
		scc.build();
		scc.assignVote();
		// scc.show();
		return scc.vs[map.get(website)].vote;
	}

	class SCC {
		void show() {
			for (V v : vs) {
				debug(v.vote);
			}
		}
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
		void makeDag() {
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
		void assignVote() {
			for (V v : sorted) {
				for (V u : v.es) {
					if (u.comp != v.comp) {
						u.vote += v.vote;
					}
				}
			}
		}
		void build() {
			init();
			n = vs.length;
			sorted = new V[n];
			for (V v : vs)
				if (!v.visit) dfs(v);
			for (V v : vs)
				v.visit = false;
			for (V u : sorted)
				if (!u.visit) dfsrev(u, n++);
		}
		void dfs(V v) {
			v.visit = true;
			for (V u : v.es)
				if (!u.visit) dfs(u);
			sorted[--n] = v;
		}
		void dfsrev(V v, int k) {
			v.visit = true;
			for (V u : v.rev)
				if (!u.visit) dfsrev(u, k);
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
			long vote = 1;
			int comp;
			void init() {
				visit = false;
				comp = 0;
			}
		}
	}
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}
	int INF = 1 << 30;
	double EPS = 1e-9;
	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}

}


// Powered by FileEdit
// Powered by CodeProcessor
