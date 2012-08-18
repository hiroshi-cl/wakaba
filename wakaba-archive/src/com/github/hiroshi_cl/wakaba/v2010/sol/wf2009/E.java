package com.github.hiroshi_cl.wakaba.v2010.sol.wf2009;

//package contest20100117E;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;

public class E {
	Scanner sc = new Scanner();

	public static void main(String[] args) {
		new E().run();
	}

	static void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void solve() {
		for (int o = 1;; o++) {
			n = sc.nextInt();
			m = sc.nextInt();
			if ((n | m) == 0)
				return;
			System.out.printf("Case %d: ", o);
			vs = new V[n];
			for (int i = 0; i < n; i++) {
				vs[i] = new V(i);
			}
			for (int i = 0; i < m; i++) {
				make(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt(), i);
			}
			init(0);
			vs[0].dfs();
			vs[n - 1].dfsrev();
			boolean multi = false;
			for (int i = 0; i < n; i++) {
				if (vs[i].multi && vs[i].multirev) {
					multi = true;
				}
			}
			if (multi) {
				System.out.println("No solution");
				continue;
			}
			int max = calcMax();
			// debug(max);
			int k = 0;
			ArrayList<Integer> is = new ArrayList<Integer>();
			ArrayList<Integer> tolls = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				if (vs[i].multi) {
					for (A e : vs[i].revs) {
						if (!e.to.multi
								&& e.to.dist + vs[i].distrev + e.dist < max) {
							e.toll = max - (e.to.dist + vs[i].distrev + e.dist);
							e.rev.toll = e.toll;
							k++;
							is.add(e.id);
							tolls.add(e.toll);
						}
					}
				}
				// else if(vs[i].multirev){
				// for(E e:vs[i].es){
				// if(e.to.distrev + vs[i].dist+e.dist < max){
				// e.toll = max - (e.to.distrev + vs[i].dist+e.dist);
				// e.rev.toll = e.toll;
				// k++;
				// is.add(e.id);
				// tolls.add(e.toll);
				// }
				// }
				// }
			}
			System.out.printf("%d %d%n", k, max);
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < k; i++) {
				sb.append(is.get(i) + 1);
				sb.append(' ');
				sb.append(tolls.get(i));
				sb.append('\n');
				// System.out.printf("%d %d%n",is.get(i)+1,tolls.get(i));
			}
			System.out.print(sb);
		}
	}

	int calcMax() {
		PriorityQueue<A> que = new PriorityQueue<A>(100, reverseOrder());
		init(0);
		vs[0].dist = 0;
		que.offer(new A(vs[0], 0, 0));
		while (!que.isEmpty()) {
			V v = que.poll().to;
			for (A e : v.es) {
				if (e.to.dist < e.dist + v.dist) {
					e.to.dist = e.dist + v.dist;
					que.offer(e);
				}
			}
		}
		return vs[n - 1].dist;
	}

	int INF = 1 << 28;

	void init(int val) {
		for (int i = 0; i < n; i++) {
			vs[i].dist = val;
		}
	}

	int n, m;
	V[] vs;

	void make(int u, int v, int dist, int id) {
		A e = new A(vs[v], dist, id);
		A r = new A(vs[u], dist, id);
		e.rev = r;
		r.rev = e;
		vs[u].es.add(e);
		vs[v].revs.add(r);
	}

	class V {
		ArrayList<A> es = new ArrayList<A>();
		ArrayList<A> revs = new ArrayList<A>();

		int id;
		int dist;
		boolean multifirst = false;
		boolean multi = false;
		int distrev;
		boolean multirev = false;

		V(int id) {
			this.id = id;
		}

		// void calcMax(){
		// for(E e:es){
		// if(e.to.di)
		// }
		// }

		void dfs() {
			for (A e : es) {
				if (multi) {
					if (!e.to.multi) {
						e.to.multi = true;
						e.to.dfs();
					}
					// if(e.to.multifirst){
					// e.to.multifirst = false;
					// e.to.dfs();
					// }
				} else {
					int nd = dist + e.dist;
					if (e.to.dist == 0) {
						e.to.dist = nd;
						e.to.dfs();
					}
					if (e.to.dist != nd) {
						if (!e.to.multi) {
							// e.to.multifirst = true;
							e.to.multi = true;
							e.to.dfs();
						}
					} else {
					}
				}
			}
		}

		void dfsrev() {
			for (A e : revs) {
				if (multirev) {
					if (!e.to.multirev) {
						e.to.multirev = true;
						e.to.dfsrev();
					}
				} else {
					int nd = distrev + e.dist;
					if (e.to.distrev == 0) {
						e.to.distrev = nd;
						e.to.dfsrev();
					}
					if (e.to.distrev != nd) {
						if (!e.to.multirev) {
							e.to.multirev = true;
							e.to.dfsrev();
						}
					} else {
					}
				}
			}
		}
	}

	class A implements Comparable<A> {
		V to;
		int id;
		int dist;
		A rev;
		int toll;

		A(V to, int dist, int id) {
			this.to = to;
			this.dist = dist;
			this.id = id;
		}

		public int compareTo(A o) {
			return 0;
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