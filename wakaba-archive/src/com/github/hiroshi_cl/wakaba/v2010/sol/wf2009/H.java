package com.github.hiroshi_cl.wakaba.v2010.sol.wf2009;


//package contest20100117H;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;

public class H {
	Scanner sc = new Scanner();
	public static void main(String[] args) {
		new H().run();
	}
	static void debug(Object... os) {
//		System.err.println(deepToString(os));
	}
	void solve() {
		for (int o = 1;; o++) {
			int m = sc.nextInt(), n = sc.nextInt();
			if (m == 0 && n == 0)
				return;
			System.out.printf("Case %d: ", o);
			LinkedList<Pair> list = new LinkedList<Pair>();
			for (int i = 0; i < n; i++) {
				int k = sc.nextInt();
				if (k <= 2) {
					for (int j = 0; j < k; j++) {
						int id = var(sc.nextInt() - 1);
						boolean b = sc.next().charAt(0) == 'y';
						if (!b)
							id = not(id);
						list.add(new Pair(id, id));
					}
				} else {
					int[] ids = new int[k];
					for (int j = 0; j < k; j++) {
						int id = var(sc.nextInt() - 1);
						boolean b = sc.next().charAt(0) == 'y';
						if (!b)
							id = not(id);
						ids[j] = id;
					}
					for (int j = 0; j < k; j++) {
						for (int j2 = j + 1; j2 < k; j2++) {
							list.add(new Pair(ids[j], ids[j2]));
						}
					}
				}
			}
			if (!two_satisfiability(m, list.toArray(new Pair[0]))) {
				System.out.println("impossible");
				continue;
			}
			list.add(null);
			Pair[] ps = list.toArray(new Pair[0]);
			debug(ps);
			int len = ps.length;
			char[] res = new char[m];
			for (int i = 0; i < m; i++) {
				ps[len - 1] = new Pair(var(i), var(i));
				boolean yes = two_satisfiability(m, ps);
				ps[len - 1] = new Pair(not(var(i)), not(var(i)));
				boolean no = two_satisfiability(m, ps);
				if (yes && no) {
					res[i] = '?';
				} else if (yes && !no) {
					res[i] = 'y';
				} else if (!yes && no) {
					res[i] = 'n';
				} else {
					debug("ERROR!");
				}
			}
			System.out.println(new String(res));
		}
	}
	boolean two_satisfiability(int m, Pair[] ps) {
		int n = 2 * m;
		SCC scc = new SCC(n);
		for (int i = 0; i < ps.length; i++) {
			int u = ps[i].x, v = ps[i].y;
			scc.make(not(u), v);
			scc.make(not(v), u);
		}
		scc.build();
		for (int i = 0; i < m; i++) {
			if (scc.vs[var(i)].comp == scc.vs[not(var(i))].comp)
				return false;
		}
		return true;
	}
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
				dag[v.comp].num++;
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
			int num;
			void init() {
				visit = false;
				comp = 0;
				num = 0; // for dag
			}
		}
	}

	int var(int x) {
		return x << 1;
	}
	int not(int x) {
		return x ^ 1;
	}
	class Pair {
		public String toString() {
			return x+" "+y;
		}
		int x, y;
		Pair(int x_, int y_) {
			x = x_;
			y = y_;
		}
	}
	void run() {
		solve();
	}
	class Scanner {
		int nextInt() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextInt();
				int res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		long nextLong() {
			try {
				int c = System.in.read();
				while (c != '-' && (c < '0' || '9' < c))
					c = System.in.read();
				if (c == '-')
					return -nextLong();
				long res = 0;
				do {
					res *= 10;
					res += c - '0';
					c = System.in.read();
				} while ('0' <= c && c <= '9');
				return res;
			} catch (Exception e) {
				return -1;
			}
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		String next() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (Character.isWhitespace(c))
					c = System.in.read();
				do {
					res.append((char) c);
				} while (!Character.isWhitespace(c = System.in.read()));
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
		String nextLine() {
			try {
				StringBuilder res = new StringBuilder("");
				int c = System.in.read();
				while (c == '\r' || c == '\n')
					c = System.in.read();
				do {
					res.append((char) c);
					c = System.in.read();
				} while (c != '\r' && c != '\n');
				return res.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}
}