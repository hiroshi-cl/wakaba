package com.github.hiroshi_cl.wakaba.v2010.sol.d2009;

import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class D {
	void debug(Object... o) {
		// System.err.println(deepToString(o));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("D"));
			System.setOut(new PrintStream("D.out"));
		} catch (Exception e) {
		}
		for (;;) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n + m == 0)
				return;
			vs = new V[n][31][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j <= 30; j++)
					for (int k = 0; k < n; k++)
						vs[i][j][k] = new V(i, j, k);
			int start = sc.nextInt() - 1;
			int goal = sc.nextInt() - 1;
			// s = vs[sc.nextInt() - 1][1];
			// g = vs[sc.nextInt() - 1][1];
			int[] xs = new int[m * 2];
			int[] ys = new int[m * 2];
			int[] ds = new int[m * 2];
			int[] cs = new int[m * 2];
			for (int i = 0; i < m; i++) {
				xs[i] = sc.nextInt() - 1;
				ys[i] = sc.nextInt() - 1;
				xs[m + i] = ys[i];
				ys[m + i] = xs[i];
				ds[i] = sc.nextInt();
				cs[i] = sc.nextInt();
				ds[m + i] = ds[i];
				cs[m + i] = cs[i];
			}

			for (int i = 0; i < m * 2; i++) {
				for (int j = 1; j <= 30; j++) {
					for (int k = 0; k < m * 2; k++) {
						if (ys[i] != xs[k])
							continue;
						int x = xs[k], y = ys[k];
						int c = cs[k], d = ds[k];
						if (j > 1) {
							if (j - 1 <= c) {
								vs[x][j][xs[i]].es.add(new E(vs[x][j][xs[i]],
										vs[y][j - 1][x], (double) d / (j - 1)));
								// vs[y][j].es.add(new
								// E(vs[y][j],vs[x][j-1],(double)d/(j-1)));
							}
						}
						if (j < 30) {
							if (j + 1 <= c) {
								vs[x][j][xs[i]].es.add(new E(vs[x][j][xs[i]],
										vs[y][j + 1][x], (double) d / (j + 1)));
								// vs[y][j].es.add(new
								// E(vs[y][j],vs[x][j+1],(double)d/(j+1)));
							}
						}
						if (j <= c) {
							vs[x][j][xs[i]].es.add(new E(vs[x][j][xs[i]],
									vs[y][j][x], (double) d / (j)));
							// vs[y][j].es.add(new
							// E(vs[y][j],vs[x][j],(double)d/(j)));
						}
					}
				}
			}
			Queue<E> que = new PriorityQueue<E>();
			for (int i = 0; i < n; i++)
				for (E e : vs[start][1][i].es) {
					if (e.to.v == 1) {
						e.to.cost = e.cost;
						que.offer(new E(vs[start][1][i], e.to, 0));
					}
				}
			// debug(que);
			double res = INF;
			while (!que.isEmpty()) {
				E e = que.poll();
				// debug(e.to);
				if (e.to.id == goal && e.to.v == 1) {
					res = min(res, e.to.cost);
				}
				for (E ne : e.to.es) {
					if (e.from.id == ne.to.id)
						continue;
					if (ne.to.cost > ne.cost + e.to.cost) {
						ne.to.cost = ne.cost + e.to.cost;
						que.offer(ne);
					}
				}
			}
			System.out.println(res == INF ? "unreachable" : res);
		}
	}

	int INF = 1 << 27;
	// V s, g;

	V[][][] vs;

	class V {
		// @Override
		int bef;

		public String toString() {
			// TODO 自動生成されたメソッド・スタブ
			return id + " " + v;
		}

		public boolean equals(Object obj) {
			V nv = (V) obj;
			return id == nv.id && v == nv.v;
		}

		double cost = INF;
		int id, v;
		ArrayList<E> es = new ArrayList<E>();

		V(int id, int v, int bef) {
			this.id = id;
			this.v = v;
			this.bef = bef;
		}
	}

	class E implements Comparable<E> {
		V from;
		V to;
		double cost;

		E(V from, V to, double cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		public int compareTo(E o) {
			return (int) signum(cost - o.cost);
		}
	}

	public static void main(String[] args) {
		new D().run();
	}
}
